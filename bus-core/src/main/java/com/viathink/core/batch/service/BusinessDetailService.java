package com.viathink.core.batch.service;

import java.util.Date;

public interface BusinessDetailService {
    void getDailyDetailReport(Date date);
    void getMonthDetailReport(Date date);
    void getHalfYearDetailReport(Date date);
    void getYearDetailReport(Date date);
}
