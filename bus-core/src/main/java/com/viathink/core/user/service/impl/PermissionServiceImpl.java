package com.viathink.core.user.service.impl;

import com.github.pagehelper.PageHelper;
import com.viathink.core.user.bean.PermissionEntity;
import com.viathink.core.user.bean.PermissionsGroupByPageEntity;
import com.viathink.core.user.dto.PageParam;
import com.viathink.core.user.dto.PermissionTypeDto;
import com.viathink.core.user.mapper.PermissionMapper;
import com.viathink.core.user.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PermissionServiceImpl implements PermissionService {
    private final PermissionMapper permissionMapper;

    @Autowired
    public PermissionServiceImpl(PermissionMapper permissionMapper) {
        this.permissionMapper = permissionMapper;
    }

    @Override
    public void addPermission(PermissionEntity permissionEntity) {
        permissionMapper.addPermission(permissionEntity);
    }

    @Override
    public Boolean updatePermissionById(PermissionEntity permissionEntity) {
        PermissionEntity permission = permissionMapper.findPermissionById(permissionEntity.getId());
        if (permission == null) {
            return false;
        }
        permissionMapper.updatePermissionById(permissionEntity);
        return true;
    }

    @Override
    public PermissionEntity findPermissionById(Long id) {
        return permissionMapper.findPermissionById(id);
    }

    @Override
    public Boolean deletePermissionById(Long id) {
        PermissionEntity permission = permissionMapper.findPermissionById(id);
        if (permission == null) {
            return false;
        }
        permissionMapper.deletePermissionById(id);
        return true;
    }

    @Override
    public List<PermissionEntity> findPermissionByPage(PageParam pageParam) {
        PageHelper.startPage(pageParam.getPageNum(), pageParam.getPageSize());
        return permissionMapper.findPermissionByPage();
    }

    @Override
    public List<PermissionsGroupByPageEntity> findPermissionsGroupByPage() {
        return permissionMapper.findPermissionsGroupByPage();
    }

    @Override
    public PermissionEntity findPermissionByPageNameAndType(PermissionTypeDto permissionTypeDto) {

        return permissionMapper.findPermissionByPageNameAndType(permissionTypeDto);
    }
}
