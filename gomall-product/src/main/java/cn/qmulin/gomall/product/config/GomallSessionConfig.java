package cn.qmulin.gomall.product.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.session.web.http.CookieSerializer;
import org.springframework.session.web.http.DefaultCookieSerializer;

/**
 * @description:
 * @author: xys
 * @date: 2022/6/30 11:48
 */
@Configuration
public class GomallSessionConfig {
    @Bean
    public CookieSerializer cookieSerializer(){
        DefaultCookieSerializer cookieSerializer = new DefaultCookieSerializer();
        cookieSerializer.setDomainName(".gomall.com");
        cookieSerializer.setCookieName("GO-SESSION");
        return cookieSerializer;
    }
    @Bean
    public RedisSerializer<Object> redisSerializer(){
        return new Jackson2JsonRedisSerializer<>(Object.class);
    }
}
