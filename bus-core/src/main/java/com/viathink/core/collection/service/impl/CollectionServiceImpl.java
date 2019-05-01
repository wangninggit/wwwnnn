package com.viathink.core.collection.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.viathink.core.collection.bean.PropertiesEntity;
import com.viathink.core.collection.mapper.PropertiesMapper;
import com.viathink.core.collection.service.CollectionService;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
public class CollectionServiceImpl implements CollectionService {
    private Logger logger = LoggerFactory.getLogger(CollectionServiceImpl.class);
    private final PropertiesMapper propertiesMapper;
    @Value("${sys.dataCollection.appKey}")
    private String appKey;
    @Value("${sys.dataCollection.appSecret}")
    private String appSecret;
    @Value("${sys.dataCollection.url.token}")
    private String tokenUrl;
    @Value("${sys.dataCollection.url.syncData}")
    private String syncDataUrl;
    @Value("${sys.dataCollection.tokenValidTimeMillis}")
    private Long tokenValidTimeMillis;
    @Value("${sys.dataCollection.limit}")
    private Long limit;

    private final OkHttpClient client = new OkHttpClient();

    @Autowired
    public CollectionServiceImpl(PropertiesMapper propertiesMapper) {
        this.propertiesMapper = propertiesMapper;
    }

    @Override
    public List<Map<String,Object>> getCollectionData() {
        String validApiToken = getValidApiToken();
        if(validApiToken == null){
            logger.info("重新拉取token");
            validApiToken = getToken();
            if(validApiToken == null){
                logger.error("token拉取失敗");
                return null;
            }
            logger.info("token拉取成功，更新token");
            updateValidApiToken(validApiToken);
        }
        return getSyncData(validApiToken);
    }

    @Override
    public void updateRecordId(long id) {
        PropertiesEntity newEntity = new PropertiesEntity();
        newEntity.setId("hs_record_id");
        newEntity.setType("type_int");
        newEntity.setTypeInt(id);
        PropertiesEntity entity = propertiesMapper.findPropertyById("hs_record_id");
        if(entity == null){
            propertiesMapper.addProperty(newEntity);
        }else{
            if(entity.getTypeInt() < id)
                propertiesMapper.updateProperty(newEntity);
        }
    }

    private String getToken(){
        FormBody formBody = new FormBody.Builder()
                .add("appKey", appKey)
                .add("appSecret", appSecret)
                .build();
        Request request = new Request.Builder()
                .url(tokenUrl)
                .post(formBody)
                .build();
        JSONObject jsonObject;
        try{
            Response response = client.newCall(request).execute();
            if ((!response.isSuccessful()) || (response.body() == null)) {
                return null;
            }
            String respStr = response.body().string();
            System.out.println("--------: " + respStr);
            jsonObject = JSON.parseObject(respStr);
            if(jsonObject.getIntValue("status") != 200) {
                return null;
            }
            else {
                return jsonObject.get("data").toString();
            }
        }catch (IOException e){
            logger.error("连接数据接口失败，请检查数据请求服务器是否启动");
            return null;
        }
    }

    private List<Map<String,Object>> getSyncData(String validApiToken){
        PropertiesEntity entity = propertiesMapper.findPropertyById("hs_record_id");
        String url = syncDataUrl + "?limit="+limit+"&id=";
        if(entity != null){
            url += (entity.getTypeInt());
        }
        Request request = new Request.Builder()
                .url(url)
                .header("X-BMS-Access-Token", validApiToken)
                .build();
        JSONObject jsonObject;
        try{
            Response response = client.newCall(request).execute();
            if ((!response.isSuccessful()) || (response.body() == null)) {
                return null;
            }
            jsonObject = JSON.parseObject(response.body().string());
            if(jsonObject.getIntValue("status") != 200) {
                return null;
            }
            else{
                List<Map<String,Object>> retList = new ArrayList<>();
                JSONArray data = jsonObject.getJSONArray("data");
                List<JSONObject> jsonObjectList = jsonObjectListSort(data.toJavaList(JSONObject.class), "id", "asc");
                for (JSONObject mapObj : jsonObjectList) {
                    if (entity == null || mapObj.getLong("id") > entity.getTypeInt()) {
                        retList.add(mapObj.getInnerMap());
                    }
                }
                return retList;
            }

        }catch (IOException e){
            return null;
        }
    }

    private String getValidApiToken() {
        PropertiesEntity token = propertiesMapper.findPropertyById("hs_api_token");
        if(token == null) {
            logger.info("本地沒有token");
            return null;
        }
        Date date = new Date();
        if((date.getTime() - token.getUpdateTime()) > tokenValidTimeMillis) {
            logger.warn("token即将过期");
            return null;
        }
        return token.getTypeVarchar();
    }

    private void updateValidApiToken(String value) {
        PropertiesEntity newtoken = new PropertiesEntity();
        newtoken.setId("hs_api_token");
        newtoken.setType("type_varchar");
        newtoken.setTypeVarchar(value);
        PropertiesEntity token = propertiesMapper.findPropertyById("hs_api_token");
        if(token == null)
            propertiesMapper.addProperty(newtoken);
        else
            propertiesMapper.updateProperty(newtoken);
    }

    private static List<JSONObject> jsonObjectListSort(List<JSONObject> list, String longSortField, String sortMode){
        list.sort((o1, o2) -> {
            Long c1, c2;
            Integer mode;
            c1 = o1.getLong(longSortField);
            c2 = o2.getLong(longSortField);
            if (sortMode.equals("desc")) {
                mode = 1;
            } else {
                mode = -1;
            }
            if (c1 < c2) {
                return mode;
            } else if (c1.equals(c2)) {
                return 0;
            } else {
                return -1 * mode;
            }
        });
        return list;
    }
}
