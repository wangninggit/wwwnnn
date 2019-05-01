package com.viathink.core.batch.dao;

import com.viathink.core.business.gene.bean.SnapshootEntity;

public interface SnapshootDao {
    void addOrUpdateSnapshootById(SnapshootEntity entity);
}
