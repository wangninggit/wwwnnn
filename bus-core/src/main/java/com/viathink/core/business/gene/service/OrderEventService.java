package com.viathink.core.business.gene.service;

import com.alibaba.fastjson.JSONObject;
import com.viathink.core.business.gene.bean.EventBaseEntity;

public interface OrderEventService {
    void orderCreated(EventBaseEntity event, JSONObject object);
    void orderPay(EventBaseEntity event, JSONObject object);
    void sampleConfirmed(EventBaseEntity event, JSONObject object);
    void testingReportUploaded(EventBaseEntity event, JSONObject object);
    void orderInvoiced(EventBaseEntity event, JSONObject object);
    void integralGranted(EventBaseEntity event, JSONObject object);
    void orderCancel(EventBaseEntity event, JSONObject object);
    void orderRefunded(EventBaseEntity event, JSONObject object);
    void orderUpdated(EventBaseEntity event, JSONObject object);
    void orderLogistics(EventBaseEntity event, JSONObject object);
}
