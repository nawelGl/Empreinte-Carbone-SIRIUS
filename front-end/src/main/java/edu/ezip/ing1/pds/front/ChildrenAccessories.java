package edu.ezip.ing1.pds.front;

import java.awt.event.ActionListener;

import javax.swing.JButton;

public class ChildrenAccessories extends CategoriesTemplate implements ActionListener{

    JButton chaussettes;
    JButton chapeaux;
    JButton echarpes;
    JButton gants;

    public ChildrenAccessories(){
        super();

        chaussettes = new JButton("Chausettes");
        setButtonStyle(chaussettes, width, height); 
        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(chaussettes, gbc);

        chapeaux = new JButton("Chapeaux");
        setButtonStyle(chapeaux, width, height); 
        gbc.gridx = 1;
        gbc.gridy = 0;
        mainPanel.add(chapeaux, gbc);

        echarpes = new JButton("Écharpes");
        setButtonStyle(echarpes, width, height); 
        gbc.gridx = 0;
        gbc.gridy = 1;
        mainPanel.add(echarpes, gbc);

        gants = new JButton("Gants");
        setButtonStyle(gants, width, height); 
        gbc.gridx = 1;
        gbc.gridy = 1;
        mainPanel.add(gants, gbc);
    }
    
}
