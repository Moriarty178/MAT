package com.example.mat_backend.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.nio.charset.StandardCharsets;

@Configuration
public class RedisConfig {

    @Bean
    public RedisConnectionFactory redisConnectionFactoryRunFlow() {
        return new LettuceConnectionFactory("localhost", 890);
    }
    //RedisTemplate đã được cấu hình đúng để sử dụng UTF-8
    @Bean
    public RedisTemplate<String, Object> redisTemplateRunFlow() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactoryRunFlow());
        template.setDefaultSerializer(new StringRedisSerializer(StandardCharsets.UTF_8));
        template.setKeySerializer(new StringRedisSerializer(StandardCharsets.UTF_8));
        template.setValueSerializer(new StringRedisSerializer(StandardCharsets.UTF_8));
        return template;
    }

//    @Bean
//    public RedisConnectionFactory redisConnectionFactoryRestfulApi() {
//        return new LettuceConnectionFactory("localhost", 881);
//    }
//
//    @Bean
//    public RedisTemplate<String, Object> redisTemplateRestfulApi() {
//        RedisTemplate<String, Object> template = new RedisTemplate<>();
//        template.setConnectionFactory(redisConnectionFactoryRestfulApi());
//        return template;
//    }
//
//    @Bean
//    public RedisConnectionFactory redisConnectionFactoryRedisTestTool() {
//        return new LettuceConnectionFactory("localhost", 882);
//    }
//
//    @Bean
//    public RedisTemplate<String, Object> redisTemplateRedisTestTool() {
//        RedisTemplate<String, Object> template = new RedisTemplate<>();
//        template.setConnectionFactory(redisConnectionFactoryRedisTestTool());
//        return template;
//    }
//
//    @Bean
//    public RedisConnectionFactory redisConnectionFactoryTenserFlow() {
//        return new LettuceConnectionFactory("localhost", 883);
//    }
//
//    @Bean
//    public RedisTemplate<String, Object> redisTemplateTenserFlow() {
//        RedisTemplate<String, Object> template = new RedisTemplate<>();
//        template.setConnectionFactory(redisConnectionFactoryTenserFlow());
//        return template;
//    }
//
//    @Bean
//    public RedisConnectionFactory redisConnectionFactoryWebSocket() {
//        return new LettuceConnectionFactory("localhost", 884);
//    }
//
//    @Bean
//    public RedisTemplate<String, Object> redisTemplateWebSocket() {
//        RedisTemplate<String, Object> template = new RedisTemplate<>();
//        template.setConnectionFactory(redisConnectionFactoryWebSocket());
//        return template;
//    }
}
