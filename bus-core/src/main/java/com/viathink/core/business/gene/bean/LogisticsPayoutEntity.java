package com.viathink.core.business.gene.bean;

import lombok.Data;

@Data
public class LogisticsPayoutEntity {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String tag;
    private String messageId;
    private Long recordId = 0L;
    private String dayStr;
    private String monthStr;
    private String yearStr;
    private String quarterStr;
    private String halfYearStr;
    private String event;
    private Long eventTime = 0L;
    private Long total = 0L;
    private String localStaffId;
    private String localStaffJobNumber;
    private String localStaffName;
    private String localStaffRegionId;
    private String localStaffRegionName;
    private String localStaffProvinceId;
    private String localStaffProvinceName;
    private String localStaffCityId;
    private String localStaffCityName;
    private String localStaffCountyId;
    private String localStaffCountyName;
    private Long updateTime = 0L;
    private Long createTime = 0L;
}
