package edu.ezip.ing1.pds.front.Categories;

import java.awt.event.ActionListener;

import javax.swing.JButton;

public class BabiesCategories extends CategoriesTemplate implements ActionListener{

    JButton barboteuses;
    JButton grenouilleres;
    JButton bodies;
    JButton pyjamas;

    public BabiesCategories(){
        super();

        barboteuses = new JButton("Barboteuses");
        setButtonStyle(barboteuses, width, height); 
        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(barboteuses, gbc);

        grenouilleres = new JButton("Grenouillères");
        setButtonStyle(grenouilleres, width, height); 
        gbc.gridx = 1;
        gbc.gridy = 0;
        mainPanel.add(grenouilleres, gbc);

        bodies = new JButton("Bodies");
        setButtonStyle(bodies, width, height); 
        gbc.gridx = 0;
        gbc.gridy = 1;
        mainPanel.add(bodies, gbc);

        pyjamas = new JButton("Pyjamas");
        setButtonStyle(pyjamas, width, height); 
        gbc.gridx = 1;
        gbc.gridy = 1;
        mainPanel.add(pyjamas, gbc);
    }
    
}
