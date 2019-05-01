package com.viathink.core.business.kpi.bean;

import lombok.Data;

@Data
public class KpiUploadEntity {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String originalFileName;
    private String storageFileName;
    private String url;
    private Long uploadTime;
}
