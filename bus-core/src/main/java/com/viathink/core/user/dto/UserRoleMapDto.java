package com.viathink.core.user.dto;

import com.viathink.core.user.bean.RoleEntity;
import com.viathink.core.user.bean.UserEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserRoleMapDto extends UserEntity {
    private List<RoleEntity> roles;
    private Map<String,Map<String,Object>> permissions;
}
