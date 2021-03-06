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
        // 1 ??????????????????
        List<CategoryEntity> entities = baseMapper.selectList(null);
        // 2 ??????????????????????????????
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
     * ?????????????????????????????????
     * @CacheEvict(): ????????????
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
     * 1. ??????????????????????????????????????????????????????????????????????????????. ???????????????????????????????????????
     * 2. @Cacheable("category")???????????????????????????????????????,??????????????????,??????????????????;
     * ?????????????????????,???????????????,????????????????????????????????????
     * 3. ????????????
     *      key??????????????????,???????????????category::SimpleKey[]
     *      ?????????value???,????????????jdk?????????????????????redis
     *      ???????????????????????????
     * @return
     */
    @Cacheable("category")//??????????????????????????????,cacheNames???category
    @Override
    public List<CategoryEntity> getLevel1Categorys() {
        return baseMapper.selectList(new QueryWrapper<CategoryEntity>().eq("parent_cid", 0));
    }

    /**
     * ??????Redisson???????????????
     * @return
     */
    public Map<String, List<Catelog2Vo>> getCatalogJsonFromDbWithRedissonLock() {
        //1.????????????,????????????????????????, ????????????,????????????
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
     * ???Redis???key?????????????????????
     *
     * @return
     */
    public Map<String, List<Catelog2Vo>> getCatalogJsonFromDbWithRedisLock() {
        //?????????
        while (true) {
            // 1 ?????????????????????redis??????
            String uuid = UUID.randomUUID().toString();
            Boolean lock = redisTemplate.opsForValue().setIfAbsent("lock", uuid, 300, TimeUnit.SECONDS);
            if (lock) {
                // ????????????...????????????
                // 2 ??????????????????
//            redisTemplate.expire("lock", 30, TimeUnit.SECONDS);
                Map<String, List<Catelog2Vo>> dataFromDB;
                try {
                    dataFromDB = getCatalogJsonFromDB();
                } finally {
                    String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
                    // ?????????
                    Long lock1 = redisTemplate.execute(new DefaultRedisScript<Long>(script, Long.class), Arrays.asList("lock"), uuid);
                }
//            redisTemplate.delete("lock");
                // ??????????????? + ?????????????????? = ????????????  lua????????????
//            String lockValue = redisTemplate.opsForValue().get("lock");
//            if (uuid.equals(lockValue)) {
//                // ?????????????????????
//                redisTemplate.delete("lock");
//            }

                return dataFromDB;
            } else {
                // ????????????
                // ??????100ms??????
                System.out.println("????????????????????????...????????????");
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
            return categoryEntity.getParentCid().longValue() == root.getCatId().longValue();  // ?????????????????????longValue()???????????????????????????bug?????????parentCid???catId???long??????
        }).map(categoryEntity -> {
            // 1 ???????????????
            categoryEntity.setChildren(getChildren(categoryEntity, entities));
            return categoryEntity;
        }).sorted(Comparator.comparingInt(menu -> (menu.getSort() == null ? 0 : menu.getSort()))).collect(Collectors.toList());
        return children;

    }

}