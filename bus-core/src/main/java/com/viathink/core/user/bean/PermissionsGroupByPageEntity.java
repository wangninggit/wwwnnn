package com.viathink.core.user.bean;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class PermissionsGroupByPageEntity extends PageEntity {
    private List<PermissionEntity> permissionList;
}
