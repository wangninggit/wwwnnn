package com.viathink.api.user.controller;

import com.viathink.api.common.exception.ParamsInvalidException;
import com.viathink.api.util.CommonUtil;
import com.viathink.core.user.bean.PermissionEntity;
import com.viathink.core.user.bean.RoleEntity;
import com.viathink.core.user.bean.RolePermissionUpdateEntity;
import com.viathink.core.user.dao.RolePermissionPageDao;
import com.viathink.core.user.dto.PermissionTypeDto;
import com.viathink.core.user.dto.RoleForm;
import com.viathink.core.user.dto.RolePermissionPageDto;
import com.viathink.core.user.service.PermissionService;
import com.viathink.core.user.service.RolePermissionService;
import com.viathink.core.user.service.RoleService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
public class RolePermissionController {
    private final RolePermissionService  rolePermissionService;
    private final RoleService roleService;
    private final PermissionService permissionService;
    private final RolePermissionPageDao rolePermissionPageDao;

    @Autowired
    public RolePermissionController(RolePermissionService rolePermissionService, RoleService roleService, PermissionService permissionService, RolePermissionPageDao rolePermissionPageDao) {
        this.rolePermissionService = rolePermissionService;
        this.roleService = roleService;
        this.permissionService = permissionService;
        this.rolePermissionPageDao = rolePermissionPageDao;
    }

    @RequiresRoles("admin")
    @GetMapping(value = "/roles/{role_id}/permissions")
    public RoleForm findPermissionIdsByRoleId(@PathVariable("role_id") Long role_id){
        RoleEntity roleEntity = roleService.findRoleById(role_id);
        if(roleEntity == null){
            throw new ParamsInvalidException(3004,"角色不存在");
        }
        RoleForm roleForm = new RoleForm();
        roleForm.setId(role_id);
        roleForm.setName(roleEntity.getName());
        roleForm.setLabel(roleEntity.getLabel());

        Map<String, Map<String, Object>> item = rolePermissionPageDao.getAllPermissions();

        List<RolePermissionPageDto> list = rolePermissionService.findPermissionsByRoleId(role_id);
        for(RolePermissionPageDto rolePermissionPageDto :list){
//            Object permissions = item.get(rolePermissionPageDto.getPageName()).get("permissions");
//            Map<String,Object> map = CommonUtil.objToMap(permissions);
            Map<String,Object> map = (Map<String, Object>) item.get(rolePermissionPageDto.getPageName()).get("permissions");
            map.put(rolePermissionPageDto.getType(),true);
        }
        roleForm.setPermissions(item);
        return roleForm;
    }

    @RequiresRoles("admin")
    @PostMapping(value = "/roles/{role_id}/permissions")
    public ResponseEntity updateRolePermissions(@PathVariable("role_id") Long role_id,
                                                @Valid @RequestBody Map<String, Map<String,Object>> map){

        //1.判断该角色是否存在
        RoleEntity roleEntity = roleService.findRoleById(role_id);
        if(roleEntity == null){
            throw new ParamsInvalidException(3004,"角色不存在");
        }
        //2.获取要添加的权限的ids 数组
        List<Long> ids = new ArrayList<>();
        Set<String> keys = map.keySet();   //此行可省略，直接将map.keySet()写在for-each循环的条件中
        for(String key:keys){
            Map<String,Object> item = CommonUtil.objToMap(map.get(key).get("permissions"));
            for(String innerKey:item.keySet()){
                if(item.get(innerKey).equals(true)){
                    //获取对应的权限
                    PermissionTypeDto permissionTypeDto = new PermissionTypeDto();
                    permissionTypeDto.setPageName(key);
                    permissionTypeDto.setType(innerKey);
                    PermissionEntity permissionEntity = permissionService.findPermissionByPageNameAndType(permissionTypeDto);
                    if(permissionEntity == null){
                        throw new ParamsInvalidException(4001,key+"页面"+innerKey+"对应的权限不存在");
                    }
                    ids.add(permissionEntity.getId());
                }

            }
        }

        //3.添加角色权限关系
        RolePermissionUpdateEntity entity = new RolePermissionUpdateEntity();
        entity.setIds(ids);
        entity.setRoleId(role_id);
        rolePermissionService.updateRolePermissions(entity);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @RequiresUser
    @GetMapping(value = "/roles/permissions")
    public Map<String, Map<String,Object>> findAllPermissions(){
        //2.获取所有的页面及权限进行初始化
        return rolePermissionPageDao.getAllPermissions();
    }

}
