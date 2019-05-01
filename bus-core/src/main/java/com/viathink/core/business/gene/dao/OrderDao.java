package com.viathink.core.business.gene.dao;


import com.viathink.core.business.gene.bean.EventBaseEntity;
import com.viathink.core.business.gene.bean.GeneDetailBaseEntity;
import com.viathink.core.business.gene.dto.LocalStaffLocationDto;
import com.viathink.core.business.gene.dto.OrderDateDto;

public interface OrderDao {
    void addOrUpdateAttachedData(GeneDetailBaseEntity geneDetailEntity, LocalStaffLocationDto locationDto);
    void orderFlowHandle(EventBaseEntity event, LocalStaffLocationDto locationDto, GeneDetailBaseEntity geneDetailEntity, GeneDetailBaseEntity oldGeneDetail,OrderDateDto orderDateDto);
    GeneDetailBaseEntity overrideGeneDetaillField(EventBaseEntity event, LocalStaffLocationDto locationDto, GeneDetailBaseEntity geneDetailEntity);
    GeneDetailBaseEntity copyBaseField(GeneDetailBaseEntity target,GeneDetailBaseEntity source);
}
