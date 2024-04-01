package edu.ezip.ing1.pds.front;

import java.awt.event.ActionListener;

import javax.swing.JButton;

public class MenSuits extends CategoriesTemplate implements ActionListener{
    JButton costumes;
    JButton vestes;
    JButton pantalons;
    JButton chemises;
    JButton cravatesAccessoires;

    public MenSuits(){
        super();

        costumes = new JButton("Costumes complets");
        setButtonStyle(costumes, width, height); 
        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(costumes, gbc);

        vestes = new JButton("Vestes de cosume");
        setButtonStyle(vestes, width, height); 
        gbc.gridx = 1;
        gbc.gridy = 0;
        mainPanel.add(vestes, gbc);

        pantalons = new JButton("Pantalons de costume");
        setButtonStyle(pantalons, width, height); 
        gbc.gridx = 2;
        gbc.gridy = 0;
        mainPanel.add(pantalons, gbc);

        chemises = new JButton("Chemises habillées");
        setButtonStyle(chemises, width, height); 
        gbc.gridx = 0;
        gbc.gridy = 1;
        mainPanel.add(chemises, gbc);

        cravatesAccessoires = new JButton("Cravates et accessoires");
        setButtonStyle(cravatesAccessoires, width, height); 
        gbc.gridx = 1;
        gbc.gridy = 1;
        mainPanel.add(cravatesAccessoires, gbc);
        
    }
}
