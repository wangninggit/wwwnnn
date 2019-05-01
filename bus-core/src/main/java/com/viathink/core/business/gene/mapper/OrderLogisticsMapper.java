package com.viathink.core.business.gene.mapper;

import com.viathink.core.business.gene.bean.OrderLogisticsEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderLogisticsMapper {
    List<OrderLogisticsEntity> findOrderLogisticsByOrderId(String orderId);
    void addOrderLogistics(OrderLogisticsEntity entity);
}
