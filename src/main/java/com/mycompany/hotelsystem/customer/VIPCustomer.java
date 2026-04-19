package com.mycompany.hotelsystem.customer;

public class VIPCustomer implements Customer {
    private String name;

    public VIPCustomer(String name) {
        this.name = name;
    }

    public String getName() { return name; }
    public String getType() { return "VIP"; }

    public void getDiscount() {
        System.out.println(name + " gets 20% discount");
    }
}
