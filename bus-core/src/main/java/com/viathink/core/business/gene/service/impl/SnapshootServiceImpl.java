package com.viathink.core.business.gene.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.viathink.core.batch.dto.OrderTrendMonthResultDto;
import com.viathink.core.business.gene.bean.SnapshootEntity;
import com.viathink.core.business.gene.dto.DateRangeStrDto;
import com.viathink.core.business.gene.mapper.SnapshootMapper;
import com.viathink.core.business.gene.service.SnapshootService;
import com.viathink.core.collection.bean.PropertiesEntity;
import com.viathink.core.collection.mapper.PropertiesMapper;
import com.viathink.core.user.dto.PageParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SnapshootServiceImpl implements SnapshootService {
    private Logger logger = LoggerFactory.getLogger(SnapshootServiceImpl.class);
    private final SnapshootMapper snapshootMapper;
    private final PropertiesMapper propertiesMapper;

    @Autowired
    public SnapshootServiceImpl(SnapshootMapper snapshootMapper, PropertiesMapper propertiesMapper) {
        this.snapshootMapper = snapshootMapper;
        this.propertiesMapper = propertiesMapper;
    }

    @Override
    public SnapshootEntity findSnapshootByClass(SnapshootEntity entity) {
        return snapshootMapper.findSnapshootByClass(entity);
    }

    @Override
    public List<OrderTrendMonthResultDto> findSnapshootByMonthAvg(DateRangeStrDto dateRangeStrDto,PageParam pageParam,Boolean isPage) {
        if(isPage){
            PageHelper.startPage(pageParam.getPageNum(), pageParam.getPageSize());
        }
        List<OrderTrendMonthResultDto> snapshootEntities = snapshootMapper.findSnapshootByMonthAvg(dateRangeStrDto);

        for(OrderTrendMonthResultDto snapshootEntity:snapshootEntities){
            JSONObject jsonObject = JSON.parseObject(snapshootEntity.getRecord());
            OrderTrendMonthResultDto orderTrendMonthResultDto = jsonObject.toJavaObject(OrderTrendMonthResultDto.class);
            snapshootEntity.setDate(orderTrendMonthResultDto.getDate());
            snapshootEntity.setWorkPlaceAvgCount(orderTrendMonthResultDto.getWorkPlaceAvgCount());
            snapshootEntity.setWorkPlaceAvgCost(orderTrendMonthResultDto.getWorkPlaceAvgCost());
            snapshootEntity.setHolidayPlaceAvgCount(orderTrendMonthResultDto.getHolidayPlaceAvgCount());
            snapshootEntity.setHolidayPlaceAvgCost(orderTrendMonthResultDto.getHolidayPlaceAvgCost());
        }
        return snapshootEntities;
    }

    @Override
    public Long getIntegralRatio() {
        PropertiesEntity propertiesEntity = propertiesMapper.findPropertyById("hs_integral");
        if(propertiesEntity==null || propertiesEntity.getTypeInt()==null){
            logger.error("integral ratio is not exist");
            return 1L;
        }
        return propertiesEntity.getTypeInt();
    }
}
