package com.mycompany.hotelsystem.prototype;

public interface RoomPrototype extends Cloneable {
    String getType();
    double getPrice();
    RoomPrototype clone();
}
