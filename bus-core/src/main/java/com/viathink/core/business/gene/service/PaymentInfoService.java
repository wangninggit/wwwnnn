package com.viathink.core.business.gene.service;

import com.viathink.core.business.gene.bean.PaymentInfoEntity;

import java.util.List;

public interface PaymentInfoService {
    List<PaymentInfoEntity> findPaymentByOrderId(String orderId);
}
