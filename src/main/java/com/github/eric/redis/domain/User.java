package com.github.eric.redis.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Eric-hu
 * @Description:
 * @create 2017-12-13 14:39
 * @Copyright: 2017 www.banmatrip.com All rights reserved.
 **/
@Data
public class User implements Serializable {

    private String name;

    private String sex;

    private String phone;

}