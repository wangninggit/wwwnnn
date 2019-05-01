package com.viathink.core.user.mapper;

import com.viathink.core.user.bean.IntegralCoefficientEntity;
import com.viathink.core.user.dto.IntegralCoefficientParamDto;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IntegralCoefficientMapper {
    void addIntegralCoefficient(IntegralCoefficientParamDto integralCoefficientParamDto);
    List<IntegralCoefficientEntity> findIntegralCoefficientList();
    IntegralCoefficientEntity findIntegralCoefficientById(Long id);
    void updateIntegralCoefficientById(IntegralCoefficientParamDto integralCoefficientParamDto);
    IntegralCoefficientEntity findIntegralCoefficientByOrderTime(Long orderTime);
}
