package com.viathink.core.business.gene.bean;

import lombok.Data;

@Data
public class ErrorLogEntity {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String type;
    private String error;
    private String detail;
    private Boolean status;
    private Long updateTime = 0L;
    private Long createTime = 0L;
}
