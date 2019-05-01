package com.viathink.core.business.gene.service;

import com.alibaba.fastjson.JSONObject;
import com.viathink.core.business.gene.bean.EventBaseEntity;

public interface CountyEventService {
    void updateCountyInfo(EventBaseEntity event, JSONObject object);
}
