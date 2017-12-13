package com.github.eric.redis.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

import java.lang.reflect.Method;

/**
 * @author Eric-hu
 * @Description:
 * @create 2017-12-12 16:46
 * @Copyright: 2017 www.banmatrip.com All rights reserved.
 **/

@Configuration
@EnableAutoConfiguration
@EnableCaching
public class RedisConfig extends CachingConfigurerSupport {

    @Autowired
    private Environment environment;

    @Bean
    public KeyGenerator wiselyKeyGenerator(){
        return new KeyGenerator() {
            @Override
            public Object generate(Object target, Method method, Object... params) {
                StringBuilder sb = new StringBuilder();
                sb.append(target.getClass().getName());
                sb.append(method.getName());
                for (Object obj : params) {
                    sb.append(obj.toString());
                }
                return sb.toString();
            }
        };
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.redis")
    public JedisPoolConfig getRedisConfig(){
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        System.out.println("jedis pool配置成功"+jedisPoolConfig.getMaxWaitMillis());
        return jedisPoolConfig;
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.redis")
    public JedisConnectionFactory getConnectionFactory(){
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
        JedisPoolConfig jedisPoolConfig = getRedisConfig();
        //
        jedisConnectionFactory.setPoolConfig(jedisPoolConfig);
        jedisConnectionFactory.setHostName(environment.getProperty("spring.redis.host"));
        jedisConnectionFactory.setPort(Integer.parseInt(environment.getProperty("spring.redis.port").trim()));
        jedisConnectionFactory.setDatabase(Integer.parseInt(environment.getProperty("spring.redis.database").trim()));
        System.out.println("jedis connection配置成功" + jedisConnectionFactory.getHostName());
        return jedisConnectionFactory;
    }

    @Bean
    public RedisTemplate<String,Object> getRedisTemplate(){
        RedisTemplate<String,Object> redisTemplate = new RedisTemplate<>();
        //配置连接工厂
        redisTemplate.setConnectionFactory(getConnectionFactory());
        //配置默认的序列化规则
        redisTemplate.setDefaultSerializer(getJackson2JsonRedisSerializer());
        //用于初始化@Bean redisTemplate
        redisTemplate.afterPropertiesSet();
        redisTemplate.setValueSerializer(getJackson2JsonRedisSerializer());
        System.out.println("redis template配置成功" + redisTemplate.getClientList());
        return redisTemplate;
    }

    @Bean
    public Jackson2JsonRedisSerializer getJackson2JsonRedisSerializer() {

        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        return jackson2JsonRedisSerializer;
    }

}