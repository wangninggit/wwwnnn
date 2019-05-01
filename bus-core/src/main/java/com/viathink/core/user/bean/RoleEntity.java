package com.viathink.core.user.bean;

import lombok.Data;

@Data
public class RoleEntity {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String name;
    private String label;
    private Long updateTime;
    private Long createTime;
}
