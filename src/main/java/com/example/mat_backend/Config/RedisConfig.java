package com.example.mat_backend.Config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import javax.print.attribute.standard.RequestingUserName;
import java.nio.charset.StandardCharsets;

@Configuration
public class RedisConfig {

    @Bean
    public RedisConnectionFactory redisConnectionFactoryRunFlow() {
        return new LettuceConnectionFactory("redis", 6379);
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
//@Configuration
//public class RedisConfig {
//
//    @Value("${redis.run_flow.host}")
//    private String runFlowHost;
//
//    @Value("${redis.run_flow.port}")
//    private int runFlowPort;
//
//    @Value("${redis.restfull_api.host}")
//    private String restfullApiHost;
//
//    @Value("${redis.restfull_api.port}")
//    private int restfullApiPort;
//
//    @Value("${redis.test_tool.host}")
//    private String testToolHost;
//
//    @Value("${redis.test_tool.port}")
//    private int testToolPort;
//
//    @Value("${redis.tenser_flow.host}")
//    private String tenserFlowHost;
//
//    @Value("$redis.tenser_flow.port}")
//    private int tenserFlowPort;
//
//    //run_flow
//    @Bean
//    public RedisConnectionFactory redisConnectionFactoryRunFlow() {
////        return new LettuceConnectionFactory("redis", 6379);
//        return new LettuceConnectionFactory(runFlowHost, runFlowPort);        //"redis": địa chỉ ip của redis container <=> tên dịch vụ,ứng dụng
//    }
//    //RedisTemplate đã được cấu hình đúng để sử dụng UTF-8
//    @Bean(name = "redisTemplateRunFlow")
//    public RedisTemplate<String, Object> redisTemplateRunFlow() {
//        RedisTemplate<String, Object> template = new RedisTemplate<>();
//        template.setConnectionFactory(redisConnectionFactoryRunFlow());
//        template.setDefaultSerializer(new StringRedisSerializer(StandardCharsets.UTF_8));
//        template.setKeySerializer(new StringRedisSerializer(StandardCharsets.UTF_8));
//        template.setValueSerializer(new StringRedisSerializer(StandardCharsets.UTF_8));
//        return template;
//    }
//
//    //restfull_api
//    @Bean
//    public RedisConnectionFactory redisConnectionFactoryRestfulApi() {
//        return new LettuceConnectionFactory(restfullApiHost, restfullApiPort);
//    }
//
//    @Bean(name = "redisTemplateRestfulApi")
//    public RedisTemplate<String, Object> redisTemplateRestfulApi() {
//        RedisTemplate<String, Object> template = new RedisTemplate<>();
//        template.setConnectionFactory(redisConnectionFactoryRestfulApi());
//        template.setDefaultSerializer(new StringRedisSerializer(StandardCharsets.UTF_8));
//        template.setKeySerializer(new StringRedisSerializer(StandardCharsets.UTF_8));
//        template.setValueSerializer((new StringRedisSerializer(StandardCharsets.UTF_8)));
//        return template;
//    }
//
//    //redis_tool_test
//    @Bean
//    public RedisConnectionFactory redisConnectionFactoryRedisTestTool() {
//        return new LettuceConnectionFactory(testToolHost, testToolPort);
//    }
//
//    @Bean(name = "redisTemplateRedisTestTool")
//    public RedisTemplate<String, Object> redisTemplateRedisTestTool() {
//        RedisTemplate<String, Object> template = new RedisTemplate<>();
//        template.setConnectionFactory(redisConnectionFactoryRedisTestTool());
//        template.setDefaultSerializer(new StringRedisSerializer(StandardCharsets.UTF_8));
//        template.setKeySerializer(new StringRedisSerializer(StandardCharsets.UTF_8));
//        template.setValueSerializer(new StringRedisSerializer(StandardCharsets.UTF_8));
//        return template;
//    }
//
//    //tenser_flow
//    @Bean
//    public RedisConnectionFactory redisConnectionFactoryTenserFlow() {
//        return new LettuceConnectionFactory(tenserFlowHost, tenserFlowPort);
//    }
//
//    @Bean(name = "redisTemplateTenserFlow")
//    public RedisTemplate<String, Object> redisTemplateTenserFlow() {
//        RedisTemplate<String, Object> template = new RedisTemplate<>();
//        template.setDefaultSerializer(new StringRedisSerializer(StandardCharsets.UTF_8));
//        template.setKeySerializer(new StringRedisSerializer(StandardCharsets.UTF_8));
//        template.setValueSerializer(new StringRedisSerializer(StandardCharsets.UTF_8));
//        template.setConnectionFactory(redisConnectionFactoryTenserFlow());
//        return template;
//    }
//
////    //websocket
////    @Bean
////    public RedisConnectionFactory redisConnectionFactoryWebSocket() {
////        return new LettuceConnectionFactory("localhost", 884);
////    }
////
////    @Bean(name="redisTemplateWebSocket")
////    public RedisTemplate<String, Object> redisTemplateWebSocket() {
////        RedisTemplate<String, Object> template = new RedisTemplate<>();
////        template.setConnectionFactory(redisConnectionFactoryWebSocket());
////        template.setDefaultSerializer(new StringRedisSerializer(StandardCharsets.UTF_8));
////        template.setKeySerializer(new StringRedisSerializer(StandardCharsets.UTF_8));
////        template.setValueSerializer(new StringRedisSerializer(StandardCharsets.UTF_8));
////        return template;
////    }
}
