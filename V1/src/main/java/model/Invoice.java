package model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;


public class Invoice implements Searchable {
    private UUID id;
    private LocalDateTime created;
    private LocalDateTime modified;
    private BigDecimal discount;
    private BigDecimal price;
    private Company issuer;
    private Company customer;
    private String cashier;
    private UUID invoiceNumber;
    private Items items;



    public Invoice(BigDecimal discount, BigDecimal price, Company issuer, Company customer, String cashier, Items items) {
        this.id = UUID.randomUUID();
        this.created = LocalDateTime.now();
        this.modified = LocalDateTime.now();
        this.discount = discount;
        this.price = price;
        this.issuer = issuer;
        this.customer = customer;
        this.cashier = cashier;
        this.invoiceNumber = UUID.randomUUID();
        this.items = items;
    }

    public UUID getInvoiceNumber() {
        return invoiceNumber;
    }

    public String getCashier() {
        return cashier;
    }

    public Company getCustomer() {
        return customer;
    }

    public Company getIssuer() {
        return issuer;
    }

    public UUID getId() {
        return id;
    }

    public BigDecimal getPrice() {

        price = price.setScale(2, BigDecimal.ROUND_HALF_UP);
        return price;
    }


    public void setCashier(String cashier) {
        this.cashier = cashier;
        modified = LocalDateTime.now();
    }

    public void setCustomer(Company customer) {
        this.customer = customer;
    }

    public void setIssuer(Company issuer) {
        this.issuer = issuer;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }


    public BigDecimal getTotal() {

        for (Map.Entry<String, Item> entry : items.getItemsTemp().entrySet()) {
            Item a = entry.getValue();
            BigDecimal totalPrice = a.getPrice().multiply(a.getQuantity());
            price = price.add(totalPrice);
        }
        price = price.subtract(discount);
        price = price.setScale(2, BigDecimal.ROUND_HALF_UP);
        return price;
    }

    public void addItem(Item item) {
        items.add(item);

        getTotal();
        modified = LocalDateTime.now();
    }

    public void removeItem(Item item) {
        items.remove(item);
        // update totals
        getTotal();
        modified = LocalDateTime.now();
    }


    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
        modified = LocalDateTime.now();
    }


    public void print() {
        System.out.println(issuer.toString() + "\n");
        System.out.println("Artikel" + "   " + "Količina" + "  " + "EUR" + "\n");
        items.print();
        System.out.println("--------------------------" + "\n");
        System.out.println("Za plačilo: " + getPrice());
        System.out.println("Blagajnik: " + cashier +
                "\n" + id + "\n");
        if (customer != null)
            System.out.println(customer.toString() + "\n");
        System.out.println("Datum " + created + "\n");
    }

    @Override
    public boolean search(String niz) {
        boolean search = false;
        if (String.valueOf(discount).contains(niz)) search = true; //contains ne equal
        if (String.valueOf(price).contains(niz)) search = true;
        if (String.valueOf(price).contains(niz)) search = true;
        if (String.valueOf(issuer).contains(niz)) search = true;
        if (String.valueOf(customer).contains(niz)) search = true;
        if (String.valueOf(cashier).contains(niz)) search = true;
        if (String.valueOf(invoiceNumber).contains(niz)) search = true;
        return search;
    }
}
