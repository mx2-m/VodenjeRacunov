package model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;


public class Item implements Searchable {
    private UUID id;
    public String name;
    private String ean13;
    private BigDecimal price;
    private BigDecimal quantity;
    private double davek;
    private LocalDateTime created;
    private LocalDateTime modified;

    public Item(String name, String ean13, BigDecimal price, BigDecimal quantity, double davek) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.ean13 = ean13;
        this.price = price;
        this.davek = davek;
        this.quantity = quantity;
        this.created = LocalDateTime.now();
    }


    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
        modified = LocalDateTime.now();
    }


    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {

        price = price.setScale(2, BigDecimal.ROUND_HALF_UP);
        return price;
    }

    public UUID getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
        modified = LocalDateTime.now();
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
        modified = LocalDateTime.now();
    }

    public String getEan() {
        return ean13;
    }

    public void setEan(String ean13) {
        this.ean13 = ean13;
        modified = LocalDateTime.now();
    }

    public double getDavek() {
        if (davek < 0.22)
            davek = 0.095;
        else
            davek = 0.22;

        return davek;
    }

    public void setDavek(double davek) {
        this.davek = davek;
        modified = LocalDateTime.now();
    }


    public void print() {
        System.out.println(this.getName() + "\t" + this.getQuantity() + "\t" + this.getPrice());
    }


    @Override
    public boolean search(String niz) {
        boolean search = false;
        if (name.contains(niz)) search = true;
        if (ean13.contains(niz)) search = true;
        if (String.valueOf(price).contains(niz)) search = true;
        if (String.valueOf(davek).contains(niz)) search = true;
        if (String.valueOf(quantity).contains(niz)) search = true;
        return search;
    }

}
