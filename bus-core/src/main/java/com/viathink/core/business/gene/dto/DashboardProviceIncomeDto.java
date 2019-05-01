package com.viathink.core.business.gene.dto;

import lombok.Data;

import java.util.LinkedHashMap;
import java.util.List;

@Data
public class DashboardProviceIncomeDto {
    private String timeDimension;
    private List<String> names;
    private List<LinkedHashMap> list;
}
