package com.viathink.core.business.gene.dto;

import com.viathink.core.batch.dto.BusinessDetailResult;
import com.viathink.core.util.BusinessDate;
import com.viathink.core.util.BusinessDateUtil;
import com.viathink.core.util.DashBoardUtil;
import lombok.Data;

import java.math.BigDecimal;
import java.util.*;

@Data
public class DashboardCompareDto {
    private DashboardCompareItemDto<Long> order = new DashboardCompareItemDto<>();
    private DashboardCompareItemDto<Double> orderIncome = new DashboardCompareItemDto<>();
    private DashboardCompareItemDto<Double> cashIncome = new DashboardCompareItemDto<>();
    private DashboardCompareItemDto<Double> financeConfirmIncome = new DashboardCompareItemDto<>();

    public DashboardCompareDto(Long startTime, Long endTime, List<BusinessDetailResult> businessDetailResultList) {
        // 0：today  1:lastday   2:lastWeekToday
        Long orderRecord[] = new Long[3];
        Double orderIncomeRecord[] = new Double[3];
        Double cashIncomeRecord[] = new Double[3];
        Double financeConfirmIncomeRecord[] = new Double[3];
        Long todayMilliSec = (new Date().getTime() / DashBoardUtil.MILLI_SEC_FOR_24HOURS) * DashBoardUtil.MILLI_SEC_FOR_24HOURS;
        Map<String,BusinessDetailResult> map = new HashMap<>();

        for(BusinessDetailResult result : businessDetailResultList){
            map.put(result.getDateStr(),result);
        }

        List<DashboardCompareListItemDto<Long>> orderList = new ArrayList<>();
        List<DashboardCompareListItemDto<Double>> orderIncomeList = new ArrayList<>();
        List<DashboardCompareListItemDto<Double>> cashIncomeList = new ArrayList<>();
        List<DashboardCompareListItemDto<Double>> financeConfirmIncomeList = new ArrayList<>();
        for(Long tm = startTime; tm < endTime; tm += DashBoardUtil.MILLI_SEC_FOR_24HOURS){
            BusinessDate date = BusinessDateUtil.getBusinessDate(tm);
            BusinessDetailResult result = map.get(date.getDayStr());

            DashboardCompareListItemDto<Long> orderDto = new DashboardCompareListItemDto<>();
            orderDto.setX(date.getDayStr());
            if(result == null || result.getOrderPlaceCount() == null)
                orderDto.setY(0L);
            else
                orderDto.setY(result.getOrderPlaceCount());
            orderList.add(orderDto);

            DashboardCompareListItemDto<Double> orderIncomeDto = new DashboardCompareListItemDto<>();
            orderIncomeDto.setX(date.getDayStr());
            if(result == null || result.getOrderIncome() == null)
                orderIncomeDto.setY(0.0);
            else{
                double y = new BigDecimal(result.getOrderIncome() / 1000.0).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                orderIncomeDto.setY(y);
            }
            orderIncomeList.add(orderIncomeDto);

            DashboardCompareListItemDto<Double> cashIncomeDto = new DashboardCompareListItemDto<>();
            cashIncomeDto.setX(date.getDayStr());
            if(result == null || result.getCashIncome() == null)
                cashIncomeDto.setY(0.0);
            else{
                double y = new BigDecimal(result.getCashIncome() / 1000.0).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                cashIncomeDto.setY(y);
            }
            cashIncomeList.add(cashIncomeDto);

            DashboardCompareListItemDto<Double> financeConfirmIncomeDto = new DashboardCompareListItemDto<>();
            financeConfirmIncomeDto.setX(date.getDayStr());
            if(result == null || result.getFinanceConfirmIncome() == null)
                financeConfirmIncomeDto.setY(0.0);
            else{
                double y = new BigDecimal(result.getFinanceConfirmIncome() / 1000.0).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                financeConfirmIncomeDto.setY(y);
            }
            financeConfirmIncomeList.add(financeConfirmIncomeDto);

            if(tm.equals(todayMilliSec)){
                orderRecord[0] = orderDto.getY();
                orderIncomeRecord[0] = orderIncomeDto.getY();
                cashIncomeRecord[0] = cashIncomeDto.getY();
                financeConfirmIncomeRecord[0] = financeConfirmIncomeDto.getY();
            }else if(tm.equals(todayMilliSec - DashBoardUtil.MILLI_SEC_FOR_24HOURS)){
                orderRecord[1] = orderDto.getY();
                orderIncomeRecord[1] = orderIncomeDto.getY();
                cashIncomeRecord[1] = cashIncomeDto.getY();
                financeConfirmIncomeRecord[1] = financeConfirmIncomeDto.getY();
            }else if(tm.equals(todayMilliSec - DashBoardUtil.MILLI_SEC_FOR_24HOURS * 7)){
                orderRecord[2] = orderDto.getY();
                orderIncomeRecord[2] = orderIncomeDto.getY();
                cashIncomeRecord[2] = cashIncomeDto.getY();
                financeConfirmIncomeRecord[2] = financeConfirmIncomeDto.getY();
            }
        }
        order.setTotal(orderRecord[0]);
        if(orderRecord[2] != 0)
            order.setWeekCompare((orderRecord[0] - orderRecord[2]) * 100 / orderRecord[2]);
        if(orderRecord[1] != 0)
            order.setDayCompare((orderRecord[0] - orderRecord[1]) * 100 / orderRecord[1]);
        order.setList(orderList);

        orderIncome.setTotal(orderIncomeRecord[0]);
        if(orderIncomeRecord[2] != 0)
            orderIncome.setWeekCompare(Math.round((orderIncomeRecord[0] - orderIncomeRecord[2]) * 100 / orderIncomeRecord[2]));
        if(orderIncomeRecord[1] != 0)
            orderIncome.setDayCompare(Math.round((orderIncomeRecord[0] - orderIncomeRecord[1]) * 100 / orderIncomeRecord[1]));
        orderIncome.setList(orderIncomeList);

        cashIncome.setTotal(cashIncomeRecord[0]);
        if(cashIncomeRecord[2] != 0)
            cashIncome.setWeekCompare(Math.round((cashIncomeRecord[0] - cashIncomeRecord[2]) * 100 / cashIncomeRecord[2]));
        if(cashIncomeRecord[1] != 0)
            cashIncome.setDayCompare(Math.round((cashIncomeRecord[0] - cashIncomeRecord[1]) * 100 / cashIncomeRecord[1]));
        cashIncome.setList(cashIncomeList);

        financeConfirmIncome.setTotal(financeConfirmIncomeRecord[0]);
        if(financeConfirmIncomeRecord[2] != 0)
            financeConfirmIncome.setWeekCompare(Math.round((financeConfirmIncomeRecord[0] - financeConfirmIncomeRecord[2]) * 100 / financeConfirmIncomeRecord[2]));
        if(financeConfirmIncomeRecord[1] != 0)
            financeConfirmIncome.setDayCompare(Math.round((financeConfirmIncomeRecord[0] - financeConfirmIncomeRecord[1]) * 100 / financeConfirmIncomeRecord[1]));
        financeConfirmIncome.setList(financeConfirmIncomeList);
    }
}

@Data
class DashboardCompareItemDto<T>{
    private T total;
    private Long weekCompare;
    private Long dayCompare;
    private List<DashboardCompareListItemDto<T>> list;
}

@Data
class DashboardCompareListItemDto <T>{
    private String x;
    private T y;
}