package com.viathink.core.business.gene.mapper;

import com.viathink.core.business.gene.bean.DoctorEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorMapper {
    void addDoctor(DoctorEntity doctorEntity);
    void updateDoctor(DoctorEntity doctorEntity);
    DoctorEntity findDoctorById(String id);
}
