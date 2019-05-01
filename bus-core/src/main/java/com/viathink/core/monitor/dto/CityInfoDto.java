package com.viathink.core.monitor.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CityInfoDto {
    public interface Group{}

    @NotBlank(message = "不能为空字符串", groups = { CityInfoDto.Group.class })
    private String cityId;
    @NotBlank(message = "不能为空字符串", groups = { CityInfoDto.Group.class })
    private String cityName;
}
