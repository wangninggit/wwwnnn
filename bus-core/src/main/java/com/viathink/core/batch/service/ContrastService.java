package com.viathink.core.batch.service;

import java.util.Date;

public interface ContrastService {
    void dailySnapshoot(Date date);
    void monthlySnapshoot(Date date);
    void halfYearlySnapshoot(Date date);
    void yearlySnapshoot(Date date);
}
