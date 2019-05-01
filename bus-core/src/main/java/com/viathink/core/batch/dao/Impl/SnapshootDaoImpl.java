package com.viathink.core.batch.dao.Impl;

import com.viathink.core.batch.dao.SnapshootDao;
import com.viathink.core.business.gene.bean.SnapshootEntity;
import com.viathink.core.business.gene.mapper.SnapshootMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class SnapshootDaoImpl implements SnapshootDao {
    private final SnapshootMapper snapshootMapper;

    @Autowired
    public SnapshootDaoImpl(SnapshootMapper snapshootMapper) {
        this.snapshootMapper = snapshootMapper;
    }

    @Override
    public void addOrUpdateSnapshootById(SnapshootEntity entity) {
        SnapshootEntity snapshoot_db = snapshootMapper.findSnapshootByClass(entity);
        if(snapshoot_db == null){
            snapshootMapper.addSnapshoot(entity);
        }else{
            entity.setId(snapshoot_db.getId());
            snapshootMapper.updateSnapshootById(entity);
        }
    }
}
