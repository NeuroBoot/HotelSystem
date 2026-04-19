package com.mycompany.hotelsystem.gui;

import javax.swing.*;
import java.awt.*;

public class HomePage extends JFrame {

    public HomePage() {
        setTitle("Hotel System");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel bannerPanel = new JPanel() {
            Image originalImage = new ImageIcon("./src/resources/HomePage.png").getImage();

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(originalImage, 0, 0, getWidth(), getHeight(), this); 
            }
        };
        bannerPanel.setLayout(null); 
        add(bannerPanel);

        JButton bookRoomBtn = new JButton("Book Room");
        bookRoomBtn.setFont(new Font("Arial", Font.BOLD, 18));
        bannerPanel.add(bookRoomBtn);

        bannerPanel.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                int btnWidth = 200;
                int btnHeight = 40;
                int x = bannerPanel.getWidth() - btnWidth - 20; 
                int y = bannerPanel.getHeight() - btnHeight - 20; 
                bookRoomBtn.setBounds(x, y, btnWidth, btnHeight);
            }
        });

        int btnWidth = 200;
        int btnHeight = 40;
        bookRoomBtn.setBounds(getWidth() - btnWidth - 20, getHeight() - btnHeight - 20, btnWidth, btnHeight);

        bookRoomBtn.addActionListener(e -> {
            new BookingPage().setVisible(true);
            dispose();
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(HomePage::new);
    }
}
