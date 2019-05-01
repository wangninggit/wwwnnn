package com.viathink.core.business.gene.mapper;

import com.viathink.core.business.gene.bean.GeneDetailBaseEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface GeneQuarterlyDetailMapper {
    void addGeneQuarterlyDetail(GeneDetailBaseEntity GeneDetailBaseEntity);
    void updateGeneQuarterlyDetail(GeneDetailBaseEntity GeneDetailBaseEntity);
    GeneDetailBaseEntity getGeneQuarterlyDetailById(Long id);
    GeneDetailBaseEntity getGeneQuarterlyDetailByOrderId(String orderId);
    GeneDetailBaseEntity getGeneQuarterlyDetailByOrderIdAndQuarterStr(GeneDetailBaseEntity GeneDetailBaseEntity);
    void updateGeneQuarterlyDetailByOrderIdAndQuarterStr(GeneDetailBaseEntity GeneDetailBaseEntity);
}
