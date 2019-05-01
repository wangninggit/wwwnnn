package com.viathink.api.report.dto;

import com.viathink.core.util.SortServiceUtil;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ReportDetailParamDto {
    private String timeDimension;
    private String dateStr;
    private String sortBy = SortServiceUtil.SORT_BY[0];
    private String sortMode = SortServiceUtil.SORT_MODE[0];
}
