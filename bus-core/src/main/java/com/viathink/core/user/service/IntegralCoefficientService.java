package com.viathink.core.user.service;

import com.viathink.core.user.bean.IntegralCoefficientEntity;
import com.viathink.core.user.dto.IntegralCoefficientParamDto;
import com.viathink.core.user.dto.PageParam;

import java.util.List;


public interface IntegralCoefficientService {
    void addIntegralCoefficient(IntegralCoefficientParamDto integralCoefficientParamDto);
    List<IntegralCoefficientEntity> findIntegralCoefficientList(PageParam pageParam);
    String updateIntegralCoefficientById(IntegralCoefficientParamDto integralCoefficientParamDto);
    IntegralCoefficientEntity findIntegralCoefficientByOrderTime(Long orderTime);
}
