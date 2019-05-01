package com.viathink.core.user.service.impl;

import com.viathink.core.user.bean.RolePermissionEntity;
import com.viathink.core.user.bean.RolePermissionUpdateEntity;
import com.viathink.core.user.dto.RolePermissionPageDto;
import com.viathink.core.user.mapper.RolePermissionMapper;
import com.viathink.core.user.service.RolePermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
//自动管理事务
@Transactional
public class RolePermissionServiceImpl implements RolePermissionService {

    private final RolePermissionMapper rolePermissionMapper;

    @Autowired
    public RolePermissionServiceImpl(RolePermissionMapper rolePermissionMapper) {
        this.rolePermissionMapper = rolePermissionMapper;
    }


    @Override
    public void updateRolePermissions(RolePermissionUpdateEntity entity) {
        //删除该角色对应的权限
        rolePermissionMapper.deleteRolePermissionsByRoleId(entity.getRoleId());

        if(entity.getIds().size() > 0){
            rolePermissionMapper.updateRolePermissions(entity);
        }
    }

    @Override
    public List<RolePermissionEntity> findPermissionIdsByRoleId(Long role_id) {
        return rolePermissionMapper.findPermissionIdsByRoleId(role_id);
    }

    @Override
    public List<RolePermissionPageDto> findPermissionsByRoleId(Long role_id) {
        return rolePermissionMapper.findPermissionsByRoleId(role_id);
    }

    @Override
    public List<RolePermissionPageDto> findPermissionsJoinPage() {

        return rolePermissionMapper.findPermissionsJoinPage();
    }
}
