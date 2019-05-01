package com.viathink.core.batch.service;

import java.util.Date;

public interface ProfitService {
    void getMonthDetailProfitReport(Date date);
    void getHalfYearDetailProfitReport(Date date);
    void getYearDetailProfitReport(Date date);
}
