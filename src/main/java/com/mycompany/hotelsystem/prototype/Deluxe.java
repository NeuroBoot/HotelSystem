package com.mycompany.hotelsystem.prototype;

public class Deluxe implements RoomPrototype {
    private double price = 900;

    public String getType() { return "Deluxe"; }
    public double getPrice() { return price; }

    public RoomPrototype clone() {
        return new Deluxe();
    }
}
