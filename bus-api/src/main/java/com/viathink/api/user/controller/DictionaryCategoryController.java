package com.viathink.api.user.controller;

import com.github.pagehelper.PageInfo;
import com.viathink.api.common.exception.ParamsInvalidException;
import com.viathink.core.user.bean.DictionaryCategoryEntity;
import com.viathink.core.user.dto.DictionaryCategoryForm;
import com.viathink.core.user.dto.PageParam;
import com.viathink.core.user.service.DictionaryCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class DictionaryCategoryController {
    private final DictionaryCategoryService dictionaryCategoryService;

    @Autowired
    public DictionaryCategoryController(DictionaryCategoryService dictionaryCategoryService) {
        this.dictionaryCategoryService = dictionaryCategoryService;
    }

    @PostMapping(value = "/dictionary/categories")
    public Map<String,Long> addDictionaryCategory(@Valid @RequestBody DictionaryCategoryForm dictionaryCategoryForm){
        DictionaryCategoryEntity dictionaryCategoryEntity = new DictionaryCategoryEntity();
        dictionaryCategoryEntity.setLabel(dictionaryCategoryForm.getLabel());
        dictionaryCategoryService.addDictionaryCategory(dictionaryCategoryEntity);
        Map<String,Long> returnMap = new HashMap<>();
        returnMap.put("id",dictionaryCategoryEntity.getId());
        return returnMap;
    }

    @DeleteMapping(value = "/dictionary/categories/{id}")
    public ResponseEntity deleteDictionaryCategoryById(@PathVariable Long id){
        Boolean isExit = dictionaryCategoryService.deleteDictionaryCategoryById(id);
        if(!isExit){
            throw new ParamsInvalidException(4006,"此类型不存在");
        }
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PutMapping(value = "/dictionary/categories/{id}")
    public Map<String,Long> updateDictionaryCategory(@PathVariable Long id,@Valid @RequestBody DictionaryCategoryForm dictionaryCategoryForm){
        DictionaryCategoryEntity dictionaryCategoryEntity = new DictionaryCategoryEntity();
        dictionaryCategoryEntity.setLabel(dictionaryCategoryForm.getLabel());
        dictionaryCategoryEntity.setId(id);
        Boolean isExit = dictionaryCategoryService.updateDictionaryCategory(dictionaryCategoryEntity);
        if(!isExit){
            throw new ParamsInvalidException(4006,"此类型不存在");
        }
        Map<String,Long> returnMap = new HashMap<>();
        returnMap.put("id",id);
        return returnMap;
    }

    @GetMapping(value = "/dictionary/categories/{id}")
    public DictionaryCategoryEntity findDictionaryCategoryById(@PathVariable Long id){
        DictionaryCategoryEntity dictionaryCategoryEntity = dictionaryCategoryService.findDictionaryCategoryById(id);
        if(dictionaryCategoryEntity == null ){
            throw new ParamsInvalidException(4006,"此类型不存在");
        }
        return dictionaryCategoryEntity;
    }

    @GetMapping(value = "/dictionary/categories")
    public PageInfo<DictionaryCategoryEntity> findDictionaryCategoryByPage(DictionaryCategoryEntity dictionaryCategoryEntity, @RequestParam(defaultValue = "1") Integer pageNum,
                                                                           @RequestParam(defaultValue = "2") Integer pageSize){
        PageParam pageParam = new PageParam();
        pageParam.setPageNum(pageNum);
        pageParam.setPageSize(pageSize);
        List<DictionaryCategoryEntity> dictionaryCategoryEntities = dictionaryCategoryService.findDictionaryCategoryByPage(dictionaryCategoryEntity,pageParam);
        return new PageInfo<>(dictionaryCategoryEntities);
    }

    @GetMapping(value = "/dictionary/categories/all")
    public List<DictionaryCategoryEntity> findDictionaryCategory(){
        return dictionaryCategoryService.findDictionaryCategory();
    }

}
