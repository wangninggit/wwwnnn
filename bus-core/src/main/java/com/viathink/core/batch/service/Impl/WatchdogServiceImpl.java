package com.viathink.core.batch.service.Impl;

import com.alibaba.fastjson.JSONObject;
import com.viathink.core.batch.dto.SnapRecreateDto;
import com.viathink.core.batch.service.*;
import com.viathink.core.business.gene.bean.ErrorLogEntity;
import com.viathink.core.business.gene.bean.EventBaseEntity;
import com.viathink.core.business.gene.bean.SnapshootEntity;
import com.viathink.core.business.gene.mapper.ErrorLogMapper;
import com.viathink.core.business.gene.mapper.SnapshootMapper;
import com.viathink.core.log.mapper.GeneLogMapper;
import com.viathink.core.monitor.dto.ErrorDetailFieldCheckDto;
import com.viathink.core.util.BatchUtil;
import com.viathink.core.util.BusinessDate;
import com.viathink.core.util.BusinessDateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class WatchdogServiceImpl implements WatchdogService {
    private Logger logger = LoggerFactory.getLogger(WatchdogServiceImpl.class);
    private final GeneLogMapper geneLogMapper;
    private final SnapshootMapper snapshootMapper;
    private final ErrorLogMapper errorLogMapper;
    private final BusinessDetailService businessDetailService;
    private final ProfitService profitService;
    private final ContrastService regionContrastService;
    private final ContrastService provinceContrastService;
    private final ContrastService testingItemContrastService;
    private final IncomeService incomeServiceImpl;

    @Autowired
    public WatchdogServiceImpl(GeneLogMapper geneLogMapper, SnapshootMapper snapshootMapper, ErrorLogMapper errorLogMapper, BusinessDetailService businessDetailService, ProfitService profitService, @Qualifier("regionContrastServiceImpl") ContrastService regionContrastService, @Qualifier("provinceContrastServiceImpl") ContrastService provinceContrastService, @Qualifier("testingItemContrastServiceImpl") ContrastService testingItemContrastService, IncomeService incomeServiceImpl) {
        this.geneLogMapper = geneLogMapper;
        this.snapshootMapper = snapshootMapper;
        this.errorLogMapper = errorLogMapper;
        this.businessDetailService = businessDetailService;
        this.profitService = profitService;
        this.regionContrastService = regionContrastService;
        this.provinceContrastService = provinceContrastService;
        this.testingItemContrastService = testingItemContrastService;
        this.incomeServiceImpl = incomeServiceImpl;
    }

    @Override
    public void watch(EventBaseEntity event, BusinessDate businessDate) {
        BusinessDate todayDate = BusinessDateUtil.getBusinessDate(System.currentTimeMillis());
        // 判断天，不等于才做出判断
        if (!todayDate.getDayStr().equals(businessDate.getDayStr())) {
            SnapshootEntity ins = new SnapshootEntity();
            ins.setTimeRange(BatchUtil.TIME_RANGE_DAY_STR);
            ins.setDayStr(businessDate.getDayStr());
            Long result = snapshootMapper.countSnapshootByTimeRangeAndDateStr(ins);
            if (result != 0) {
                logger.info("watch today: 重新生成快照，dayStr: " + businessDate.getDayStr());
                snapshootMapper.setReCreateTrueByTimeRangeAndDateStr(ins);
            } else {
                logger.error("watch today: 没有找到对应的快照，dayStr: " + businessDate.getDayStr());
                this.initEventBatchError(event, ins.getTimeRange(), ins.getDayStr());
            }
        }
        // 判断月
        if (!todayDate.getMonthStr().equals(businessDate.getMonthStr())) {
            SnapshootEntity ins = new SnapshootEntity();
            ins.setTimeRange(BatchUtil.TIME_RANGE_MONTH_STR);
            ins.setMonthStr(businessDate.getMonthStr());
            Long result = snapshootMapper.countSnapshootByTimeRangeAndDateStr(ins);
            if (result != 0) {
                logger.error("watch month: 重新生成快照，monthStr: " + businessDate.getMonthStr());
                snapshootMapper.setReCreateTrueByTimeRangeAndDateStr(ins);
            } else {
                logger.error("watch month: 没有找到对应的快照，monthStr: " + businessDate.getMonthStr());
                this.initEventBatchError(event, ins.getTimeRange(), ins.getMonthStr());
            }
        }
        // 判断季度
        if (!todayDate.getQuarterStr().equals(businessDate.getQuarterStr())) {
            SnapshootEntity ins = new SnapshootEntity();
            ins.setTimeRange(BatchUtil.TIME_RANGE_QUARTER_STR);
            ins.setQuarterStr(businessDate.getQuarterStr());
            Long result = snapshootMapper.countSnapshootByTimeRangeAndDateStr(ins);
            if (result != 0) {
                logger.info("watch quarter: 重新生成快照，quarterStr: " + businessDate.getQuarterStr());
                snapshootMapper.setReCreateTrueByTimeRangeAndDateStr(ins);
            } else {
                logger.error("watch quarter: 没有找到对应的快照，quarterStr: " + businessDate.getQuarterStr());
                this.initEventBatchError(event, ins.getTimeRange(), ins.getQuarterStr());
            }
        }
        // 判断半年
        if (!todayDate.getHalfYearStr().equals(businessDate.getHalfYearStr())) {
            SnapshootEntity ins = new SnapshootEntity();
            ins.setTimeRange(BatchUtil.TIME_RANGE_HALF_YEAR_STR);
            ins.setHalfYearStr(businessDate.getHalfYearStr());
            Long result = snapshootMapper.countSnapshootByTimeRangeAndDateStr(ins);
            if (result != 0) {
                logger.info("watch halfYear: 重新生成快照，halfYearStr: " + businessDate.getHalfYearStr());
                snapshootMapper.setReCreateTrueByTimeRangeAndDateStr(ins);
            } else {
                logger.error("watch halfYear: 没有找到对应的快照，halfYearStr: " + businessDate.getHalfYearStr());
                this.initEventBatchError(event, ins.getTimeRange(), ins.getHalfYearStr());
            }
        }
        // 判断年
        if (!todayDate.getYearStr().equals(businessDate.getYearStr())) {
            SnapshootEntity ins = new SnapshootEntity();
            ins.setTimeRange(BatchUtil.TIME_RANGE_YEAR_STR);
            ins.setYearStr(businessDate.getYearStr());
            Long result = snapshootMapper.countSnapshootByTimeRangeAndDateStr(ins);
            if (result != 0) {
                logger.info("watch year: 重新生成快照，yearStr: " + businessDate.getYearStr());
                snapshootMapper.setReCreateTrueByTimeRangeAndDateStr(ins);
            } else {
                logger.error("watch year: 没有找到对应的快照，yearStr: " + businessDate.getYearStr());
                this.initEventBatchError(event, ins.getTimeRange(), ins.getYearStr());
            }
        }
    }

    @Override
    public void handle() {
        List<SnapshootEntity> list = snapshootMapper.findSnapshootByRecreate(true);
        for (SnapshootEntity snapshoot : list) {
            String type = snapshoot.getType();
            switch (type) {
                case BatchUtil.TYPE_GENE_BUSINESS_DETAIL:
                    this.businessDetailSnapshoot(snapshoot);
                    break;
                case BatchUtil.TYPE_GENE_REGION_CONTRAST:
                    this.contrastSnapshoot(regionContrastService, snapshoot);
                    break;
                case BatchUtil.TYPE_GENE_PROVINCE_CONTRAST:
                    this.contrastSnapshoot(provinceContrastService, snapshoot);
                    break;
                case BatchUtil.TYPE_GENE_TESTING_ITEM_CONTRAST:
                    this.contrastSnapshoot(testingItemContrastService, snapshoot);
                    break;
                case BatchUtil.TYPE_GENE_PROFIT:
                    this.profitSnapshoot(snapshoot);
                    break;
                case BatchUtil.TYPE_GENE_INCOME_DETAIL:
                    this.incomeDetailSnapshoot(snapshoot);
                    break;
            }
        }
    }

    @Override
    public Boolean recordIdCheck(EventBaseEntity event) {
        Long count = geneLogMapper.countByRecordId(event.getRecordId());
        if (count > 0) {
            this.initRecordIdCheckError(event);
            return true;
        }
        return false;
    }

    @Override
    public void snapshotRecreate(BusinessDate startDate,BusinessDate endDate) {
        // 日表
        SnapRecreateDto day = new SnapRecreateDto();
        day.setStartDate(startDate.getDayStr());
        day.setEndDate(endDate.getDayStr());
        day.setTimeRange(BatchUtil.TIME_RANGE_DAY_STR);
        Long result = snapshootMapper.countSnapshotByTimeRangeAndDateRange(day);
        if (result != 0) {
            logger.info("watch today: 重新生成快照，dayStr: " + startDate.getDayStr()+"--"+endDate.getDayStr());
            snapshootMapper.setReCreateTrueByTimeRangeAndDateRange(day);
        } else {
            logger.error("watch today: 没有找到对应的快照，dayStr: " + startDate.getDayStr()+"--"+endDate.getDayStr());
        }

        // 月表
        SnapRecreateDto month = new SnapRecreateDto();
        month.setStartDate(startDate.getMonthStr());
        month.setEndDate(endDate.getMonthStr());
        month.setTimeRange(BatchUtil.TIME_RANGE_MONTH_STR);
        Long monthResult = snapshootMapper.countSnapshotByTimeRangeAndDateRange(month);
        if (monthResult != 0) {
            logger.info("watch today: 重新生成快照，monthStr: " + startDate.getMonthStr()+"--"+endDate.getMonthStr());
            snapshootMapper.setReCreateTrueByTimeRangeAndDateRange(month);
        } else {
            logger.error("watch today: 没有找到对应的快照，monthStr: " + startDate.getMonthStr()+"--"+endDate.getMonthStr());
        }

        // 半年表
        SnapRecreateDto halfYear = new SnapRecreateDto();
        halfYear.setStartDate(startDate.getHalfYearStr());
        halfYear.setEndDate(endDate.getHalfYearStr());
        halfYear.setTimeRange(BatchUtil.TIME_RANGE_HALF_YEAR_STR);
        Long halfYearResult = snapshootMapper.countSnapshotByTimeRangeAndDateRange(halfYear);
        if (halfYearResult != 0) {
            logger.info("watch today: 重新生成快照，halfYearStr: " + startDate.getHalfYearStr()+"--"+endDate.getHalfYearStr());
            snapshootMapper.setReCreateTrueByTimeRangeAndDateRange(halfYear);
        } else {
            logger.error("watch today: 没有找到对应的快照，halfYearStr: " + startDate.getHalfYearStr()+"--"+endDate.getHalfYearStr());
        }

        // 年表
        SnapRecreateDto year = new SnapRecreateDto();
        year.setStartDate(startDate.getYearStr());
        year.setEndDate(endDate.getYearStr());
        year.setTimeRange(BatchUtil.TIME_RANGE_YEAR_STR);
        Long yearResult = snapshootMapper.countSnapshotByTimeRangeAndDateRange(year);
        if (yearResult != 0) {
            logger.info("watch today: 重新生成快照，YearStr: " + startDate.getYearStr()+"--"+endDate.getYearStr());
            snapshootMapper.setReCreateTrueByTimeRangeAndDateRange(year);
        } else {
            logger.error("watch today: 没有找到对应的快照，YearStr: " + startDate.getYearStr()+"--"+endDate.getYearStr());
        }

    }

    private void initRecordIdCheckError(EventBaseEntity event) {
        logger.error("recordIdCheck recordId重复，recordId: " + event.getRecordId());
        ErrorLogEntity resultDto = new ErrorLogEntity();
        ErrorDetailFieldCheckDto fieldCheckDto = new ErrorDetailFieldCheckDto();
        fieldCheckDto.setEvent(event.getEvent());
        fieldCheckDto.setEventTime(event.getEventTime());
        fieldCheckDto.setMessageId(event.getMessageId());
        fieldCheckDto.setRecordId(event.getRecordId());
        fieldCheckDto.setTag(event.getTag());
        List<String> list = new ArrayList<>();
        list.add("recordId重复，recordId: " + event.getRecordId());
        fieldCheckDto.setErrorList(list);
        resultDto.setError("recordId重复，recordId: " + event.getRecordId());
        resultDto.setType(BatchUtil.ERROR_TYPE_BUSINESS);
        resultDto.setDetail(JSONObject.toJSONString(fieldCheckDto));
        resultDto.setStatus(false);
        errorLogMapper.addErrorLog(resultDto);
    }

    private void initEventBatchError(EventBaseEntity event, String timeRange, String dateStr) {
        ErrorLogEntity resultDto = new ErrorLogEntity();
        ErrorDetailFieldCheckDto fieldCheckDto = new ErrorDetailFieldCheckDto();
        fieldCheckDto.setEvent(event.getEvent());
        fieldCheckDto.setEventTime(event.getEventTime());
        fieldCheckDto.setMessageId(event.getMessageId());
        fieldCheckDto.setRecordId(event.getRecordId());
        fieldCheckDto.setTag(event.getTag());
        List<String> list = new ArrayList<>();
        list.add("watch " + timeRange + ": 没有找到对应的快照，yearStr: " + dateStr);
        fieldCheckDto.setErrorList(list);
        resultDto.setError("报表快照检查异常");
        resultDto.setType(BatchUtil.ERROR_TYPE_BATCH);
        resultDto.setDetail(JSONObject.toJSONString(fieldCheckDto));
        resultDto.setStatus(false);
        errorLogMapper.addErrorLog(resultDto);
    }

    private void businessDetailSnapshoot(SnapshootEntity snapshoot) {
        String timeRange = snapshoot.getTimeRange();
        switch (timeRange) {
            case BatchUtil.TIME_RANGE_DAY_STR:
                businessDetailService.getDailyDetailReport(BusinessDateUtil.dayStrToDate(snapshoot.getDayStr()));
                break;
            case BatchUtil.TIME_RANGE_MONTH_STR:
                businessDetailService.getMonthDetailReport(BusinessDateUtil.monthStrToDate(snapshoot.getMonthStr()));
                break;
            case BatchUtil.TIME_RANGE_QUARTER_STR:
                break;
            case BatchUtil.TIME_RANGE_HALF_YEAR_STR:
                businessDetailService.getHalfYearDetailReport(BusinessDateUtil.halfYearStrToDate(snapshoot.getHalfYearStr()));
                break;
            case BatchUtil.TIME_RANGE_YEAR_STR:
                businessDetailService.getYearDetailReport(BusinessDateUtil.yearStrToDate(snapshoot.getYearStr()));
                break;
        }
    }

    private void contrastSnapshoot(ContrastService contrastService, SnapshootEntity snapshoot) {
        String timeRange = snapshoot.getTimeRange();
        switch (timeRange) {
            case BatchUtil.TIME_RANGE_DAY_STR:
                contrastService.dailySnapshoot(BusinessDateUtil.dayStrToDate(snapshoot.getDayStr()));
                break;
            case BatchUtil.TIME_RANGE_MONTH_STR:
                contrastService.monthlySnapshoot(BusinessDateUtil.monthStrToDate(snapshoot.getMonthStr()));
                break;
            case BatchUtil.TIME_RANGE_QUARTER_STR:
                break;
            case BatchUtil.TIME_RANGE_HALF_YEAR_STR:
                contrastService.halfYearlySnapshoot(BusinessDateUtil.halfYearStrToDate(snapshoot.getHalfYearStr()));
                break;
            case BatchUtil.TIME_RANGE_YEAR_STR:
                contrastService.yearlySnapshoot(BusinessDateUtil.yearStrToDate(snapshoot.getYearStr()));
                break;
        }
    }

    private void profitSnapshoot(SnapshootEntity snapshoot) {
        String timeRange = snapshoot.getTimeRange();
        switch (timeRange) {
            case BatchUtil.TIME_RANGE_MONTH_STR:
                profitService.getMonthDetailProfitReport(BusinessDateUtil.monthStrToDate(snapshoot.getMonthStr()));
                break;
            case BatchUtil.TIME_RANGE_QUARTER_STR:
                break;
            case BatchUtil.TIME_RANGE_HALF_YEAR_STR:
                profitService.getHalfYearDetailProfitReport(BusinessDateUtil.halfYearStrToDate(snapshoot.getHalfYearStr()));
                break;
            case BatchUtil.TIME_RANGE_YEAR_STR:
                profitService.getYearDetailProfitReport(BusinessDateUtil.yearStrToDate(snapshoot.getYearStr()));
                break;
        }
    }

    private void incomeDetailSnapshoot(SnapshootEntity snapshoot) {
        String timeRange = snapshoot.getTimeRange();
        switch (timeRange) {
            case BatchUtil.TIME_RANGE_MONTH_STR:
                incomeServiceImpl.getMonthDetailReport(BusinessDateUtil.monthStrToDate(snapshoot.getMonthStr()));
                break;
        }
    }
}
