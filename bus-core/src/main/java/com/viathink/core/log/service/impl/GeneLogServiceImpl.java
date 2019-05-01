package com.viathink.core.log.service.impl;

import com.viathink.core.log.bean.GeneLogEntity;
import com.viathink.core.log.mapper.GeneLogMapper;
import com.viathink.core.log.service.GeneLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class GeneLogServiceImpl implements GeneLogService {

    private final GeneLogMapper geneLogMapper;

    @Autowired
    public GeneLogServiceImpl(GeneLogMapper geneLogMapper) {
        this.geneLogMapper = geneLogMapper;
    }

    @Override
    public void addLog(GeneLogEntity geneLogEntity) {
        geneLogMapper.addLog(geneLogEntity);
    }
}
