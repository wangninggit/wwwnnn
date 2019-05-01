package com.viathink.core.business.gene.dto;

import lombok.Data;

import java.util.List;

@Data
public class HolidayParamDto {
    private String monthStr;
    private String yearStr;
    private List<String> days;
}
