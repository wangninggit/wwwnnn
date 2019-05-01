package com.viathink.core.monitor.dto;

import lombok.Data;

@Data
public class CheckResultDto {
    private Boolean checked = true;
    private String type;
    private String error;
    private String detail;
}
