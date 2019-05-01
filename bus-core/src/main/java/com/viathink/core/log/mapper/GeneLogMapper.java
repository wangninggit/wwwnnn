package com.viathink.core.log.mapper;

import com.viathink.core.log.bean.GeneLogEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface GeneLogMapper {
    void addLog(GeneLogEntity geneLogEntity);

    GeneLogEntity findByRecordId(Long recordId);

    Long countByRecordId(Long recordId);
}
