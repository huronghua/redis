package com.github.eric.redis.controller;

import com.github.eric.redis.domain.RedisResponse;
import com.github.eric.redis.domain.User;
import com.github.eric.redis.service.RedisService;
import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Eric-hu
 * @Description:
 * @create 2017-12-13 10:26
 * @Copyright: 2017 www.banmatrip.com All rights reserved.
 **/

@Controller
public class RedisController {

    @Autowired
    RedisService redisService;

    Gson gson = new Gson();

    @RequestMapping(value = "/save",method = RequestMethod.GET)
    @ResponseBody
    public RedisResponse setValue(){
        Map<String,Map<?,?>> map = new HashMap<>();
        Map<String,String> map2 = new HashMap<>();
        map2.put("name","huronghua");
        map2.put("phone","17326122759");
        map.put("member",map2);
        redisService.saveValue(map);
        return new RedisResponse("200","存入redis缓存成功",gson.toJson(map));
    }

    @RequestMapping(value = "/get",method = RequestMethod.GET)
    @ResponseBody
    public String getValue(){
        String key = "member";
        return redisService.getValue(key);
    }

    @RequestMapping(value = "/saveUser",method = RequestMethod.GET)
    @ResponseBody
    public RedisResponse saveUser(){
        User user = new User();
        user.setName("huronghua");
        user.setPhone("17326122759");
        user.setSex("男");
        redisService.saveUser("user",user);
        return new RedisResponse("200","存入redis缓存成功",gson.toJson(user));
    }
}