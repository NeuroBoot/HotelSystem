package com.mycompany.hotelsystem.proxy;

import com.mycompany.hotelsystem.prototype.RoomPrototype;
import com.mycompany.hotelsystem.builder.Reservation;
import com.mycompany.hotelsystem.singleton.ReservationManager;

import java.util.Date;
import java.util.Map;

public class RoomProxy implements Room {
    private RealRoom realRoom;

    private static final Map<String, Integer> HOTEL_INVENTORY =
            Map.of("Standard", 1, "Deluxe", 1, "Suite", 1);

    public RoomProxy(RoomPrototype proto) {
        this.realRoom = new RealRoom(proto);
    }

    private RealRoom getRealRoom() { return realRoom; }

    @Override
    public String getType() { return getRealRoom().getType(); }

    @Override
    public double getPrice() { return getRealRoom().getPrice(); }

    @Override
    public boolean isAvailable(Date checkIn, Date checkOut) {
        String roomType = getRealRoom().getType();
        int totalRoomsAvailable = HOTEL_INVENTORY.getOrDefault(roomType, 0);
        int booked = 0;

        for (Reservation r : ReservationManager.getInstance().getReservations()) {
            if (r.getRoomType().equalsIgnoreCase(roomType) &&
                ReservationManager.getInstance().overlaps(
                        r.getCheckIn(), r.getCheckOut(), checkIn, checkOut)) {
                booked++;
            }
        }

        if (booked >= totalRoomsAvailable) {
            System.out.println("PROXY: Hotel full - " + roomType + " (" + booked + "/" + totalRoomsAvailable + ")");
            return false;
        }

        System.out.println("PROXY: " + roomType + " OK (" + booked + "/" + totalRoomsAvailable + ")");
        return true;
    }
}
