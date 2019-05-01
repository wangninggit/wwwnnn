package com.viathink.core.user.service.impl;

import com.github.pagehelper.PageHelper;
import com.viathink.core.user.bean.DictionaryCategoryEntity;
import com.viathink.core.user.bean.DictionaryEntity;
import com.viathink.core.user.dto.PageParam;
import com.viathink.core.user.mapper.DictionaryCategoryMapper;
import com.viathink.core.user.mapper.DictionaryMapper;
import com.viathink.core.user.service.DictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DictionaryServiceImpl implements DictionaryService {
    private final DictionaryMapper dictionaryMapper;
    private final DictionaryCategoryMapper dictionaryCategoryMapper;

    @Autowired
    public DictionaryServiceImpl(DictionaryMapper dictionaryMapper, DictionaryCategoryMapper dictionaryCategoryMapper) {
        this.dictionaryMapper = dictionaryMapper;
        this.dictionaryCategoryMapper = dictionaryCategoryMapper;
    }

    @Override
    public Boolean addDictionary(DictionaryEntity dictionaryEntity) {
        DictionaryCategoryEntity dictionaryCategoryEntity = dictionaryCategoryMapper.findDictionaryCategoryById(dictionaryEntity.getCategoryId());
        if(dictionaryCategoryEntity == null)
            return false;
        dictionaryMapper.addDictionary(dictionaryEntity);
        return true;
    }

    @Override
    public Boolean updateDictionaryById(DictionaryEntity dictionaryEntity) {
        DictionaryEntity dictionary = dictionaryMapper.findDictionaryById(dictionaryEntity.getId());
        if(dictionary == null)
            return false;
        dictionaryMapper.updateDictionaryById(dictionaryEntity);
        return true;
    }

    @Override
    public DictionaryEntity findDictionaryById(Long id) {
        return dictionaryMapper.findDictionaryById(id);
    }

    @Override
    public Boolean deleteDictionaryById(Long id) {
        DictionaryEntity dictionary = dictionaryMapper.findDictionaryById(id);
        if(dictionary == null)
            return false;
        dictionaryMapper.deleteDictionaryById(id);
        return true;
    }

    @Override
    public List<DictionaryEntity> findDictionaryByPage(PageParam pageParam) {
        PageHelper.startPage(pageParam.getPageNum(), pageParam.getPageSize());
        return dictionaryMapper.findDictionaryByPage();
    }
}
