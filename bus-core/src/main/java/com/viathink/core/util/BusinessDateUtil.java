package com.viathink.core.util;

import com.viathink.core.batch.dto.HolidayDateDto;
import com.viathink.core.business.gene.bean.HolidayDayEntity;
import com.viathink.core.business.gene.dto.DateRangeDto;
import com.viathink.core.business.gene.dto.DateRangeStrDto;
import com.viathink.core.business.gene.mapper.HolidayDayMapper;
import com.viathink.core.business.gene.service.HolidayDayService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class BusinessDateUtil {

    public static BusinessDate getBusinessDate(Long eventTime){
        BusinessDate BusinessDate = new BusinessDate();
        Date d = new Date(eventTime);
        BusinessDate.setDayStr(new SimpleDateFormat("yyyy-MM-dd").format(d));
        BusinessDate.setMonthStr(new SimpleDateFormat("yyyy-MM").format(d));
        BusinessDate.setYearStr(new SimpleDateFormat("yyyy").format(d));
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        int month = cal.get(Calendar.MONTH);
        int quarter = month/3 + 1;
        int halfYear = month/6 + 1;
        BusinessDate.setQuarterStr(BusinessDate.getYearStr() + "-q" + quarter);
        BusinessDate.setHalfYearStr(BusinessDate.getYearStr() + "-h" + halfYear);
        return BusinessDate;
    }
    public static Date dayStrToDate(String dayStr) {
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
        try {
            return df.parse(dayStr);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
    public static Date monthStrToDate(String monthStr) {
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM");
        try {
            return df.parse(monthStr);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
    public static Date yearStrToDate(String yearStr) {
        SimpleDateFormat df=new SimpleDateFormat("yyyy");
        try {
            return df.parse(yearStr);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
    public static Date halfYearStrToDate(String halfYearStr) {
        String [] splits = halfYearStr.split("-");
        String year = splits[0];
        String half = splits[1];
        String dateStr;
        if (half.equals("h1")) {
            dateStr= year + "-03";
        } else {
            dateStr= year + "-09";
        }
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");
        try {
            return df.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
    // 前开后闭区间 >=start <end
    public static DateRangeStrDto getWeekStartEndDateStr() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        Date start = cal.getTime();
        String startStr = dateFormat.format(start);
        // 下周一日期，小于此日期即可
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(start);
        cal2.add(Calendar.DAY_OF_WEEK, 7);
        Date end = cal2.getTime();
        String endStr = dateFormat.format(end);
        DateRangeStrDto dateRangeStrDto = new DateRangeStrDto();
        dateRangeStrDto.setStartDateStr(startStr);
        dateRangeStrDto.setEndDateStr(endStr);
        return dateRangeStrDto;
    }
    public static String getCurrentMonthStr() {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
        return dateFormat.format(date);
    }
    public static String getCurrentYearStr() {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
        return dateFormat.format(date);
    }

    private static List<String> weekDayList(String startDayStr, String endDayStr) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dt = sdf.parse(startDayStr);
        Date de = sdf.parse(endDayStr);

        Instant instant = dt.toInstant();
        Instant instant1 = de.toInstant();

        ZoneId zoneId = ZoneId.systemDefault();
        LocalDate startDate = instant.atZone(zoneId).toLocalDate();
        LocalDate endDate = instant1.atZone(zoneId).toLocalDate();

        int days = (int)startDate.until(endDate, ChronoUnit.DAYS);

        List<String> list = new ArrayList<>();
        list.add(startDayStr);
        for (int i = days - 1; i > 0; i -=1) {
            int diff = 0 - i;
            Calendar cal = Calendar.getInstance();
            cal.setTime(de);
            cal.add(Calendar.DATE, diff);

            String oldDay = new SimpleDateFormat( "yyyy-MM-dd").format(cal.getTime());
            list.add(oldDay);
        }
        return list;
    }
    //日期转周
    public static String dateToWeek(String datetime) {
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        String[] weekDays = { "周日", "周一", "周二", "周三", "周四", "周五", "周六" };
        Calendar cal = Calendar.getInstance(); // 获得一个日历
        Date datet;
        try {
            datet = f.parse(datetime);
            cal.setTime(datet);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1; // 指示一个星期中的某天。
        if (w < 0)
            w = 0;
        return weekDays[w];
    }

    //获取当月和下月第一天
    public static DateRangeStrDto getMonthStartEndDateStr(){
        // 获取当月第一天和最后一天
        Calendar cale;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String firstday,nextMonthFirstDay;
        // 获取当月的第一天
        cale = Calendar.getInstance();
        cale.add(Calendar.MONTH, 0);
        cale.set(Calendar.DAY_OF_MONTH, 1);
        firstday = format.format(cale.getTime());
        // 获取当月的最后一天
        cale = Calendar.getInstance();
        cale.add(Calendar.MONTH, 1);
        cale.set(Calendar.DAY_OF_MONTH, 0);
        //下个月第一天
        cale = Calendar.getInstance();
        cale.set(Calendar.MONTH, cale.get(Calendar.MONTH)+1);
        cale.set(Calendar.DAY_OF_MONTH, 1);
        nextMonthFirstDay = format.format(cale.getTime());
        DateRangeStrDto dateRangeStrDto = new DateRangeStrDto();
        dateRangeStrDto.setStartDateStr(firstday);
        dateRangeStrDto.setEndDateStr(nextMonthFirstDay);
        return dateRangeStrDto;
    }

    public static int getCurrentMonthLastDay()
    {
        Calendar a = Calendar.getInstance();
        a.set(Calendar.DATE, 1);//把日期设置为当月第一天
        a.roll(Calendar.DATE, -1);//日期回滚一天，也就是最后一天
        return a.get(Calendar.DATE);
    }

    public static int getDateMonthByDay(String dayStr)
    {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date;
        try {
            date = format.parse(dayStr);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            return cal.get(Calendar.DAY_OF_MONTH);
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public  static int getMonthByMonthStr(String monthStr){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
        Date date;
        try {
            date = format.parse(monthStr);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            return cal.get(Calendar.MONTH)+1;
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }

    //获取当天某小时的时间戳
    public static DateRangeDto getDayHourTimeInMillis(int startHour,int endHour){
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = dateFormat.format(date);
        Long startDate;
        Long endDate;
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, startHour);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        startDate = c.getTimeInMillis();

        c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, endHour);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        endDate = c.getTimeInMillis();
        DateRangeDto dateRangeDto = new DateRangeDto();
        dateRangeDto.setStartDate(startDate);
        dateRangeDto.setEndDate(endDate);
        dateRangeDto.setDate(dateStr);
        return dateRangeDto;
    }

    private static DateRangeStrDto getNowMonthStartEndDateStr(Date date){
        // 获取当月第一天和最后一天
        Calendar cale;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String firstday, nextMonthFirstDay;
        // 获取当月的第一天
        cale = Calendar.getInstance();
        cale.setTime(date);
        cale.add(Calendar.MONTH, 0);
        cale.set(Calendar.DAY_OF_MONTH, 1);
        firstday = format.format(cale.getTime());
        // 获取当月的最后一天
        cale = Calendar.getInstance();
        cale.setTime(date);
        cale.add(Calendar.MONTH, 1);
        cale.set(Calendar.DAY_OF_MONTH, 0);
        //下个月第一天
        cale = Calendar.getInstance();
        cale.setTime(date);
        cale.set(Calendar.MONTH, cale.get(Calendar.MONTH)+1);
        cale.set(Calendar.DAY_OF_MONTH, 1);
        nextMonthFirstDay = format.format(cale.getTime());
        DateRangeStrDto dateRangeStrDto = new DateRangeStrDto();
        dateRangeStrDto.setStartDateStr(firstday);
        dateRangeStrDto.setEndDateStr(nextMonthFirstDay);
        return dateRangeStrDto;
    }

    public static HolidayDateDto getHolidayDates(Date date,HolidayDayMapper holidayDayMapper,HolidayDayService holidayDayService){
        BusinessDate businessDate = BusinessDateUtil.getBusinessDate(date.getTime());
        String monthStr = businessDate.getMonthStr();
        DateRangeStrDto dateRangeStrDto = BusinessDateUtil.getNowMonthStartEndDateStr(date);
        //获取该月所有的天数
        List<String> days = new ArrayList<>();
        try {
            days = BusinessDateUtil.weekDayList(dateRangeStrDto.getStartDateStr(),dateRangeStrDto.getEndDateStr());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //从数据库中获取该月的节假日天数
        List<HolidayDayEntity> holidays = holidayDayMapper.findHolidaysByMonthStr(monthStr);
        if(holidays.size()==0){
            int year = Integer.parseInt(businessDate.getYearStr());
            holidayDayService.addHolidays(year);
            holidays = holidayDayMapper.findHolidaysByMonthStr(monthStr);
        }
        //节假日
        List<String> holidayStrings = new ArrayList<>();
        if(holidays.size()>0){
           holidayStrings = holidays.stream().map(HolidayDayEntity::getDayStr).collect(Collectors.toList());
        }
        //工作日
        List<String> dateStrings = new ArrayList<>();
        for (String day : days) {
            if (!holidayStrings.contains(day)) {
                dateStrings.add(day);
            }
        }
        HolidayDateDto holidayDateDto1 = new HolidayDateDto();
        holidayDateDto1.setHolidayList(holidayStrings);
        holidayDateDto1.setWorkList(dateStrings);
        return holidayDateDto1;
    }

    //获取某个日期的前几个或后几个月的某天某时某分某秒的时间戳
    public static Long getDateStrTimeInMillis(String monthStr,SimpleDateFormat dateFormat,int nextMonth,int day,int hour,int minute,int second,int millSecond){
        Date date = null;
        try {
            date = dateFormat.parse(monthStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar cale;
        cale = Calendar.getInstance();
        cale.setTime(date);
        cale.set(Calendar.MONTH, cale.get(Calendar.MONTH)+nextMonth);
        cale.set(Calendar.DAY_OF_MONTH, day);
        cale.set(Calendar.HOUR_OF_DAY, hour);
        cale.set(Calendar.MINUTE, minute);
        cale.set(Calendar.SECOND, second);
        cale.set(Calendar.MILLISECOND, millSecond);
//        String nextMonthFirstDay = dateFormat.format(cale.getTime());
        return cale.getTimeInMillis();
    }


}
