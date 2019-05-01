package com.viathink.api.user.controller;

import com.github.pagehelper.PageInfo;
import com.viathink.api.common.exception.ParamsInvalidException;
import com.viathink.core.user.bean.UserEntity;
import com.viathink.core.user.dto.*;
import com.viathink.core.user.service.UserService;
import com.viathink.core.util.CryptoUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequiresUser
    @GetMapping(value = "/users/current")
    public UserRoleMapDto getCurrentUser() {
        Subject subject = SecurityUtils.getSubject();
        UserEntity user = (UserEntity) subject.getPrincipal();
        return userService.findUserRoleByUserId(user.getId());
    }

    @RequiresRoles("admin")
    @GetMapping(value = "/users/{id}")
    public UserRoleResultDto getUserById(@PathVariable Long id) {
        UserRoleResultDto userRoleResultDto = userService.findUserByIdJoinRole(id);
        if (userRoleResultDto == null) {
            throw new ParamsInvalidException(2008,"此id对应的用户不存在");
        }
        return userRoleResultDto;
    }

    @RequiresRoles("admin")
    @GetMapping(value = "/users")
    public PageInfo<UserRoleDto> getUserByPage(UserRoleParamDto userRoleParamDto, @RequestParam(defaultValue = "1") Integer pageNum,
                                               @RequestParam(defaultValue = "10") Integer pageSize) {
        PageParam pageParam = new PageParam();
        pageParam.setPageNum(pageNum);
        pageParam.setPageSize(pageSize);
        List<UserRoleDto> users = userService.findUserByPage(userRoleParamDto, pageParam);
        return new PageInfo<>(users);
    }

    @RequiresRoles("admin")
    @PostMapping(value = "/users")
    public Map<String, Long> addUser(@Validated(UserForm.UserAddGroup.class) @RequestBody UserForm userForm) {
        String salt = CryptoUtil.getRandomSalt();
        UserEntity userEntity = new UserEntity();
        userEntity.setSalt(salt);
        userEntity.setEmail(userForm.getEmail());
        userEntity.setNickname(userForm.getNickname());
        userEntity.setPhoneNumber(userForm.getPhoneNumber());
        userEntity.setActivate(userForm.getActivate());
        userEntity.setPassword(CryptoUtil.sha1Hash(userForm.getPassword(), salt));
        UserRoleForm userRoleForm = new UserRoleForm();
        userRoleForm.setRoleIds(userForm.getRoleIds());
        String isExit = userService.addUser(userEntity, userRoleForm);
        if (isExit.equals("user is already exit")) {
            throw new ParamsInvalidException(2009,"该邮箱已经被注册");
        } else if (isExit.equals("roleNotFound")) {
            throw new ParamsInvalidException(3004,"角色不存在");
        }
        Long id = userEntity.getId();
        Map<String, Long> returnMap = new HashMap<>();
        returnMap.put("id", id);
        return returnMap;
    }

    @RequiresRoles("admin")
    @DeleteMapping(value = "/users/{id}")
    public ResponseEntity deleteUserById(@PathVariable Long id) {
        Boolean isExit = userService.deleteUserById(id);
        if (!isExit) {
            throw new ParamsInvalidException(2008,"此id对应的用户不存在");
        }
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @RequiresRoles("admin")
    @PutMapping(value = "/users/{id}")
    public Map<String, Long> updateUser(@PathVariable Long id, @Validated(UserForm.UserUpdateGroup.class) @RequestBody UserForm userForm) {
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(userForm.getEmail());
        userEntity.setNickname(userForm.getNickname());
        userEntity.setPhoneNumber(userForm.getPhoneNumber());
        userEntity.setActivate(userForm.getActivate());
        userEntity.setId(id);

        UserRoleForm userRoleForm = new UserRoleForm();
        userRoleForm.setRoleIds(userForm.getRoleIds());
        userRoleForm.setUserId(id);

        String isExit = userService.updateUser(userEntity, userRoleForm);
        switch (isExit) {
            case "userNotFound":
                throw new ParamsInvalidException(2008,"此id对应的用户不存在");
            case "emailIsRegistered":
                throw new ParamsInvalidException(2009,"该邮箱已经被注册");
            case "roleNotFound":
                throw new ParamsInvalidException(3004,"角色不存在");

        }
        Map<String, Long> returnMap = new HashMap<>();
        returnMap.put("id", id);
        return returnMap;
    }

    @RequiresRoles("admin")
    @PutMapping(value = "/users/{id}/password")
    public Map<String, Long> updateUserPassword(@PathVariable Long id, @Valid @RequestBody UserPasswordForm userPasswordForm) {
        if(!userPasswordForm.getPassword().equals(userPasswordForm.getConfirmPassword())){
            throw new ParamsInvalidException(2010,"两次输入密码不一致");
        }
        UserEntity userEntity = new UserEntity();
        String salt = CryptoUtil.getRandomSalt();
        userEntity.setSalt(salt);
        userEntity.setPassword(CryptoUtil.sha1Hash(userPasswordForm.getPassword(), salt));
        userEntity.setId(id);

        String isExit = userService.updateUserPassword(userEntity);
        switch (isExit) {
            case "userNotFound":
                throw new ParamsInvalidException(2008,"此id对应的用户不存在");
        }
        Map<String, Long> returnMap = new HashMap<>();
        returnMap.put("id", id);
        return returnMap;
    }


}
