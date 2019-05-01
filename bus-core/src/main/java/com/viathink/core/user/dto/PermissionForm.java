package com.viathink.core.user.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class PermissionForm {
    private static final long serialVersionUID = 1L;
    @NotNull
    private Long pageId;
    @NotBlank
    private String name;
    @NotBlank
    private String label;
    @NotBlank
    private String type;
    private String url;
    private Long parent;
    private Integer sort;
}
