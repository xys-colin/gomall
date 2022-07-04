package cn.qmulin.gomall.order;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableRabbit//开启RabbitMQ
@EnableDiscoveryClient
@EnableFeignClients
public class GomallOrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(GomallOrderApplication.class, args);
    }

}
