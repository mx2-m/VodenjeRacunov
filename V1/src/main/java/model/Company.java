package model;

import java.util.UUID;

public class Company implements Searchable {
    private UUID id;
    private String name;
    private int taxNumber;
    private String registrationNumber;
    private boolean taxpayer;
    private String address;


    public Company(String name, int taxNumber, String registrationNumber, String address) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.taxNumber = taxNumber;
        this.registrationNumber = registrationNumber;
        if(taxNumber > 1){
            taxpayer = true;
        }
        else
            taxpayer = false;

        this.address = address;
    }


    public String getName() {
        return name;
    }

    public UUID getId() {
        return id;
    }

    public boolean isTaxpayer() {
        return taxpayer;
    }

    public String getAddress() {
        return address;
    }

    public int getTaxNumber() {
        return taxNumber;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public boolean getTaxpayer() {
        return taxpayer;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTaxNumber(int taxNumber) {
        this.taxNumber = taxNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public void setTaxpayer(boolean taxpayer) {
        this.taxpayer = taxpayer;
    }

    @Override
    public String toString() {
        String a = name + "\n" + address + "\n" + registrationNumber + "\n" + "--------------------------------" + "\n";
        return a;

    }

    @Override
    public boolean search(String niz) {
        boolean search = false;
        if (name.contains(niz)) search = true;
        if (String.valueOf(taxNumber).contains(niz)) search = true;
        if (String.valueOf(registrationNumber).contains(niz)) search = true;
        if (String.valueOf(taxpayer).contains(niz)) search = true;
        return search;
    }
}
