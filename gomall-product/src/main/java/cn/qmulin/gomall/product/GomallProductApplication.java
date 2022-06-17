package cn.qmulin.gomall.product;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("cn.qmulin.gomall.product.dao")
public class GomallProductApplication {

    public static void main(String[] args) {
        SpringApplication.run(GomallProductApplication.class, args);
    }

}
