package com.viathink.core.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class DayListUtil {
    public static List<Date> dayList(String dayStr) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dt = sdf.parse(dayStr);
        Instant instant = dt.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDate localDate = instant.atZone(zoneId).toLocalDate();
        int days = (int) localDate.until(LocalDate.now(), ChronoUnit.DAYS);
        List<Date> list = new ArrayList<>();

        for (int i = days - 1; i > 0; i -=1) {
            int diff = 0 - i;
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DATE, diff);
            list.add(cal.getTime());
        }
        return list;
    }

    public static List<Date> monthList(String monthStr) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        Date dt = sdf.parse(monthStr);
        Instant instant = dt.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDate localDate = instant.atZone(zoneId).toLocalDate();
        int months = (int) localDate.until(LocalDate.now(), ChronoUnit.MONTHS);
        List<Date> list = new ArrayList<>();

        for (int i = months - 1; i > 0; i -=1) {
            int diff = 0 - i;
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.MONTH, diff);
            list.add(cal.getTime());
        }
        return list;
    }

    public static List<Date> yearList(String yearStr) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        Date dt = sdf.parse(yearStr);
        Instant instant = dt.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDate localDate = instant.atZone(zoneId).toLocalDate();
        int years = (int) localDate.until(LocalDate.now(), ChronoUnit.YEARS);
        List<Date> list = new ArrayList<>();

        for (int i = years - 1; i > 0; i -=1) {
            int diff = 0 - i;
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.YEAR, diff);
            list.add(cal.getTime());
        }
        return list;
    }

    public static List<Date> halfYearList(String halfYearStr){
        String [] splits;
        String dateStr;
        List<Date> list = new ArrayList<>();

        if((splits = halfYearStr.split("-")).length == 3){
            int Hn = (Integer.valueOf(splits[1]) > 6)? 2 : 1;
            halfYearStr = splits[0] + "-h" + Hn;
        }

        if((splits = halfYearStr.split("-h")).length == 2){
            dateStr = splits[0] + "-" + (Integer.valueOf(splits[1]) * 6) + "-" + "1";
            try {
                List<Date> dateList = DayListUtil.monthList(dateStr);
                for(Date date:dateList){
                    Calendar calendar = new GregorianCalendar();
                    calendar.setTime(date);
                    if(((calendar.get(Calendar.MONTH) + 1) % 6) == 0)
                        list.add(date);
                }
                return list;
            } catch (ParseException e) {
                return null;
            }
        }else{
            return null;
        }
    }
}
