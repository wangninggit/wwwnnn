package com.viathink.core.user.service;

import com.viathink.core.user.bean.UserEntity;
import com.viathink.core.user.dto.*;

import java.util.List;

public interface UserService {
    UserEntity findUserById(Long id);
    List<UserRoleDto> findUserByPage(UserRoleParamDto userRoleParamDto, PageParam pageParam);
    String addUser(UserEntity userEntity,UserRoleForm userRoleForm);
    Boolean deleteUserById(Long id);
    String updateUser(UserEntity userEntity,UserRoleForm userRoleForm);
    UserRolePermissionDto findUserRolePermissionById(Long id);
    UserRoleMapDto findUserRoleByUserId(Long id);
    UserRoleResultDto findUserByIdJoinRole(Long id);
    String updateUserPassword(UserEntity userEntity);
}
