package edu.ezip.ing1.pds.front;

import java.awt.event.ActionListener;

import javax.swing.JButton;

public class WomenLingerie extends CategoriesTemplate implements ActionListener {
    
    JButton soutiensGorge;
    JButton culottes;
    JButton nuisettes;
    JButton pyjamas;

    public WomenLingerie(){
        super();

        soutiensGorge = new JButton("Soutiens-gorge");
        setButtonStyle(soutiensGorge, width, height); 
        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(soutiensGorge, gbc);

        culottes = new JButton("Culottes");
        setButtonStyle(culottes, width, height); 
        gbc.gridx = 1;
        gbc.gridy = 0;
        mainPanel.add(culottes, gbc);

        nuisettes = new JButton("Nuisettes");
        setButtonStyle(nuisettes, width, height); 
        gbc.gridx = 0;
        gbc.gridy = 1;
        mainPanel.add(nuisettes, gbc);

        pyjamas = new JButton("Pyjamas");
        setButtonStyle(pyjamas, width, height); 
        gbc.gridx = 1;
        gbc.gridy = 1;
        mainPanel.add(pyjamas, gbc);
    }
}
