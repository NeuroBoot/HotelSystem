package com.mycompany.hotelsystem.proxy;

import com.mycompany.hotelsystem.prototype.RoomPrototype;
import java.util.Date;

public class RealRoom implements Room {
    private RoomPrototype proto;

    public RealRoom(RoomPrototype proto) {
        this.proto = proto;
    }

    public String getType() { return proto.getType(); }
    public double getPrice() { return proto.getPrice(); }

    public boolean isAvailable(Date in, Date out) {
        return true;
    }
}
