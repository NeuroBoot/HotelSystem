package com.mycompany.hotelsystem.proxy;

import java.util.Date;

public interface Room {
    String getType();
    double getPrice();
    boolean isAvailable(Date in, Date out);
}
