package com.viathink.engine.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.viathink.core.batch.service.WatchdogService;
import com.viathink.core.business.gene.bean.EventBaseEntity;
import com.viathink.core.util.BusinessDate;
import com.viathink.core.util.BusinessDateUtil;
import com.viathink.engine.EngineApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = EngineApplication.class, webEnvironment = NONE)
public class WatchdogTest {
    @Autowired
    private WatchdogService watchdogService;

    public JSONObject parseStrToJSONObject(String jsonFileName) {
        InputStream fileInputStream =
                WatchdogTest.class.getClassLoader().getResourceAsStream(jsonFileName);
        byte[] bytes = new byte[0];
        try {
            bytes = new byte[fileInputStream.available()];
            fileInputStream.read(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String str = new String(bytes);
        return JSON.parseObject(str);
    }
    public Map<String, Object> getParamMap(String jsonFileName) {
        JSONObject jsonObject = parseStrToJSONObject(jsonFileName);
        EventBaseEntity event = new EventBaseEntity();
        event.setTag(jsonObject.getString("tag"));
        event.setEvent(jsonObject.getString("event"));
        event.setEventTime(Long.valueOf(jsonObject.getString("eventTime")));
        event.setMessageId("message id");
        event.setRecordId(Long.valueOf(jsonObject.getString("id")));
        BusinessDate businessDate = BusinessDateUtil.getBusinessDate(Long.valueOf(jsonObject.getString("eventTime")));
        event.setDayStr(businessDate.getDayStr());
        event.setMonthStr(businessDate.getMonthStr());
        event.setYearStr(businessDate.getYearStr());
        event.setQuarterStr(businessDate.getQuarterStr());
        event.setHalfYearStr(businessDate.getHalfYearStr());
        JSONObject data = jsonObject.getJSONObject("data");
        Map<String, Object> map = new HashMap<>();
        map.put("event", event);
        map.put("data", data);
        return map;
    }
    @Test
    public void orderCreateEvent() {
        Map<String, Object> map = this.getParamMap("orderCreate.json");
        EventBaseEntity event = (EventBaseEntity) map.get("event");
        BusinessDate businessDate = BusinessDateUtil.getBusinessDate(event.getEventTime());
        watchdogService.watch(event, businessDate);
    }
}
