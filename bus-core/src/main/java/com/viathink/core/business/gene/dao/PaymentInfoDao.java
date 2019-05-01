package com.viathink.core.business.gene.dao;

import com.viathink.core.business.gene.bean.GeneDetailBaseEntity;

public interface PaymentInfoDao {
    String TYPE_PAY = "pay";
    String TYPE_REFUND = "refund";

    void addPayment(GeneDetailBaseEntity entity, String type);
}
