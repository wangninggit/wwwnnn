package com.viathink.api.user.controller;

import com.github.pagehelper.PageInfo;
import com.viathink.api.common.exception.ParamsInvalidException;
import com.viathink.core.user.bean.DictionaryEntity;
import com.viathink.core.user.dto.DictionaryForm;
import com.viathink.core.user.dto.PageParam;
import com.viathink.core.user.service.DictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class DictionaryController {
    private final DictionaryService dictionaryService;

    @Autowired
    public DictionaryController(DictionaryService dictionaryService) {
        this.dictionaryService = dictionaryService;
    }

    @PostMapping("/dictionaries")
    public Map<String, Long> addDictionary(@Valid @RequestBody DictionaryForm form) {
        DictionaryEntity dictionaryEntity = new DictionaryEntity();
        dictionaryEntity.setLabel(form.getLabel());
        dictionaryEntity.setValue(form.getValue());
        dictionaryEntity.setCategoryId(form.getCategoryId());
        Boolean successFlag = dictionaryService.addDictionary(dictionaryEntity);
        if(!successFlag)
            throw new ParamsInvalidException(4005,"此id对应的记录不存在");
        Map<String, Long> map = new HashMap<>();
        map.put("id", dictionaryEntity.getId());
        return map;
    }

    @DeleteMapping("/dictionaries/{id}")
    public ResponseEntity deleteDictionaryById(@PathVariable Long id) {
        Boolean result = dictionaryService.deleteDictionaryById(id);
        if (!result) {
            throw new ParamsInvalidException(4005,"此id对应的记录不存在");
        }
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/dictionaries/{id}")
    public Map<String, Long> updateDictionary(@PathVariable Long id, @Valid @RequestBody DictionaryForm form) {
        DictionaryEntity dictionaryEntity = new DictionaryEntity();
        dictionaryEntity.setLabel(form.getLabel());
        dictionaryEntity.setValue(form.getValue());
        dictionaryEntity.setCategoryId(form.getCategoryId());
        dictionaryEntity.setId(id);
        Boolean result = dictionaryService.updateDictionaryById(dictionaryEntity);
        if (!result) {
            throw new ParamsInvalidException(4005,"此id对应的记录不存在");
        }
        Map<String, Long> map = new HashMap<>();
        map.put("id", id);
        return map;
    }

    @GetMapping("/dictionaries/{id}")
    public DictionaryEntity getPermissionById(@PathVariable Long id) {
        DictionaryEntity dictionary = dictionaryService.findDictionaryById(id);
        if (dictionary == null) {
            throw new ParamsInvalidException(4005,"此id对应的记录不存在");
        }
        return dictionary;
    }

    @GetMapping(value = "/dictionaries")
    public PageInfo<DictionaryEntity> getDictionaryByPage(@RequestParam(defaultValue = "1") Integer pageNum,
                                                          @RequestParam(defaultValue = "10") Integer pageSize) {
        PageParam pageParam = new PageParam();
        pageParam.setPageNum(pageNum);
        pageParam.setPageSize(pageSize);
        List<DictionaryEntity> list = dictionaryService.findDictionaryByPage(pageParam);
        return new PageInfo<>(list);
    }
}
