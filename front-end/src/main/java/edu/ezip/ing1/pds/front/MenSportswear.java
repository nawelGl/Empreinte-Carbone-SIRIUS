package edu.ezip.ing1.pds.front;

import java.awt.event.ActionListener;

import javax.swing.JButton;

public class MenSportswear extends CategoriesTemplate implements ActionListener{

    JButton tshirt;
    JButton shorts;
    JButton survetement;

    public MenSportswear(){
        super();

        tshirt = new JButton("T-shirts de sport");
        setButtonStyle(tshirt, width, height); 
        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(tshirt, gbc);

        shorts = new JButton("Shorts de sport");
        setButtonStyle(shorts, width, height); 
        gbc.gridx = 1;
        gbc.gridy = 0;
        mainPanel.add(shorts, gbc);

        survetement = new JButton("Survêtements");
        setButtonStyle(survetement, width, height); 
        gbc.gridx = 2;
        gbc.gridy = 0;
        mainPanel.add(survetement, gbc);

    }
}
