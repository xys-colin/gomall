package cn.qmulin.gomall.product.service.impl;

import cn.qmulin.gomall.product.service.CategoryBrandRelationService;
import cn.qmulin.gomall.product.vo.Catelog2Vo;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import org.apache.commons.lang.StringUtils;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.qmulin.common.utils.PageUtils;
import cn.qmulin.common.utils.Query;

import cn.qmulin.gomall.product.dao.CategoryDao;
import cn.qmulin.gomall.product.entity.CategoryEntity;
import cn.qmulin.gomall.product.service.CategoryService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;


@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryEntity> implements CategoryService {
    @Autowired
    CategoryBrandRelationService categoryBrandRelationService;
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private RedissonClient redissonClient;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryEntity> page = this.page(
                new Query<CategoryEntity>().getPage(params),
                new QueryWrapper<CategoryEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<CategoryEntity> listWithTree() {
        // 1 查出所有分类
        List<CategoryEntity> entities = baseMapper.selectList(null);
        // 2 组装成父子的树形结构
        List<CategoryEntity> level1Menus = entities.stream().filter(categoryEntity ->
                categoryEntity.getParentCid() == 0
        ).map((menu) -> {
            menu.setChildren(getChildren(menu, entities));
            return menu;
        }).sorted(Comparator.comparingInt(menu -> (menu.getSort() == null ? 0 : menu.getSort()))).collect(Collectors.toList());
        return level1Menus;

    }

    @Override
    public void removeMenuByIds(List<Long> asList) {
        // TODO: 2022/6/18
        baseMapper.deleteBatchIds(asList);
    }

    @Override
    public Long[] findCatelogPath(Long catelogId) {
        ArrayList<Long> paths = new ArrayList<>();
        List<Long> parentPath = findParentPath(catelogId, paths);
        Collections.reverse(parentPath);
        return parentPath.toArray(new Long[0]);
    }

    /**
     * 级联更新所有关联的数据
     * @CacheEvict(): 缓存失效
     * @param category
     */

    @Caching(evict = {@CacheEvict(value = "category",key = "'getLevel1Categorys'"),
            @CacheEvict(value = "category",key = "'catelogJson'")
    })
    @Transactional
    @Override
    public void updateCascade(CategoryEntity category) {
        this.updateById(category);
        categoryBrandRelationService.updateCategory(category.getCatId(), category.getName());
    }

    /**
     * 1. 每一个需要缓存的数据我们都来指定要放到哪个名字的缓存. 缓存的分区按照业务类型来分
     * 2. @Cacheable("category")代表当前方法的结果需要缓存,如果缓存中有,方法不用调用;
     * 如果缓存中没有,会调用方法,最后将方法的结果放入缓存
     * 3. 默认行为
     *      key默认自动生成,缓存名字为category::SimpleKey[]
     *      缓存的value值,默认使用jdk序列化机制存入redis
     *      默认过期时间为永久
     * @return
     */
    @Cacheable("category")//将方法结果放入缓存中,cacheNames为category
    @Override
    public List<CategoryEntity> getLevel1Categorys() {
        return baseMapper.selectList(new QueryWrapper<CategoryEntity>().eq("parent_cid", 0));
    }

    /**
     * 使用Redisson的分布式锁
     * @return
     */
    public Map<String, List<Catelog2Vo>> getCatalogJsonFromDbWithRedissonLock() {
        //1.锁的名字,可以决定锁的力度, 锁的粒度,越细越好
        RLock lock = redissonClient.getLock("catalogJson-lock");
        lock.lock();
        Map<String, List<Catelog2Vo>> dataFromDB;
        try {
            dataFromDB = getCatalogJsonFromDB();
        } finally {
            lock.unlock();
        }
        return dataFromDB;

    }

    /**
     * 用Redis存key来实现分布式锁
     *
     * @return
     */
    public Map<String, List<Catelog2Vo>> getCatalogJsonFromDbWithRedisLock() {
        //锁自旋
        while (true) {
            // 1 占分布式锁，去redis占坑
            String uuid = UUID.randomUUID().toString();
            Boolean lock = redisTemplate.opsForValue().setIfAbsent("lock", uuid, 300, TimeUnit.SECONDS);
            if (lock) {
                // 加锁成功...执行业务
                // 2 设置过期时间
//            redisTemplate.expire("lock", 30, TimeUnit.SECONDS);
                Map<String, List<Catelog2Vo>> dataFromDB;
                try {
                    dataFromDB = getCatalogJson();
                } finally {
                    String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
                    // 删除锁
                    Long lock1 = redisTemplate.execute(new DefaultRedisScript<Long>(script, Long.class), Arrays.asList("lock"), uuid);
                }
//            redisTemplate.delete("lock");
                // 获取值对比 + 对比成功删除 = 原子操作  lua脚本解锁
//            String lockValue = redisTemplate.opsForValue().get("lock");
//            if (uuid.equals(lockValue)) {
//                // 删除我自己的锁
//                redisTemplate.delete("lock");
//            }

                return dataFromDB;
            } else {
                // 加锁失败
                // 休眠100ms重试
                System.out.println("获取分布式锁失败...等待重试");
                try {
                    Thread.sleep(200);
                } catch (Exception e) {

                }
            }
        }
    }

    public Map<String, List<Catelog2Vo>> getCatalogJson() {
        String catalogJson = redisTemplate.opsForValue().get("catalogJson");
        if (StringUtils.isNotEmpty(catalogJson)) {
            return JSON.parseObject(catalogJson,
                    new TypeReference<Map<String, List<Catelog2Vo>>>() {});
        }
        Map<String, List<Catelog2Vo>> catalogJsonFromDB = getCatalogJsonFromDbWithRedissonLock();

        return catalogJsonFromDB;
    }

    public Map<String, List<Catelog2Vo>> getCatalogJsonFromDB() {
        List<CategoryEntity> entities = baseMapper.selectList(null);
        List<CategoryEntity> level1Categorys = getParentCid(entities, 0L);
        Map<String, List<Catelog2Vo>> parent_cid = level1Categorys.stream().collect(Collectors.toMap(k -> k.getCatId().toString(), v -> {
            List<CategoryEntity> categoryEntities = getParentCid(entities, v.getCatId());
            List<Catelog2Vo> catelog2Vos = null;
            if (!CollectionUtils.isEmpty(categoryEntities)) {
                catelog2Vos = categoryEntities.stream().map(item -> {
                    Catelog2Vo catelog2Vo = new Catelog2Vo(v.getCatId().toString(), null, item.getCatId().toString(), item.getName());
                    List<CategoryEntity> level3 = getParentCid(entities, item.getCatId());
                    if (!CollectionUtils.isEmpty(level3)) {
                        List<Catelog2Vo.Catelog3Vo> catelog3Vos = level3.stream().map(categoryEntity -> {
                            Catelog2Vo.Catelog3Vo catelog3Vo = new Catelog2Vo.Catelog3Vo(item.getCatId().toString(), categoryEntity.getCatId().toString(), categoryEntity.getName());
                            return catelog3Vo;
                        }).collect(Collectors.toList());
                        catelog2Vo.setCatalog3List(catelog3Vos);
                    }
                    return catelog2Vo;
                }).collect(Collectors.toList());
            }
            return catelog2Vos;
        }));
        String s = JSON.toJSONString(parent_cid);
        redisTemplate.opsForValue().set("catalogJson", s, 1, TimeUnit.DAYS);
        return parent_cid;
    }

    private List<CategoryEntity> getParentCid(List<CategoryEntity> entities, Long parentCid) {
        return entities.stream().filter(item -> Objects.equals(item.getParentCid(), parentCid)).collect(Collectors.toList());
    }

    private List<Long> findParentPath(Long catelogId, ArrayList<Long> paths) {
        paths.add(catelogId);
        CategoryEntity byId = this.getById(catelogId);
        if (byId.getParentCid() != 0) {
            findParentPath(byId.getParentCid(), paths);
        }
        return paths;
    }


    private List<CategoryEntity> getChildren(CategoryEntity root, List<CategoryEntity> entities) {
        List<CategoryEntity> children = entities.stream().filter(categoryEntity -> {
            return categoryEntity.getParentCid().longValue() == root.getCatId().longValue();  // 注意此处应该用longValue()来比较，否则会出先bug，因为parentCid和catId是long类型
        }).map(categoryEntity -> {
            // 1 找到子菜单
            categoryEntity.setChildren(getChildren(categoryEntity, entities));
            return categoryEntity;
        }).sorted(Comparator.comparingInt(menu -> (menu.getSort() == null ? 0 : menu.getSort()))).collect(Collectors.toList());
        return children;

    }

}