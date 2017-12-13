package com.github.eric.redis.service.impl;

import com.github.eric.redis.domain.User;
import com.github.eric.redis.service.RedisService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;

/**
 * @author Eric-hu
 * @Description:
 * @create 2017-12-13 9:54
 * @Copyright: 2017 www.banmatrip.com All rights reserved.
 **/

@Service
public class RedisServiceImp implements RedisService {

    @Autowired
    RedisTemplate<String,Object> redisTemplate;

    @Autowired
    Gson gson;

    @Override
    public void saveValue(Map<String,Map<?,?>> map) {

        ValueOperations<String,Object> valueOperations = redisTemplate.opsForValue();

        for(Map.Entry<String,Map<?,?>> entry:map.entrySet()){

            String jsonStringFromMap = gson.toJson(entry.getValue());
            valueOperations.set(entry.getKey(),jsonStringFromMap);
        }
    }

    @Override
    public String getValue(String key) {
        ValueOperations<String,Object> valueOperations = redisTemplate.opsForValue();

        Set<String> keyList = redisTemplate.keys("*");

        return (String) valueOperations.get(key);
    }

    @Override
    public void saveUser(String key,User user) {
        ValueOperations<String,Object> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(key,user);
    }
}