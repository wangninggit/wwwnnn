package com.viathink.core.business.gene.service;

import com.alibaba.fastjson.JSONObject;
import com.viathink.core.business.gene.bean.EventBaseEntity;

public interface ProvinceEventService {
    void updateProvinceInfo(EventBaseEntity event, JSONObject object);
    void updateProvinceRegionRelation(EventBaseEntity event, JSONObject object);
}
