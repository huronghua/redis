package com.github.eric.redis.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Eric-hu
 * @Description:
 * @create 2017-12-13 15:53
 * @Copyright: 2017 www.banmatrip.com All rights reserved.
 **/
@Data
public class PurchaseInfoVO {

    private String confirmPrice;

    private String status;

    private String payType;

    private String purchaseVenderId;

    private String remark;

    private String operatorId;
}