package com.viathink.core.user.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Map;

@Data
public class RoleForm {
    private static final long serialVersionUID = 1L;
    private Long id;
    @NotNull(message = "{role.RoleForm.name}")
    private String name;
    @NotNull(message = "{role.RoleForm.label}")
    private String label;
    @NotNull(message = "{role.RoleForm.permissions}")
    private Map<String, Map<String,Object>> permissions;
}
