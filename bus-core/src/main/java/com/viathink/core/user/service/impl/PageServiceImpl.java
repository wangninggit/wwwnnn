package com.viathink.core.user.service.impl;

import com.github.pagehelper.PageHelper;
import com.viathink.core.user.bean.PageEntity;
import com.viathink.core.user.dto.PageParam;
import com.viathink.core.user.mapper.PageMapper;
import com.viathink.core.user.mapper.PermissionMapper;
import com.viathink.core.user.mapper.RolePermissionMapper;
import com.viathink.core.user.service.PageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
//自动管理事务
@Transactional
public class PageServiceImpl implements PageService {

    private final PageMapper pageMapper;
    private final PermissionMapper permissionMapper;
    private final RolePermissionMapper rolePermissionMapper;

    @Autowired
    public PageServiceImpl(PageMapper pageMapper, PermissionMapper permissionMapper, RolePermissionMapper rolePermissionMapper) {
        this.pageMapper = pageMapper;
        this.permissionMapper = permissionMapper;
        this.rolePermissionMapper = rolePermissionMapper;
    }

    @Override
    public void addPage(PageEntity pageEntity) {
        pageMapper.addPage(pageEntity);
    }

    @Override
    public Boolean deletePageById(Long id) {
        PageEntity page = pageMapper.findPageById(id);
        if(page == null){
            return false;
        }
        rolePermissionMapper.deleteRolePermissionsByPageId(id);
        permissionMapper.deletePermissionByPageId(id);
        pageMapper.deletePageById(id);
        return true;
    }

    @Override
    public Boolean updatePage(PageEntity pageEntity) {
        PageEntity page = pageMapper.findPageById(pageEntity.getId());
        if(page == null){
            return false;
        }
        pageMapper.updatePage(pageEntity);
        return true;
    }

    @Override
    public List<PageEntity> findPagesByPage(PageParam pageParam) {
        PageHelper.startPage(pageParam.getPageNum(), pageParam.getPageSize());
        return pageMapper.findPagesByPage();
    }
}
