package com.viathink.core.util;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BusinessDate {
    private String dayStr;
    private String monthStr;
    private String yearStr;
    private String quarterStr;
    private String halfYearStr;
}