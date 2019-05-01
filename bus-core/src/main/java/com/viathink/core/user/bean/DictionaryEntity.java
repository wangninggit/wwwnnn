package com.viathink.core.user.bean;

import lombok.Data;

@Data
public class DictionaryEntity {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String label;
    private String value;
    private Long categoryId;
    private Long updateTime;
    private Long createTime;
}
