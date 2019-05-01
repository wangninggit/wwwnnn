package com.viathink.api.user.controller;

import com.viathink.api.common.exception.ParamsInvalidException;
import com.viathink.core.user.bean.UserRoleEntity;
import com.viathink.core.user.dto.UserRoleForm;
import com.viathink.core.user.service.UserRoleService;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class UserRoleController {

    private final UserRoleService userRoleService;

    @Autowired
    public UserRoleController(UserRoleService userRoleService) {
        this.userRoleService = userRoleService;
    }

    @RequiresRoles("admin")
    @GetMapping(value = "/users/{id}/roles")
    public Map<String, List<Long>> findByUserId(@PathVariable Long id) {
        List<UserRoleEntity> list = userRoleService.findByUserId(id);
        List<Long> idList = list.stream().map(UserRoleEntity::getRoleId).collect(Collectors.toList());
        Map<String, List<Long>> map = new HashMap<>();
        map.put("list", idList);
        return map;
    }

    @RequiresRoles("admin")
    @PutMapping(value = "/users/{id}/roles")
    public Long[] updateUserRoles(@PathVariable Long id, @Valid @RequestBody UserRoleForm userRoleForm) {
        userRoleForm.setUserId(id);
        String isExit = userRoleService.updateUserRoles(userRoleForm);
        if (isExit.equals("userNotFound")) {
            throw new ParamsInvalidException(2008,"此用户不存在");
        } else if (isExit.equals("roleNotFound")) {
            throw new ParamsInvalidException(3004,"角色不存在");
        }
        return userRoleForm.getRoleIds();
    }

}
