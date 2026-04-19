package com.mycompany.hotelsystem.prototype;

public class Suite implements RoomPrototype {
    private double price = 1500;

    public String getType() { return "Suite"; }
    public double getPrice() { return price; }

    public RoomPrototype clone() {
        return new Suite();
    }
}
