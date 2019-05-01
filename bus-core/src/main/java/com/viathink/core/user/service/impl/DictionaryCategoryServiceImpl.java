package com.viathink.core.user.service.impl;

import com.github.pagehelper.PageHelper;
import com.viathink.core.user.bean.DictionaryCategoryEntity;
import com.viathink.core.user.dto.PageParam;
import com.viathink.core.user.mapper.DictionaryCategoryMapper;
import com.viathink.core.user.mapper.DictionaryMapper;
import com.viathink.core.user.service.DictionaryCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DictionaryCategoryServiceImpl implements DictionaryCategoryService {

    private final DictionaryCategoryMapper dictionaryCategoryMapper;
    private final DictionaryMapper dictionaryMapper;

    @Autowired
    public DictionaryCategoryServiceImpl(DictionaryCategoryMapper dictionaryCategoryMapper, DictionaryMapper dictionaryMapper) {
        this.dictionaryCategoryMapper = dictionaryCategoryMapper;
        this.dictionaryMapper = dictionaryMapper;
    }

    @Override
    public void addDictionaryCategory(DictionaryCategoryEntity dictionaryCategoryEntity) {
        dictionaryCategoryMapper.addDictionaryCategory(dictionaryCategoryEntity);
    }

    @Override
    public Boolean deleteDictionaryCategoryById(Long id) {
        DictionaryCategoryEntity dictionaryCategoryEntity =dictionaryCategoryMapper.findDictionaryCategoryById(id);
        if(dictionaryCategoryEntity == null){
            return false;
        }
        dictionaryMapper.deleteDictionaryByCategoryId(id);
        dictionaryCategoryMapper.deleteDictionaryCategoryById(id);
        return true;
    }

    @Override
    public Boolean updateDictionaryCategory(DictionaryCategoryEntity dictionaryCategoryEntity) {
        DictionaryCategoryEntity dictionaryCategoryEntity1 =dictionaryCategoryMapper.findDictionaryCategoryById(dictionaryCategoryEntity.getId());
        if(dictionaryCategoryEntity1 == null){
            return false;
        }
        dictionaryCategoryMapper.updateDictionaryCategory(dictionaryCategoryEntity);
        return true;
    }

    @Override
    public DictionaryCategoryEntity findDictionaryCategoryById(Long id) {
        return dictionaryCategoryMapper.findDictionaryCategoryById(id);
    }

    @Override
    public List<DictionaryCategoryEntity> findDictionaryCategoryByPage(DictionaryCategoryEntity dictionaryCategoryEntity, PageParam pageParam) {
        PageHelper.startPage(pageParam.getPageNum(), pageParam.getPageSize());
        return dictionaryCategoryMapper.findDictionaryCategoryByPage(dictionaryCategoryEntity);
    }

    @Override
    public List<DictionaryCategoryEntity> findDictionaryCategory() {
        return dictionaryCategoryMapper.findDictionaryCategory();
    }
}
