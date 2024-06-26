package edu.ezip.ing1.pds.front.Categories;

import edu.ezip.ing1.pds.business.dto.SousCategorieB;
import edu.ezip.ing1.pds.business.dto.SousCategoriesB;
import edu.ezip.ing1.pds.client.Categories.SelectAllSousCategorieB;
import edu.ezip.ing1.pds.client.Categories.SelectSousCategorieBByName;
import edu.ezip.ing1.pds.front.CategoriesFrame;
import edu.ezip.ing1.pds.front.Categories.MainCategorie;

import static java.lang.String.valueOf;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;

public class WomenTop extends CategoriesTemplate implements ActionListener{

    private SousCategoriesB sous_categories_B;
    private SousCategorieB sous_categorie_B;
    private int x = 0;
    private int y = 0;

    public WomenTop(){
        super();

        try{
            sous_categories_B = SelectAllSousCategorieB.launchSelectAllSousCatB("0,1,1,1");
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        for(int i = 0; i < sous_categories_B.getSousCategoriesB().size(); i++){
            JButton bouton = new JButton();
            sous_categorie_B = sous_categories_B.getSousCategoriesB().get(i);
            bouton.setText(sous_categorie_B.getNomSouscatB());
            bouton.addActionListener(this);
            setButtonStyle(bouton, width, height);
            gbc.gridx = x;
            gbc.gridy = y;
            mainPanel.add(bouton, gbc);
            x++;
            if(x==3){
                x = 0;
            }
            if(i == 2 || i == 5){
                y++;
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof JButton) {
            JButton button = (JButton) e.getSource();
            try {
                sous_categorie_B = SelectSousCategorieBByName.launchSelectSousCatBByName(button.getText());
                CategoriesFrame categoriesFrame = new CategoriesFrame(sous_categorie_B, WomenCategories.getIdCategorie(), 1);
                categorieFrame.dispose();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
