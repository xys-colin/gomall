package cn.qmulin.gomall.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class GomallAuthServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(GomallAuthServerApplication.class, args);
    }

}
