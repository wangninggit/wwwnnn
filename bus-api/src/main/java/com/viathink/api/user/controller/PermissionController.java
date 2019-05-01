package com.viathink.api.user.controller;

import com.github.pagehelper.PageInfo;
import com.viathink.api.common.exception.ParamsInvalidException;
import com.viathink.core.user.bean.PermissionEntity;
import com.viathink.core.user.bean.PermissionsGroupByPageEntity;
import com.viathink.core.user.dto.PageParam;
import com.viathink.core.user.dto.PermissionForm;
import com.viathink.core.user.service.PermissionService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class PermissionController {
    private final PermissionService permissionService;

    @Autowired
    public PermissionController(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @RequiresPermissions("permission:get")
    @GetMapping("/permissions/{id}")
    public PermissionEntity getPermissionById(@PathVariable Long id) {
        PermissionEntity permissionEntity = permissionService.findPermissionById(id);
        if (permissionEntity == null) {
            throw new ParamsInvalidException(4001,"此id对应的记录不存在");
        }
        return permissionEntity;
    }
    @RequiresPermissions("permission:add")
    @PostMapping("/permissions")
    public Map<String, Long> addPermission(@Valid @RequestBody PermissionForm form) {
        PermissionEntity permissionEntity = new PermissionEntity();
        permissionEntity.setPageId(form.getPageId());
        permissionEntity.setLabel(form.getLabel());
        permissionEntity.setName(form.getName());
        permissionEntity.setType(form.getType());
        permissionEntity.setSort(form.getSort());
        permissionEntity.setUrl(form.getUrl());
        permissionEntity.setParent(form.getParent());
        permissionService.addPermission(permissionEntity);
        Map<String, Long> map = new HashMap<>();
        map.put("id", permissionEntity.getId());
        return map;
    }

    @RequiresPermissions("permission:update")
    @PutMapping("/permissions/{id}")
    public Map<String, Long> updatePermission(@PathVariable Long id, @Valid @RequestBody PermissionForm form) {
        PermissionEntity permissionEntity = new PermissionEntity();
        permissionEntity.setId(id);
        permissionEntity.setPageId(form.getPageId());
        permissionEntity.setLabel(form.getLabel());
        permissionEntity.setName(form.getName());
        permissionEntity.setType(form.getType());
        permissionEntity.setSort(form.getSort());
        permissionEntity.setUrl(form.getUrl());
        permissionEntity.setParent(form.getParent());
        Boolean result = permissionService.updatePermissionById(permissionEntity);
        if (!result) {
            throw new ParamsInvalidException(4001,"此id对应的记录不存在");
        }
        Map<String, Long> map = new HashMap<>();
        map.put("id", id);
        return map;
    }

    @RequiresPermissions("permission:delete")
    @DeleteMapping("/permissions/{id}")
    public ResponseEntity deletePermissionById(@PathVariable Long id) {
        Boolean result = permissionService.deletePermissionById(id);
        if (!result) {
            throw new ParamsInvalidException(4001,"此id对应的记录不存在");
        }
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @RequiresPermissions("permission:list")
    @GetMapping(value = "/permissions")
    public PageInfo<PermissionEntity> getPermissionByPage(@RequestParam(defaultValue = "1") Integer pageNum,
    @RequestParam(defaultValue = "10") Integer pageSize) {
        PageParam pageParam = new PageParam();
        pageParam.setPageNum(pageNum);
        pageParam.setPageSize(pageSize);
        List<PermissionEntity> list = permissionService.findPermissionByPage(pageParam);
        return new PageInfo<>(list);
    }

    @RequiresUser
    @GetMapping(value = "/permissions/group")
    public List<PermissionsGroupByPageEntity> getPermissionGroupByPage() {
        return permissionService.findPermissionsGroupByPage();
    }
}
