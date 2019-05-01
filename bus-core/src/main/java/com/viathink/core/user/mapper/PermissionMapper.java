package com.viathink.core.user.mapper;

import com.viathink.core.user.bean.PermissionEntity;
import com.viathink.core.user.bean.PermissionsGroupByPageEntity;
import com.viathink.core.user.dto.PermissionTypeDto;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PermissionMapper {
    void addPermission(PermissionEntity permissionEntity);

    void updatePermissionById(PermissionEntity permissionEntity);

    PermissionEntity findPermissionById(Long id);

    void deletePermissionById(Long id);

    List<PermissionEntity> findPermissionByPage();

    void deletePermissionByPageId(Long pageId);

    List<PermissionsGroupByPageEntity> findPermissionsGroupByPage();

    List<PermissionEntity> findAllPermissions();
    PermissionEntity findPermissionByPageNameAndType(PermissionTypeDto permissionTypeDto);
}
