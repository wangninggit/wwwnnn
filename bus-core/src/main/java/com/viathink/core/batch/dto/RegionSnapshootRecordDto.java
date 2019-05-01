package com.viathink.core.batch.dto;

import com.viathink.core.business.gene.dto.StatisticalDataBaseDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class RegionSnapshootRecordDto extends StatisticalDataBaseDto {
    private String regionId;
    private String regionName;
}
