package cn.qmulin.gomall.order.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @description:
 * @author: xys
 * @date: 2022/6/28 18:47
 */
@Configuration
public class MyThreadConfig {
    @Bean
    public ThreadPoolExecutor threadPoolExecutor(){
       return new ThreadPoolExecutor(20,100,10,
               TimeUnit.MINUTES,
               new LinkedBlockingQueue<>(),
               Executors.defaultThreadFactory(),
               new ThreadPoolExecutor.AbortPolicy());
    }
}
