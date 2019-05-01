package com.viathink.core.user.service;

import com.viathink.core.user.bean.RoleEntity;
import com.viathink.core.user.bean.RolePermissionUpdateEntity;
import com.viathink.core.user.dto.PageParam;

import java.util.List;
import java.util.Map;


public interface RoleService {
    void addRole(RoleEntity roleEntity,RolePermissionUpdateEntity entity);
    Boolean deleteRoleById(Long id);
    Boolean updateRole(RoleEntity roleEntity,RolePermissionUpdateEntity entity);
    RoleEntity findRoleById(Long id);
    List<RoleEntity> findRoleByPage(RoleEntity roleEntity, PageParam pageParam);
    List<RoleEntity> findRoles();
}
