package cn.qmulin.gomall.order;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableRabbit//开启RabbitMQ
@EnableDiscoveryClient
@EnableFeignClients
@EnableRedisHttpSession
@EnableTransactionManagement
public class GomallOrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(GomallOrderApplication.class, args);
    }

}
