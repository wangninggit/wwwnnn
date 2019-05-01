package com.viathink.core.user.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class PageForm {
    private static final long serialVersionUID = 1L;
    @NotBlank(message = "不能为空")
    private String name;
}
