package edu.ezip.ing1.pds.front.Categories;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.JButton;

import edu.ezip.ing1.pds.business.dto.SousCategorieA;
import edu.ezip.ing1.pds.business.dto.SousCategoriesA;
import edu.ezip.ing1.pds.client.Categories.SelectAllSousCategorieA;

public class WomenCategories extends CategoriesTemplate implements ActionListener {
    //buttons for each sub category :
    JButton hauts;
    JButton bas;
    JButton robes;
    JButton vetementsMaternite;
    JButton lingerie;
    private SousCategoriesA sous_categories_A;
    private SousCategorieA sous_categorie_A;

    public WomenCategories(){
        super();

        //Ajouter la requete pour récupérer toute sles catégories de femmes :
        try{
            sous_categories_A = SelectAllSousCategorieA.launchSelectAllSousCatA("0,1");
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        //Test récupération des sous categories A :
        System.out.println("TEST SOUS CATEGORIES A : " + sous_categories_A.toString());

        hauts = new JButton("Hauts");
        hauts.addActionListener(this);
        setButtonStyle(hauts, width, height); 
        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(hauts, gbc);

        bas = new JButton("Bas");
        bas.addActionListener(this);
        setButtonStyle(bas, width, height); 
        gbc.gridx = 1;
        gbc.gridy = 0;
        mainPanel.add(bas, gbc);

        robes = new JButton("Robes");
        robes.addActionListener(this);
        setButtonStyle(robes, width, height); 
        gbc.gridx = 2;
        gbc.gridy = 0;
        mainPanel.add(robes, gbc);

        vetementsMaternite = new JButton("Vêtements de maternité");
        vetementsMaternite.addActionListener(this);
        setButtonStyle(vetementsMaternite, width, height); 
        gbc.gridx = 0;
        gbc.gridy = 1;
        mainPanel.add(vetementsMaternite, gbc);

        lingerie = new JButton("Lingerie");
        lingerie.addActionListener(this);
        setButtonStyle(lingerie, width, height); 
        gbc.gridx = 1;
        gbc.gridy = 1;
        mainPanel.add(lingerie, gbc);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        super.actionPerformed(e);
        if(e.getSource() == hauts){
            WomenTop womenTop = new WomenTop();
            categorieFrame.dispose();
        } else  if(e.getSource() == bas){
            WomenPants womenPants = new WomenPants();
            categorieFrame.dispose();
        } else if (e.getSource() == robes){
            WomenDresses womenDresses = new WomenDresses();
            categorieFrame.dispose();
        } else if(e.getSource() == vetementsMaternite){
            WomenMaternityClothes womenMaternityClothes = new WomenMaternityClothes();
            categorieFrame.dispose();
        } else if(e.getSource() == lingerie){
            WomenLingerie womenLingerie = new WomenLingerie();
            categorieFrame.dispose();
        }
    }
    
}
