package com.viathink.core.batch.dto;

import lombok.Data;

import java.util.List;

@Data
public class HolidayDateDto {
    private List<String> holidayList;
    private List<String> extraWorkdays;
    private List<String> weekDays;
    private List<String> workList;
}
