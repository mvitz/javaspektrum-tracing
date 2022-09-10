package de.mvitz.apm.example.backend;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.util.Map;

@Configuration(proxyBeanMethods = false)
public class RedisConfiguration {

    @Bean
    public RedisTemplate<String, Map<String, String>> sessionRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Map<String, String>> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        template.setKeySerializer(RedisSerializer.string());
        return template;
    }
}
