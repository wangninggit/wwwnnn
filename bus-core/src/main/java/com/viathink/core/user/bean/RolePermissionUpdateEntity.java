package com.viathink.core.user.bean;

import lombok.Data;

import java.util.List;

@Data
public class RolePermissionUpdateEntity {
    private static final long serialVersionUID = 1L;
    private Long roleId;
    private List<Long> ids;
}
