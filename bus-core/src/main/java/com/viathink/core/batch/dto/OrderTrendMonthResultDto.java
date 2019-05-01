package com.viathink.core.batch.dto;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

@Data
public class OrderTrendMonthResultDto {
    private Long holidayPlaceAvgCost = 0L;
    private Long holidayPlaceAvgCount = 0L;
    private Long workPlaceAvgCost = 0L;
    private Long workPlaceAvgCount = 0L;
    private String date;
    @JSONField(serialize = false)
    private String type;
    @JSONField(serialize = false)
    private String timeRange;
    @JSONField(serialize = false)
    private String dayStr;
    @JSONField(serialize = false)
    private String monthStr;
    @JSONField(serialize = false)
    private String yearStr;
    @JSONField(serialize = false)
    private String quarterStr;
    @JSONField(serialize = false)
    private String halfYearStr;
    @JSONField(serialize = false)
    private String record;
}
