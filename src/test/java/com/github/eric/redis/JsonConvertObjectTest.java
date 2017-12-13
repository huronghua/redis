package com.github.eric.redis;

import com.github.eric.redis.domain.PurchaseInfoVO;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * @author Eric-hu
 * @Description:
 * @create 2017-12-13 16:06
 * @Copyright: 2017 www.banmatrip.com All rights reserved.
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class JsonConvertObjectTest {

    Gson gson = new Gson();

    @Test
    public void jsonToObject(){
        String jsonString = "{\n" +
                "    \"operator_id\": \"676\",\n" +
                "    \"quote_flag\": \"2\",\n" +
                "    \"info\": {\n" +
                "        \"134600329\": {\n" +
                "            \"remark\": [\n" +
                "                \"800\"\n" +
                "            ],\n" +
                "            \"booking_price\": \"800\",\n" +
                "            \"currency_id\": \"1\"\n" +
                "        },\n" +
                "        \"134600330\": {\n" +
                "            \"remark\": [\n" +
                "                \"600\"\n" +
                "            ],\n" +
                "            \"booking_price\": \"600\",\n" +
                "            \"currency_id\": \"1\"\n" +
                "        }\n" +
                "    },\n" +
                "    \"purchase_vender_id\": \"25\",\n" +
                "    \"pay_type\": \"1\",\n" +
                "    \"status\": \"2\",\n" +
                "    \"confirm_price\": 200\n" +
                "}";

        if(isGoodJson(jsonString)){
            PurchaseInfoVO purchaseInfoVO = new PurchaseInfoVO();
            Map<String,Object> purchaseInfoMap = gson.fromJson(jsonString, Map.class);
            //操作者
            String operatorId = String.valueOf(purchaseInfoMap.get("operator_id"));
            purchaseInfoVO.setOperatorId(operatorId);
            //采购单最终金额
            String confirmPrice = String.valueOf(purchaseInfoMap.get("confirm_price"));
            purchaseInfoVO.setConfirmPrice(confirmPrice);
            //采购状态
            String status = String.valueOf(purchaseInfoMap.get("status"));
            purchaseInfoVO.setStatus(status);
            //支付方式
            String payType = String.valueOf(purchaseInfoMap.get("pay_type"));
            purchaseInfoVO.setPayType(payType);
            //供应商
            String purchaseVenderId = String.valueOf(purchaseInfoMap.get("purchase_vender_id"));
            purchaseInfoVO.setPurchaseVenderId(purchaseVenderId);
            //供应商订单号
            Map<String,Object> infoMap = (Map<String, Object>) purchaseInfoMap.get("info");
            Iterator infoIterator = infoMap.entrySet().iterator();
            //准备一个用于存储供应商订单的list
            List<String> venderList = new ArrayList<>();
            while (infoIterator.hasNext()){
                Map.Entry<String,Object> infoEntry = (Map.Entry<String, Object>) infoIterator.next();
                Map<String,Object> venderMap = (Map<String, Object>) infoEntry.getValue();
                venderList.addAll((Collection<? extends String>) venderMap.get("remark"));
            }
            String remark = listToString(venderList, ',');
            purchaseInfoVO.setRemark(remark);
        }
    }

    /**
     * 判断一个字符串是否是json字符串
     * @param json
     * @return
     */
    public static boolean isGoodJson(String json) {
        if (StringUtils.isEmpty(json)) {
            return false;
        }
        try {
            new JsonParser().parse(json);
            return true;
        } catch (JsonParseException e) {
            return false;
        }
    }

    /**List转String
     * @Param List:需要转换的List
     * @Param separator:分割list元素的符号
     * **/
    public static String listToString(List list, char separator) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i)).append(separator);
        }
        return sb.toString().substring(0,sb.toString().length()-1);}
}