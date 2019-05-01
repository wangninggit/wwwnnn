package com.viathink.core.business.gene.mapper;

import com.viathink.core.business.gene.bean.LocalStaffEntity;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface LocalStaffMapper {
    void addLocalStaff(LocalStaffEntity localStaffEntity);
    void updateLocalStaff(LocalStaffEntity localStaffEntity);
    LocalStaffEntity findLocalStaffById(String id);
    Long getCountWhenJobNumberEqualsButIdNotEquals(LocalStaffEntity entity);
    List<LocalStaffEntity> findLocalStaffByJobNumber(String jobNumber);
}
