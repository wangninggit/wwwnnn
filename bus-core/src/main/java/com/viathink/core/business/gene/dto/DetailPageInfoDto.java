package com.viathink.core.business.gene.dto;

import com.github.pagehelper.PageInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class DetailPageInfoDto<T> extends PageInfo<T> {
    private DetailSumDto sum;
    public DetailPageInfoDto(List<T> list) {
        super(list);
    }
}
