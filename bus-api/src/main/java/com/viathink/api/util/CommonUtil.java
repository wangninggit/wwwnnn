package com.viathink.api.util;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.util.Map;

public class CommonUtil {
    public static Map<String,Object> objToMap(Object obj){
        String s = JSONObject.toJSONString(obj, SerializerFeature.WriteMapNullValue);
        return JSONObject.parseObject(s).getInnerMap();
    }
}
