package com.viathink.core.log.bean;

import lombok.Data;

@Data
public class GeneLogEntity {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String tag;
    private String event;
    private Long eventTime;
    private String messageId;
    private Long recordId;
    private String dayStr;
    private String monthStr;
    private String yearStr;
    private String quarterStr;
    private String halfYearStr;
    private String record;
    private String orderId;
    private Long updateTime;
    private Long createTime;
}
