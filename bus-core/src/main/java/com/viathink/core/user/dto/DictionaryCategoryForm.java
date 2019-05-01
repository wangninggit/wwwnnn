package com.viathink.core.user.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class DictionaryCategoryForm {
    private static final long serialVersionUID = 1L;
    @NotNull(message = "类型标签不能为空")
    private String label;
}
