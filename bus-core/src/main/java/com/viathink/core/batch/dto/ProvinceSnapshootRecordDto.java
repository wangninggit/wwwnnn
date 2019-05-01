package com.viathink.core.batch.dto;

import com.viathink.core.business.gene.dto.StatisticalDataBaseDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ProvinceSnapshootRecordDto extends StatisticalDataBaseDto {
    private String provinceId;
    private String provinceName;
}
