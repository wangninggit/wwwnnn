package com.viathink.core.business.gene.mapper;

import com.viathink.core.business.gene.bean.PaymentInfoEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentInfoMapper {
    List<PaymentInfoEntity> findPaymentByOrderId(String orderId);
    void addPayment(PaymentInfoEntity entity);
}
