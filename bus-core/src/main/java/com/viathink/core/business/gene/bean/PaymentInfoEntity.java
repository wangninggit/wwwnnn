package com.viathink.core.business.gene.bean;

import lombok.Data;

@Data
public class PaymentInfoEntity {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String orderId;
    private String type;
    private Long total = 0L;
    private String account;
    private String userName;
    private Boolean localStaffPayroll = false;
    private String localStaffPayrollTrans;
    private String paymentType;
    private String orderPayAttorneyUrl;
    private String tradeNumber;
    private Long payTime = 0L;
}
