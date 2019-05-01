package com.viathink.core.business.gene.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.viathink.core.business.gene.bean.DoctorEntity;
import com.viathink.core.business.gene.bean.EventBaseEntity;
import com.viathink.core.business.gene.mapper.DoctorMapper;
import com.viathink.core.business.gene.service.DoctorEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DoctorEventServiceImpl implements DoctorEventService {

    private final DoctorMapper doctorMapper;

    @Autowired
    public DoctorEventServiceImpl(DoctorMapper doctorMapper) {
        this.doctorMapper = doctorMapper;
    }

    @Override
    public void eventHandle(EventBaseEntity event, JSONObject object) {

        DoctorEntity doctorEntity1 = doctorMapper.findDoctorById(object.getString("doctorId"));

        DoctorEntity doctorEntity = new DoctorEntity();
        doctorEntity.setDoctorId(object.getString("doctorId"));
        doctorEntity.setDoctorName(object.getString("doctorName"));
        doctorEntity.setHospitalName(object.getString("hospitalName"));
        doctorEntity.setHospitalAddress(object.getString("hospitalAddress"));

        if(doctorEntity1 == null){
            doctorMapper.addDoctor(doctorEntity);
        }else{
            doctorMapper.updateDoctor(doctorEntity);
        }
    }
}
