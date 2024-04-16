package edu.ezip.ing1.pds.front.Categories;

import java.awt.event.ActionListener;

import javax.swing.JButton;

public class BoyCategories extends CategoriesTemplate implements ActionListener{

    JButton tshirt;
    JButton pantalons;
    JButton shorts;
    JButton chemises;

    public BoyCategories(){
        super();

        tshirt = new JButton("T-shirts");
        setButtonStyle(tshirt, width, height); 
        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(tshirt, gbc);

        pantalons = new JButton("Pantalons");
        setButtonStyle(pantalons, width, height); 
        gbc.gridx = 1;
        gbc.gridy = 0;
        mainPanel.add(pantalons, gbc);

        shorts = new JButton("Shorts");
        setButtonStyle(shorts, width, height); 
        gbc.gridx = 0;
        gbc.gridy = 1;
        mainPanel.add(shorts, gbc);

        chemises = new JButton("Chemises");
        setButtonStyle(chemises, width, height); 
        gbc.gridx = 1;
        gbc.gridy = 1;
        mainPanel.add(chemises, gbc);
    }
    
}
