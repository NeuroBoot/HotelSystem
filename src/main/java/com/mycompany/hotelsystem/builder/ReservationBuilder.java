package com.mycompany.hotelsystem.builder;

import java.util.Date;

public class ReservationBuilder {
    private String customerName;
    private String customerType;
    private String roomType;
    private Date checkIn;
    private Date checkOut;
    private double total;

    public ReservationBuilder setCustomerName(String customerName) {
        this.customerName = customerName;
        return this;
    }

    public ReservationBuilder setCustomerType(String customerType) {
        this.customerType = customerType;
        return this;
    }

    public ReservationBuilder setRoomType(String roomType) {
        this.roomType = roomType;
        return this;
    }

    public ReservationBuilder setCheckIn(Date checkIn) {
        this.checkIn = checkIn;
        return this;
    }

    public ReservationBuilder setCheckOut(Date checkOut) {
        this.checkOut = checkOut;
        return this;
    }

    public ReservationBuilder setTotal(double total) {
        this.total = total;
        return this;
    }

    public Reservation build() {
        return new Reservation.Builder()
                .setCustomerName(customerName)
                .setCustomerType(customerType)
                .setRoomType(roomType)
                .setCheckIn(checkIn)
                .setCheckOut(checkOut)
                .setTotal(total)
                .build();
    }
}
