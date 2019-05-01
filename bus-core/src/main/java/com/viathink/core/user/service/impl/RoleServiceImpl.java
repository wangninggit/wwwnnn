package com.viathink.core.user.service.impl;

import com.github.pagehelper.PageHelper;
import com.viathink.core.user.bean.RoleEntity;
import com.viathink.core.user.bean.RolePermissionUpdateEntity;
import com.viathink.core.user.dto.PageParam;
import com.viathink.core.user.mapper.RoleMapper;
import com.viathink.core.user.mapper.RolePermissionMapper;
import com.viathink.core.user.mapper.UserRoleMapper;
import com.viathink.core.user.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    private final RoleMapper roleMapper;
    private final UserRoleMapper userRoleMapper;
    private final RolePermissionMapper rolePermissionMapper;

    @Autowired
    public RoleServiceImpl(RoleMapper roleMapper, UserRoleMapper userRoleMapper, RolePermissionMapper rolePermissionMapper) {
        this.roleMapper = roleMapper;
        this.userRoleMapper = userRoleMapper;
        this.rolePermissionMapper = rolePermissionMapper;
    }

    @Override
    public void addRole(RoleEntity roleEntity,RolePermissionUpdateEntity entity) {
        //添加角色
        roleMapper.addRole(roleEntity);
        //关联角色和权限
        entity.setRoleId(roleEntity.getId());
        if(entity.getIds().size() > 0){
            rolePermissionMapper.updateRolePermissions(entity);
        }
    }

    @Override
    public Boolean deleteRoleById(Long id) {
        //判断角色是否存在
        RoleEntity role = roleMapper.findRoleById(id);
        if(role == null){
            return false;
        }
        userRoleMapper.deleteByRoleId(id);
        rolePermissionMapper.deleteRolePermissionsByRoleId(id);
        roleMapper.deleteRoleById(id);
        return true;
    }

    @Override
    public Boolean updateRole(RoleEntity roleEntity,RolePermissionUpdateEntity entity) {
        Long id = roleEntity.getId();
        RoleEntity role = roleMapper.findRoleById(id);
        if(role == null){
            return false;
        }
        //更新角色信息
        roleMapper.updateRole(roleEntity);
        //更新角色权限
        rolePermissionMapper.deleteRolePermissionsByRoleId(entity.getRoleId());

        if(entity.getIds().size() > 0){
            rolePermissionMapper.updateRolePermissions(entity);
        }
        return true;
    }

    @Override
    public RoleEntity findRoleById(Long id) {
        return roleMapper.findRoleById(id);
    }

    @Override
    public List<RoleEntity> findRoleByPage(RoleEntity roleEntity,PageParam pageParam) {
        PageHelper.startPage(pageParam.getPageNum(), pageParam.getPageSize());
        return roleMapper.findRoleByPage(roleEntity);
    }

    @Override
    public List<RoleEntity> findRoles() {
        return roleMapper.findRoles();
    }
}
