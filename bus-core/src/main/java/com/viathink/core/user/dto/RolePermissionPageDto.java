package com.viathink.core.user.dto;

import lombok.Data;

@Data
public class RolePermissionPageDto {
    private Long permissionId;
    private String type;
    private Long pageId;
    private String pageName;
    private String label;
    private String description;
}
