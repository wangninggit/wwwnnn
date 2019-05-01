package com.viathink.core.user.dto;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class IntegralCoefficientParamDto {
    private Long id;
    @NotNull(message = "{user.Integral.startDate}")
    @Min(value = 1000000000000L, message = "{user.Integral.startDate}")
    private Long startDate;
    @NotNull(message = "{user.Integral.startDate}")
    @Min(value = 1000000000000L, message = "{user.Integral.startDate}")
    private Long endDate;
    @NotNull(message = "{user.Integral.ration}")
    private Double integralRation;
    @NotNull(message = "{user.Integral.ration}")
    private Double dcwRation;
}
