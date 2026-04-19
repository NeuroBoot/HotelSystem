package com.mycompany.hotelsystem.customer;

public class RegularCustomer implements Customer {
    private String name;

    public RegularCustomer(String name) {
        this.name = name;
    }

    public String getName() { return name; }
    public String getType() { return "Regular"; }

    public void getDiscount() {
        System.out.println(name + " gets 0% discount");
    }
}
