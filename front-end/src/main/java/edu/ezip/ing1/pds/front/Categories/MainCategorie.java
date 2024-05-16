package edu.ezip.ing1.pds.front.Categories;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import edu.ezip.ing1.pds.business.dto.Categorie;
import javax.swing.JButton;

public class MainCategorie extends CategoriesTemplate implements ActionListener{

    public MainCategorie(){
        super();

        for (int i = 0; i < categories.getCategories().size(); i++){
            JButton bouton = new JButton();
            Categorie categorie = categories.getCategories().get(i);
            bouton.setText(categorie.getNomCategorie());
            bouton.addActionListener(this);
            setButtonStyle(bouton, width, height);
            gbc.gridx = i;
            gbc.gridy = 0;
            mainPanel.add(bouton, gbc);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        super.actionPerformed(e);
        if (e.getSource() instanceof JButton) {
            JButton button = (JButton) e.getSource();
            if(button.getText().equals("Hommes")){
                MenCategories menCategories = new MenCategories();
                categorieFrame.dispose();
            } else if(button.getText().equals("Femmes")){
                WomenCategories womenCategories = new WomenCategories();
                categorieFrame.dispose();
            } else if(button.getText().equals("Enfants")){
                ChildrenCategories childrenCategories = new ChildrenCategories();
                categorieFrame.dispose();
            }
        } 
    }
    
}
