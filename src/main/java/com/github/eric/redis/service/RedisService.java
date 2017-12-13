package com.github.eric.redis.service;

import com.github.eric.redis.domain.User;

import java.util.Map;

public interface RedisService {
    public void saveValue(Map<String,Map<?,?>> map);

    public String getValue(String key);

    public void saveUser(String key,User user);
}
