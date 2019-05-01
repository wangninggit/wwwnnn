package com.viathink.core.business.gene.service.impl;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.viathink.core.batch.dto.BusinessDetailResult;
import com.viathink.core.batch.dto.OrderTrendMonthAvgResultDto;
import com.viathink.core.batch.dto.OrderTrendMonthResultDto;
import com.viathink.core.batch.dto.ProvinceSnapshootRecordDto;
import com.viathink.core.business.gene.dto.*;
import com.viathink.core.business.gene.mapper.GeneDailyDetailMapper;
import com.viathink.core.business.gene.mapper.GeneMonthlyDetailMapper;
import com.viathink.core.business.gene.mapper.SnapshootMapper;
import com.viathink.core.business.gene.service.DashboardService;
import com.viathink.core.util.BatchUtil;
import com.viathink.core.util.BusinessDateUtil;
import com.viathink.core.util.DashBoardUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

import static com.viathink.core.util.DashBoardUtil.*;

@Service
@Transactional
public class DashboardServiceImpl implements DashboardService {
    private final GeneDailyDetailMapper geneDailyDetailMapper;
    private final GeneMonthlyDetailMapper geneMonthlyDetailMapper;
    private final SnapshootMapper snapshootMapper;

    @Autowired
    public DashboardServiceImpl(GeneDailyDetailMapper geneDailyDetailMapper, GeneMonthlyDetailMapper geneMonthlyDetailMapper,SnapshootMapper snapshootMapper) {
        this.geneDailyDetailMapper = geneDailyDetailMapper;
        this.geneMonthlyDetailMapper = geneMonthlyDetailMapper;
        this.snapshootMapper = snapshootMapper;
    }

    @Override
    public DashboardProviceIncomeDto getWeekProvinceIncome() {
        DateRangeStrDto weekDateRange = BusinessDateUtil.getWeekStartEndDateStr();
        List<ProvinceSnapshootRecordDto> recordDtoList = geneDailyDetailMapper.queryBusinessProvinceWeek(weekDateRange);
        return this.formatIncomeDto("week", recordDtoList);
    }

    @Override
    public DashboardProviceIncomeDto getMonthProvinceIncome() {
        String monthStr = BusinessDateUtil.getCurrentMonthStr();
        List<ProvinceSnapshootRecordDto> recordDtoList = geneDailyDetailMapper.queryBusinessProvinceMonth(monthStr);
        return this.formatIncomeDto("month", recordDtoList);
    }

    @Override
    public DashboardProviceIncomeDto getYearProvinceIncome() {
        String yearStr = BusinessDateUtil.getCurrentYearStr();
        List<ProvinceSnapshootRecordDto> recordDtoList = geneDailyDetailMapper.queryBusinessProvinceYear(yearStr);
        return this.formatIncomeDto("year", recordDtoList);
    }

    @Override
    public DashboardCompareDto getDashboardCompareDate() {
        Long endMilliSec = (new Date().getTime() / DashBoardUtil.MILLI_SEC_FOR_24HOURS + 1) * DashBoardUtil.MILLI_SEC_FOR_24HOURS;
        Long startMilliSec = endMilliSec - DashBoardUtil.MILLI_SEC_FOR_24HOURS * 14;
        DetailCountForm form = new DetailCountForm();
        form.setStartDate(startMilliSec);
        form.setEndDate(endMilliSec);
        List<BusinessDetailResult> resultList = geneDailyDetailMapper.queryDailyDetail(form);
        return new DashboardCompareDto(startMilliSec,endMilliSec,resultList);
    }

    @Override
    public Map<String, Object> getOrderCountRank(String timeDimension) {
        QueryRegionContrastParamDto paramDto = new QueryRegionContrastParamDto();
        Long endMilliSec = (new Date().getTime() / DashBoardUtil.MILLI_SEC_FOR_24HOURS + 1) * DashBoardUtil.MILLI_SEC_FOR_24HOURS;
        paramDto.setEndDate(endMilliSec);
        Calendar cal = Calendar.getInstance();
        switch(timeDimension){
            case ORDER_COUNT_RANK_PARAM_DAY:
                paramDto.setStartDate(endMilliSec - DashBoardUtil.MILLI_SEC_FOR_24HOURS);
                break;
            case ORDER_COUNT_RANK_PARAM_WEEK:
                int week = ((cal.get(Calendar.DAY_OF_WEEK) - 1) == 0)? 7 : (cal.get(Calendar.DAY_OF_WEEK) - 1);
                paramDto.setStartDate(endMilliSec - DashBoardUtil.MILLI_SEC_FOR_24HOURS * week);
                break;
            case ORDER_COUNT_RANK_PARAM_MONTH:
                int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
                paramDto.setStartDate(endMilliSec - DashBoardUtil.MILLI_SEC_FOR_24HOURS * dayOfMonth);
                break;
            case ORDER_COUNT_RANK_PARAM_YEAR:
                int dayOfYear = cal.get(Calendar.DAY_OF_YEAR);
                paramDto.setStartDate(endMilliSec - DashBoardUtil.MILLI_SEC_FOR_24HOURS * dayOfYear);
                break;
            default:
                break;
        }
        List<OrderCountRankDto> orderCountRankDtoList = geneDailyDetailMapper.getOrderCountByDateDimensionGroupByRegionId(paramDto);
        Map<String,Object> map = new HashMap<>();
        map.put("timeDimension",timeDimension);
        map.put("list",orderCountRankDtoList);
        return map;
    }

    @Override
    public DashboardOrderTrendResultDto getDayOrderTrend() {
        DashboardOrderTrendResultDto<DashboardOrderTrendParamDto> dashboardOrderTrendResultDto = new DashboardOrderTrendResultDto<>();
        List<DashboardOrderTrendParamDto> list = new ArrayList<>();
        //获取0-9
        DateRangeDto hourFirstRange = BusinessDateUtil.getDayHourTimeInMillis(0,9);
        DashboardOrderTrendDto orderTrendFirstDtos = geneDailyDetailMapper.queryOrderTrendHour(hourFirstRange);
        if (orderTrendFirstDtos == null) {
            orderTrendFirstDtos = new DashboardOrderTrendDto();
        }
        DashboardOrderTrendParamDto firstParamDto = this.getOrderTrendParamDto(orderTrendFirstDtos,"0-9");
        list.add(firstParamDto);

        //获取9-18
        DateRangeDto hourSecondRange = BusinessDateUtil.getDayHourTimeInMillis(9,18);
        List<DashboardOrderTrendDto> orderTrendDtos = geneDailyDetailMapper.queryOrderTrendHourGroup(hourSecondRange);

        for(int i=9;i<19;i++){
            DashboardOrderTrendParamDto nineParamDto = this.getHourOrderTrendDto(i,i+1,orderTrendDtos, String.valueOf(i));
            list.add(nineParamDto);
        }

        //获取19_24
        DateRangeDto hourThirdRange = BusinessDateUtil.getDayHourTimeInMillis(19,24);
        DashboardOrderTrendDto orderTrendThirdDtos = geneDailyDetailMapper.queryOrderTrendHour(hourThirdRange);
        if (orderTrendThirdDtos == null) {
            orderTrendThirdDtos = new DashboardOrderTrendDto();
        }
        DashboardOrderTrendParamDto thirdParamDto = this.getOrderTrendParamDto(orderTrendThirdDtos,"19-24");
        list.add(thirdParamDto);

        dashboardOrderTrendResultDto.setTimeDimension("day");
        dashboardOrderTrendResultDto.setList(list);
        return dashboardOrderTrendResultDto;
    }

    @Override
    public DashboardOrderTrendResultDto getWeekOrderTrend(){
        //获取周一和下周一的日期
        DateRangeStrDto weekDateRange = BusinessDateUtil.getWeekStartEndDateStr();
        //获取该周的每一天日期
        DashboardOrderTrendResultDto<DashboardOrderTrendParamDto> dashboardOrderTrendResultDto = new DashboardOrderTrendResultDto<>();
        List<DashboardOrderTrendParamDto> list = new ArrayList<>();
        List<DashboardOrderTrendDto> orderTrendDtos = geneDailyDetailMapper.queryOrderTrendWeek(weekDateRange);
        String[] weekDays = { "周一", "周二", "周三", "周四", "周五", "周六","周日"};

        for (String weekDay1 : weekDays) {
            if (orderTrendDtos.size() > 0) {
                for (int j = 0; j < orderTrendDtos.size(); j++) {
                    //获取该日期是周几
                    String weekDay = BusinessDateUtil.dateToWeek(orderTrendDtos.get(j).getDate());
                    if (weekDay1.equals(weekDay)) {
                        DashboardOrderTrendParamDto dashboardOrderTrendParamDto = new DashboardOrderTrendParamDto();
                        dashboardOrderTrendParamDto.setDate(weekDay1);
                        dashboardOrderTrendParamDto.setOrderPlaceCount(orderTrendDtos.get(j).getOrderPlaceCount());
                        double d = 1000;
                        BigDecimal bg = new BigDecimal(orderTrendDtos.get(j).getOrderPlaceCost() / d);
                        double orderPlaceCost = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                        dashboardOrderTrendParamDto.setOrderPlaceCost(orderPlaceCost);
                        list.add(dashboardOrderTrendParamDto);
                        break;
                    } else if (j == (orderTrendDtos.size() - 1)) {
                        DashboardOrderTrendParamDto dashboardOrderTrendParamDto = new DashboardOrderTrendParamDto();
                        dashboardOrderTrendParamDto.setDate(weekDay1);
                        list.add(dashboardOrderTrendParamDto);
                    }
                }
            } else {
                DashboardOrderTrendParamDto dashboardOrderTrendParamDto = new DashboardOrderTrendParamDto();
                dashboardOrderTrendParamDto.setDate(weekDay1);
                list.add(dashboardOrderTrendParamDto);
            }
        }
        dashboardOrderTrendResultDto.setTimeDimension("week");
        dashboardOrderTrendResultDto.setList(list);
        return dashboardOrderTrendResultDto;
    }

    @Override
    public DashboardOrderTrendResultDto getMonthOrderTrend() {
        //获取当月第一天和下月第一天
        DateRangeStrDto monthDateRange = BusinessDateUtil.getMonthStartEndDateStr();
        List<DashboardOrderTrendDto> orderTrendDtos = geneDailyDetailMapper.queryOrderTrendWeek(monthDateRange);
        //获取当月的总天数
        int days = BusinessDateUtil.getCurrentMonthLastDay();
        return this.getOrderTrendParamDtoList(orderTrendDtos,days,"month");
    }

    @Override
    public DashboardOrderTrendResultDto getYearOrderTrend() {
        //获取当年第一月和最后一月
        String year = BusinessDateUtil.getCurrentYearStr();
        DateRangeStrDto monthDateRange = new DateRangeStrDto();
        monthDateRange.setStartDateStr(year+"-01");
        monthDateRange.setEndDateStr(year+"-12");

        List<DashboardOrderTrendDto> orderTrendDtos = geneMonthlyDetailMapper.queryOrderTrendYear(monthDateRange);
        int days = 12;
        return this.getOrderTrendParamDtoList(orderTrendDtos,days,"year");
    }

    @Override
    public DashboardOrderTrendResultDto getMonthOrderTrendAvg() {
        //获取当年第一月和最后一月
        String year = BusinessDateUtil.getCurrentYearStr();
        DateRangeStrDto monthDateRange = new DateRangeStrDto();
        monthDateRange.setStartDateStr(year+"-01");
        monthDateRange.setEndDateStr(year+"-12");
        monthDateRange.setDate(BatchUtil.TYPE_GENE_ORDER_TREND_DETAIL);
        monthDateRange.setTimeRange(BatchUtil.TIME_RANGE_MONTH_STR);

        List<OrderTrendMonthResultDto> snapshootEntities = snapshootMapper.findSnapshootByMonthAvg(monthDateRange);
        int days = 12;
        int monthDay;
        List<OrderTrendMonthAvgResultDto> list = new ArrayList<>();
        for(int i=1;i<=days;i++){
            if(snapshootEntities.size()>0){
                for(int j=0;j<snapshootEntities.size();j++){

                    monthDay = BusinessDateUtil.getMonthByMonthStr(snapshootEntities.get(j).getMonthStr());

                    if(i == monthDay){
                        OrderTrendMonthAvgResultDto orderTrendMonthAvgResultDto = new OrderTrendMonthAvgResultDto();

                        orderTrendMonthAvgResultDto.setDate(String.valueOf(i)+'月');

                        JSONObject jsonObject = JSON.parseObject(snapshootEntities.get(j).getRecord());
                        OrderTrendMonthResultDto orderTrendMonthResultDto = jsonObject.toJavaObject(OrderTrendMonthResultDto.class);

                        orderTrendMonthAvgResultDto.setHolidayPlaceAvgCount(orderTrendMonthResultDto.getHolidayPlaceAvgCount());
                        orderTrendMonthAvgResultDto.setWorkPlaceAvgCount(orderTrendMonthResultDto.getWorkPlaceAvgCount());

                        double d = 1000;
                        BigDecimal bg = new BigDecimal(orderTrendMonthResultDto.getHolidayPlaceAvgCost()/d);
                        double holidayPlaceCost = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                        orderTrendMonthAvgResultDto.setHolidayPlaceAvgCost(holidayPlaceCost);
                        BigDecimal bg1 = new BigDecimal(orderTrendMonthResultDto.getWorkPlaceAvgCost()/d);
                        double workPlaceCost = bg1.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                        orderTrendMonthAvgResultDto.setWorkPlaceAvgCost(workPlaceCost);

                        list.add(orderTrendMonthAvgResultDto);
                        break;
                    }else if(j==(snapshootEntities.size()-1)){
                        OrderTrendMonthAvgResultDto orderTrendMonthResultDto = new OrderTrendMonthAvgResultDto();
                        orderTrendMonthResultDto.setDate(String.valueOf(i)+'月');
                        list.add(orderTrendMonthResultDto);
                    }
                }
            }else{
                OrderTrendMonthAvgResultDto orderTrendMonthResultDto = new OrderTrendMonthAvgResultDto();
                orderTrendMonthResultDto.setDate(String.valueOf(i)+'月');
                list.add(orderTrendMonthResultDto);
            }

        }
        DashboardOrderTrendResultDto<OrderTrendMonthAvgResultDto> dashboardOrderTrendResultDto = new DashboardOrderTrendResultDto<>();
        dashboardOrderTrendResultDto.setTimeDimension("year");
        dashboardOrderTrendResultDto.setList(list);
        return dashboardOrderTrendResultDto;
    }

    private DashboardOrderTrendParamDto getHourOrderTrendDto(int startHour,int endHour,List<DashboardOrderTrendDto> orderTrendDtos,String date){
        //获取9小时的
        DateRangeDto hourRange = BusinessDateUtil.getDayHourTimeInMillis(startHour,endHour);

        List<DashboardOrderTrendDto> orderTrendDtoHour = new ArrayList<>();
        //获取时间戳在9小时的记录
        for(DashboardOrderTrendDto orderTrendDto:orderTrendDtos){
            if(hourRange.getStartDate()<=orderTrendDto.getOrderCreateTime()&&orderTrendDto.getOrderCreateTime()<hourRange.getEndDate()){
                orderTrendDtoHour.add(orderTrendDto);
            }
        }
        Long orderPlaceCost = 0L;
        Long orderPlaceCount = 0L;
        for(DashboardOrderTrendDto orderTrendDto :orderTrendDtoHour){
            orderPlaceCost = orderPlaceCost+orderTrendDto.getOrderPlaceCost();
            orderPlaceCount = orderPlaceCount+orderTrendDto.getOrderPlaceCount();
        }
        DashboardOrderTrendDto orderTrendFourDtos = new DashboardOrderTrendDto();
        orderTrendFourDtos.setOrderPlaceCost(orderPlaceCost);
        orderTrendFourDtos.setOrderPlaceCount(orderPlaceCount);
        return this.getOrderTrendParamDto(orderTrendFourDtos,date);
    }

    private DashboardOrderTrendParamDto getOrderTrendParamDto(DashboardOrderTrendDto orderTrendFirstDtos,String date){
        DashboardOrderTrendParamDto firstParamDto = new DashboardOrderTrendParamDto();
        firstParamDto.setDate(date);
        firstParamDto.setOrderPlaceCount(orderTrendFirstDtos.getOrderPlaceCount());
        double d = 1000;
        BigDecimal bg = new BigDecimal(orderTrendFirstDtos.getOrderPlaceCost()/d);
        double orderPlaceCost = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        firstParamDto.setOrderPlaceCost(orderPlaceCost);
        return firstParamDto;
    }

    private DashboardOrderTrendResultDto getOrderTrendParamDtoList(List<DashboardOrderTrendDto> orderTrendDtos,int days,String timeDimension){
        List<DashboardOrderTrendParamDto> list = new ArrayList<>();
        int monthDay;
        for(int i=1;i<=days;i++){
            if(orderTrendDtos.size()>0){
                for(int j=0;j<orderTrendDtos.size();j++){
                    if(timeDimension.equals("month")){
                        //获取该日期是当月第几天
                        monthDay = BusinessDateUtil.getDateMonthByDay(orderTrendDtos.get(j).getDate());
                    }else{
                        monthDay = BusinessDateUtil.getMonthByMonthStr(orderTrendDtos.get(j).getDate());
                    }

                    if(i== monthDay){
                        DashboardOrderTrendParamDto dashboardOrderTrendParamDto = new DashboardOrderTrendParamDto();
                        if(timeDimension.equals("month")){
                            dashboardOrderTrendParamDto.setDate(String.valueOf(i));
                        }else{
                            dashboardOrderTrendParamDto.setDate(String.valueOf(i)+'月');
                        }

                        dashboardOrderTrendParamDto.setOrderPlaceCount(orderTrendDtos.get(j).getOrderPlaceCount());
                        double d = 1000;
                        BigDecimal bg = new BigDecimal(orderTrendDtos.get(j).getOrderPlaceCost()/d);
                        double orderPlaceCost = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                        dashboardOrderTrendParamDto.setOrderPlaceCost(orderPlaceCost);
                        list.add(dashboardOrderTrendParamDto);
                        break;
                    }else if(j==(orderTrendDtos.size()-1)){
                        DashboardOrderTrendParamDto dashboardOrderTrendParamDto = new DashboardOrderTrendParamDto();
                        if(timeDimension.equals("month")){
                            dashboardOrderTrendParamDto.setDate(String.valueOf(i));
                        }else{
                            dashboardOrderTrendParamDto.setDate(String.valueOf(i)+'月');
                        }
                        list.add(dashboardOrderTrendParamDto);
                    }
                }
            }else{
                DashboardOrderTrendParamDto dashboardOrderTrendParamDto = new DashboardOrderTrendParamDto();
                if(timeDimension.equals("month")){
                    dashboardOrderTrendParamDto.setDate(String.valueOf(i));
                }else{
                    dashboardOrderTrendParamDto.setDate(String.valueOf(i)+'月');
                }
                list.add(dashboardOrderTrendParamDto);
            }

        }

        DashboardOrderTrendResultDto<DashboardOrderTrendParamDto> dashboardOrderTrendResultDto = new DashboardOrderTrendResultDto<>();
        dashboardOrderTrendResultDto.setTimeDimension(timeDimension);
        dashboardOrderTrendResultDto.setList(list);
        return dashboardOrderTrendResultDto;

    }

    private DashboardProviceIncomeDto formatIncomeDto(String timeDimension, List<ProvinceSnapshootRecordDto> recordDtoList) {
        DashboardProviceIncomeDto incomeDto = new DashboardProviceIncomeDto();
        List<String> nameList = new ArrayList<>();
        List<LinkedHashMap> incomeList = new ArrayList<>();
        LinkedHashMap<String, Object> orderIncomeMap = new LinkedHashMap<>();
        orderIncomeMap.put("name", "订单收入");
        LinkedHashMap<String, Object> cashIncomeMap = new LinkedHashMap<>();
        cashIncomeMap.put("name", "现金收入");
        LinkedHashMap<String, Object> financeConfirmIncomeMap = new LinkedHashMap<>();
        financeConfirmIncomeMap.put("name", "财务确认收入");
        for (ProvinceSnapshootRecordDto dto: recordDtoList) {
            String provinceName = dto.getProvinceName();
            double d = 1000;
            BigDecimal bg = new BigDecimal(dto.getOrderIncome()/d);
            double orderIncome = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            BigDecimal bg2 = new BigDecimal(dto.getCashIncome()/d);
            double cashIncome = bg2.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            BigDecimal bg3 = new BigDecimal(dto.getFinanceConfirmIncome()/d);
            double financeConfirmIncome = bg3.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            nameList.add(provinceName);
            orderIncomeMap.put(provinceName, orderIncome);
            cashIncomeMap.put(provinceName, cashIncome);
            financeConfirmIncomeMap.put(provinceName, financeConfirmIncome);
        }
        incomeList.add(orderIncomeMap);
        incomeList.add(cashIncomeMap);
        incomeList.add(financeConfirmIncomeMap);
        incomeDto.setTimeDimension(timeDimension);
        incomeDto.setNames(nameList);
        incomeDto.setList(incomeList);
        return incomeDto;
    }

}
