package edu.ezip.ing1.pds.front.Categories;

import edu.ezip.ing1.pds.business.dto.SousCategorieA;
import edu.ezip.ing1.pds.business.dto.SousCategoriesA;
import edu.ezip.ing1.pds.client.Categories.SelectAllSousCategorieA;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;

public class MenCategories extends CategoriesTemplate implements ActionListener{

    private SousCategoriesA sous_categories_A;
    private int x = 0;
    private int y = 0;

    public MenCategories(){
        super();

        try{
            sous_categories_A = SelectAllSousCategorieA.launchSelectAllSousCatA("0,2");
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        for(int i = 0; i < sous_categories_A.getSousCategoriesA().size(); i++){
            JButton bouton = new JButton();
            SousCategorieA categorie = sous_categories_A.getSousCategoriesA().get(i);
            bouton.setText(categorie.getNomSouscatA());
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
    public void actionPerformed(ActionEvent e){
        super.actionPerformed(e);

        if (e.getSource() instanceof JButton) {
            JButton button = (JButton) e.getSource();
            if(button.getText().equals("Vêtements de sport")){
                MenSportswear menSportswear = new MenSportswear();
                categorieFrame.dispose();
            } else if(button.getText().equals("Sous vêtements")){
                MenUnderwears menUnderwears = new MenUnderwears();
                //categorieFrame.dispose();
            } else if(button.getText().equals("Accessoire")){
                //ChildrenCategories childrenCategories = new ChildrenCategories();
                //categorieFrame.dispose();
            } else if(button.getText().equals("Hauts")){
                MenTops menTops = new MenTops();
                categorieFrame.dispose();
            } else if(button.getText().equals("Bas")){
                MenPants menPants = new MenPants();
                categorieFrame.dispose();
            } else if(button.getText().equals("Costumes")){
                MenSuits menSuits = new MenSuits();
                categorieFrame.dispose();
            } else if(button.getText().equals("Pyjamas")){
                //WomenDresses womenDresses = new WomenDresses();
                //categorieFrame.dispose();
            }
        }
    }
}
