package com.viathink.core.business.gene.mapper;

import com.viathink.core.business.gene.bean.GeneDetailBaseEntity;
import com.viathink.core.business.gene.dto.DateRangeDto;
import com.viathink.core.business.gene.dto.IncomeOrderDto;
import com.viathink.core.business.gene.dto.OrderWithoutInvoiceDto;
import com.viathink.core.business.gene.dto.OrderWithoutInvoiceForm;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GeneDetailMapper {
    void addGeneDetail(GeneDetailBaseEntity geneDetailEntity);
    void updateGeneDetail(GeneDetailBaseEntity geneDetailEntity);
    GeneDetailBaseEntity getGeneDetailById(Long id);
    GeneDetailBaseEntity getGeneDetailByOrderId(String orderId);
    void updateGeneDetailByOrderId(GeneDetailBaseEntity geneDetailEntity);
    List<IncomeOrderDto> getGeneDetailIncomeReportByPage(DateRangeDto dateRangeDto);
    List<IncomeOrderDto> getIncomeDetailReport(DateRangeDto dateRangeDto);
    List<OrderWithoutInvoiceDto> getOrderWithoutInvoice(OrderWithoutInvoiceForm form);
    List<OrderWithoutInvoiceDto> getOrderWithoutInvoiceByPage(OrderWithoutInvoiceForm form);
}
