package com.viathink.core.business.gene.service.impl;

import com.viathink.core.business.gene.bean.PaymentInfoEntity;
import com.viathink.core.business.gene.mapper.PaymentInfoMapper;
import com.viathink.core.business.gene.service.PaymentInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PaymentInfoServiceImpl implements PaymentInfoService {
    @Autowired
    private PaymentInfoMapper paymentInfoMapper;
    @Override
    public List<PaymentInfoEntity> findPaymentByOrderId(String orderId) {
        return paymentInfoMapper.findPaymentByOrderId(orderId);
    }
}
