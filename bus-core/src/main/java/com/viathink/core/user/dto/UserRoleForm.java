package com.viathink.core.user.dto;

import com.viathink.core.user.bean.UserRoleEntity;
import lombok.Data;

import java.util.List;

@Data
public class UserRoleForm {
    private static final long serialVersionUID = 1L;
    private Long userId;
    private List<UserRoleEntity> userRoleEntities;
    private Long updateTime;
    private Long createTime;
    private Long[] roleIds;
    private List<Long> userIds;
}
