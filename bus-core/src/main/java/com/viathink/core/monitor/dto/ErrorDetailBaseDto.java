package com.viathink.core.monitor.dto;

import lombok.Data;

@Data
class ErrorDetailBaseDto {
    private String tag;
    private String messageId;
    private Long recordId = 0L;
    private String event;
    private Long eventTime = 0L;
}
