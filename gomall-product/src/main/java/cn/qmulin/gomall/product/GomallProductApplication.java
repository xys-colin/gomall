package cn.qmulin.gomall.product;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@EnableCaching//启用缓存
@SpringBootApplication
@MapperScan("cn.qmulin.gomall.product.dao")
@EnableFeignClients(basePackages = "cn.qmulin.gomall.product.feign")
@EnableRedisHttpSession//启用redis来存储session
public class GomallProductApplication {

    public static void main(String[] args) {
        SpringApplication.run(GomallProductApplication.class, args);
    }

}
