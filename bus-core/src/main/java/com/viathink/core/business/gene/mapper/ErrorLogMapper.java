package com.viathink.core.business.gene.mapper;

import com.viathink.core.business.gene.bean.ErrorLogEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ErrorLogMapper {
    void addErrorLog(ErrorLogEntity entity);
    List<ErrorLogEntity> findAllFieldCheckError();
}
