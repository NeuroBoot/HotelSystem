package com.mycompany.hotelsystem.factory;

import com.mycompany.hotelsystem.customer.*;

public class CustomerProfileFactory {
    public static Customer createCustomer(String type, String name) {
        switch (type.toLowerCase()) {
            case "vip":
                return new VIPCustomer(name);
            case "corporate":
                return new CorporateCustomer(name);
            default:
                return new RegularCustomer(name);
        }
    }
}
