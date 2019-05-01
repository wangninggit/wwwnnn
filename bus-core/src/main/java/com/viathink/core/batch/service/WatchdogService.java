package com.viathink.core.batch.service;

import com.viathink.core.business.gene.bean.EventBaseEntity;
import com.viathink.core.util.BusinessDate;

public interface WatchdogService {
    void watch(EventBaseEntity event, BusinessDate businessDate);
    void handle();
    Boolean recordIdCheck(EventBaseEntity event);
    void snapshotRecreate(BusinessDate startBusinessDate,BusinessDate endBusinessDate);
}
