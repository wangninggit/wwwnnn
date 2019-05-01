package com.viathink.core.business.gene.service;

import com.alibaba.fastjson.JSONObject;
import com.viathink.core.business.gene.bean.EventBaseEntity;

public interface RegionEventService {
    void updateRegionInfo(EventBaseEntity event, JSONObject object);
}
