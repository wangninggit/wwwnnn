package com.viathink.core.user.mapper;

import com.viathink.core.user.bean.UserRoleEntity;
import com.viathink.core.user.dto.UserRoleForm;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRoleMapper {
    void deleteByUserId(Long id);
    void deleteByRoleId(Long id);
    List<UserRoleEntity> findByUserId(Long id);
    void updateUserRoles(UserRoleForm userRoleForm);
}
