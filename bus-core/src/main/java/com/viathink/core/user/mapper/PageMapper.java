package com.viathink.core.user.mapper;

import com.viathink.core.user.bean.PageEntity;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PageMapper {
    Long addPage(PageEntity pageEntity);
    void deletePageById(Long id);
    void updatePage(PageEntity pageEntity);
    List<PageEntity> findPagesByPage();
    PageEntity findPageById(Long id);
}
