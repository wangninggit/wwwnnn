package com.viathink.core.user.mapper;

import com.viathink.core.user.bean.DictionaryEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DictionaryMapper {
    void addDictionary(DictionaryEntity dictionaryEntity);

    void updateDictionaryById(DictionaryEntity dictionaryEntity);

    DictionaryEntity findDictionaryById(Long id);

    void deleteDictionaryById(Long id);

    List<DictionaryEntity> findDictionaryByPage();

    void deleteDictionaryByCategoryId(Long categoryId);

}
