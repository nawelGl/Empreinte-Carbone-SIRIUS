package edu.ezip.ing1.pds.front;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ProductFrame extends JFrame {
    private int width = 1000;
    private int height= 700;

    private JLabel text;
    public void createProductFrame() {
        // installation de frame
        JFrame frame = new JFrame("Statistique par produit");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(width, height);
        frame.getContentPane().setBackground(new Color(0xE7EBE4));

        // main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(new Color(0xE7EBE4));

        // panel pour le header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(Color.white);
        headerPanel.setBorder(new EmptyBorder(30, 30, 30, 30));
        JLabel headerLabel = new JLabel("Statistique par produit");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 20));
        headerPanel.add(headerLabel);

        JPanel contentPanel = new JPanel();
        text = new JLabel("work in progress...");
        contentPanel.add(text);

        //ajoute
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(contentPanel, BorderLayout.CENTER);
        frame.add(mainPanel);

        frame.setVisible(true);



    }
}
