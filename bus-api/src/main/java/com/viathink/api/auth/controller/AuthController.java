package com.viathink.api.auth.controller;

import com.viathink.api.common.exception.ParamsInvalidException;
import com.viathink.core.auth.service.AuthTokenService;
import com.viathink.core.user.dto.LoginForm;
import com.viathink.core.user.dto.UserRolePermissionDto;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
public class AuthController {
    private final AuthTokenService authTokenService;

    @Autowired
    public AuthController(AuthTokenService authTokenService) {
        this.authTokenService = authTokenService;
    }

    @PostMapping(value = "/login")
    public Map<String, String> login(@Valid @RequestBody LoginForm form) {
        String result = authTokenService.login(form);
        if (result.equals("userNotFound") || result.equals("passwordError")) {
            throw new ParamsInvalidException(2011,"账号或密码错误");
        }
        switch (result) {
            case "userIsNotActivate":
                throw new ParamsInvalidException(2012,"账号未启用");
            case "userNotFound":
                throw new ParamsInvalidException(2011,"账号或密码错误");
            case "passwordError":
                throw new ParamsInvalidException(2011,"账号或密码错误");
        }
        Map<String, String> map = new HashMap<>();
        map.put("token", result);
        return map;
    }
    @RequiresUser
    @GetMapping(value = "/logout")
    public ResponseEntity logout() {
        Subject subject = SecurityUtils.getSubject();
        UserRolePermissionDto userInfo = (UserRolePermissionDto) subject.getPrincipal();
        authTokenService.deleteAuthTokenById(userInfo.getTokenId());
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
