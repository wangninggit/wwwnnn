package com.viathink.core.monitor.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class RegionProvinceDto {
    public interface Group{}

    @NotBlank(message = "不能为空字符串", groups = { RegionProvinceDto.Group.class })
    private String regionId;
    @NotBlank(message = "不能为空字符串", groups = { RegionProvinceDto.Group.class })
    private String provinceId;
}
