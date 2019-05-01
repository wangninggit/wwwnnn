package com.viathink.core.user.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class DictionaryForm {
    private static final long serialVersionUID = 1L;
    @NotBlank(message = "不能为空")
    private String label;
    @NotBlank(message = "不能为空")
    private String value;
    @NotNull(message = "不能为空")
    private Long categoryId;
}
