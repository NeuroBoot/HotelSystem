package com.mycompany.hotelsystem.gui;

import com.mycompany.hotelsystem.builder.Reservation;
import com.mycompany.hotelsystem.builder.ReservationBuilder;
import com.mycompany.hotelsystem.customer.Customer;
import com.mycompany.hotelsystem.factory.CustomerProfileFactory;
import com.mycompany.hotelsystem.factory.RoomFactory;
import com.mycompany.hotelsystem.prototype.RoomPrototype;
import com.mycompany.hotelsystem.proxy.RoomProxy;
import com.mycompany.hotelsystem.singleton.PaymentProcessor;
import com.mycompany.hotelsystem.singleton.ReservationManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BookingPage extends JFrame {

    private JTextField nameField, checkinField, checkoutField;
    private JComboBox<String> customerTypeCombo, roomTypeCombo;
    private JLabel totalLabel;
    private DefaultTableModel tableModel;
    private JTable table;

    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public BookingPage() {
        setTitle("Room Booking");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setLayout(null);

        JButton returnBtn = new JButton("Return to Home");
        returnBtn.setBounds(20, 20, 150, 30);
        returnBtn.addActionListener(e -> {
            new HomePage().setVisible(true);
            dispose();
        });
        add(returnBtn);

        // Form Panel
        JPanel formPanel = new JPanel(null);
        formPanel.setBorder(BorderFactory.createTitledBorder("Booking Form"));
        formPanel.setBounds(20, 70, 840, 180);

        formPanel.add(new JLabel("Customer Name:")).setBounds(20, 30, 150, 25);
        nameField = new JTextField();
        nameField.setBounds(170, 30, 200, 25);
        formPanel.add(nameField);

        formPanel.add(new JLabel("Customer Type:")).setBounds(400, 30, 150, 25);
        customerTypeCombo = new JComboBox<>(new String[]{"Regular", "VIP", "Corporate"});
        customerTypeCombo.setBounds(530, 30, 200, 25);
        formPanel.add(customerTypeCombo);

        formPanel.add(new JLabel("Room Type:")).setBounds(20, 70, 150, 25);
        roomTypeCombo = new JComboBox<>(new String[]{"Standard", "Deluxe", "Suite"});
        roomTypeCombo.setBounds(170, 70, 200, 25);
        formPanel.add(roomTypeCombo);

        formPanel.add(new JLabel("Check-in (yyyy-MM-dd):")).setBounds(20, 110, 200, 25);
        checkinField = new JTextField();
        checkinField.setBounds(220, 110, 150, 25);
        formPanel.add(checkinField);

        formPanel.add(new JLabel("Check-out (yyyy-MM-dd):")).setBounds(400, 110, 200, 25);
        checkoutField = new JTextField();
        checkoutField.setBounds(580, 110, 150, 25);
        formPanel.add(checkoutField);

        totalLabel = new JLabel("Total: $0.00");
        totalLabel.setBounds(750, 145, 150, 25);
        formPanel.add(totalLabel);

        JButton bookBtn = new JButton("Book Room");
        bookBtn.setBounds(250, 145, 150, 25);
        bookBtn.addActionListener(this::bookRoom);
        formPanel.add(bookBtn);

        JButton cancelBtn = new JButton("Cancel Selected");
        cancelBtn.setBounds(420, 145, 150, 25);
        cancelBtn.addActionListener(e -> cancelReservation());
        formPanel.add(cancelBtn);

        add(formPanel);

        // Table
        tableModel = new DefaultTableModel(
                new String[]{"Name", "Customer Type", "Room Type", "Check-in", "Check-out", "Total"}, 0
        );
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(20, 270, 840, 260);
        add(scrollPane);

        // Update total when fields change
        checkinField.addActionListener(e -> updateTotalDisplay());
        checkoutField.addActionListener(e -> updateTotalDisplay());
        roomTypeCombo.addActionListener(e -> updateTotalDisplay());
        customerTypeCombo.addActionListener(e -> updateTotalDisplay());
    }

    private void updateTotalDisplay() {
        try {
            Date checkIn = parseDate(checkinField.getText());
            Date checkOut = parseDate(checkoutField.getText());

            RoomPrototype proto = RoomFactory.createRoomPrototype(
                    roomTypeCombo.getSelectedItem().toString()
            );
            RoomProxy proxy = new RoomProxy(proto);

            double baseTotal = proxy.getPrice() * 2; 
            String custType = customerTypeCombo.getSelectedItem().toString();
            double discount = switch (custType.toLowerCase()) {
                case "vip" -> 0.20;
                case "corporate" -> 0.10;
                default -> 0.0;
            };
            double finalTotal = baseTotal * (1 - discount);

            totalLabel.setText(String.format("Total: $%.2f (%.0f%% discount)", finalTotal, discount * 100));
        } catch (ParseException ex) {
            totalLabel.setText("Total: $0.00");
        }
    }

    private void bookRoom(ActionEvent e) {
        try {
            Date checkIn = parseDate(checkinField.getText());
            Date checkOut = parseDate(checkoutField.getText());

            if (!checkOut.after(checkIn)) {
                JOptionPane.showMessageDialog(this, "Check-out date must be after check-in!");
                return;
            }

            RoomPrototype proto = RoomFactory.createRoomPrototype(
                    roomTypeCombo.getSelectedItem().toString()
            );
            RoomProxy proxy = new RoomProxy(proto);

            if (!proxy.isAvailable(checkIn, checkOut)) {
                JOptionPane.showMessageDialog(this, "Room unavailable for selected dates!");
                return;
            }

            Customer customer = CustomerProfileFactory.createCustomer(
                    customerTypeCombo.getSelectedItem().toString(),
                    nameField.getText()
            );

            if (ReservationManager.getInstance().hasDuplicate(customer.getName(), checkIn, checkOut)) {
                JOptionPane.showMessageDialog(this, "Cannot book multiple rooms for the same customer at the same time!");
                return;
            }

            double baseTotal = proxy.getPrice() * 2;
            double discount = switch (customer.getType().toLowerCase()) {
                case "vip" -> 0.20;
                case "corporate" -> 0.10;
                default -> 0.0;
            };
            double finalTotal = baseTotal * (1 - discount);

            Reservation reservation = new ReservationBuilder()
                    .setCustomerName(customer.getName())
                    .setCustomerType(customer.getType())
                    .setRoomType(proxy.getType())
                    .setCheckIn(checkIn)
                    .setCheckOut(checkOut)
                    .setTotal(finalTotal)
                    .build();

            if (PaymentProcessor.getInstance().processPayment(finalTotal, nameField)) {
                ReservationManager.getInstance().addReservation(reservation);

                tableModel.addRow(new Object[]{
                        reservation.getCustomerName(),
                        reservation.getCustomerType(),
                        reservation.getRoomType(),
                        sdf.format(reservation.getCheckIn()),
                        sdf.format(reservation.getCheckOut()),
                        "$" + reservation.getTotal()
                });

                JOptionPane.showMessageDialog(this, "Booking Confirmed!");
            } else {
                JOptionPane.showMessageDialog(this, "Payment Failed!");
            }

        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(this, "Invalid date format! Use yyyy-MM-dd.");
        }
    }

    private void cancelReservation() {
        int row = table.getSelectedRow();
        if (row >= 0) {
            tableModel.removeRow(row);
            JOptionPane.showMessageDialog(this, "Reservation cancelled.");
        }
    }

    
    private Date parseDate(String text) throws ParseException {
        return sdf.parse(text.trim());
    }
}
