package edu.ezip.ing1.pds.front.Categories;

import edu.ezip.ing1.pds.business.dto.SousCategorieA;
import edu.ezip.ing1.pds.business.dto.SousCategorieB;
import edu.ezip.ing1.pds.business.dto.SousCategoriesB;
import edu.ezip.ing1.pds.client.Categories.SelectAllSousCategorieA;
import edu.ezip.ing1.pds.client.Categories.SelectAllSousCategorieB;
import edu.ezip.ing1.pds.front.Categories.CategoriesTemplate;

import static java.lang.String.valueOf;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;

public class WomenTop extends CategoriesTemplate implements ActionListener{

    private SousCategoriesB sous_categories_B;
    private int x = 0;
    private int y = 0;
    //buttons for each sub category :
    JButton chemisiers;
    JButton tshirts;
    JButton blouses;
    JButton pulls;
    JButton vestes;
    JButton gilets;

    public WomenTop(){
        super();

        try{
            sous_categories_B = SelectAllSousCategorieB.launchSelectAllSousCatB("0,1");
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        for(int i = 0; i < sous_categories_B.getSousCategoriesB().size(); i++){
            JButton bouton = new JButton();
            SousCategorieB categorie = sous_categories_B.getSousCategoriesB().get(i);
            bouton.setText(categorie.getNomSouscatB());
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

//        chemisiers = new JButton("Chemisiers");
//        setButtonStyle(chemisiers, width, height);
//        gbc.gridx = 0;
//        gbc.gridy = 0;
//        mainPanel.add(chemisiers, gbc);
//
//        tshirts = new JButton("T-shirts");
//        setButtonStyle(tshirts, width, height);
//        gbc.gridx = 1;
//        gbc.gridy = 0;
//        mainPanel.add(tshirts, gbc);
//
//        blouses = new JButton("Blouses");
//        setButtonStyle(blouses, width, height);
//        gbc.gridx = 2;
//        gbc.gridy = 0;
//        mainPanel.add(blouses, gbc);
//
//        pulls = new JButton("Pulls");
//        setButtonStyle(pulls, width, height);
//        gbc.gridx = 0;
//        gbc.gridy = 1;
//        mainPanel.add(pulls, gbc);
//
//        vestes = new JButton("Vestes");
//        setButtonStyle(vestes, width, height);
//        gbc.gridx = 1;
//        gbc.gridy = 1;
//        mainPanel.add((vestes), gbc);
//
//        gilets = new JButton("Gilets");
//        setButtonStyle(gilets, width, height);
//        gbc.gridx = 2;
//        gbc.gridy = 1;
//        mainPanel.add(gilets, gbc);
    }


    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
