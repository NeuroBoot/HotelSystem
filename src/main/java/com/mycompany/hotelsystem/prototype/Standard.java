package com.mycompany.hotelsystem.prototype;

public class Standard implements RoomPrototype {
    private double price = 500;

    public String getType() { return "Standard"; }
    public double getPrice() { return price; }

    public RoomPrototype clone() {
        return new Standard();
    }
}
