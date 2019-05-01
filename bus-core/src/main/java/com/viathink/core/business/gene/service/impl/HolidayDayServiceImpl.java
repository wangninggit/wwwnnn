package com.viathink.core.business.gene.service.impl;

import com.viathink.core.business.gene.dto.HolidayParamDto;
import com.viathink.core.business.gene.mapper.HolidayDayMapper;
import com.viathink.core.business.gene.service.HolidayDayService;
import com.viathink.core.util.BusinessDate;
import com.viathink.core.util.BusinessDateUtil;
import com.viathink.core.util.HolidayUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class HolidayDayServiceImpl implements HolidayDayService {
    private final HolidayDayMapper holidayDayMapper;

    @Autowired
    public HolidayDayServiceImpl(HolidayDayMapper holidayDayMapper) {
        this.holidayDayMapper = holidayDayMapper;
    }

    @Override
    public void addHolidays(int year){
        //获取今年所有月
        List<Date> monthDate =  HolidayUtil.getMonthDate(year);
        for(Date date : monthDate){
            BusinessDate BusinessDate = BusinessDateUtil.getBusinessDate(date.getTime());
            List<String> holidays = null;
            try {
                holidays = HolidayUtil.getWeekday(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            HolidayParamDto holidayParamDto = new HolidayParamDto();
            holidayParamDto.setDays(holidays);
            holidayParamDto.setYearStr(BusinessDate.getYearStr());
            holidayParamDto.setMonthStr(BusinessDate.getMonthStr());
            holidayDayMapper.addHolidays(holidayParamDto);
        }
    }
}
