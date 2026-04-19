package com.mycompany.hotelsystem.singleton;

import com.mycompany.hotelsystem.builder.Reservation;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.text.SimpleDateFormat;

public class ReservationManager {

    private static ReservationManager instance;
    private final List<Reservation> reservations = new ArrayList<>();
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    private ReservationManager() {}

    public static ReservationManager getInstance() {
        if (instance == null) {
            instance = new ReservationManager();
        }
        return instance;
    }

    public void addReservation(Reservation r) {
        reservations.add(r);
        System.out.println("📌 Reservation Added -> "
                + r.getRoomType() + " | "
                + sdf.format(r.getCheckIn()) + " to " + sdf.format(r.getCheckOut())
                + " | Customer: " + r.getCustomerName());
    }

    public List<Reservation> getReservations() {
        return new ArrayList<>(reservations); 
    }

    
    public boolean overlaps(Date existingIn, Date existingOut,
                            Date newIn, Date newOut) {
        return newIn.before(existingOut) && newOut.after(existingIn);
    }

    
    public boolean hasDuplicate(String customerName, Date newIn, Date newOut) {
        for (Reservation r : reservations) {
            if (r.getCustomerName().equalsIgnoreCase(customerName) &&
                overlaps(r.getCheckIn(), r.getCheckOut(), newIn, newOut)) {
                return true;
            }
        }
        return false;
    }
}
