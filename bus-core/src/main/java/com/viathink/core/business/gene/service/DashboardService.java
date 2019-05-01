package com.viathink.core.business.gene.service;

import com.viathink.core.business.gene.dto.DashboardOrderTrendResultDto;
import com.viathink.core.business.gene.dto.DashboardCompareDto;
import com.viathink.core.business.gene.dto.DashboardProviceIncomeDto;

import java.util.Map;

public interface DashboardService {
    DashboardProviceIncomeDto getWeekProvinceIncome();
    DashboardProviceIncomeDto getMonthProvinceIncome();
    DashboardProviceIncomeDto getYearProvinceIncome();
    DashboardOrderTrendResultDto getDayOrderTrend();
    DashboardOrderTrendResultDto getWeekOrderTrend();
    DashboardOrderTrendResultDto getMonthOrderTrend();
    DashboardOrderTrendResultDto getYearOrderTrend();
    DashboardCompareDto getDashboardCompareDate();
    Map<String,Object> getOrderCountRank(String timeDimension);
    DashboardOrderTrendResultDto getMonthOrderTrendAvg();
}
