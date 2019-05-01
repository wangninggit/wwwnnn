package com.viathink.core.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import okhttp3.*;

public class ExpressTrackingUtil {

    private static final OkHttpClient client = new OkHttpClient();
    private static final String url = "http://express.woyueche.com/query.action";
    private static final String hearKey = "Authorization";
    private static final String expressKey = "express";
    private static final String trackingNoKey = "trackingNo";

    public static JSONObject expressTracking(String express, String trackingNo,String hearValue) {

        RequestBody formBody = new FormBody.Builder()
                .add(expressKey, express)
                .add(trackingNoKey, trackingNo)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .addHeader(hearKey, hearValue)
                .addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
                .post(formBody)
                .build();

        try{
            Response response = client.newCall(request).execute();
            if (!response.isSuccessful())
                throw new RuntimeException("物流信息请求失败 ：" + response);
            if(response.body() == null)
                throw new RuntimeException("物流信息查询响应错误 ：" + response);
            return JSON.parseObject(response.body().string());
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

}
