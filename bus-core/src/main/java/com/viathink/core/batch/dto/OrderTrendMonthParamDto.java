package com.viathink.core.batch.dto;

import lombok.Data;

import java.util.List;

@Data
public class OrderTrendMonthParamDto {
    private List<String> holidayList;
    private int days;
}
