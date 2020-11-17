package dao;

import model.Invoice;

import java.util.UUID;


public interface InvoiceDao<O, D> extends DaoCrud<Invoice, O, D> {
    Invoice getByInvoiceNumber(UUID invoiceNumber);
}
