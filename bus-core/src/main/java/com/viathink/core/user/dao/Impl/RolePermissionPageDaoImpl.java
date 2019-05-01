package com.viathink.core.user.dao.Impl;

import com.viathink.core.user.bean.PermissionEntity;
import com.viathink.core.user.dao.RolePermissionPageDao;
import com.viathink.core.user.dto.PermissionTypeDto;
import com.viathink.core.user.dto.RolePermissionPageDto;
import com.viathink.core.user.mapper.PermissionMapper;
import com.viathink.core.user.mapper.RolePermissionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class RolePermissionPageDaoImpl implements RolePermissionPageDao {
    private final RolePermissionMapper rolePermissionMapper;
    private final PermissionMapper permissionMapper;

    @Autowired
    public RolePermissionPageDaoImpl(RolePermissionMapper rolePermissionMapper, PermissionMapper permissionMapper) {
        this.rolePermissionMapper = rolePermissionMapper;
        this.permissionMapper = permissionMapper;
    }

    @Override
    public Map<String, Map<String, Object>> getAllPermissions() {
        List<RolePermissionPageDto> listAll = rolePermissionMapper.findPermissionsJoinPage();
        Map<String, Map<String,Object>> item = new HashMap<>();
        for(RolePermissionPageDto dto:listAll){
            //先判断键是否存在 role
            if(item.containsKey(dto.getPageName())){
                //role 存在,设置description
                item.get(dto.getPageName()).put("description",dto.getDescription());
                //判断permissions是否存在
                if(item.get(dto.getPageName()).containsKey("permissions")){
                    Map<String,Object> map = (Map<String, Object>) item.get(dto.getPageName()).get("permissions");
                    map.put(dto.getType(),false);
                }else{
                    Map<String,Object> map = new HashMap<>();
                    map.put(dto.getType(),false);
                    item.get(dto.getPageName()).put("permissions",map);
                }
            }else{
                Map<String, Object> item1 = new HashMap<>();
                item1.put("description",dto.getDescription());
                Map<String, Object> item2 = new HashMap<>();
                item2.put(dto.getType(),false);
                item1.put("permissions",item2);
                item.put(dto.getPageName(),item1);
            }
        }
        return item;
    }

    @Override
    public List<Long> getUpdatePermissionIds(Map<String, Map<String,Object>> map) {
        List<Long> ids = new ArrayList<>();
        Set<String> keys = map.keySet();   //此行可省略，直接将map.keySet()写在for-each循环的条件中
        for(String key:keys){
            Map<String,Object> item = (Map<String, Object>) map.get(key).get("permissions");
            for(String innerKey:item.keySet()){
                if(item.get(innerKey).equals(true)){
                    //获取对应的权限
                    PermissionTypeDto permissionTypeDto = new PermissionTypeDto();
                    permissionTypeDto.setPageName(key);
                    permissionTypeDto.setType(innerKey);
                    PermissionEntity permissionEntity = permissionMapper.findPermissionByPageNameAndType(permissionTypeDto);
                    if(permissionEntity==null){
                        return null;
                    }
                    ids.add(permissionEntity.getId());
                }

            }
        }
        return ids;
    }
}
