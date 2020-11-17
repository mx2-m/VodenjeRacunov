package dao.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import dao.InvoiceDao;
import model.Invoice;
import util.Util;

import java.lang.reflect.Type;
import java.util.List;
import java.util.UUID;

public class JsonInvoice implements InvoiceDao<List<Invoice>, JsonReader> {
    private final String filename = "src/main/resources/invoice.json";
    private List<Invoice> invoices;


    public Invoice getByInvoiceNumber(UUID invoiceNumber) {
        List<Invoice> invoice = getAll();
        for (Invoice invoice1 : invoice) {
            if (invoice1.getInvoiceNumber().equals(invoiceNumber)) {
                return invoice1;
            }
        }
        return null;
    }


    @Override
    public Invoice getById(UUID id) {
        List<Invoice> invoiceLists = getAll();
        for (Invoice invoice : invoiceLists) {
            if (invoice.getId().equals(id)) {
                return invoice;
            }
        }
        return null;
    }

    @Override
    public List<Invoice> getAll() {
        JsonReader reader = Util.readJsonFromFile(filename);
        return mapDataToObject(reader);
    }

    @Override
    public boolean insert(Invoice addInvoice) {
        List<Invoice> invoices = getAll();
        for (Invoice invoice : invoices) {
            if (invoice.getId().equals(addInvoice.getId()))
                return false;
        }
        invoices.add(addInvoice);

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return Util.writeJsonToFile(filename, gson.toJson(invoices));
    }

    @Override
    public boolean update(Invoice m) {
        List<Invoice> invoices = getAll();
        for (int i = 0; i< invoices.size(); i++) {
       // for (Invoice invoice : invoices) {
        if (invoices.get(i).getId().equals(m.getId())) {
                invoices.remove(invoices.get(i));
                invoices.add(m);
            }

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            return Util.writeJsonToFile(filename, gson.toJson(invoices));
        }
        return false;
    }

    @Override
    public boolean delete(Invoice m) {
        List<Invoice> invoices = getAll();
        for (Invoice invoice : invoices) {
            if (invoice.getId().equals(m.getId())) {
                invoices.remove(invoice);
            }

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            return Util.writeJsonToFile(filename, gson.toJson(invoices));
        }
        return false;
    }


    @Override
    public List<Invoice> mapDataToObject(JsonReader data) {
        Type itemsListType = new TypeToken<List<Invoice>>() {
        }.getType();
        Gson gson = new Gson();
        return gson.fromJson(data, itemsListType);
    }
}
