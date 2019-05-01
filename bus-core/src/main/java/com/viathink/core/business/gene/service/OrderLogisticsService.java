package com.viathink.core.business.gene.service;

import com.alibaba.fastjson.JSONObject;


public interface OrderLogisticsService {
    JSONObject findOrderLogisticsByOrderId(String orderId);
}
