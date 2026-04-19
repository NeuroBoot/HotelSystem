package com.mycompany.hotelsystem.factory;

import com.mycompany.hotelsystem.prototype.*;

public class RoomFactory {
    public static RoomPrototype createRoomPrototype(String type) {
        switch (type.toLowerCase()) {
            case "deluxe":
                return new Deluxe();
            case "suite":
                return new Suite();
            default:
                return new Standard();
        }
    }
}
