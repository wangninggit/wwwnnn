package com.viathink.core.user.dto;

import com.viathink.core.user.bean.UserEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserRoleResultDto extends UserEntity {
    private List<Long> roleIds;
}
