package com.viathink.core.business.gene.service.impl;

import com.github.pagehelper.PageHelper;
import com.viathink.core.business.gene.bean.InvoiceEntity;
import com.viathink.core.business.gene.bean.PaymentInfoEntity;
import com.viathink.core.business.gene.bean.TestingItemEntity;
import com.viathink.core.business.gene.dto.DateRangeDto;
import com.viathink.core.business.gene.dto.IncomeOrderDto;
import com.viathink.core.business.gene.mapper.GeneDetailMapper;
import com.viathink.core.business.gene.mapper.OrderInvoiceMapper;
import com.viathink.core.business.gene.mapper.PaymentInfoMapper;
import com.viathink.core.business.gene.mapper.TestingItemMapper;
import com.viathink.core.business.gene.service.IncomeReportService;
import com.viathink.core.user.dto.PageParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.viathink.core.batch.service.Impl.IncomeServiceImpl.resetPaymentType;

@Service
@Transactional
public class IncomeReportServiceImpl implements IncomeReportService {
    @Value("${sys.logistics.url}")
    private String orderLogisticsUrl;
    private final GeneDetailMapper geneDetailMapper;
    private final OrderInvoiceMapper orderInvoiceMapper;
    private final TestingItemMapper testingItemMapper;
    private final PaymentInfoMapper paymentInfoMapper;

    @Autowired
    public IncomeReportServiceImpl(GeneDetailMapper geneDetailMapper, OrderInvoiceMapper orderInvoiceMapper, TestingItemMapper testingItemMapper, PaymentInfoMapper paymentInfoMapper) {
        this.geneDetailMapper = geneDetailMapper;
        this.orderInvoiceMapper = orderInvoiceMapper;
        this.testingItemMapper = testingItemMapper;
        this.paymentInfoMapper = paymentInfoMapper;
    }

    @Override
    public List<IncomeOrderDto> getGeneDetailIncomeReportByPage(DateRangeDto dateRangeDto, PageParam pageParam,Boolean isPage) {
        if(isPage){
            PageHelper.startPage(pageParam.getPageNum(), pageParam.getPageSize());
        }
        //分页订单数据
        List<IncomeOrderDto> list = geneDetailMapper.getGeneDetailIncomeReportByPage(dateRangeDto);
        for(IncomeOrderDto incomeOrderDto:list){
            String logisticsUrl;
            logisticsUrl = orderLogisticsUrl + "/"+incomeOrderDto.getOrderId();
            incomeOrderDto.setOrderLogisticsUrl(logisticsUrl);
            //根据订单id获取所有的发票
            List<InvoiceEntity> invoices = orderInvoiceMapper.findInvoiceDetailByOrderId(incomeOrderDto.getOrderId());
            incomeOrderDto.setInvoices(invoices);
            //根据订单id获取所有的检测套餐
            List<TestingItemEntity> testingItemEntities = testingItemMapper.findTestingItemByOrderId(incomeOrderDto.getOrderId());
            incomeOrderDto.setTestings(testingItemEntities);
            //根据订单id获取所有的缴款信息
            List<PaymentInfoEntity> payments = paymentInfoMapper.findPaymentByOrderId(incomeOrderDto.getOrderId());
            for(PaymentInfoEntity dto:payments){
                resetPaymentType(dto);
            }
            incomeOrderDto.setPayments(payments);
        }
        return list;
    }
}
