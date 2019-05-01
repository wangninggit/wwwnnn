package com.viathink.core.user.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class UserForm {
    private static final long serialVersionUID = 1L;
    public static interface UserAddGroup {};
    public static interface UserUpdateGroup {};
    @NotBlank(message = "{user.UserForm.phoneNumber}", groups = {UserAddGroup.class, UserUpdateGroup.class})
    private String phoneNumber;
    @NotBlank(message = "{user.UserForm.email}", groups = {UserAddGroup.class, UserUpdateGroup.class})
    @Email(message = "{user.UserForm.email}", groups = {UserAddGroup.class, UserUpdateGroup.class})
    private String email;
    @NotBlank(message = "{user.UserForm.nickname}", groups = {UserAddGroup.class, UserUpdateGroup.class})
    private String nickname;
    @NotBlank(message = "{user.UserForm.password}", groups = {UserAddGroup.class})
    @Length(min = 6, max = 30, message = "{user.UserForm.password}")
    private String password;
    @NotNull(message = "{user.UserForm.activate}", groups = {UserAddGroup.class, UserUpdateGroup.class})
    private Boolean activate;
    @NotNull(message = "{user.UserForm.roleIds}", groups = {UserAddGroup.class, UserUpdateGroup.class})
    private Long[] roleIds;
}
