package cn.qmulin.gomall.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class GomallGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GomallGatewayApplication.class, args);
    }

}
