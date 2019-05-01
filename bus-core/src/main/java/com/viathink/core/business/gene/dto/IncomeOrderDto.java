package com.viathink.core.business.gene.dto;

import com.viathink.core.business.gene.bean.InvoiceEntity;
import com.viathink.core.business.gene.bean.PaymentInfoEntity;
import com.viathink.core.business.gene.bean.TestingItemEntity;
import lombok.Data;

import java.util.List;

@Data
public class IncomeOrderDto {

    private String orderId;
    private String orderNumber;
    private String patientName;
    private Integer patientAge;
    private String patientClinicalDiagnosis;
    private String hospitalName;
    private String doctorName;
    private String localStaffName;
    private String orderLogisticsUrl;
    private Long orderPrice = 0L;

    private List<TestingItemEntity> testings;
    private List<InvoiceEntity> invoices;
    private List<PaymentInfoEntity> payments;
}
