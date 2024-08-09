package com.example.mat_backend.Config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.nio.charset.StandardCharsets;

@Configuration
public class RedisConfig {

    @Value("${redis.run_flow.host}")
    private String runFlowHost;

    @Value("${redis.run_flow.port}")
    private int runFlowPort;

    @Value("${redis.restfull_api.host}")
    private String restfullApiHost;

    @Value("${redis.restfull_api.port}")
    private int restfullApiPort;

    @Value("${redis.test_tool.host}")
    private String testToolHost;

    @Value("${redis.test_tool.port}")
    private int testToolPort;

    @Value("${redis.tenser_flow.host}")
    private String tenserFlowHost;

    @Value("${redis.tenser_flow.port}")
    private int tenserFlowPort;

    @Bean
    public RedisConnectionFactory redisConnectionFactoryRunFlow() {
        return new LettuceConnectionFactory(runFlowHost, runFlowPort);
    }

    @Bean(name = "redisTemplateRunFlow")
    public RedisTemplate<String, Object> redisTemplateRunFlow() {
        return createRedisTemplate(redisConnectionFactoryRunFlow());
    }

    @Bean
    public RedisConnectionFactory redisConnectionFactoryRestfulApi() {
        return new LettuceConnectionFactory(restfullApiHost, restfullApiPort);
    }

    @Bean(name = "redisTemplateRestfulApi")
    public RedisTemplate<String, Object> redisTemplateRestfulApi() {
        return createRedisTemplate(redisConnectionFactoryRestfulApi());
    }

    @Bean
    public RedisConnectionFactory redisConnectionFactoryRedisTestTool() {
        return new LettuceConnectionFactory(testToolHost, testToolPort);
    }

    @Bean(name = "redisTemplateRedisTestTool")
    public RedisTemplate<String, Object> redisTemplateRedisTestTool() {
        return createRedisTemplate(redisConnectionFactoryRedisTestTool());
    }

    @Bean
    public RedisConnectionFactory redisConnectionFactoryTenserFlow() {
        return new LettuceConnectionFactory(tenserFlowHost, tenserFlowPort);
    }

    @Bean(name = "redisTemplateTenserFlow")
    public RedisTemplate<String, Object> redisTemplateTenserFlow() {
        return createRedisTemplate(redisConnectionFactoryTenserFlow());
    }

    //Nếu không sử dụng lớp này => bị lõi
    /*A component required a bean named 'redisTemplate' that could not be found. Consider defining a bean named 'redisTemplate' in your configuration.
    Mặc dù không có lớp nào yêu cầu "redisTemplate", các lớp yêu cầu kêt nối đến các Redis Instance đã chỉ định rõ name bằng
    NGUYÊN NHÂN:
       + Do thư viện hoặc một số module phụ thuộc vào Spring Data Redis yêu cầu (sử dụng NGẦM) một @Bean mặc định "redisTemplate"
    Giải pháp:
       + Tạo thêm một @Bean mặc định cho redisTemplate ngoài caác @Bean tương ứng với các Redis Instance khác.
    */
    //Bean RedíTemplate mặc định
    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactoryRunFlow()); // Hoặc bạn có thể chọn một connection factory khác nếu cần
        template.setDefaultSerializer(new StringRedisSerializer(StandardCharsets.UTF_8));
        template.setKeySerializer(new StringRedisSerializer(StandardCharsets.UTF_8));
        template.setValueSerializer(new StringRedisSerializer(StandardCharsets.UTF_8));
        return template;
    }

    private RedisTemplate<String, Object> createRedisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        template.setDefaultSerializer(new StringRedisSerializer(StandardCharsets.UTF_8));
        template.setKeySerializer(new StringRedisSerializer(StandardCharsets.UTF_8));
        template.setValueSerializer(new StringRedisSerializer(StandardCharsets.UTF_8));
        return template;
    }
}

