package com.viathink.core.business.service;

import com.alibaba.fastjson.JSONObject;
import com.viathink.core.business.gene.bean.EventBaseEntity;

public interface CustomerEventService {
    void eventHandler(EventBaseEntity event, JSONObject jsonObject);
}
