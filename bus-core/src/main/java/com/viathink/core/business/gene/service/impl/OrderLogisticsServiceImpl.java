package com.viathink.core.business.gene.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.viathink.core.business.gene.bean.GeneDetailBaseEntity;
import com.viathink.core.business.gene.bean.OrderLogisticsEntity;
import com.viathink.core.business.gene.mapper.GeneDetailMapper;
import com.viathink.core.business.gene.mapper.OrderLogisticsMapper;
import com.viathink.core.business.gene.service.OrderLogisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrderLogisticsServiceImpl implements OrderLogisticsService {

    private final OrderLogisticsMapper orderLogisticsMapper;
    private final GeneDetailMapper geneDetailMapper;

    @Autowired
    public OrderLogisticsServiceImpl(OrderLogisticsMapper orderLogisticsMapper, GeneDetailMapper geneDetailMapper) {
        this.orderLogisticsMapper = orderLogisticsMapper;
        this.geneDetailMapper = geneDetailMapper;
    }

    @Override
    public JSONObject findOrderLogisticsByOrderId(String orderId) {
        GeneDetailBaseEntity geneDetailBaseEntity = geneDetailMapper.getGeneDetailByOrderId(orderId);
        if(geneDetailBaseEntity==null){
            return null;
        }
        List<OrderLogisticsEntity> orderLogisticsResultDtos = orderLogisticsMapper.findOrderLogisticsByOrderId(orderId);
        return JSON.parseObject(JSON.toJSONString(orderLogisticsResultDtos.stream().collect(Collectors.groupingBy(OrderLogisticsEntity::getLogisticsType))));
    }
}
