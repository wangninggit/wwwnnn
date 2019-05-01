package com.viathink.core.monitor.dto;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class EventDto {
    public interface group{}

    @Min(value = 0, message = "最小值为0", groups = {group.class})
    @NotNull(message = "不能为null", groups = {group.class})
    private Long id;
    @NotBlank(message = "不能为空字符串", groups = {group.class})
    private String tag;
    @NotBlank(message = "不能为空字符串", groups = {group.class})
    private String event;
//    @Min(value = 1514736000000L, message = "最小值为 1514736000000L（2018年01月01日 00:00:00）", groups = {group.class})
    @NotNull(message = "不能为null", groups = {group.class})
    private Long eventTime;
    @NotNull(message = "不能为null", groups = {group.class})
    private Object data;
}
