package com.viathink.core.user.service;

import com.viathink.core.user.bean.UserRoleEntity;
import com.viathink.core.user.dto.UserRoleForm;

import java.util.List;

public interface UserRoleService {
    Boolean deleteByUserId(Long id);
    Boolean deleteByRoleId(Long id);
    List<UserRoleEntity> findByUserId(Long id);
    String updateUserRoles(UserRoleForm userRoleForm);
}
