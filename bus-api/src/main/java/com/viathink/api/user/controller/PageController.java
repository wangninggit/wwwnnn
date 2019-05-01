package com.viathink.api.user.controller;

import com.github.pagehelper.PageInfo;
import com.viathink.api.common.exception.ParamsInvalidException;
import com.viathink.core.user.bean.PageEntity;
import com.viathink.core.user.dto.PageForm;
import com.viathink.core.user.service.PageService;
import com.viathink.core.user.dto.PageParam;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class PageController {
    private final PageService pageService;

    @Autowired
    public PageController(PageService pageService) {
        this.pageService = pageService;
    }

    @RequiresPermissions("page:add")
    @PostMapping(value = "/pages")
    public Map<String,Long> addPage(@Valid @RequestBody PageForm pageForm){
        PageEntity pageEntity = new PageEntity();
        pageEntity.setName(pageForm.getName());
        pageService.addPage(pageEntity);
        Map<String, Long> returnMap = new HashMap<>();
        returnMap.put("id", pageEntity.getId());
        return returnMap;
    }

    @RequiresPermissions("page:delete")
    @DeleteMapping(value = "/pages/{id}")
    public ResponseEntity deletePageById(@PathVariable Long id){
        Boolean exist = pageService.deletePageById(id);
        if(!exist){
            throw new ParamsInvalidException(4004,"此id对应的记录不存在");
        }
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @RequiresPermissions("page:update")
    @PutMapping(value = "/pages/{id}")
    public Map<String,Long> updatePage(@PathVariable Long id,@Valid @RequestBody PageForm pageForm) {
        PageEntity pageEntity = new PageEntity();
        pageEntity.setName(pageForm.getName());
        pageEntity.setId(id);
        Boolean exist = pageService.updatePage(pageEntity);
        if(!exist){
            throw new ParamsInvalidException(4004,"此id对应的记录不存在");
        }
        Map<String, Long> returnMap = new HashMap<>();
        returnMap.put("id", pageEntity.getId());
        return returnMap;
    }

    @RequiresPermissions("page:list")
    @GetMapping(value = "/pages")
    public PageInfo<PageEntity> getPagesByPage(
            @RequestParam(value = "pageSize",required = false,defaultValue = "10") Integer pageSize,
            @RequestParam(value = "pageNum",required = false,defaultValue = "1") Integer pageNum) {
        PageParam pageParam = new PageParam();
        pageParam.setPageSize(pageSize);
        pageParam.setPageNum(pageNum);
        List<PageEntity> pages = pageService.findPagesByPage(pageParam);
        return new PageInfo<>(pages);
    }
}
