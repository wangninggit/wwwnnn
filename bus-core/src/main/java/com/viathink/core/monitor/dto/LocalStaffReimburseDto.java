package com.viathink.core.monitor.dto;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class LocalStaffReimburseDto {
    public interface Group{}

    @NotBlank(message = "不能为空字符串", groups = { LocalStaffReimburseDto.Group.class })
    private String localStaffId;
//    @NotBlank(message = "不能为空字符串", groups = { LocalStaffReimburseDto.Group.class })
    private String localStaffJobNumber;
    @NotBlank(message = "不能为空字符串", groups = { LocalStaffReimburseDto.Group.class })
    private String localStaffName;
    @NotNull(message = "不能为null", groups = { LocalStaffReimburseDto.Group.class })
    @Valid
    private LocalStaffLocationFieldDto localStaffLocation;
    @Min(value = 0, message = "最小值为0", groups = { LocalStaffReimburseDto.Group.class })
    @NotNull(message = "不能为null", groups = { LocalStaffReimburseDto.Group.class })
    private Long total;
}
