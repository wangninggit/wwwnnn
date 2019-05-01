package com.viathink.core.business.kpi.dto;

import lombok.Data;

@Data
public class KpiHistoryDto {
    private static final long serialVersionUID = 1L;
    private Long exportId;
    private String exportFileName;
    private Long exportTime;
    private Long kpiDate;
    private Long uploadId;
    private String uploadFileName;
}
