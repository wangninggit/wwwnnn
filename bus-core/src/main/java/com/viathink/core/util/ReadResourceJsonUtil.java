package com.viathink.core.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.io.IOException;
import java.io.InputStream;

public class ReadResourceJsonUtil {
    public static JSONObject parseStrToJSONObject(String jsonFileName) {
        // excel/aa.xlsx
        InputStream fileInputStream =
                ReadResourceJsonUtil.class.getClassLoader().getResourceAsStream(jsonFileName);
        byte[] bytes;
        try {
            bytes = new byte[fileInputStream.available()];
            fileInputStream.read(bytes);
            String str = new String(bytes);
            return JSON.parseObject(str);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
