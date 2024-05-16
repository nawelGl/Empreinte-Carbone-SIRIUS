package edu.ezip.ing1.pds.front.Categories;

import edu.ezip.ing1.pds.business.dto.SousCategorieA;
import edu.ezip.ing1.pds.business.dto.SousCategorieB;
import edu.ezip.ing1.pds.business.dto.SousCategoriesB;
import edu.ezip.ing1.pds.client.Categories.SelectAllSousCategorieA;
import edu.ezip.ing1.pds.client.Categories.SelectAllSousCategorieB;
import edu.ezip.ing1.pds.front.Categories.CategoriesTemplate;
import edu.ezip.ing1.pds.front.CategoriesFrame;

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
            sous_categories_B = SelectAllSousCategorieB.launchSelectAllSousCatB("0,2,1,2");
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
        //Besoin de passer en parametres que la categorie choisie par l'user pas toutes
        CategoriesFrame categoriesFrame = new CategoriesFrame(sous_categorie_B);
    }
}
