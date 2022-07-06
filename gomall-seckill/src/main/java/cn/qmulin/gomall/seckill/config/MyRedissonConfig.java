package cn.qmulin.gomall.seckill.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @description:
 * @author: xys
 * @date: 2022/6/26 0:02
 */
@Configuration
public class MyRedissonConfig {

    @Bean(destroyMethod = "shutdown")
    public RedissonClient redisson(){
        Config config = new Config();
        //配置单节点
        config.useSingleServer().setAddress("redis://39.99.246.104:6379").setPassword("990326bai");
        return Redisson.create(config);
    }
}
