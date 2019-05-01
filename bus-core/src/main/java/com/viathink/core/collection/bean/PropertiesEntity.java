package com.viathink.core.collection.bean;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class PropertiesEntity {
    private static final long serialVersionUID = 1L;
    private String id;
    private String type;
    @NotNull(message="{property.PropertiesEntity.typeInt}")
    private Long typeInt;
    private String typeVarchar;
    private Long updateTime;
    private Long createTime;
}
