package com.viathink.core.user.service;

import com.viathink.core.user.bean.PermissionEntity;
import com.viathink.core.user.bean.PermissionsGroupByPageEntity;
import com.viathink.core.user.dto.PageParam;
import com.viathink.core.user.dto.PermissionTypeDto;

import java.util.List;

public interface PermissionService {
    void addPermission(PermissionEntity permissionEntity);

    Boolean updatePermissionById(PermissionEntity permissionEntity);

    PermissionEntity findPermissionById(Long id);

    Boolean deletePermissionById(Long id);
    // findPermissionByPage
    List<PermissionEntity> findPermissionByPage(PageParam pageParam);

    List<PermissionsGroupByPageEntity> findPermissionsGroupByPage();
    PermissionEntity findPermissionByPageNameAndType(PermissionTypeDto permissionTypeDto);
}
