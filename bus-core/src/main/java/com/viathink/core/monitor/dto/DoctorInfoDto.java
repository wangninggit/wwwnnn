package com.viathink.core.monitor.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class DoctorInfoDto {
    public interface Group{}

    @NotBlank(message = "不能为空字符串", groups = { DoctorInfoDto.Group.class })
    private String hospitalName;
    @NotBlank(message = "不能为空字符串", groups = { DoctorInfoDto.Group.class })
    private String hospitalAddress;
    @NotBlank(message = "不能为空字符串", groups = { DoctorInfoDto.Group.class })
    private String doctorId;
    @NotBlank(message = "不能为空字符串", groups = { DoctorInfoDto.Group.class })
    private String doctorName;
}
