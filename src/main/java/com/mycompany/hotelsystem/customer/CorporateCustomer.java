package com.mycompany.hotelsystem.customer;

public class CorporateCustomer implements Customer {
    private String name;

    public CorporateCustomer(String name) {
        this.name = name;
    }

    public String getName() { return name; }
    public String getType() { return "Corporate"; }

    public void getDiscount() {
        System.out.println(name + " gets 10% discount");
    }
}
