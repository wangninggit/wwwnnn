package com.viathink.core.business.gene.bean;

import lombok.Data;

@Data
public class TestingItemEntity {
    private static final long serialVersionUID = 1L;
    private Long id = 0L;
    private String orderId;
    private String testingResult;
    private String testingAgency;
    private String testingItem;
    private Integer testingItemId = 0;
    private Long testingItemPrice = 0L;
    private Long testingItemCost = 0L;
    private Integer testingAgencyId = 0;
    private String testingAgencyAddress;
    private String testingReportNumber;
    private Long testingReportUploadTime = 0L;
    private Long samplingTime = 0L;
    private Long sampleConfirmTime = 0L;
    private Long updateTime = 0L;
    private Long createTime = 0L;
    private String testingReportUrl;
}
