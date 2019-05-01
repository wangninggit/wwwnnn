package com.viathink.core.business.gene.bean;

import lombok.Data;

@Data
public class HolidayDayEntity {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String dayStr;
    private String monthStr;
    private String yearStr;
}

