package com.viathink.core.business.gene.bean;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class EventBaseEntity {
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
    private Integer changeCount = 0;
    private String event;
    private Long eventTime = 0L;
    private Long updateTime = 0L;
    private Long createTime = 0L;
}
