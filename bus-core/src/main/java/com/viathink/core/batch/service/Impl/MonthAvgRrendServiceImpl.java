package com.viathink.core.batch.service.Impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.viathink.core.batch.dao.SnapshootDao;
import com.viathink.core.batch.dto.HolidayDateDto;
import com.viathink.core.batch.dto.OrderTrendMonthDto;
import com.viathink.core.batch.dto.OrderTrendMonthParamDto;
import com.viathink.core.batch.dto.OrderTrendMonthResultDto;
import com.viathink.core.batch.service.MonthAvgRrendService;
import com.viathink.core.business.gene.bean.SnapshootEntity;
import com.viathink.core.business.gene.mapper.GeneDailyDetailMapper;
import com.viathink.core.business.gene.mapper.HolidayDayMapper;
import com.viathink.core.business.gene.service.HolidayDayService;
import com.viathink.core.util.BatchUtil;
import com.viathink.core.util.BusinessDate;
import com.viathink.core.util.BusinessDateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Transactional
public class MonthAvgRrendServiceImpl implements MonthAvgRrendService {
    private final GeneDailyDetailMapper geneDailyDetailMapper;
    private final SnapshootDao snapshootDao;
    private final HolidayDayMapper holidayDayMapper;
    private final HolidayDayService holidayDayService;

    @Autowired
    public MonthAvgRrendServiceImpl(GeneDailyDetailMapper geneDailyDetailMapper, SnapshootDao snapshootDao, HolidayDayMapper holidayDayMapper, HolidayDayService holidayDayService) {
        this.geneDailyDetailMapper = geneDailyDetailMapper;
        this.snapshootDao = snapshootDao;
        this.holidayDayMapper = holidayDayMapper;
        this.holidayDayService = holidayDayService;
    }

    @Override
    public void getMonthAvgReport(Date date) {
        BusinessDate businessDate = BusinessDateUtil.getBusinessDate(date.getTime());
        String monthStr = businessDate.getMonthStr();

        //1.获取当前月的节假日和工作日天数
        HolidayDateDto holidayDateDto =  BusinessDateUtil.getHolidayDates(date,holidayDayMapper,holidayDayService);

        //2.分别查出当前月的节假日和工作日数据
        OrderTrendMonthParamDto holiday = new OrderTrendMonthParamDto();
        holiday.setHolidayList(holidayDateDto.getHolidayList());
        holiday.setDays(holidayDateDto.getHolidayList().size());
        OrderTrendMonthDto holidayMonth = geneDailyDetailMapper.getOrderTrendMonthAvg(holiday);

        OrderTrendMonthParamDto work = new OrderTrendMonthParamDto();
        work.setHolidayList(holidayDateDto.getWorkList());
        work.setDays(holidayDateDto.getWorkList().size());
        OrderTrendMonthDto workMonth = geneDailyDetailMapper.getOrderTrendMonthAvg(work);

        OrderTrendMonthResultDto orderTrendMonthResultDto = new OrderTrendMonthResultDto();
        orderTrendMonthResultDto.setHolidayPlaceAvgCost(holidayMonth.getOrderPlaceAvgCost());
        orderTrendMonthResultDto.setHolidayPlaceAvgCount(holidayMonth.getOrderPlaceAvgCount());
        orderTrendMonthResultDto.setWorkPlaceAvgCost(workMonth.getOrderPlaceAvgCost());
        orderTrendMonthResultDto.setWorkPlaceAvgCount(workMonth.getOrderPlaceAvgCount());
        orderTrendMonthResultDto.setDate(monthStr);

        //3.插入到快照表中
        SnapshootEntity snapshootEntity = new SnapshootEntity();
        snapshootEntity.setType(BatchUtil.TYPE_GENE_ORDER_TREND_DETAIL);
        snapshootEntity = BatchUtil.setDatelyStr(snapshootEntity,businessDate);
        snapshootEntity.setTimeRange(BatchUtil.TIME_RANGE_MONTH_STR);
        snapshootEntity.setRecord(JSON.toJSONString(orderTrendMonthResultDto,SerializerFeature.WriteMapNullValue));
        snapshootDao.addOrUpdateSnapshootById(snapshootEntity);

    }
}
