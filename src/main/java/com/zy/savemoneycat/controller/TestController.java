package com.zy.savemoneycat.controller;

import com.alibaba.fastjson.JSONObject;
import com.zy.savemoneycat.utils.PddUtil;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {
    @RequestMapping("/hello")
    public JSONObject hello(@RequestBody JSONObject params) {
        JSONObject res = new JSONObject();
        res.put("goodsSign", PddUtil.getGoodsSign(params.getString("goodsId")));
        return res;
    }

    @RequestMapping("/getPromotion")
    public JSONObject getPromotion(@RequestBody JSONObject params) {
        JSONObject res = new JSONObject();
        params.put("goods_sign_list", "[\"" + PddUtil.getGoodsSign(params.getString("goodsId")) + "\"]");
        res = JSONObject.parseObject(PddUtil.getPromotion(params));
        return res;
    }
}
