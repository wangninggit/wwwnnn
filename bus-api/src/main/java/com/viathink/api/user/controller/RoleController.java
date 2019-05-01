package com.viathink.api.user.controller;

import com.github.pagehelper.PageInfo;
import com.viathink.api.common.exception.ParamsInvalidException;
import com.viathink.core.user.bean.RoleEntity;
import com.viathink.core.user.bean.RolePermissionUpdateEntity;
import com.viathink.core.user.dao.RolePermissionPageDao;
import com.viathink.core.user.dto.PageParam;
import com.viathink.core.user.dto.RoleForm;
import com.viathink.core.user.service.RoleService;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
public class RoleController {
    private final RoleService roleService;
    private final RolePermissionPageDao rolePermissionPageDao;

    @Autowired
    public RoleController(RoleService roleService, RolePermissionPageDao rolePermissionPageDao) {
        this.roleService = roleService;
        this.rolePermissionPageDao = rolePermissionPageDao;
    }

    @RequiresRoles("admin")
    @PostMapping(value = "/roles")
    public Map<String,Long> addRole(@Valid @RequestBody RoleForm roleForm){
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setName(roleForm.getName());
        roleEntity.setLabel(roleForm.getLabel());
        Map<String, Map<String,Object>> map = roleForm.getPermissions();
        List<Long> ids = rolePermissionPageDao.getUpdatePermissionIds(map);
        if(ids==null){
            throw new ParamsInvalidException(4001,"权限不存在");
        }
        RolePermissionUpdateEntity entity = new RolePermissionUpdateEntity();
        entity.setIds(ids);

        roleService.addRole(roleEntity,entity);
        Long id = roleEntity.getId();
        Map<String,Long> returnMap = new HashMap<>();
        returnMap.put("id",id);
        return returnMap;
    }

    @RequiresRoles("admin")
    @DeleteMapping(value = "/roles/{id}")
    public ResponseEntity deleteRoleById(@PathVariable Long id) {
        Boolean isExit = roleService.deleteRoleById(id);
        if(!isExit){
            throw new ParamsInvalidException(3004,"此角色不存在");
        }
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @RequiresRoles("admin")
    @PutMapping(value = "/roles/{id}")
    public Map<String,Long> UpdateRole(@PathVariable Long id,@Valid @RequestBody RoleForm roleForm){
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setName(roleForm.getName());
        roleEntity.setLabel(roleForm.getLabel());
        roleEntity.setId(id);

        Map<String, Map<String,Object>> map = roleForm.getPermissions();
        List<Long> ids = rolePermissionPageDao.getUpdatePermissionIds(map);
        if(ids==null){
            throw new ParamsInvalidException(4001,"权限不存在");
        }
        RolePermissionUpdateEntity entity = new RolePermissionUpdateEntity();
        entity.setIds(ids);
        entity.setRoleId(id);

        Boolean isExit = roleService.updateRole(roleEntity,entity);
        if(!isExit){
            throw new ParamsInvalidException(3004,"此角色不存在");
        }
        Map<String,Long> returnMap = new HashMap<>();
        returnMap.put("id",id);
        return returnMap;
    }

    @RequiresRoles("admin")
    @GetMapping(value = "/roles/{id}")
    public RoleEntity findRoleById(@PathVariable Long id){
        RoleEntity role = roleService.findRoleById(id);
        if(role == null){
            throw new ParamsInvalidException(3004,"此角色不存在");
        }
        return role;
    }

    @RequiresRoles("admin")
    @GetMapping(value = "/roles")
    public PageInfo<RoleEntity> findRoleByPage(RoleEntity roleEntity, @RequestParam(defaultValue = "1") Integer pageNum,
                                               @RequestParam(defaultValue = "10") Integer pageSize){
        PageParam pageParam = new PageParam();
        pageParam.setPageNum(pageNum);
        pageParam.setPageSize(pageSize);
        List<RoleEntity> roles = roleService.findRoleByPage(roleEntity,pageParam);
        return new PageInfo<>(roles);
    }

    @RequiresRoles("admin")
    @GetMapping(value = "/roles/all")
    public List<RoleEntity> findRoles(){
        return roleService.findRoles();
    }
}
