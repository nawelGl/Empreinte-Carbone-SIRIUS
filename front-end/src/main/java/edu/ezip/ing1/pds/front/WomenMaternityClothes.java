package edu.ezip.ing1.pds.front;

import java.awt.event.ActionListener;

import javax.swing.JButton;

public class WomenMaternityClothes extends CategoriesTemplate implements ActionListener{

    JButton hauts;
    JButton pantalons;
    JButton robes;

    public WomenMaternityClothes(){
        hauts = new JButton("Hauts de maternité");
        setButtonStyle(hauts, width, height); 
        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(hauts, gbc);

        pantalons = new JButton("Pantalons de maternité");
        setButtonStyle(pantalons, width, height); 
        gbc.gridx = 1;
        gbc.gridy = 0;
        mainPanel.add(pantalons, gbc);

        robes = new JButton("Robes de maternité");
        setButtonStyle(robes, width, height); 
        gbc.gridx = 2;
        gbc.gridy = 0;
        mainPanel.add(robes, gbc);

    }
    
}
