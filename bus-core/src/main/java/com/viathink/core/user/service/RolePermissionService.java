package com.viathink.core.user.service;

import com.viathink.core.user.bean.RolePermissionEntity;
import com.viathink.core.user.bean.RolePermissionUpdateEntity;
import com.viathink.core.user.dto.RolePermissionPageDto;

import java.util.List;

public interface RolePermissionService {
    void updateRolePermissions(RolePermissionUpdateEntity entity);

    List<RolePermissionEntity> findPermissionIdsByRoleId(Long role_id);
    List<RolePermissionPageDto> findPermissionsByRoleId(Long role_id);
    List<RolePermissionPageDto> findPermissionsJoinPage();
}
