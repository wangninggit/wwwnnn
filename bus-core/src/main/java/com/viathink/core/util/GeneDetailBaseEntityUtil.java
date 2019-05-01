package com.viathink.core.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.viathink.core.business.gene.bean.EventBaseEntity;
import com.viathink.core.business.gene.bean.GeneDetailBaseEntity;
import com.viathink.core.business.gene.bean.InvoiceEntity;
import com.viathink.core.business.gene.dao.OrderDao;
import com.viathink.core.business.gene.dto.LocalStaffLocationDto;
import com.viathink.core.business.gene.dto.OrderInvoiceDto;
import com.viathink.core.business.gene.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GeneDetailBaseEntityUtil {
    private final GeneDetailMapper geneDetailMapper;
    private final GeneDailyDetailMapper geneDailyDetailMapper;
    private final GeneMonthlyDetailMapper geneMonthlyDetailMapper;
    private final GeneQuarterlyDetailMapper geneQuarterlyDetailMapper;
    private final GeneYearlyDetailMapper geneYearlyDetailMapper;
    private final GeneHalfYearlyDetailMapper geneHalfYearlyDetailMapper;
    private final OrderDao orderDao;
    private final OrderInvoiceMapper orderInvoiceMapper;
    private final InvoiceMapper invoiceMapper;

    @Autowired
    public GeneDetailBaseEntityUtil(GeneDetailMapper geneDetailMapper, GeneDailyDetailMapper geneDailyDetailMapper, GeneMonthlyDetailMapper geneMonthlyDetailMapper, GeneQuarterlyDetailMapper geneQuarterlyDetailMapper, GeneYearlyDetailMapper geneYearlyDetailMapper, GeneHalfYearlyDetailMapper geneHalfYearlyDetailMapper, OrderDao orderDao, OrderInvoiceMapper orderInvoiceMapper, InvoiceMapper invoiceMapper) {
        this.geneDetailMapper = geneDetailMapper;
        this.geneDailyDetailMapper = geneDailyDetailMapper;
        this.geneMonthlyDetailMapper = geneMonthlyDetailMapper;
        this.geneQuarterlyDetailMapper = geneQuarterlyDetailMapper;
        this.geneYearlyDetailMapper = geneYearlyDetailMapper;
        this.geneHalfYearlyDetailMapper = geneHalfYearlyDetailMapper;
        this.orderDao = orderDao;
        this.orderInvoiceMapper = orderInvoiceMapper;
        this.invoiceMapper = invoiceMapper;
    }

    public static GeneDetailBaseEntity copyGeneDetailBaseEntityAndModifyMainFieldByDatelyEntity(GeneDetailBaseEntity copyEntity,GeneDetailBaseEntity sourceEntuty){
        if(copyEntity == null)
            return null;
        GeneDetailBaseEntity tempGeneDetail = JSONObject.parseObject(JSONObject.toJSONString(copyEntity), GeneDetailBaseEntity.class);

        if(sourceEntuty != null) {
            tempGeneDetail.setIntegralCost(sourceEntuty.getIntegralCost());
            tempGeneDetail.setDcwIntegralCost(sourceEntuty.getDcwIntegralCost());
            tempGeneDetail.setDcwIntegralRawCost(sourceEntuty.getDcwIntegralRawCost());
            tempGeneDetail.setEmpiriValue(sourceEntuty.getEmpiriValue());
            tempGeneDetail.setTestingItemCost(sourceEntuty.getTestingItemCost());
            tempGeneDetail.setTestingItemConfirmCost(sourceEntuty.getTestingItemConfirmCost());
            tempGeneDetail.setOrderIncome(sourceEntuty.getOrderIncome());
            tempGeneDetail.setCashIncome(sourceEntuty.getCashIncome());
            tempGeneDetail.setFinanceConfirmIncome(sourceEntuty.getFinanceConfirmIncome());
            tempGeneDetail.setOrderPlaceCost(sourceEntuty.getOrderPlaceCost());
            tempGeneDetail.setOrderPlaceCount(sourceEntuty.getOrderPlaceCount());
            tempGeneDetail.setOrderCancelCount(sourceEntuty.getOrderCancelCount());
            tempGeneDetail.setOrderCancelCost(sourceEntuty.getOrderCancelCost());
        }else{
            tempGeneDetail.setIntegralCost(0L);
            tempGeneDetail.setDcwIntegralCost(0L);
            tempGeneDetail.setDcwIntegralRawCost(0L);
            tempGeneDetail.setEmpiriValue(0L);
            tempGeneDetail.setTestingItemCost(0L);
            tempGeneDetail.setTestingItemConfirmCost(0L);
            tempGeneDetail.setOrderIncome(0L);
            tempGeneDetail.setCashIncome(0L);
            tempGeneDetail.setFinanceConfirmIncome(0L);
            tempGeneDetail.setOrderPlaceCost(0L);
            tempGeneDetail.setOrderPlaceCount(0L);
            tempGeneDetail.setOrderCancelCount(0L);
            tempGeneDetail.setOrderCancelCost(0L);
        }
        return tempGeneDetail;
    }

    public void orderEventServiceHandler(EventBaseEntity event, JSONObject object, EventHandler<GeneDetailBaseEntity> eventServiceUtil) {
        GeneDetailBaseEntity geneDetailEntity = object.toJavaObject(GeneDetailBaseEntity.class);
        LocalStaffLocationDto locationDto = object.getObject("localStaffLocation", LocalStaffLocationDto.class);
        // 先查找老的，如果没有 记录异常
        GeneDetailBaseEntity oldGeneDetail = geneDetailMapper.getGeneDetailByOrderId(geneDetailEntity.getOrderId());

        geneDetailEntity = orderDao.overrideGeneDetaillField(event, locationDto, geneDetailEntity);
        // 更新附属信息
        orderDao.addOrUpdateAttachedData(geneDetailEntity, locationDto);

        eventServiceUtil.perfectDataAtTheBeginning(geneDetailEntity);

        if (event.getEvent().equals("orderCreated")) {
            geneDetailEntity = eventServiceUtil.perfectDataWhenOldDetailIsNull(geneDetailEntity);

            geneDetailMapper.addGeneDetail(geneDetailEntity);
            geneDailyDetailMapper.addGeneDailyDetail(geneDetailEntity);
            geneMonthlyDetailMapper.addGeneMonthlyDetail(geneDetailEntity);
            geneQuarterlyDetailMapper.addGeneQuarterlyDetail(geneDetailEntity);
            geneHalfYearlyDetailMapper.addGeneHalfYearlyDetail(geneDetailEntity);
            geneYearlyDetailMapper.addGeneYearlyDetail(geneDetailEntity);
        } else {
            GeneDetailBaseEntity mergeGeneDetail = orderDao.copyBaseField(oldGeneDetail,geneDetailEntity);

            GeneDetailBaseEntity geneDatelyDetail;
            GeneDetailBaseEntity tempGeneDetail;
            //  日
            geneDatelyDetail = geneDailyDetailMapper.getGeneDailyDetailByOrderIdAndDailyStr(mergeGeneDetail);
            tempGeneDetail = GeneDetailBaseEntityUtil.copyGeneDetailBaseEntityAndModifyMainFieldByDatelyEntity(mergeGeneDetail,geneDatelyDetail);
            tempGeneDetail = eventServiceUtil.perfectDataInDatelyDetail(tempGeneDetail,mergeGeneDetail);
            if (geneDatelyDetail != null) {
                geneDailyDetailMapper.updateGeneDailyDetailByDayStrAndOrderId(tempGeneDetail);
            } else {
                geneDailyDetailMapper.addGeneDailyDetail(tempGeneDetail);
            }
            //  月
            geneDatelyDetail = geneMonthlyDetailMapper.getGeneMonthlyDetailByOrderIdAndMonthStr(mergeGeneDetail);
            tempGeneDetail = GeneDetailBaseEntityUtil.copyGeneDetailBaseEntityAndModifyMainFieldByDatelyEntity(mergeGeneDetail,geneDatelyDetail);
            tempGeneDetail = eventServiceUtil.perfectDataInDatelyDetail(tempGeneDetail,mergeGeneDetail);
            if (geneDatelyDetail != null) {
                geneMonthlyDetailMapper.updateGeneMonthlyDetailByOrderIdAndMonthStr(tempGeneDetail);
            } else {
                geneMonthlyDetailMapper.addGeneMonthlyDetail(tempGeneDetail);
            }
            //  季度
            geneDatelyDetail = geneQuarterlyDetailMapper.getGeneQuarterlyDetailByOrderIdAndQuarterStr(mergeGeneDetail);
            tempGeneDetail = GeneDetailBaseEntityUtil.copyGeneDetailBaseEntityAndModifyMainFieldByDatelyEntity(mergeGeneDetail,geneDatelyDetail);
            tempGeneDetail = eventServiceUtil.perfectDataInDatelyDetail(tempGeneDetail,mergeGeneDetail);
            if (geneDatelyDetail != null) {
                geneQuarterlyDetailMapper.updateGeneQuarterlyDetailByOrderIdAndQuarterStr(tempGeneDetail);
            } else {
                geneQuarterlyDetailMapper.addGeneQuarterlyDetail(tempGeneDetail);
            }
            //  半年
            geneDatelyDetail = geneHalfYearlyDetailMapper.getGeneHalfYearlyDetailByOrderIdAndHalfYearStr(mergeGeneDetail);
            tempGeneDetail = GeneDetailBaseEntityUtil.copyGeneDetailBaseEntityAndModifyMainFieldByDatelyEntity(mergeGeneDetail,geneDatelyDetail);
            tempGeneDetail = eventServiceUtil.perfectDataInDatelyDetail(tempGeneDetail,mergeGeneDetail);
            if (geneDatelyDetail != null) {
                geneHalfYearlyDetailMapper.updateGeneHalfYearlyDetailByOrderIdAndHalfYearStr(tempGeneDetail);
            } else {
                geneHalfYearlyDetailMapper.addGeneHalfYearlyDetail(tempGeneDetail);
            }
            //  年
            geneDatelyDetail = geneYearlyDetailMapper.getGeneYearlyDetailByOrderIdAndYearStr(mergeGeneDetail);
            tempGeneDetail = GeneDetailBaseEntityUtil.copyGeneDetailBaseEntityAndModifyMainFieldByDatelyEntity(mergeGeneDetail,geneDatelyDetail);
            tempGeneDetail = eventServiceUtil.perfectDataInDatelyDetail(tempGeneDetail,mergeGeneDetail);
            if (geneDatelyDetail != null) {
                geneYearlyDetailMapper.updateGeneYearlyDetailByOrderIdAndYearStr(tempGeneDetail);
            } else {
                geneYearlyDetailMapper.addGeneYearlyDetail(tempGeneDetail);
            }

            mergeGeneDetail = eventServiceUtil.perfectDataInDatelyDetail(mergeGeneDetail,mergeGeneDetail);
            geneDetailMapper.updateGeneDetailByOrderId(mergeGeneDetail);
        }
    }

    public static void setUpdateTime(EventBaseEntity event,GeneDetailBaseEntity updateDetail){
        updateDetail.setTag(event.getTag());
        updateDetail.setMessageId(event.getMessageId());
        updateDetail.setRecordId(event.getRecordId());
        updateDetail.setDayStr(event.getDayStr());
        updateDetail.setMonthStr(event.getMonthStr());
        updateDetail.setQuarterStr(event.getQuarterStr());
        updateDetail.setHalfYearStr(event.getHalfYearStr());
        updateDetail.setYearStr(event.getYearStr());
        updateDetail.setEvent(event.getEvent());
        updateDetail.setEventTime(event.getEventTime());
    }

    public void orderInvoice(EventBaseEntity event, JSONObject object){
        JSONArray jsonArray = object.getJSONArray("orderIds");
        List<String> orderIdList = jsonArray.toJavaList(String.class);
        for (String orderId : orderIdList) {
            GeneDetailBaseEntity baseDetail = geneDetailMapper.getGeneDetailByOrderId(orderId);

            setUpdateTime(event,baseDetail);

            EventHandler<GeneDetailBaseEntity> handler = new EventHandler<GeneDetailBaseEntity>() {
                @Override
                public GeneDetailBaseEntity perfectDataInDatelyDetail(GeneDetailBaseEntity tempDetail, GeneDetailBaseEntity mergeDetail) {
                    tempDetail.setFinanceConfirmIncome(mergeDetail.getCashIncome() - mergeDetail.getFinanceConfirmIncome() + tempDetail.getFinanceConfirmIncome());
                    tempDetail.setTestingItemConfirmCost(mergeDetail.getTestingItemCost() - mergeDetail.getTestingItemConfirmCost() + tempDetail.getTestingItemConfirmCost());
                    tempDetail.setOrderInvoiceTime(mergeDetail.getEventTime());
                    return tempDetail;
                }
            };

            GeneDetailBaseEntity dateDetail;
            GeneDetailBaseEntity tempDetail;

            dateDetail = geneDailyDetailMapper.getGeneDailyDetailByOrderIdAndDailyStr(baseDetail);
            tempDetail = copyGeneDetailBaseEntityAndModifyMainFieldByDatelyEntity(baseDetail, dateDetail);
            tempDetail = handler.perfectDataInDatelyDetail(tempDetail,baseDetail);
            if (dateDetail != null) {
                geneDailyDetailMapper.updateGeneDailyDetailByDayStrAndOrderId(tempDetail);
            } else {
                geneDailyDetailMapper.addGeneDailyDetail(tempDetail);
            }

            dateDetail = geneMonthlyDetailMapper.getGeneMonthlyDetailByOrderIdAndMonthStr(baseDetail);
            tempDetail = copyGeneDetailBaseEntityAndModifyMainFieldByDatelyEntity(baseDetail, dateDetail);
            tempDetail = handler.perfectDataInDatelyDetail(tempDetail,baseDetail);
            if (dateDetail != null) {
                geneMonthlyDetailMapper.updateGeneMonthlyDetailByOrderIdAndMonthStr(tempDetail);
            } else {
                geneMonthlyDetailMapper.addGeneMonthlyDetail(tempDetail);
            }

            dateDetail = geneQuarterlyDetailMapper.getGeneQuarterlyDetailByOrderIdAndQuarterStr(baseDetail);
            tempDetail = copyGeneDetailBaseEntityAndModifyMainFieldByDatelyEntity(baseDetail, dateDetail);
            tempDetail = handler.perfectDataInDatelyDetail(tempDetail,baseDetail);
            if (dateDetail != null) {
                geneQuarterlyDetailMapper.updateGeneQuarterlyDetailByOrderIdAndQuarterStr(tempDetail);
            } else {
                geneQuarterlyDetailMapper.addGeneQuarterlyDetail(tempDetail);
            }

            dateDetail = geneHalfYearlyDetailMapper.getGeneHalfYearlyDetailByOrderIdAndHalfYearStr(baseDetail);
            tempDetail = copyGeneDetailBaseEntityAndModifyMainFieldByDatelyEntity(baseDetail, dateDetail);
            tempDetail = handler.perfectDataInDatelyDetail(tempDetail,baseDetail);
            if (dateDetail != null) {
                geneHalfYearlyDetailMapper.updateGeneHalfYearlyDetailByOrderIdAndHalfYearStr(tempDetail);
            } else {
                geneHalfYearlyDetailMapper.addGeneHalfYearlyDetail(tempDetail);
            }

            dateDetail = geneYearlyDetailMapper.getGeneYearlyDetailByOrderIdAndYearStr(baseDetail);
            tempDetail = copyGeneDetailBaseEntityAndModifyMainFieldByDatelyEntity(baseDetail, dateDetail);
            tempDetail = handler.perfectDataInDatelyDetail(tempDetail,baseDetail);
            if (dateDetail != null) {
                geneYearlyDetailMapper.updateGeneYearlyDetailByOrderIdAndYearStr(tempDetail);
            } else {
                geneYearlyDetailMapper.addGeneYearlyDetail(tempDetail);
            }

            tempDetail = handler.perfectDataInDatelyDetail(baseDetail,baseDetail);
            geneDetailMapper.updateGeneDetailByOrderId(tempDetail);
        }
        //订单关联发票
        InvoiceEntity invoiceEntity = object.toJavaObject(InvoiceEntity.class);
        invoiceEntity.setInvoiceTime(event.getEventTime());
        InvoiceEntity invoiceEntity1 = invoiceMapper.findInvoiceByCode(invoiceEntity.getInvoiceCode());
        if (invoiceEntity1 == null) {
            invoiceMapper.addInvoice(invoiceEntity);
        } else {
            invoiceMapper.updateInvoiceByCode(invoiceEntity);
        }

        String invoiceCode = invoiceEntity.getInvoiceCode();
        OrderInvoiceDto orderInvoiceDto = new OrderInvoiceDto();
        orderInvoiceDto.setInvoiceCode(invoiceCode);
        String[] orderIds = new String[orderIdList.size()];
        orderInvoiceDto.setOrderIds(orderIdList.toArray(orderIds));
        orderInvoiceMapper.addInvoiceOrders(orderInvoiceDto);
    }
}
