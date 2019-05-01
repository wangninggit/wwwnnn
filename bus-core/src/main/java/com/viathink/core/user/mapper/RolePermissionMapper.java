package com.viathink.core.user.mapper;

import com.viathink.core.user.bean.RolePermissionEntity;
import com.viathink.core.user.bean.RolePermissionUpdateEntity;
import com.viathink.core.user.dto.RolePermissionDto;
import com.viathink.core.user.dto.RolePermissionPageDto;
import com.viathink.core.user.dto.UserRoleMapDto;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RolePermissionMapper {
    void deleteRolePermissionsByRoleId(Long role_id);
    void addRolePermissions(RolePermissionUpdateEntity entity);
    void deleteRolePermissionsByPageId(Long page_id);
    List<RolePermissionEntity> findPermissionIdsByRoleId(Long role_id);
    List<RolePermissionPageDto> findPermissionsByRoleId(Long role_id);
    List<RolePermissionPageDto> findPermissionsJoinPage();
    void updateRolePermissions(RolePermissionUpdateEntity rolePermissionUpdateEntity);
    List<RolePermissionPageDto> findPermissionsByRoleIds(UserRoleMapDto userRoleMapDto);
}
