package com.viathink.core.business.gene.mapper;

import com.viathink.core.batch.dto.OrderTrendMonthResultDto;
import com.viathink.core.batch.dto.SnapRecreateDto;
import com.viathink.core.business.gene.bean.SnapshootEntity;
import com.viathink.core.business.gene.dto.DateRangeStrDto;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SnapshootMapper {
    void addSnapshoot(SnapshootEntity entity);
    void updateSnapshootById(SnapshootEntity entity);
    SnapshootEntity findSnapshootByClass(SnapshootEntity entity);
    List<SnapshootEntity> findAllSnapshoots();
    SnapshootEntity findSnapshootByLatestDate(SnapshootEntity snapshootEntity);
    // findSnapshootByRecreate
    List<SnapshootEntity> findSnapshootByRecreate(Boolean recreate);
    Long countSnapshootByTimeRangeAndDateStr(SnapshootEntity snapshootEntity);
    void setReCreateTrueByTimeRangeAndDateStr(SnapshootEntity snapshootEntity);
    List<OrderTrendMonthResultDto> findSnapshootByMonthAvg(DateRangeStrDto dateRangeStrDto);
    Long countSnapshotByTimeRangeAndDateRange(SnapRecreateDto snapRecreateDto);
    void setReCreateTrueByTimeRangeAndDateRange(SnapRecreateDto snapRecreateDto);
}
