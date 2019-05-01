package com.viathink.api.user.controller;

import com.github.pagehelper.PageInfo;
import com.viathink.api.common.exception.ParamsInvalidException;
import com.viathink.core.user.bean.IntegralCoefficientEntity;
import com.viathink.core.user.bean.UserEntity;
import com.viathink.core.user.dto.*;
import com.viathink.core.user.service.IntegralCoefficientService;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class IntegralCoefficientController {

    @Autowired
    private IntegralCoefficientService integralCoefficientService;

    @RequiresRoles("admin")
    @PostMapping("/integral-coefficient")
    public Map<String, Long> addIntegralCoefficient(@Valid @RequestBody IntegralCoefficientParamDto integralCoefficientParamDto) {
        integralCoefficientService.addIntegralCoefficient(integralCoefficientParamDto);
        Map<String, Long> map = new HashMap<>();
        map.put("id", integralCoefficientParamDto.getId());
        return map;
    }

    @RequiresRoles("admin")
    @GetMapping(value = "/integral-coefficient")
    public PageInfo<IntegralCoefficientEntity> getIntegralCoefficientByPage(PageParam pageParam) {
        List<IntegralCoefficientEntity> list = integralCoefficientService.findIntegralCoefficientList(pageParam);
        return new PageInfo<>(list);
    }

//    @RequiresRoles("admin")
    @PutMapping(value = "/integral-coefficient/{id}")
    public Map<String, Long> updateIntegralCoefficient(@PathVariable Long id,@Valid @RequestBody IntegralCoefficientParamDto integralCoefficientParamDto) {
        integralCoefficientParamDto.setId(id);
        String isExit = integralCoefficientService.updateIntegralCoefficientById(integralCoefficientParamDto);
        switch (isExit) {
            case "RecordNotFound":
                throw new ParamsInvalidException(2008,"此id对应的记录不存在");
        }
        Map<String, Long> returnMap = new HashMap<>();
        returnMap.put("id", id);
        return returnMap;
    }
}
