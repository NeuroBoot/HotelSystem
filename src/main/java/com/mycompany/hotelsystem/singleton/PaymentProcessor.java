package com.mycompany.hotelsystem.singleton;

import javax.swing.JTextField;

public class PaymentProcessor {
    private static PaymentProcessor instance;

    private PaymentProcessor(){}

    public static PaymentProcessor getInstance(){
        if(instance==null) instance=new PaymentProcessor();
        return instance;
    }

    public boolean processPayment(double amount, JTextField name){
        return !name.getText().isEmpty();
    }
}

