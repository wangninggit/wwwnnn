package com.viathink.core.user.bean;

import lombok.Data;

@Data
public class UserRoleEntity {
    private static final long serialVersionUID = 1L;
    private Long id;
    private Long userId;
    private Long roleId;
    private Long updateTime;
    private Long createTime;
}
