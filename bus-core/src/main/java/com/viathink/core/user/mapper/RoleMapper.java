package com.viathink.core.user.mapper;

import com.viathink.core.user.bean.RoleEntity;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleMapper {
    void addRole(RoleEntity roleEntity);
    void deleteRoleById(Long id);
    void updateRole(RoleEntity roleEntity);
    RoleEntity findRoleById(Long id);
    List<RoleEntity> findRoleByPage(RoleEntity roleEntity);
    List<RoleEntity> findRoles();
}
