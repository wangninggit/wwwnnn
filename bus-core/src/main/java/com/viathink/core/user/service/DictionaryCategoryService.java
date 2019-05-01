package com.viathink.core.user.service;

import com.viathink.core.user.bean.DictionaryCategoryEntity;
import com.viathink.core.user.dto.PageParam;

import java.util.List;

public interface DictionaryCategoryService {
    void addDictionaryCategory(DictionaryCategoryEntity dictionaryCategoryEntity);
    Boolean deleteDictionaryCategoryById(Long id);
    Boolean updateDictionaryCategory(DictionaryCategoryEntity dictionaryCategoryEntity);
    DictionaryCategoryEntity findDictionaryCategoryById(Long id);
    List<DictionaryCategoryEntity> findDictionaryCategoryByPage(DictionaryCategoryEntity dictionaryCategoryEntity, PageParam pageParam);
    List<DictionaryCategoryEntity> findDictionaryCategory();
}
