package com.viathink.core.user.service.impl;

import com.github.pagehelper.PageHelper;
import com.viathink.core.batch.service.WatchdogService;
import com.viathink.core.user.bean.IntegralCoefficientEntity;
import com.viathink.core.user.dto.IntegralCoefficientParamDto;
import com.viathink.core.user.dto.PageParam;
import com.viathink.core.user.mapper.IntegralCoefficientMapper;
import com.viathink.core.user.service.IntegralCoefficientService;
import com.viathink.core.util.BusinessDate;
import com.viathink.core.util.BusinessDateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class IntegralCoefficientServiceImpl implements IntegralCoefficientService {
    @Autowired
    private IntegralCoefficientMapper integralCoefficientMapper;
    @Autowired
    private WatchdogService watchdogService;

    @Override
    public void addIntegralCoefficient(IntegralCoefficientParamDto integralCoefficientParamDto) {
        integralCoefficientMapper.addIntegralCoefficient(integralCoefficientParamDto);
        Long startDate = integralCoefficientParamDto.getStartDate();
        Long endDate = integralCoefficientParamDto.getEndDate();

        // 快照表标志位设置
        BusinessDate startBusinessDate = BusinessDateUtil.getBusinessDate(startDate);
        BusinessDate endBusinessDate = BusinessDateUtil.getBusinessDate(endDate);
        watchdogService.snapshotRecreate(startBusinessDate,endBusinessDate);
        // 日月半年年表标志位设置及修改系数和积分


    }

    @Override
    public IntegralCoefficientEntity findIntegralCoefficientByOrderTime(Long orderTime) {
        return integralCoefficientMapper.findIntegralCoefficientByOrderTime(orderTime);
    }

    @Override
    public List<IntegralCoefficientEntity> findIntegralCoefficientList(PageParam pageParam) {
        PageHelper.startPage(pageParam.getPageNum(),pageParam.getPageSize());
        return integralCoefficientMapper.findIntegralCoefficientList();
    }

    @Override
    public String updateIntegralCoefficientById(IntegralCoefficientParamDto integralCoefficientParamDto) {
        IntegralCoefficientEntity entity = integralCoefficientMapper.findIntegralCoefficientById(integralCoefficientParamDto.getId());
        if(entity==null){
            return "RecordNotFound";
        }
        integralCoefficientMapper.updateIntegralCoefficientById(integralCoefficientParamDto);

        // 获取startDate 和 endDate
        Long oldStartDate = entity.getStartDate();
        Long oldEndDate = entity.getStartDate();
        Long newStartDate = integralCoefficientParamDto.getStartDate();
        Long newEndDate = integralCoefficientParamDto.getEndDate();

        Long startDate = oldStartDate<newStartDate?oldStartDate:newStartDate;
        Long endDate = oldEndDate<newEndDate?newEndDate:oldEndDate;

        // 快照表标志位设置
        BusinessDate startBusinessDate = BusinessDateUtil.getBusinessDate(startDate);
        BusinessDate endBusinessDate = BusinessDateUtil.getBusinessDate(endDate);
        watchdogService.snapshotRecreate(startBusinessDate,endBusinessDate);
        // 日月半年年表标志位设置及修改系数和积分

        // 获取时间区间内的所有的记录
        // 遍历所有的记录，每条记录获取其的系数

        return "success";
    }
}
