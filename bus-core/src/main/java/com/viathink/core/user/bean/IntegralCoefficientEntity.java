package com.viathink.core.user.bean;

import lombok.Data;

@Data
public class IntegralCoefficientEntity {

    private static final long serialVersionUID = 1L;
    private Long id;
    private Long startDate;
    private Long endDate;
    private Double integralRation;
    private Double dcwRation;
    private Long updateTime;
    private Long createTime;
}
