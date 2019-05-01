package com.viathink.core.user.dto;

import com.viathink.core.user.bean.PermissionEntity;
import com.viathink.core.user.bean.RoleEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class RolePermissionDto extends RoleEntity {
    private List<PermissionEntity> permissions;
}
