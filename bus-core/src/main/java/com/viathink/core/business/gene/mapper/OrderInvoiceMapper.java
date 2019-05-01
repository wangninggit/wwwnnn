package com.viathink.core.business.gene.mapper;

import com.viathink.core.business.gene.bean.InvoiceEntity;
import com.viathink.core.business.gene.bean.OrderInvoiceEntity;
import com.viathink.core.business.gene.dto.OrderInvoiceDto;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderInvoiceMapper {
    void addOrderInvoice(OrderInvoiceEntity orderInvoiceEntity);
    List<OrderInvoiceEntity> findByInvoiceCode(String invoiceCode);
    void deleteByInvoiceCode(String invoiceCode);
    List<OrderInvoiceEntity> findByOrderId(String orderId);
    void deleteByOrderId(String orderId);
    void addInvoiceOrders(OrderInvoiceDto orderInvoiceDto);
    List<InvoiceEntity> findInvoiceDetailByOrderId(String orderId);
}
