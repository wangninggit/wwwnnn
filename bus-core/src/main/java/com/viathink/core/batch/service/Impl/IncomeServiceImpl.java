package com.viathink.core.batch.service.Impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.viathink.core.batch.dao.SnapshootDao;
import com.viathink.core.batch.service.IncomeService;
import com.viathink.core.business.gene.bean.InvoiceEntity;
import com.viathink.core.business.gene.bean.PaymentInfoEntity;
import com.viathink.core.business.gene.bean.SnapshootEntity;
import com.viathink.core.business.gene.bean.TestingItemEntity;
import com.viathink.core.business.gene.dto.DateRangeDto;
import com.viathink.core.business.gene.dto.IncomeOrderDto;
import com.viathink.core.business.gene.dto.IncomeOrderSumDto;
import com.viathink.core.business.gene.mapper.GeneDetailMapper;
import com.viathink.core.business.gene.mapper.OrderInvoiceMapper;
import com.viathink.core.business.gene.mapper.PaymentInfoMapper;
import com.viathink.core.business.gene.mapper.TestingItemMapper;
import com.viathink.core.util.BatchUtil;
import com.viathink.core.util.BusinessDate;
import com.viathink.core.util.BusinessDateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class IncomeServiceImpl implements IncomeService {
    @Value("${sys.logistics.url}")
    private String orderLogisticsUrl;
    private final GeneDetailMapper geneDetailMapper;
    private final OrderInvoiceMapper orderInvoiceMapper;
    private final TestingItemMapper testingItemMapper;
    private final SnapshootDao snapshootDao;
    private final PaymentInfoMapper paymentInfoMapper;

    @Autowired
    public IncomeServiceImpl(GeneDetailMapper geneDetailMapper, OrderInvoiceMapper orderInvoiceMapper, TestingItemMapper testingItemMapper, SnapshootDao snapshootDao, PaymentInfoMapper paymentInfoMapper) {
        this.geneDetailMapper = geneDetailMapper;
        this.orderInvoiceMapper = orderInvoiceMapper;
        this.testingItemMapper = testingItemMapper;
        this.snapshootDao = snapshootDao;
        this.paymentInfoMapper = paymentInfoMapper;
    }

    @Override
    public void getMonthDetailReport(Date date) {
        BusinessDate businessDate = BusinessDateUtil.getBusinessDate(date.getTime());
        String monthStr = businessDate.getMonthStr();
        DateRangeDto dateRangeDto = getDateRange(monthStr);
        List<IncomeOrderDto> list = geneDetailMapper.getIncomeDetailReport(dateRangeDto);

        Long orderPrice = 0L;
        Long testingItemCost = 0L;
        Long testingItemPrice = 0L;
        for(IncomeOrderDto incomeOrderDto:list){
            String logisticsUrl;
            logisticsUrl = orderLogisticsUrl + "/"+incomeOrderDto.getOrderId();
            incomeOrderDto.setOrderLogisticsUrl(logisticsUrl);

            if(incomeOrderDto.getOrderPrice()!=null){
                orderPrice = orderPrice + incomeOrderDto.getOrderPrice();
            }
            //根据订单id获取所有的发票
            List<InvoiceEntity> invoices = orderInvoiceMapper.findInvoiceDetailByOrderId(incomeOrderDto.getOrderId());
            incomeOrderDto.setInvoices(invoices);
            //根据订单id获取所有的检测套餐
            List<TestingItemEntity> testingItemEntities = testingItemMapper.findTestingItemByOrderId(incomeOrderDto.getOrderId());
            for(TestingItemEntity testingItemEntity:testingItemEntities){
                testingItemCost = testingItemCost+testingItemEntity.getTestingItemCost();
                testingItemPrice = testingItemPrice + testingItemEntity.getTestingItemPrice();
            }
            incomeOrderDto.setTestings(testingItemEntities);
            //根据订单id获取所有的缴款信息
            List<PaymentInfoEntity> payments = paymentInfoMapper.findPaymentByOrderId(incomeOrderDto.getOrderId());
            for(PaymentInfoEntity dto:payments){
                resetPaymentType(dto);
            }
            incomeOrderDto.setPayments(payments);
        }

        IncomeOrderSumDto incomeOrderSumDto = new IncomeOrderSumDto();
        incomeOrderSumDto.setOrderPrice(orderPrice);
        incomeOrderSumDto.setTestingItemCost(testingItemCost);
        incomeOrderSumDto.setTestingItemPrice(testingItemPrice);

        Map<String, Object> map = new HashMap<>();
        map.put("list", list);
        map.put("sum",list.size() == 0 ? null : incomeOrderSumDto);
        SnapshootEntity snapshootEntity = new SnapshootEntity();
        snapshootEntity.setType(BatchUtil.TYPE_GENE_INCOME_DETAIL);
        snapshootEntity = BatchUtil.setDatelyStr(snapshootEntity,businessDate);
        snapshootEntity.setTimeRange(BatchUtil.TIME_RANGE_MONTH_STR);
        snapshootEntity.setRecord(JSON.toJSONString(map,SerializerFeature.WriteMapNullValue));
        snapshootDao.addOrUpdateSnapshootById(snapshootEntity);
    }

    public static PaymentInfoEntity resetPaymentType(PaymentInfoEntity incomeOrderDto){
        if(incomeOrderDto.getPaymentType()!=null){
            switch (incomeOrderDto.getPaymentType()) {
                case "alipay":
                    incomeOrderDto.setPaymentType("支付宝");
                    break;
                case "wechatpay":
                    incomeOrderDto.setPaymentType("微信");
                    break;
                case "bankTransfer":
                    incomeOrderDto.setPaymentType("银行转账");
                    break;
            }
        }
        if(incomeOrderDto.getLocalStaffPayroll()){
            incomeOrderDto.setLocalStaffPayrollTrans("是");
        }else{
            incomeOrderDto.setLocalStaffPayrollTrans("否");
        }
        return incomeOrderDto;
    }

    public static DateRangeDto getDateRange( String monthStr){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
        Long startDate = BusinessDateUtil.getDateStrTimeInMillis(monthStr,dateFormat,0,1,0,0,0,0);
        Long endDate = BusinessDateUtil.getDateStrTimeInMillis(monthStr,dateFormat,1,1,0,0,0,0);
        DateRangeDto dateRangeDto = new DateRangeDto();
        dateRangeDto.setStartDate(startDate);
        dateRangeDto.setEndDate(endDate);
        return dateRangeDto;
    }
}
