package com.viathink.core.business.gene.dao.Impl;

import com.viathink.core.business.gene.bean.DoctorEntity;
import com.viathink.core.business.gene.dao.DoctorDao;
import com.viathink.core.business.gene.mapper.DoctorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DoctorDaoImpl implements DoctorDao {
    private final DoctorMapper doctorMapper;

    @Autowired
    public DoctorDaoImpl(DoctorMapper doctorMapper) {
        this.doctorMapper = doctorMapper;
    }

    @Override
    public void addOrUpdateDoctorById(DoctorEntity entity) {
        DoctorEntity doctorEntity = doctorMapper.findDoctorById(entity.getDoctorId());
        if (doctorEntity == null) {
            doctorMapper.addDoctor(entity);
        } else {
            doctorEntity.setDoctorId(entity.getDoctorId());
            doctorEntity.setDoctorName(entity.getDoctorName());
            doctorEntity.setHospitalName(entity.getHospitalName());
            doctorEntity.setHospitalAddress(entity.getHospitalAddress());
            doctorMapper.updateDoctor(doctorEntity);
        }
    }
}
