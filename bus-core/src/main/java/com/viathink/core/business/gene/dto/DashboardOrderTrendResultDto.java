package com.viathink.core.business.gene.dto;

import lombok.Data;

import java.util.List;

@Data
public class DashboardOrderTrendResultDto<T>{
    private String timeDimension;
    private List<T> list;
}
