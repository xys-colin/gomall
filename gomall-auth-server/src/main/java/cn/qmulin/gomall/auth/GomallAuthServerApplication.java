package cn.qmulin.gomall.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@EnableRedisHttpSession//整合redis作为Session存储
public class GomallAuthServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(GomallAuthServerApplication.class, args);
    }

}
