package edu.ezip.ing1.pds.front;

import java.awt.event.ActionListener;

import javax.swing.JButton;

public class WomenDresses extends CategoriesTemplate implements ActionListener{
    
    JButton soiree;
    JButton decontractee;
    JButton ete;
    JButton cocktail;

    public WomenDresses(){
        super();

        soiree = new JButton("Robes de soirée");
        setButtonStyle(soiree, width, height); 
        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(soiree, gbc);

        decontractee = new JButton("Robes décontractées");
        setButtonStyle(decontractee, width, height); 
        gbc.gridx = 0;
        gbc.gridy = 1;
        mainPanel.add(decontractee, gbc);

        ete = new JButton("Robes d'été");
        setButtonStyle(ete, width, height); 
        gbc.gridx = 1;
        gbc.gridy = 0;
        mainPanel.add(ete, gbc);

        cocktail = new JButton("Robes cocktail");
        setButtonStyle(cocktail, width, height); 
        gbc.gridx = 1;
        gbc.gridy = 1;
        mainPanel.add(cocktail, gbc);
    }
}
