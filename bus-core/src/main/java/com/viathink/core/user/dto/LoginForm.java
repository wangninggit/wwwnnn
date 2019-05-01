package com.viathink.core.user.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class LoginForm {
    private static final long serialVersionUID = 1L;
    @NotBlank(message = "{user.UserForm.email}")
    @Email(message = "{user.UserForm.email}")
    private String email;
    @NotBlank(message = "{user.UserForm.password}")
    @Length(min = 6, max = 30, message = "{user.UserForm.password}")
    private String password;
}
