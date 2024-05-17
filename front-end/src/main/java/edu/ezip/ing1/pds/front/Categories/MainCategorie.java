package edu.ezip.ing1.pds.front.Categories;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import edu.ezip.ing1.pds.business.dto.Categorie;
import edu.ezip.ing1.pds.business.dto.Categories;
import edu.ezip.ing1.pds.client.Categories.SelectAllCategorie;

import javax.swing.JButton;

public class MainCategorie extends CategoriesTemplate implements ActionListener{

    Categories categories;
    private static int idCategorie;

    public MainCategorie(){
        super();

        // ----- Récupértation des infos via des requetes -------
        try {
            categories = SelectAllCategorie.launchSelectAllCategorie();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


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

    public int getIdCategorie() {
        return idCategorie;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        super.actionPerformed(e);
        if (e.getSource() instanceof JButton) {
            JButton button = (JButton) e.getSource();
            if(button.getText().equals("Hommes")){
                idCategorie = 2;
                MenCategories menCategories = new MenCategories();
                categorieFrame.dispose();
            } else if(button.getText().equals("Femmes")){
                idCategorie = 1;
                WomenCategories womenCategories = new WomenCategories();
                categorieFrame.dispose();
            } else if(button.getText().equals("Enfants")){
                idCategorie = 3;
                ChildrenCategories childrenCategories = new ChildrenCategories();
                categorieFrame.dispose();
            }
        } 
    }
}
