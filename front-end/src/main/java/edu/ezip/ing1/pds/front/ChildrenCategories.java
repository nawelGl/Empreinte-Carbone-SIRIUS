package edu.ezip.ing1.pds.front;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class ChildrenCategories extends CategoriesTemplate implements ActionListener{

    JButton bebe;
    JButton garcons;
    JButton filles;
    JButton accessoires;

    public ChildrenCategories(){
        super();

        bebe = new JButton("Bébés");
        bebe.addActionListener(this);
        setButtonStyle(bebe, width, height); 
        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(bebe, gbc);

        garcons = new JButton("Garçons");
        garcons.addActionListener(this);
        setButtonStyle(garcons, width, height); 
        gbc.gridx = 1;
        gbc.gridy = 0;
        mainPanel.add(garcons, gbc);

        filles = new JButton("Filles");
        filles.addActionListener(this);
        setButtonStyle(filles, width, height); 
        gbc.gridx = 0;
        gbc.gridy = 1;
        mainPanel.add(filles, gbc);

        accessoires = new JButton("Accessoires");
        accessoires.addActionListener(this);
        setButtonStyle(accessoires, width, height); 
        gbc.gridx = 1;
        gbc.gridy = 1;
        mainPanel.add(accessoires, gbc);
    }

    @Override
    public void actionPerformed(ActionEvent e){
        super.actionPerformed(e);
        if(e.getSource() == bebe){
            categorieFrame.dispose();
            BabiesCategories babiesCategories = new BabiesCategories();
        } else if(e.getSource() == garcons){
            categorieFrame.dispose();
            BoyCategories boyCategories = new BoyCategories();
        } else if(e.getSource() == filles){
            categorieFrame.dispose();
            GirlsCategories girlsCategories = new GirlsCategories();
        } else if(e.getSource() == accessoires){
            categorieFrame.dispose();
            ChildrenAccessories childrenAccessories = new ChildrenAccessories();
        }
    }
    
}
