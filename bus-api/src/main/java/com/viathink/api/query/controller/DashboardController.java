package com.viathink.api.query.controller;

import com.viathink.api.common.exception.ParamsInvalidException;
import com.viathink.core.business.gene.dto.DashboardCompareDto;
import com.viathink.core.business.gene.dto.DashboardOrderTrendResultDto;
import com.viathink.core.business.gene.dto.DashboardProviceIncomeDto;
import com.viathink.core.business.gene.service.DashboardService;
import com.viathink.core.util.DashBoardUtil;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RequiresUser
@RestController
public class DashboardController {
    private final DashboardService dashboardService;

    @Autowired
    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping(value = "/dashboard/income-province")
    public DashboardProviceIncomeDto provinceIncome(String timeDimension) {
        if (timeDimension == null || timeDimension.equals("")) {
            timeDimension = "week";
        }
        DashboardProviceIncomeDto dto = null;
        switch (timeDimension) {
            case "week":
                dto = dashboardService.getWeekProvinceIncome();
                break;
            case "month":
                dto = dashboardService.getMonthProvinceIncome();
                break;
            case "year":
                dto = dashboardService.getYearProvinceIncome();
                break;
        }
        return dto;
    }

    @GetMapping(value = "/dashboard/order-trend")
    public DashboardOrderTrendResultDto orderTrend(String timeDimension) {
        if (timeDimension == null || timeDimension.equals("")) {
            timeDimension = "day";
        }
        DashboardOrderTrendResultDto dto = null;

        switch (timeDimension) {
            case "day":
                dto = dashboardService.getDayOrderTrend();
                break;
            case "week":
                dto = dashboardService.getWeekOrderTrend();
                break;
            case "month":
                dto = dashboardService.getMonthOrderTrend();
                break;
            case "year":
                dto = dashboardService.getYearOrderTrend();
                break;
        }
        return dto;
    }

    @GetMapping(value = "/dashboard/dimension-count")
    public DashboardCompareDto dimensionCompare(){
        return dashboardService.getDashboardCompareDate();
    }

    @GetMapping(value = "dashboard/order-count-top")
    public Map orderCountTop(@RequestParam(value = "timeDimension", defaultValue = "") String timeDimension){
        if(timeDimension.equals(DashBoardUtil.ORDER_COUNT_RANK_PARAM_DAY) ||
                timeDimension.equals(DashBoardUtil.ORDER_COUNT_RANK_PARAM_WEEK) ||
                timeDimension.equals(DashBoardUtil.ORDER_COUNT_RANK_PARAM_MONTH) ||
                timeDimension.equals(DashBoardUtil.ORDER_COUNT_RANK_PARAM_YEAR)){
            return dashboardService.getOrderCountRank(timeDimension);
        }else{
            throw new ParamsInvalidException(1006,"请求参数错误");
        }
    }

    @GetMapping(value = "/dashboard/month/order-trend")
    public DashboardOrderTrendResultDto orderTrendMonthAvg(String timeDimension) {

        DashboardOrderTrendResultDto dto = null;

        switch (timeDimension) {
            case "year":
                dto = dashboardService.getMonthOrderTrendAvg();
                break;
        }
        return dto;
    }
}
