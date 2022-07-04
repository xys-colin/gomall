package cn.qmulin.gomall.order.config;

import cn.qmulin.gomall.order.interceptor.LoginUserInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @description:
 * @author: xys
 * @date: 2022/7/2 16:00
 */
@Configuration
public class OrderWebConfig implements WebMvcConfigurer {
    @Autowired
    LoginUserInterceptor loginUserInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginUserInterceptor).addPathPatterns("/**");
    }
}
