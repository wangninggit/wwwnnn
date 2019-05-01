package com.viathink.core.business.gene.bean;

import lombok.Data;

@Data
public class DoctorEntity {
    private static final long serialVersionUID = 1L;
    private String doctorId;
    private String doctorName;
    private String hospitalName;
    private String hospitalAddress;
    private Long updateTime = 0L;
    private Long createTime = 0L;
}
