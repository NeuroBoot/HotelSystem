package com.mycompany.hotelsystem.builder;

import java.util.Date;

public class Reservation {
    private String customerName;
    private String customerType;
    private String roomType;
    private Date checkIn;
    private Date checkOut;
    private double total;

    private Reservation() {}

    public static class Builder {
        private String customerName;
        private String customerType;
        private String roomType;
        private Date checkIn;
        private Date checkOut;
        private double total;

        public Builder setCustomerName(String customerName) {
            this.customerName = customerName;
            return this;
        }

        public Builder setCustomerType(String customerType) {
            this.customerType = customerType;
            return this;
        }

        public Builder setRoomType(String roomType) {
            this.roomType = roomType;
            return this;
        }

        public Builder setCheckIn(Date checkIn) {
            this.checkIn = checkIn;
            return this;
        }

        public Builder setCheckOut(Date checkOut) {
            this.checkOut = checkOut;
            return this;
        }

        public Builder setTotal(double total) {
            this.total = total;
            return this;
        }

        public Reservation build() {
            Reservation reservation = new Reservation();
            reservation.customerName = this.customerName;
            reservation.customerType = this.customerType;
            reservation.roomType = this.roomType;
            reservation.checkIn = this.checkIn;
            reservation.checkOut = this.checkOut;
            reservation.total = this.total;
            return reservation;
        }
    }

    public String getCustomerName() { return customerName; }
    public String getCustomerType() { return customerType; }
    public String getRoomType() { return roomType; }
    public Date getCheckIn() { return checkIn; }
    public Date getCheckOut() { return checkOut; }
    public double getTotal() { return total; }
}
