package com.viathink.core.user.mapper;

import com.viathink.core.user.bean.UserEntity;
import com.viathink.core.user.dto.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper {
    UserEntity findUserById(Long id);
    List<UserRoleDto> findUserByPage(UserRoleParamDto userRoleParamDto);
    void addUser(UserEntity userEntity);
    void deleteUserById(Long id);
    void updateUser(UserEntity userEntity);
    UserEntity findByEmail(String email);
    UserRolePermissionDto findUserRolePermissionById(Long id);
    UserRoleMapDto findUserRoleByUserId(Long id);
    List<UserRoleMapDto> findUsersByPageJoinRole(UserRoleForm userRoleForm);
    List<UserRoleDto> findUserByPageAngRoleName(UserRoleParamDto userRoleParamDto);
}
