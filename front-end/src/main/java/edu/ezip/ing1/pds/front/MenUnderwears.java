package edu.ezip.ing1.pds.front;

import java.awt.event.ActionListener;

import javax.swing.JButton;

public class MenUnderwears extends CategoriesTemplate implements ActionListener{

    JButton boxers;
    JButton calecons;
    JButton chausettes;

    public MenUnderwears(){
        super();
        boxers = new JButton("Boxers");
        setButtonStyle(boxers, width, height); 
        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(boxers, gbc);

        calecons = new JButton("Caleçons");
        setButtonStyle(calecons, width, height); 
        gbc.gridx = 1;
        gbc.gridy = 0;
        mainPanel.add(calecons, gbc);

        chausettes = new JButton("Chaussettes");
        setButtonStyle(chausettes, width, height); 
        gbc.gridx = 2;
        gbc.gridy = 0;
        mainPanel.add(chausettes, gbc);
    }
}