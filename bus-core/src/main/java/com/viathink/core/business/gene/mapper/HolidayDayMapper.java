package com.viathink.core.business.gene.mapper;

import com.viathink.core.business.gene.bean.HolidayDayEntity;
import com.viathink.core.business.gene.dto.HolidayParamDto;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HolidayDayMapper {
    void addHolidays(HolidayParamDto holidayParamDto);
    List<HolidayDayEntity> findHolidaysByMonthStr(String monthStr);
    void deleteHolidaysByYearStr(String yearStr);
}
