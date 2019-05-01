package com.viathink.core.batch.scheduler.service.impl;

import com.viathink.core.batch.scheduler.service.SchedulerTaskService;
import com.viathink.core.batch.service.*;
import com.viathink.core.business.gene.bean.SnapshootEntity;
import com.viathink.core.business.gene.mapper.SnapshootMapper;
import com.viathink.core.util.BatchUtil;
import com.viathink.core.util.DayListUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

@Component
public class GeneSchedulerTaskServiceImpl implements SchedulerTaskService {
    @Value("${sys.schedulerTaskStartDate}")
    private String startDate;
    private Logger logger = LoggerFactory.getLogger(GeneSchedulerTaskServiceImpl.class);

    private final SnapshootMapper snapshootMapper;
    private final BusinessDetailService businessDetailService;
    private final ProfitService profitService;
    private final ContrastService regionContrastService;
    private final ContrastService provinceContrastService;
    private final ContrastService testingItemContrastService;
    private final IncomeService incomeServiceImpl;
    private final MonthAvgRrendService monthAvgRrendService;

    @Autowired
    public GeneSchedulerTaskServiceImpl(SnapshootMapper snapshootMapper, BusinessDetailService businessDetailService, ProfitService profitService, @Qualifier("regionContrastServiceImpl") ContrastService regionContrastService, @Qualifier("provinceContrastServiceImpl") ContrastService provinceContrastService, @Qualifier("testingItemContrastServiceImpl") ContrastService testingItemContrastService, IncomeService incomeServiceImpl, MonthAvgRrendService monthAvgRrendService) {
        this.snapshootMapper = snapshootMapper;
        this.businessDetailService = businessDetailService;
        this.profitService = profitService;
        this.regionContrastService = regionContrastService;
        this.provinceContrastService = provinceContrastService;
        this.testingItemContrastService = testingItemContrastService;
        this.incomeServiceImpl = incomeServiceImpl;
        this.monthAvgRrendService = monthAvgRrendService;
    }

    public void task() {
        try {
            regionTask();
            businessTask();
            profitTask();
            provinceTask();
            TestItemTask();
            incomeTask();
            orderTrendMonthAvgTask();
        }catch(ParseException e){
            logger.error("geneSchedulerTask dateStr ParseException: " + e.getMessage());
        }
    }

    private void regionTask(){
        logger.info("开始执行大区对比定时任务...");
        SnapshootEntity snapshootEntity = new SnapshootEntity();
        snapshootEntity.setType(BatchUtil.TYPE_GENE_REGION_CONTRAST);

        // 更新日快照
        snapshootEntity.setTimeRange(BatchUtil.TIME_RANGE_DAY_STR);
        snapshootEntity = snapshootMapper.findSnapshootByLatestDate(snapshootEntity);
        String dateStr;
        if(snapshootEntity == null){
            dateStr = startDate;
        }else{
            dateStr = snapshootEntity.getDayStr();
        }

        List<Date> dateList;
        try {
            dateList = DayListUtil.dayList(dateStr);
            for(Date date:dateList){
                regionContrastService.dailySnapshoot(date);
            }
        } catch (ParseException e) {
            logger.error("regionTask: dayStr parse Date ERROR; dayStr = "+dateStr);
        }


        // 月
        snapshootEntity = new SnapshootEntity();
        snapshootEntity.setType(BatchUtil.TYPE_GENE_REGION_CONTRAST);
        snapshootEntity.setTimeRange(BatchUtil.TIME_RANGE_MONTH_STR);
        snapshootEntity = snapshootMapper.findSnapshootByLatestDate(snapshootEntity);
        if(snapshootEntity == null){
            dateStr = startDate;
        }else{
            dateStr = snapshootEntity.getMonthStr();
        }

        try {
            dateList = DayListUtil.monthList(dateStr);
            for(Date date:dateList){
                regionContrastService.monthlySnapshoot(date);
            }
        } catch (ParseException e) {
            logger.error("regionTask: monthStr parse Date ERROR; monthStr = "+dateStr);
        }

        // 半年
        snapshootEntity = new SnapshootEntity();
        snapshootEntity.setType(BatchUtil.TYPE_GENE_REGION_CONTRAST);
        snapshootEntity.setTimeRange(BatchUtil.TIME_RANGE_HALF_YEAR_STR);
        snapshootEntity = snapshootMapper.findSnapshootByLatestDate(snapshootEntity);
        if(snapshootEntity == null){
            dateStr = startDate;
        }else{
            dateStr = snapshootEntity.getHalfYearStr();
        }

        if((dateList = DayListUtil.halfYearList(dateStr)) != null){
            for(Date date:dateList){
                regionContrastService.halfYearlySnapshoot(date);
            }
        }

        // 年
        snapshootEntity = new SnapshootEntity();
        snapshootEntity.setType(BatchUtil.TYPE_GENE_REGION_CONTRAST);
        snapshootEntity.setTimeRange(BatchUtil.TIME_RANGE_YEAR_STR);
        snapshootEntity = snapshootMapper.findSnapshootByLatestDate(snapshootEntity);
        if(snapshootEntity == null){
            dateStr = startDate;
        }else{
            dateStr = snapshootEntity.getYearStr();
        }

        try {
            dateList = DayListUtil.yearList(dateStr);
            for(Date date:dateList){
                regionContrastService.yearlySnapshoot(date);
            }
        } catch (ParseException e) {
            logger.error("regionTask: yearStr parse Date ERROR; yearStr = "+dateStr);
        }
        logger.info("执行大区对比定时任务完成...");
    }

    private void businessTask() throws ParseException{
        logger.info("开始执行业务明细定时任务...");
        //每日操作
        SnapshootEntity day = new SnapshootEntity();
        day.setType(BatchUtil.TYPE_GENE_BUSINESS_DETAIL);
        day.setTimeRange(BatchUtil.TIME_RANGE_DAY_STR);
        SnapshootEntity dayLatest = snapshootMapper.findSnapshootByLatestDate(day);
        String day_str;
        if(dayLatest == null){
            day_str = startDate;
        }else{
            day_str = dayLatest.getDayStr();
        }
        List<Date> dayList = DayListUtil.dayList(day_str);
        for(Date dayDate : dayList){
            businessDetailService.getDailyDetailReport(dayDate);
        }

        //每月操作
        String month_str;
        SnapshootEntity month = new SnapshootEntity();
        month.setType(BatchUtil.TYPE_GENE_BUSINESS_DETAIL);
        month.setTimeRange(BatchUtil.TIME_RANGE_MONTH_STR);
        SnapshootEntity monthLatest = snapshootMapper.findSnapshootByLatestDate(month);
        if(monthLatest == null){
            month_str = startDate;
        }else{
            month_str = monthLatest.getMonthStr();
        }
        List<Date>  monthList = DayListUtil.monthList(month_str);
        for(Date monthDate : monthList){
            businessDetailService.getMonthDetailReport(monthDate);
        }

        // 半年
        String dateStr;
        List<Date> dateList;
        SnapshootEntity snapshootEntity = new SnapshootEntity();
        snapshootEntity.setType(BatchUtil.TYPE_GENE_BUSINESS_DETAIL);
        snapshootEntity.setTimeRange(BatchUtil.TIME_RANGE_HALF_YEAR_STR);
        snapshootEntity = snapshootMapper.findSnapshootByLatestDate(snapshootEntity);
        if(snapshootEntity == null){
            dateStr = startDate;
        }else{
            dateStr = snapshootEntity.getHalfYearStr();
        }

        if((dateList = DayListUtil.halfYearList(dateStr)) != null){
            for(Date date:dateList){
                businessDetailService.getHalfYearDetailReport(date);
            }
        }
        //每年操作
        String year_str;
        SnapshootEntity year = new SnapshootEntity();
        year.setType(BatchUtil.TYPE_GENE_BUSINESS_DETAIL);
        year.setTimeRange(BatchUtil.TIME_RANGE_YEAR_STR);
        SnapshootEntity yearLatest = snapshootMapper.findSnapshootByLatestDate(year);
        if(yearLatest == null){
            year_str = startDate;
        }else{
            year_str = yearLatest.getYearStr();
        }
        List<Date>  yearList = DayListUtil.yearList(year_str);
        for(Date yearDate : yearList){
            businessDetailService.getYearDetailReport(yearDate);
        }
        logger.info("执行业务明细定时任务完成...");
    }

    private void profitTask() throws ParseException{
        logger.info("开始执行利润定时任务...");
        //每月操作
        String month_str;
        SnapshootEntity month = new SnapshootEntity();
        month.setType(BatchUtil.TYPE_GENE_PROFIT);
        month.setTimeRange(BatchUtil.TIME_RANGE_MONTH_STR);
        SnapshootEntity monthLatest = snapshootMapper.findSnapshootByLatestDate(month);
        if(monthLatest == null){
            month_str = startDate;
        }else{
            month_str = monthLatest.getMonthStr();
        }
        List<Date>  monthList = DayListUtil.monthList(month_str);
        for(Date monthDate : monthList){
            profitService.getMonthDetailProfitReport(monthDate);
        }
        // 半年
        String dateStr;
        List<Date> dateList;
        SnapshootEntity snapshootEntity = new SnapshootEntity();
        snapshootEntity.setType(BatchUtil.TYPE_GENE_PROFIT);
        snapshootEntity.setTimeRange(BatchUtil.TIME_RANGE_HALF_YEAR_STR);
        snapshootEntity = snapshootMapper.findSnapshootByLatestDate(snapshootEntity);
        if(snapshootEntity == null){
            dateStr = startDate;
        }else{
            dateStr = snapshootEntity.getHalfYearStr();
        }

        if((dateList = DayListUtil.halfYearList(dateStr)) != null){
            for(Date date:dateList){
                profitService.getHalfYearDetailProfitReport(date);
            }
        }
        //每年操作
        String year_str;
        SnapshootEntity year = new SnapshootEntity();
        year.setType(BatchUtil.TYPE_GENE_PROFIT);
        year.setTimeRange(BatchUtil.TIME_RANGE_YEAR_STR);
        SnapshootEntity yearLatest = snapshootMapper.findSnapshootByLatestDate(year);
        if(yearLatest == null){
            year_str = startDate;
        }else{
            year_str = yearLatest.getYearStr();
        }
        List<Date>  yearList = DayListUtil.yearList(year_str);
        for(Date yearDate : yearList){
            profitService.getYearDetailProfitReport(yearDate);
        }
        logger.info("执行利润定时任务完成...");
    }

    private void provinceTask() throws ParseException{
        logger.info("开始执行省份对比定时任务...");
        //每日操作
        SnapshootEntity day = new SnapshootEntity();
        day.setType(BatchUtil.TYPE_GENE_PROVINCE_CONTRAST);
        day.setTimeRange(BatchUtil.TIME_RANGE_DAY_STR);
        SnapshootEntity dayLatest = snapshootMapper.findSnapshootByLatestDate(day);
        String day_str;
        if(dayLatest == null){
            day_str = startDate;
        }else{
            day_str = dayLatest.getDayStr();
        }
        List<Date> dayList = DayListUtil.dayList(day_str);
        for(Date dayDate : dayList){
            provinceContrastService.dailySnapshoot(dayDate);
        }

        //每月操作
        String month_str;
        SnapshootEntity month = new SnapshootEntity();
        month.setType(BatchUtil.TYPE_GENE_PROVINCE_CONTRAST);
        month.setTimeRange(BatchUtil.TIME_RANGE_MONTH_STR);
        SnapshootEntity monthLatest = snapshootMapper.findSnapshootByLatestDate(month);
        if(monthLatest == null){
            month_str = startDate;
        }else{
            month_str = monthLatest.getMonthStr();
        }
        List<Date>  monthList = DayListUtil.monthList(month_str);
        for(Date monthDate : monthList){
            provinceContrastService.monthlySnapshoot(monthDate);
        }
        // 半年
        String dateStr;
        List<Date> dateList;
        SnapshootEntity snapshootEntity = new SnapshootEntity();
        snapshootEntity.setType(BatchUtil.TYPE_GENE_PROVINCE_CONTRAST);
        snapshootEntity.setTimeRange(BatchUtil.TIME_RANGE_HALF_YEAR_STR);
        snapshootEntity = snapshootMapper.findSnapshootByLatestDate(snapshootEntity);
        if(snapshootEntity == null){
            dateStr = startDate;
        }else{
            dateStr = snapshootEntity.getHalfYearStr();
        }

        if((dateList = DayListUtil.halfYearList(dateStr)) != null){
            for(Date date:dateList){
                provinceContrastService.halfYearlySnapshoot(date);
            }
        }
        //每年操作
        String year_str;
        SnapshootEntity year = new SnapshootEntity();
        year.setType(BatchUtil.TYPE_GENE_PROVINCE_CONTRAST);
        year.setTimeRange(BatchUtil.TIME_RANGE_YEAR_STR);
        SnapshootEntity yearLatest = snapshootMapper.findSnapshootByLatestDate(year);
        if(yearLatest == null){
            year_str = startDate;
        }else{
            year_str = yearLatest.getYearStr();
        }
        List<Date>  yearList = DayListUtil.yearList(year_str);
        for(Date yearDate : yearList){
            provinceContrastService.yearlySnapshoot(yearDate);
        }
        logger.info("执行省份对比定时任务完成...");
    }

    private void TestItemTask() throws ParseException{
        logger.info("开始执行检测套餐对比定时任务...");
        //每日操作
        SnapshootEntity day = new SnapshootEntity();
        day.setType(BatchUtil.TYPE_GENE_TESTING_ITEM_CONTRAST);
        day.setTimeRange(BatchUtil.TIME_RANGE_DAY_STR);
        SnapshootEntity dayLatest = snapshootMapper.findSnapshootByLatestDate(day);
        String day_str;
        if(dayLatest == null){
            day_str = startDate;
        }else{
            day_str = dayLatest.getDayStr();
        }
        List<Date> dayList = DayListUtil.dayList(day_str);
        for(Date dayDate : dayList){
            testingItemContrastService.dailySnapshoot(dayDate);
        }

        //每月操作
        String month_str;
        SnapshootEntity month = new SnapshootEntity();
        month.setType(BatchUtil.TYPE_GENE_TESTING_ITEM_CONTRAST);
        month.setTimeRange(BatchUtil.TIME_RANGE_MONTH_STR);
        SnapshootEntity monthLatest = snapshootMapper.findSnapshootByLatestDate(month);
        if(monthLatest == null){
            month_str = startDate;
        }else{
            month_str = monthLatest.getMonthStr();
        }
        List<Date>  monthList = DayListUtil.monthList(month_str);
        for(Date monthDate : monthList){
            testingItemContrastService.monthlySnapshoot(monthDate);
        }
        // 半年
        String dateStr;
        List<Date> dateList;
        SnapshootEntity snapshootEntity = new SnapshootEntity();
        snapshootEntity.setType(BatchUtil.TYPE_GENE_TESTING_ITEM_CONTRAST);
        snapshootEntity.setTimeRange(BatchUtil.TIME_RANGE_HALF_YEAR_STR);
        snapshootEntity = snapshootMapper.findSnapshootByLatestDate(snapshootEntity);
        if(snapshootEntity == null){
            dateStr = startDate;
        }else{
            dateStr = snapshootEntity.getHalfYearStr();
        }

        if((dateList = DayListUtil.halfYearList(dateStr)) != null){
            for(Date date:dateList){
                testingItemContrastService.halfYearlySnapshoot(date);
            }
        }
        //每年操作
        String year_str;
        SnapshootEntity year = new SnapshootEntity();
        year.setType(BatchUtil.TYPE_GENE_TESTING_ITEM_CONTRAST);
        year.setTimeRange(BatchUtil.TIME_RANGE_YEAR_STR);
        SnapshootEntity yearLatest = snapshootMapper.findSnapshootByLatestDate(year);
        if(yearLatest == null){
            year_str = startDate;
        }else{
            year_str = yearLatest.getYearStr();
        }
        List<Date>  yearList = DayListUtil.yearList(year_str);
        for(Date yearDate : yearList){
            testingItemContrastService.yearlySnapshoot(yearDate);
        }
        logger.info("执行检测套餐对比定时任务完成...");
    }

    private void incomeTask() throws ParseException{
        logger.info("开始执行收入明细定时任务...");
        //每月操作
        String month_str;
        SnapshootEntity month = new SnapshootEntity();
        month.setType(BatchUtil.TYPE_GENE_INCOME_DETAIL);
        month.setTimeRange(BatchUtil.TIME_RANGE_MONTH_STR);
        SnapshootEntity monthLatest = snapshootMapper.findSnapshootByLatestDate(month);
        if(monthLatest == null){
            month_str = startDate;
        }else{
            month_str = monthLatest.getMonthStr();
        }
        List<Date>  monthList = DayListUtil.monthList(month_str);
        for(Date monthDate : monthList){
            incomeServiceImpl.getMonthDetailReport(monthDate);
        }
        logger.info("执行收入明细定时任务完成...");
    }

    private void orderTrendMonthAvgTask() throws ParseException{
        //每月操作
        String month_str;
        SnapshootEntity month = new SnapshootEntity();
        month.setType(BatchUtil.TYPE_GENE_ORDER_TREND_DETAIL);
        month.setTimeRange(BatchUtil.TIME_RANGE_MONTH_STR);
        SnapshootEntity monthLatest = snapshootMapper.findSnapshootByLatestDate(month);
        if(monthLatest == null){
            month_str = startDate;
        }else{
            month_str = monthLatest.getMonthStr();
        }
        List<Date>  monthList = DayListUtil.monthList(month_str);
        for(Date monthDate : monthList){
            monthAvgRrendService.getMonthAvgReport(monthDate);
        }
    }
}
