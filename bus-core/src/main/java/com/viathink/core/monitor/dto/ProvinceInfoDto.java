package com.viathink.core.monitor.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ProvinceInfoDto {
    public interface Group{}

    @NotBlank(message = "不能为空字符串", groups = { ProvinceInfoDto.Group.class })
    private String provinceId;
    @NotBlank(message = "不能为空字符串", groups = { ProvinceInfoDto.Group.class })
    private String provinceName;
}
