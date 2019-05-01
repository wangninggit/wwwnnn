package com.viathink.core.batch.dto;

import lombok.Data;

import java.util.List;

@Data
public class ProfitResultDto {
    private ProfitReportDto sum;
    private List<ProfitReportDto> list;
}
