package com.viathink.core.business.gene.dao.Impl;

import com.viathink.core.business.gene.bean.GeneDetailBaseEntity;
import com.viathink.core.business.gene.bean.PaymentInfoEntity;
import com.viathink.core.business.gene.dao.PaymentInfoDao;
import com.viathink.core.business.gene.mapper.PaymentInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PaymentInfoDaoImpl implements PaymentInfoDao {
    private final PaymentInfoMapper paymentInfoMapper;

    @Autowired
    public PaymentInfoDaoImpl(PaymentInfoMapper paymentInfoMapper) {
        this.paymentInfoMapper = paymentInfoMapper;
    }

    @Override
    public void addPayment(GeneDetailBaseEntity entity, String type) {
        if((!type.equals(PaymentInfoDao.TYPE_PAY)) && (!type.equals(PaymentInfoDao.TYPE_REFUND)))
            return;
        PaymentInfoEntity payEntity = new PaymentInfoEntity();
        payEntity.setType(type);
        payEntity.setOrderId(entity.getOrderId());
        payEntity.setPayTime(entity.getEventTime());
        if(type.equals(PaymentInfoDao.TYPE_PAY)){
            payEntity.setTotal(entity.getPayTotal());
            payEntity.setAccount(entity.getOrderPayerAccount());
            payEntity.setUserName(entity.getOrderPayerName());
            payEntity.setLocalStaffPayroll(entity.getLocalStaffPayroll());
            payEntity.setPaymentType(entity.getPaymentType());
            payEntity.setTradeNumber(entity.getTradeNumber());
            payEntity.setOrderPayAttorneyUrl(entity.getOrderPayAttorneyUrl());
        }else if(type.equals(PaymentInfoDao.TYPE_REFUND)){
            payEntity.setTotal(0L - entity.getRefundTotal());
            payEntity.setAccount(entity.getRefundReceiverAccount());
            payEntity.setUserName(entity.getRefundReceiverName());
            payEntity.setPaymentType(entity.getRefundType());
        }
        paymentInfoMapper.addPayment(payEntity);
    }
}
