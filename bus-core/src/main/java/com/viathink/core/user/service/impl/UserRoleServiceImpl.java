package com.viathink.core.user.service.impl;

import com.viathink.core.user.bean.RoleEntity;
import com.viathink.core.user.bean.UserEntity;
import com.viathink.core.user.bean.UserRoleEntity;
import com.viathink.core.user.dto.UserRoleForm;
import com.viathink.core.user.mapper.RoleMapper;
import com.viathink.core.user.mapper.UserMapper;
import com.viathink.core.user.mapper.UserRoleMapper;
import com.viathink.core.user.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserRoleServiceImpl implements UserRoleService {

    private final RoleMapper roleMapper;
    private final UserMapper userMapper;
    private final UserRoleMapper userRoleMapper;

    @Autowired
    public UserRoleServiceImpl(RoleMapper roleMapper, UserMapper userMapper, UserRoleMapper userRoleMapper) {
        this.roleMapper = roleMapper;
        this.userMapper = userMapper;
        this.userRoleMapper = userRoleMapper;
    }


    @Override
    public Boolean deleteByUserId(Long id) {
        UserEntity userEntity = userMapper.findUserById(id);
        if(userEntity == null){
            return false;
        }
        userRoleMapper.deleteByUserId(id);
        return true;
    }

    @Override
    public Boolean deleteByRoleId(Long id) {
        RoleEntity role = roleMapper.findRoleById(id);
        if(role == null){
            return false;
        }
        userRoleMapper.deleteByRoleId(id);
        return true;
    }


    @Override
    public List<UserRoleEntity> findByUserId(Long id) {
        return userRoleMapper.findByUserId(id);
    }

    @Override
    public String updateUserRoles(UserRoleForm userRoleForm) {
        Boolean result = this.deleteByUserId(userRoleForm.getUserId());
        if(!result){
            return "userNotFound";
        }
        Long[] roleIds = userRoleForm.getRoleIds();
        List<RoleEntity> roleEntities = roleMapper.findRoles();
        List<Long> allIds = roleEntities.stream().map(RoleEntity::getId).collect(Collectors.toList());
        List<Long> ids = Arrays.asList(roleIds);
        if (!allIds.containsAll(ids)) {
            return "roleNotFound";
        }
        if(userRoleForm.getRoleIds().length > 0){
            userRoleMapper.updateUserRoles(userRoleForm);
        }
        return "success";
    }
}
