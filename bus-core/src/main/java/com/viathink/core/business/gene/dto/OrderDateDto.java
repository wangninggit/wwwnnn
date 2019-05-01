package com.viathink.core.business.gene.dto;

import com.viathink.core.business.gene.bean.GeneDetailBaseEntity;
import lombok.Data;

@Data
public class OrderDateDto {
    private GeneDetailBaseEntity day;
    private GeneDetailBaseEntity month;
    private GeneDetailBaseEntity quarter;
    private GeneDetailBaseEntity halfYear;
    private GeneDetailBaseEntity year;
}
