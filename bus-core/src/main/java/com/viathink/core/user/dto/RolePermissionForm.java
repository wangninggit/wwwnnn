package com.viathink.core.user.dto;

import lombok.Data;
import javax.validation.constraints.NotNull;

@Data
public class RolePermissionForm {
    private static final long serialVersionUID = 1L;
    @NotNull(message = "不能为空")
    private Long[] permissionIds;
}
