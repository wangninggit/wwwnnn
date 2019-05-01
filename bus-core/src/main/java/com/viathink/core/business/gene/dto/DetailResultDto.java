package com.viathink.core.business.gene.dto;

import lombok.Data;

@Data
public class DetailResultDto {
    private Long id;
    private Long orderIncome = 0L;
    private Long cashIncome = 0L;
    private Long financeConfirmIncome = 0L;
    private Long testingItemCost = 0L;
    private Long testingItemConfirmCost = 0L;
    private Long integralCost = 0L;
    private String orderId;
    private String orderNumber;
    private Integer changeCount = 0;
    private String patientName;
    private Integer patientAge;
    private String patientGender;
    private String patientIdCard;
    private String localStaffRegionId;
    private String localStaffRegionName;
    private String hospitalName;
    private String doctorName;
    private String doctorId;
    private String localStaffName;
}
