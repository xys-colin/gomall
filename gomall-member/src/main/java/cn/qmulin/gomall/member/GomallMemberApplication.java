package cn.qmulin.gomall.member;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "cn.qmulin.gomall.member.feign")
public class GomallMemberApplication {

    public static void main(String[] args) {
        SpringApplication.run(GomallMemberApplication.class, args);
    }

}
