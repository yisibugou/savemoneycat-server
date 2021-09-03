package com.zy.savemoneycat.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.util.DigestUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

public class PddUtil {
    private static final String CLIENT_ID = "5f4a9e7408904a4cad0deb562abe2119";
    private static final String CLIENT_SECRET = "f7565af500801223deb999a90a6765233ff4002d";
    private static final String URL = "https://gw-api.pinduoduo.com/api/router";
    /**
     * 拼多多搜索商品
     * @param params
     * @return
     */
    public static String searchGoods(JSONObject params) {
        String result = "";
        CloseableHttpClient httpclient = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(URL);
        try {
            params.put("type", "pdd.ddk.goods.search");
            params.put("client_id", CLIENT_ID);
            params.put("timestamp", System.currentTimeMillis() / 1000);
            params.put("pid", "8718647_219283366");
            params.put("sign", getSign(params));
            StringEntity s = new StringEntity(params.toJSONString());
            s.setContentEncoding("utf-8");
            s.setContentType("application/json");
            post.setEntity(s);
            HttpResponse res = httpclient.execute(post);
            result = EntityUtils.toString(res.getEntity());
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    /**
     * 拼多多获取goodsSign
     * @param goodsId
     * @return
     */
    public static String getGoodsSign(String goodsId) {
        String goodsSign = "";
        JSONObject params = new JSONObject();
        params.put("keyword", goodsId);
        String result = searchGoods(params);
        JSONObject resultJson = JSON.parseObject(result);
        JSONObject response = resultJson.getJSONObject("goods_search_response");
        JSONArray goodsList = response.getJSONArray("goods_list");
        goodsSign = goodsList.getJSONObject(0).getString("goods_sign");
        return goodsSign;
    }
    /**
     * 拼多多请求需要的签名
     * @param params
     * @return
     */
    public static String getSign(JSONObject params) {
        String sign = "";
        try {
            // 按key的ASCII码升序
            Map<String, Object> sortMap = new TreeMap<String, Object>(new Comparator<String>() {
                public int compare(String obj1, String obj2) {
                    return obj1.compareTo(obj2);//升序排序
                }
            });
            sortMap.putAll(params);

            // 拼接
            StringBuilder paramsBuilder = new StringBuilder();
            paramsBuilder.append(CLIENT_SECRET);
            for (Map.Entry<String, Object> param : sortMap.entrySet()) {
                paramsBuilder.append(param.getKey()).append(param.getValue());
            }
            paramsBuilder.append(CLIENT_SECRET);

            // MD5
            sign =  DigestUtils.md5DigestAsHex(paramsBuilder.toString().getBytes()).toUpperCase();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sign;
    }

    /**
     * 拼多多获取推广链接
     * @param params
     * @return
     */
    public static String getPromotion(JSONObject params) {
        String result = "";
        CloseableHttpClient httpclient = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(URL);
        try {
            params.put("type", "pdd.ddk.goods.promotion.url.generate");
            params.put("client_id", CLIENT_ID);
            params.put("timestamp", System.currentTimeMillis() / 1000);
            params.put("p_id", "8718647_219283366");
            params.put("sign", getSign(params));
            StringEntity s = new StringEntity(params.toJSONString());
            s.setContentEncoding("utf-8");
            s.setContentType("application/json");
            post.setEntity(s);
            HttpResponse res = httpclient.execute(post);
            result = EntityUtils.toString(res.getEntity());
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
