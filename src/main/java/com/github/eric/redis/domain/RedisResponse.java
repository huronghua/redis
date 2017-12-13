package com.github.eric.redis.domain;

import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Eric-hu
 * @Description:
 * @create 2017-12-13 10:28
 * @Copyright: 2017 www.banmatrip.com All rights reserved.
 **/
@Data
@AllArgsConstructor
public class RedisResponse {

    private String code;

    private String msg;

    private String data;

}