package com.viathink.core.user.dto;

import com.viathink.core.user.bean.UserEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserRoleParamDto extends UserEntity {
    private String roleName;
}
