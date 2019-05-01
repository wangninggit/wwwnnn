package com.viathink.core.user.service.impl;

import com.github.pagehelper.PageHelper;
import com.viathink.core.user.bean.RoleEntity;
import com.viathink.core.user.bean.UserEntity;
import com.viathink.core.user.dao.RolePermissionPageDao;
import com.viathink.core.user.dto.*;
import com.viathink.core.user.mapper.RoleMapper;
import com.viathink.core.user.mapper.RolePermissionMapper;
import com.viathink.core.user.mapper.UserMapper;
import com.viathink.core.user.mapper.UserRoleMapper;
import com.viathink.core.user.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserMapper userMapper;
    private final RolePermissionMapper rolePermissionMapper;
    private final RolePermissionPageDao rolePermissionPageDao;
    private final RoleMapper roleMapper;
    private final UserRoleMapper userRoleMapper;

    @Autowired
    public UserServiceImpl(UserMapper userMapper, RolePermissionMapper rolePermissionMapper, RolePermissionPageDao rolePermissionPageDao, RoleMapper roleMapper, UserRoleMapper userRoleMapper) {
        this.userMapper = userMapper;
        this.rolePermissionMapper = rolePermissionMapper;
        this.rolePermissionPageDao = rolePermissionPageDao;
        this.roleMapper = roleMapper;
        this.userRoleMapper = userRoleMapper;
    }

    @Override
    public UserEntity findUserById(Long id) {
        logger.info("UserServiceImpl findUserById");
        return userMapper.findUserById(id);
    }
    @Override
    public List<UserRoleDto>findUserByPage(UserRoleParamDto userRoleParamDto, PageParam pageParam) {
        logger.info("UserServiceImpl findUserByPage");
        PageHelper.startPage(pageParam.getPageNum(), pageParam.getPageSize());
        //获取所有的用户
        List<UserRoleDto> userRoleDtos;
        if(userRoleParamDto.getRoleName()!=null && (!userRoleParamDto.getRoleName().equals(""))){
            userRoleDtos = userMapper.findUserByPageAngRoleName(userRoleParamDto);
        }else{
            userRoleDtos = userMapper.findUserByPage(userRoleParamDto);
        }

        List<Long> allIds = userRoleDtos.stream().map(UserEntity::getId).collect(Collectors.toList());
        UserRoleForm userRoleForm = new UserRoleForm();
        userRoleForm.setUserIds(allIds);
        if(allIds.size()>0){
            List<UserRoleMapDto> users =userMapper.findUsersByPageJoinRole(userRoleForm);
            for (UserRoleMapDto user : users) {
                List<RoleEntity> roles = user.getRoles();
                List<String> roleNames = roles.stream().map(RoleEntity::getLabel).collect(Collectors.toList());
                for (UserRoleDto userRoleDto : userRoleDtos) {
                    if (user.getId().equals(userRoleDto.getId())) {
                        userRoleDto.setRoles(StringUtils.join(roleNames.toArray(), ","));
                    }
                }

            }
        }

        return userRoleDtos;
    }

    @Override
    @Transactional
    public String addUser(UserEntity userEntity,UserRoleForm userRoleForm) {
        logger.info("UserServiceImpl addUser");
        String email = userEntity.getEmail();
        UserEntity userEntity1 = userMapper.findByEmail(email);
        if(userEntity1!=null){
            return "user is already exit";
        }
        Long[] roleIds = userRoleForm.getRoleIds();
        List<RoleEntity> roleEntities = roleMapper.findRoles();
        List<Long> allIds = roleEntities.stream().map(RoleEntity::getId).collect(Collectors.toList());
        List<Long> ids = Arrays.asList(roleIds);
        if (!allIds.containsAll(ids)) {
            return "roleNotFound";
        }
        //添加用户
        userMapper.addUser(userEntity);
        //关联roles
        userRoleForm.setUserId(userEntity.getId());

        if(roleIds.length>0){
            userRoleMapper.updateUserRoles(userRoleForm);
        }
        return "success";
    }

    @Override
    public Boolean deleteUserById(Long id) {
        logger.info("UserServiceImpl deleteUserById");
        UserEntity user = userMapper.findUserById(id);
        if(user == null) {
            return false;
        }
        userMapper.deleteUserById(id);
        return true;
    }

    @Override
    public String updateUser(UserEntity userEntity,UserRoleForm userRoleForm) {
        logger.info("UserServiceImpl updateUser");
        UserEntity user = userMapper.findUserById(userEntity.getId());
        if(user == null) {
            return "userNotFound";
        }
        String email = userEntity.getEmail();
        UserEntity userEntity1 = userMapper.findByEmail(email);
        if(userEntity1!=null && !userEntity1.getId().equals(user.getId())){
            return "emailIsRegistered";
        }
        Long[] roleIds = userRoleForm.getRoleIds();
        List<RoleEntity> roleEntities = roleMapper.findRoles();
        List<Long> allIds = roleEntities.stream().map(RoleEntity::getId).collect(Collectors.toList());
        List<Long> ids = Arrays.asList(roleIds);
        if (!allIds.containsAll(ids)) {
            return "roleNotFound";
        }
        //更新用户信息
        userMapper.updateUser(userEntity);
        //更新用户角色关联关系
        userRoleMapper.deleteByUserId(userRoleForm.getUserId());
        if(userRoleForm.getRoleIds().length > 0){
            userRoleMapper.updateUserRoles(userRoleForm);
        }
        return "success";
    }

    @Override
    public UserRolePermissionDto findUserRolePermissionById(Long id) {
        return userMapper.findUserRolePermissionById(id);
    }

    @Override
    public UserRoleMapDto findUserRoleByUserId(Long id) {
        //获取用户角色信息
        UserRoleMapDto userRoleMapDto = userMapper.findUserRoleByUserId(id);
        //获取角色拥有的权限
        List<RolePermissionPageDto> list = rolePermissionMapper.findPermissionsByRoleIds(userRoleMapDto);
        //2.获取所有的页面及权限进行初始化
        Map<String, Map<String, Object>> item = rolePermissionPageDao.getAllPermissions();
        for(RolePermissionPageDto rolePermissionPageDto :list){
            Map<String,Object> map = (Map<String, Object>) item.get(rolePermissionPageDto.getPageName()).get("permissions");
            map.put(rolePermissionPageDto.getType(),true);
        }
        userRoleMapDto.setPermissions(item);
        return  userRoleMapDto;
    }

    @Override
    public UserRoleResultDto findUserByIdJoinRole(Long id) {
        UserEntity user = userMapper.findUserById(id);
        if(user == null) {
            return null;
        }
        //获取用户角色信息
        UserRoleMapDto userRoleMapDto = userMapper.findUserRoleByUserId(id);
        List<RoleEntity> roleEntities = userRoleMapDto.getRoles();
        List<Long> allIds = roleEntities.stream().map(RoleEntity::getId).collect(Collectors.toList());
        UserRoleResultDto userRoleResultDto = new UserRoleResultDto();
        userRoleResultDto.setId(userRoleMapDto.getId());
        userRoleResultDto.setPhoneNumber(userRoleMapDto.getPhoneNumber());
        userRoleResultDto.setEmail(userRoleMapDto.getEmail());
        userRoleResultDto.setNickname(userRoleMapDto.getNickname());
        userRoleResultDto.setActivate(userRoleMapDto.getActivate());
        userRoleResultDto.setUpdateTime(userRoleMapDto.getUpdateTime());
        userRoleResultDto.setCreateTime(userRoleMapDto.getCreateTime());
        userRoleResultDto.setDeleted(userRoleMapDto.getDeleted());
        userRoleResultDto.setRoleIds(allIds);

        return userRoleResultDto;
    }

    @Override
    public String updateUserPassword(UserEntity userEntity) {
        UserEntity user = userMapper.findUserById(userEntity.getId());
        if(user == null) {
            return "userNotFound";
        }
        //更新用户信息
        userMapper.updateUser(userEntity);
        return "success";
    }
}
