package com.viathink.core.monitor.service;

import com.alibaba.fastjson.JSONObject;

public interface MonitorService {
    Boolean messageCheck(JSONObject jsonObject,String messageId);
}
