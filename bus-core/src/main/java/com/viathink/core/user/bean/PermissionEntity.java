package com.viathink.core.user.bean;

import lombok.Data;

@Data
public class PermissionEntity {
    private static final long serialVersionUID = 1L;
    private Long id;
    private Long pageId;
    private String name;
    private String label;
    private String type;
    private String url;
    private Long parent;
    private Integer sort;
    private Long updateTime;
    private Long createTime;
}
