package com.viathink.core.business.gene.dto;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class ProvinceQueryForm {
    @NotNull(message="{export.DetailCountForm.startDate}")
    @Min(value = 1000000000000L, message = "{export.DetailCountForm.startDate}")
    private Long startDate;
    @NotNull(message="{export.DetailCountForm.endDate}")
    @Min(value = 1000000000000L, message = "{export.DetailCountForm.endDate}")
    private Long endDate;
    private String[] provinceIds;
}
