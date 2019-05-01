package com.viathink.core.business.gene.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.github.pagehelper.PageHelper;
import com.viathink.core.batch.dto.*;
import com.viathink.core.business.gene.bean.InvoiceEntity;
import com.viathink.core.business.gene.bean.PaymentInfoEntity;
import com.viathink.core.business.gene.bean.TestingItemEntity;
import com.viathink.core.business.gene.dto.*;
import com.viathink.core.business.gene.mapper.*;
import com.viathink.core.business.gene.service.GeneDailyDetailService;
import com.viathink.core.collection.bean.PropertiesEntity;
import com.viathink.core.collection.mapper.PropertiesMapper;
import com.viathink.core.user.dto.PageParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.viathink.core.batch.service.Impl.IncomeServiceImpl.resetPaymentType;
import static com.viathink.core.batch.service.Impl.ProvinceContrastServiceImpl.provincePackRecordData;
import static com.viathink.core.batch.service.Impl.RegionContrastServiceImpl.regionPackRecordData;
import static com.viathink.core.batch.service.Impl.TestingItemContrastServiceImpl.testingItemPackRecordData;

@Service
@Transactional
public class GeneDailyDetailServiceImpl implements GeneDailyDetailService {
    private final GeneDailyDetailMapper geneDailyDetailMapper;
    private final TestingItemDailyDetailMapper testingItemDailyDetailMapper;
    private final PropertiesMapper propertiesMapper;
    private final GeneDetailMapper geneDetailMapper;
    private final TestingItemMapper testingItemMapper;
    private final OrderInvoiceMapper orderInvoiceMapper;
    private final PaymentInfoMapper paymentInfoMapper;
    private Logger logger = LoggerFactory.getLogger(GeneDailyDetailServiceImpl.class);

    @Autowired
    public GeneDailyDetailServiceImpl(GeneDailyDetailMapper geneDailyDetailMapper, TestingItemDailyDetailMapper testingItemDailyDetailMapper, PropertiesMapper propertiesMapper, GeneDetailMapper geneDetailMapper, TestingItemMapper testingItemMapper, OrderInvoiceMapper orderInvoiceMapper, PaymentInfoMapper paymentInfoMapper) {
        this.geneDailyDetailMapper = geneDailyDetailMapper;
        this.testingItemDailyDetailMapper = testingItemDailyDetailMapper;
        this.propertiesMapper = propertiesMapper;
        this.geneDetailMapper = geneDetailMapper;
        this.testingItemMapper = testingItemMapper;
        this.orderInvoiceMapper = orderInvoiceMapper;
        this.paymentInfoMapper = paymentInfoMapper;
    }

    @Override
    public BusinessResult queryDailyDetailReport(DetailCountForm detailQueryForm) {
        List<BusinessDetailResult> businessDetailResults =  geneDailyDetailMapper.queryDailyDetail(detailQueryForm);

        BusinessDetailResult businessDetailResult = geneDailyDetailMapper.queryDailyDetailCount(detailQueryForm);
        if(businessDetailResult!=null){
            businessDetailResult.setIntegralCost(businessDetailResult.getIntegralCost());
        }
        BusinessResult businessResult = new BusinessResult();
        businessResult.setSum(businessDetailResults.size() == 0 ? null : businessDetailResult);
        businessResult.setList(businessDetailResults);
        return businessResult;
    }

    @Override
    public JSONObject getDailyDetailExcelList(DetailCountForm form) {
        List<DetailResultDto> list = geneDailyDetailMapper.getDailyDetailExcelList(form);

        DetailCountForm detailForm = new DetailCountForm();
        detailForm.setStartDate(form.getStartDate());
        detailForm.setEndDate(form.getEndDate());
        detailForm.setRegionId(form.getRegionId());
        detailForm.setLocalStaffName(form.getLocalStaffName());
        BusinessDetailResult detailResult = geneDailyDetailMapper.queryDailyDetailCount(detailForm);
        if(detailResult!=null){
            detailResult.setIntegralCost(detailResult.getIntegralCost());
        }
        Map<String, Object> map = new HashMap<>();
        map.put("list", list);
        map.put("sum",detailResult);
        return JSONObject.parseObject(JSONObject.toJSONString(map));
    }

    @Override
    public List<OrderWithoutInvoiceDto> getOrderWithoutInvoice(OrderWithoutInvoiceForm form) {
        List<OrderWithoutInvoiceDto> orderWithoutInvoice = geneDetailMapper.getOrderWithoutInvoice(form);
        for(OrderWithoutInvoiceDto orderWithoutInvoiceDto : orderWithoutInvoice){
            Long orderCreateTime = orderWithoutInvoiceDto.getOrderCreateTime();
            orderWithoutInvoiceDto.setOrderCreateDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date(orderCreateTime)));

        }
        return orderWithoutInvoice;
    }
    @Override
    public List<OrderWithoutInvoiceDto> getOrderWithoutInvoiceByPage(OrderWithoutInvoiceForm form, PageParam pageParam,Boolean isPage) {
        if(isPage){
            PageHelper.startPage(pageParam.getPageNum(), pageParam.getPageSize());
        }

        List<OrderWithoutInvoiceDto> orderWithoutInvoice = geneDetailMapper.getOrderWithoutInvoiceByPage(form);

        for(OrderWithoutInvoiceDto orderWithoutInvoiceDto : orderWithoutInvoice){
            Long orderCreateTime = orderWithoutInvoiceDto.getOrderCreateTime();
            orderWithoutInvoiceDto.setOrderCreateDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date(orderCreateTime)));
            orderWithoutInvoiceDto.setBalance(Math.abs(orderWithoutInvoiceDto.getFinanceConfirmIncome() - orderWithoutInvoiceDto.getCashIncome()));
            //根据订单id获取所有的发票
            List<InvoiceEntity> invoices = orderInvoiceMapper.findInvoiceDetailByOrderId(orderWithoutInvoiceDto.getOrderId());
            orderWithoutInvoiceDto.setInvoices(invoices);
            List<TestingItemEntity> testingItemEntities = testingItemMapper.findTestingItemByOrderId(orderWithoutInvoiceDto.getOrderId());
            orderWithoutInvoiceDto.setTestings(testingItemEntities);
            //根据订单id获取所有的缴款信息
            List<PaymentInfoEntity> payments = paymentInfoMapper.findPaymentByOrderId(orderWithoutInvoiceDto.getOrderId());
            for(PaymentInfoEntity dto:payments){
                resetPaymentType(dto);
            }
            orderWithoutInvoiceDto.setPayments(payments);

        }
        return orderWithoutInvoice;
    }

    @Override
    public DetailPageInfoDto<DetailResultDto> getDailyDetailList(DetailQueryForm form) {
        PageHelper.startPage(form.getPageNum(), form.getPageSize());
        List<DetailResultDto> list = geneDailyDetailMapper.getDailyDetailList(form);

        DetailPageInfoDto<DetailResultDto> pageInfo = new DetailPageInfoDto<>(list);
        if (pageInfo.isIsLastPage()) {
            DetailCountForm detailForm = new DetailCountForm();
            detailForm.setStartDate(form.getStartDate());
            detailForm.setEndDate(form.getEndDate());
            detailForm.setRegionId(form.getRegionId());
            detailForm.setLocalStaffName(form.getLocalStaffName());
            BusinessDetailResult detailResult = geneDailyDetailMapper.queryDailyDetailCount(detailForm);
            DetailSumDto detailSumDto = new DetailSumDto();
            detailSumDto.setOrderIncome(detailResult.getOrderIncome());
            detailSumDto.setCashIncome(detailResult.getCashIncome());
            detailSumDto.setFinanceConfirmIncome(detailResult.getFinanceConfirmIncome());
            detailSumDto.setTestingItemCost(detailResult.getTestingItemCost());
            detailSumDto.setTestingItemConfirmCost(detailResult.getTestingItemConfirmCost());
            detailSumDto.setIntegralCost(detailResult.getIntegralCost());
            detailSumDto.setOrderPlaceCount(detailResult.getOrderPlaceCount());
            detailSumDto.setOrderCancelCount(detailResult.getOrderCancelCount());
            detailSumDto.setOrderPlaceAvgCost(detailResult.getOrderPlaceAvgCost());
            detailSumDto.setOrderCancelAvgCost(detailResult.getOrderCancelAvgCost());
            pageInfo.setSum(detailSumDto);
        }
        if(list.size()==0){
            pageInfo.setSum(null);
        }
        return pageInfo;
    }

    @Override
    public JSONObject queryBussinessRegionContrast(QueryRegionContrastParamDto queryRegionContrastParamDto) {
        List<RegionSnapshootRecordDto> recordDtoList = geneDailyDetailMapper.getSnapshootRecordByDailyRangeAndRegionIdsAndGroupByRegionId(queryRegionContrastParamDto);

        if(recordDtoList.size() == 0){
            return JSON.parseObject(JSON.toJSONString(new RecordObjectDto(), SerializerFeature.WriteMapNullValue));
        }else{
            RecordObjectDto objectDto = regionPackRecordData(recordDtoList);
            return JSONObject.parseObject(JSONObject.toJSONString(objectDto,SerializerFeature.WriteMapNullValue));
        }
    }

    @Override
    public JSONObject queryBussinessTestingItemContrast(QueryTestingItemContrastParamDto queryTestingItemContrastParamDto) {
        List<TestingItemSnapshootRecordDto> recordDtoList = testingItemDailyDetailMapper.getSnapshootRecordByDatelyRangeAndTestingItemIdsAndGroupByTestingItemId(queryTestingItemContrastParamDto);
        if(recordDtoList == null || recordDtoList.size() == 0){
            return JSON.parseObject(JSON.toJSONString(new RecordObjectDto(), SerializerFeature.WriteMapNullValue));
        }else{
            RecordObjectDto objectDto = testingItemPackRecordData(recordDtoList);
            return JSONObject.parseObject(JSONObject.toJSONString(objectDto,SerializerFeature.WriteMapNullValue));
        }
    }

    @Override
    public JSONObject queryBusinessProvinceReport(ProvinceQueryForm provinceQueryForm) {
        List<ProvinceSnapshootRecordDto> recordDtoList =  geneDailyDetailMapper.queryBusinessProvince(provinceQueryForm);

        if(recordDtoList.size() == 0){
            return JSON.parseObject(JSON.toJSONString(new RecordObjectDto(), SerializerFeature.WriteMapNullValue));
        }else{
            RecordObjectDto objectDto = provincePackRecordData(recordDtoList);
            return JSONObject.parseObject(JSONObject.toJSONString(objectDto,SerializerFeature.WriteMapNullValue));
        }
    }

    private Long getIntegralRatio(){
        PropertiesEntity propertiesEntity = propertiesMapper.findPropertyById("hs_integral");
        if(propertiesEntity==null || propertiesEntity.getTypeInt()==null){
            logger.error("integral ratio is not exist");
            return 1L;
        }
        return propertiesEntity.getTypeInt();
    }
}
