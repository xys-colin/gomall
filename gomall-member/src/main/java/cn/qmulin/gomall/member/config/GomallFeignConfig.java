package cn.qmulin.gomall.member.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @description:
 * @author: xys
 * @date: 2022/7/2 18:18
 */
@Configuration
public class GomallFeignConfig {
    @Bean
    public RequestInterceptor requestInterceptor(){
        return new RequestInterceptor() {
            @Override
            public void apply(RequestTemplate requestTemplate) {
                ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
                if (attributes!=null){
                    HttpServletRequest request = attributes.getRequest();
                    if (request!=null){
                        //为feign远程调用时带上这次请求的cookie
                        requestTemplate.header("Cookie",request.getHeader("Cookie"));
                    }
                }

            }
        };
    }
}
