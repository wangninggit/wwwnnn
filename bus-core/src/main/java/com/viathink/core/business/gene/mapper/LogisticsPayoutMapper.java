package com.viathink.core.business.gene.mapper;

import com.viathink.core.business.gene.bean.LogisticsPayoutEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface LogisticsPayoutMapper {
    void addLogisticsPayout(LogisticsPayoutEntity logisticsPayoutEntity);
    void updateLogisticsPayout(LogisticsPayoutEntity logisticsPayoutEntity);
    LogisticsPayoutEntity findLogisticsPayoutById(Long id);
}
