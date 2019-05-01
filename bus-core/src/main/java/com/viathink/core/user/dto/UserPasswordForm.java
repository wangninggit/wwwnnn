package com.viathink.core.user.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Data
public class UserPasswordForm {
    @NotBlank(message = "{user.UserForm.password}")
    @Length(min = 6, max = 30, message = "{user.UserForm.password}")
    private String password;
    @NotBlank(message = "{user.UserPasswordForm.confirmPassword}")
    @Length(min = 6, max = 30, message = "{user.UserPasswordForm.confirmPassword}")
    private String confirmPassword;
}
