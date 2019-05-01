package com.viathink.core.business.gene.bean;

import lombok.Data;

@Data
public class SnapshootEntity {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String type;
    private String timeRange;
    private String dayStr;
    private String monthStr;
    private String yearStr;
    private String quarterStr;
    private String halfYearStr;
    private String record;
    private Boolean recreate = false;
    private Long updateTime = 0L;
    private Long createTime = 0L;
}
