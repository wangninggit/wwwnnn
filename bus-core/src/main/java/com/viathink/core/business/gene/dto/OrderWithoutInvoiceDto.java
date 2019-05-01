package com.viathink.core.business.gene.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.viathink.core.business.gene.bean.InvoiceEntity;
import com.viathink.core.business.gene.bean.PaymentInfoEntity;
import com.viathink.core.business.gene.bean.TestingItemEntity;
import lombok.Data;

import java.util.List;

@Data
public class OrderWithoutInvoiceDto{
    private String orderId;
    @JSONField(serialize=false)
    private Long orderCreateTime = 0L;
    private String orderCreateDate;
    private String orderNumber;
    private Long cashIncome = 0L;
    @JSONField(serialize=false)
    private Long financeConfirmIncome = 0L;
    private String patientName;
    private Long balance = 0L;

    private List<TestingItemEntity> testings;
    private List<InvoiceEntity> invoices;
    private List<PaymentInfoEntity> payments;
}
