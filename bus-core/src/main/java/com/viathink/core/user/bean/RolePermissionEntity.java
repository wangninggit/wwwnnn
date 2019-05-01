package com.viathink.core.user.bean;

import lombok.Data;

@Data
public class RolePermissionEntity {
    private static final long serialVersionUID = 1L;
    private Long id;
    private Long roleId;
    private Long permissionId;
}
