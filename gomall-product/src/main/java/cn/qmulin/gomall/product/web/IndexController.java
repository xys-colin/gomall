package cn.qmulin.gomall.product.web;

import cn.qmulin.gomall.product.entity.CategoryEntity;
import cn.qmulin.gomall.product.service.CategoryService;
import cn.qmulin.gomall.product.vo.Catelog2Vo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: xys
 * @date: 2022/6/25 0:39
 */
@Controller
public class IndexController {
    @Autowired
    CategoryService categoryService;


//    @Autowired
//    RedissonClient redisson;
//
//    @Autowired
//    StringRedisTemplate redisTemplate;


    @GetMapping({"/", "/index.html"})
    public String indexPage(Model model) {
        // 1 查询所有的1级分类
        List<CategoryEntity> categoryEntities = categoryService.getLevel1Categorys();
        model.addAttribute("categorys", categoryEntities);
        return "index";
    }

    @ResponseBody
    @GetMapping("/index/catalog.json")
    public Map<String, List<Catelog2Vo>> getCatalogJson() {

        Map<String, List<Catelog2Vo>> catalogJson = categoryService.getCatalogJson();
        return catalogJson;

    }
//
//    @ResponseBody
//    @GetMapping("/hello")
//    public String hello() {
//        //1 获取一把锁，只要锁的名字一样，就是同一把锁
//        RLock lock = redisson.getLock("my-lock");
//        // 2 加锁
//        lock.lock(); // 阻塞式等待，默认加的锁都是30s
//        // 锁的自动续期，如果业务超长，运行期间自动给锁续上新的30s。不用担心业务时间长，锁自动过期被删除
//        // 加锁的业务只要运行完成，就不会给当前锁续期，即使不手动解锁，锁默认在30s以后自动删除
//        lock.lock(10, TimeUnit.SECONDS); //10s自动解锁，自动解锁时间一定要大于业务的执行时间
////        lock.lock(10, TimeUnit.SECONDS); // 锁时间到了之后，不会自动续期
//        try {
//            System.out.println("加锁成功，执行业务" + Thread.currentThread().getId());
//            Thread.sleep(30000);
//        } catch (Exception e) {
//
//        }finally {
//            // 3 解锁
//            System.out.println("释放锁" + Thread.currentThread().getId());
//            lock.unlock();
//        }
//        return "hello";
//    }
//
//    @GetMapping("/write")
//    @ResponseBody
//    public String writeValue() {
//        RReadWriteLock lock = redisson.getReadWriteLock("rw-lock");
//        String s = "";
//        RLock rLock = lock.writeLock();
//        try {
//            rLock.lock();
//            System.out.println("nihao");
//            s = UUID.randomUUID().toString();
//            Thread.sleep(30000);
//            redisTemplate.opsForValue().set("writeValue", s);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }finally {
//            rLock.unlock();
//        }
//        return s;
//    }
//
//    @GetMapping("/read")
//    @ResponseBody
//    public String readValue() {
//        RReadWriteLock lock = redisson.getReadWriteLock("rw-lock");
//        String s = "";
//        RLock rLock = lock.readLock();
//        try {
//            rLock.lock();
//            s = redisTemplate.opsForValue().get("writeValue");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }finally {
//            rLock.unlock();
//        }
//        return s;
//    }
}
