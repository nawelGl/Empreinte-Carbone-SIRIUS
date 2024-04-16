package edu.ezip.ing1.pds.front.Categories;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class MainCategorie extends CategoriesTemplate implements ActionListener{
    //Buttons for each category :
    JButton categorieFemme;
    JButton categorieHomme;
    JButton categorieEnfant;

    public MainCategorie(){
        super();

        //Women category :
        categorieFemme = new JButton("Femmes");
        categorieFemme.addActionListener(this);
        setButtonStyle(categorieFemme, width, height); 
        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(categorieFemme, gbc);

        //Men category :
        categorieHomme = new JButton("Hommes");
        categorieHomme.addActionListener(this);
        setButtonStyle(categorieHomme, width, height);
        gbc.gridx = 1;
        gbc.gridy = 0;
        mainPanel.add(categorieHomme, gbc);

        //Child category :
        categorieEnfant = new JButton("Enfants");
        categorieEnfant.addActionListener(this);
        setButtonStyle(categorieEnfant, width, height);
        gbc.gridx = 2;
        gbc.gridy = 0;
        mainPanel.add(categorieEnfant, gbc);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        super.actionPerformed(e);
        if(e.getSource() == categorieFemme){
            WomenCategories womenCategories = new WomenCategories();
            categorieFrame.dispose();
        } else if(e.getSource() == categorieHomme){
            MenCategories menCategories = new MenCategories();
            categorieFrame.dispose();
        } else if(e.getSource() == categorieEnfant){
            ChildrenCategories childrenCategories = new ChildrenCategories();
            categorieFrame.dispose();
        } 
    }
    
}
