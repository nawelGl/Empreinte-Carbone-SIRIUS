package edu.ezip.ing1.pds.front.Categories;

import edu.ezip.ing1.pds.front.Categories.CategoriesTemplate;

import java.awt.event.ActionListener;

import javax.swing.JButton;

public class GirlsCategories extends CategoriesTemplate implements ActionListener{

    JButton robes;
    JButton jupes;
    JButton leggings;
    JButton tshirts;

    public GirlsCategories(){
        super();

        robes = new JButton("Robes");
        setButtonStyle(robes, width, height); 
        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(robes, gbc);

        jupes = new JButton("Jupes");
        setButtonStyle(jupes, width, height); 
        gbc.gridx = 1;
        gbc.gridy = 0;
        mainPanel.add(jupes, gbc);

        leggings = new JButton("Leggings");
        setButtonStyle(leggings, width, height); 
        gbc.gridx = 0;
        gbc.gridy = 1;
        mainPanel.add(leggings, gbc);

        tshirts = new JButton("T-shirts");
        setButtonStyle(tshirts, width, height); 
        gbc.gridx = 1;
        gbc.gridy = 1;
        mainPanel.add(tshirts, gbc);
    }
    
}
