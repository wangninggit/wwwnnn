package com.viathink.core.monitor.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CountyInfoDto {
    public interface Group{}

    @NotBlank(message = "不能为空字符串", groups = { CountyInfoDto.Group.class })
    private String countyId;
    @NotBlank(message = "不能为空字符串", groups = { CountyInfoDto.Group.class })
    private String countyName;
}
