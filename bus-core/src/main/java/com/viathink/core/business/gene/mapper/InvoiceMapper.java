package com.viathink.core.business.gene.mapper;

import com.viathink.core.business.gene.bean.InvoiceEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceMapper {
    void removeInvoiceByCode(String code);
    void addInvoice(InvoiceEntity invoiceEntity);
    InvoiceEntity findInvoiceByCode(String code);
    void updateInvoiceByCode(InvoiceEntity invoiceEntity);
}
