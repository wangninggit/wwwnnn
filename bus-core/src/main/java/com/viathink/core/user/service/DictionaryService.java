package com.viathink.core.user.service;

import com.viathink.core.user.bean.DictionaryEntity;
import com.viathink.core.user.dto.PageParam;

import java.util.List;

public interface DictionaryService {
    Boolean addDictionary(DictionaryEntity dictionaryEntity);

    Boolean updateDictionaryById(DictionaryEntity dictionaryEntity);

    DictionaryEntity findDictionaryById(Long id);

    Boolean deleteDictionaryById(Long id);

    List<DictionaryEntity> findDictionaryByPage(PageParam pageParam);
}
