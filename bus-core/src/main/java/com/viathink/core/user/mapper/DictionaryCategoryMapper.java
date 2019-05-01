package com.viathink.core.user.mapper;

import com.viathink.core.user.bean.DictionaryCategoryEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DictionaryCategoryMapper {
    void addDictionaryCategory(DictionaryCategoryEntity dictionaryCategoryEntity);
    void deleteDictionaryCategoryById(Long id);
    void updateDictionaryCategory(DictionaryCategoryEntity dictionaryCategoryEntity);
    DictionaryCategoryEntity findDictionaryCategoryById(Long id);
    List<DictionaryCategoryEntity> findDictionaryCategoryByPage(DictionaryCategoryEntity dictionaryCategoryEntity);
    List<DictionaryCategoryEntity> findDictionaryCategory();
}
