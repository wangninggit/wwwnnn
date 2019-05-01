package com.viathink.core.user.service;

import com.viathink.core.user.bean.PageEntity;
import com.viathink.core.user.dto.PageParam;

import java.util.List;

public interface PageService {
    void addPage(PageEntity pageEntity);
    Boolean deletePageById(Long id);
    Boolean updatePage(PageEntity pageEntity);
    List<PageEntity> findPagesByPage(PageParam pageParam);
}
