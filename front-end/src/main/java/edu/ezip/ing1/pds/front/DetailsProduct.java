package edu.ezip.ing1.pds.front;

import edu.ezip.ing1.pds.business.dto.Categorie;
import edu.ezip.ing1.pds.business.dto.Marque;
import edu.ezip.ing1.pds.business.dto.Produit;
import edu.ezip.ing1.pds.business.dto.Ville;
import edu.ezip.ing1.pds.client.Categories.SelectCategorieByID;
import edu.ezip.ing1.pds.client.UC1.SelectMarqueById;
import edu.ezip.ing1.pds.client.UC1.SelectVilleById;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class DetailsProduct {

    JFrame frame;
    JPanel mainPanel;
    JPanel panelProduct;
    RoundedPanel panelPicture;
    Produit produit;
    Ville villeProduit;
    Marque marqueProduit;

    Categorie categorie;


    JLabel nameProduct;
    JLabel footprintProduct;
    JLabel priceProduct;
    JLabel scoreProduct;
    JLabel countryProduct;
    JLabel categorieProduct;
    JLabel brandProdcut;


    public DetailsProduct(Produit produit){
        this.produit= produit;
        frame  = new JFrame();
        frame.setSize(Template.LONGUEUR, Template.LARGEUR);
        frame.setTitle("Details Produit ");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);


        //----------panel header--------------
        String titreHeader = "Les détails du produit";
        MethodesFront.header(frame, titreHeader, 580);
        //------------------------------------


        //-------main panel-------------------
        mainPanel = new JPanel();
        mainPanel.setBackground(Color.decode(Template.COULEUR_PRINCIPALE));
        mainPanel.setLayout(null);
        frame.add(mainPanel);

        //-------Panel Product UC1 --------
        panelProduct = new JPanel();
        panelProduct.setLayout(null);
        panelProduct.setBounds(35, 60,500,580);
        panelProduct.setBackground(Color.decode(Template.COULEUR_SECONDAIRE));
        mainPanel.add(panelProduct);

        panelPicture=new RoundedPanel(30,30);
        panelPicture.setBounds(145,45,200,200);
        panelProduct.add(panelPicture);

        // ---------Differents Label--------
        scoreProduct= MethodesFront.setlabelIconScore(produit.getScore());
        nameProduct=new JLabel(produit.getNomProduit()+" "+ produit.getCouleur());
        footprintProduct=new JLabel(String.valueOf(new BigDecimal(produit.getEmpreinte()).setScale(1, RoundingMode.HALF_UP)
       )+" gCO2e");

        priceProduct=new JLabel("Prix : "+String.valueOf(produit.getPrix())+ " €");

        //TODO:
//        try{
//        marque=SelectMarqueById.launchSelectMarquByRef(valueOf(produit.getReference());
////    } catch(Exception e){
////        System.out.println("Erreur sur la récupération des points : " + e.getMessage());
////    }

                try{
        villeProduit= SelectVilleById.launchSelectVilleById(String.valueOf(produit.getIdVilleDepart()));

    } catch(Exception e){
        System.out.println("Erreur sur la récupération des de la ville de depart  : " + e.getMessage());
    }
        countryProduct=new JLabel("Pays d'origine : "+villeProduit.getNomPays());



        try{
            categorie= SelectCategorieByID.launchSelectCategorieById(String.valueOf(produit.getIdCategorie()));
        } catch (Exception e) {
            System.out.println("Erreur sur la récupération du nom de la categorie  : " + e.getMessage());
        }
        categorieProduct= new JLabel("Categorie : "+ categorie.getNomCategorie());


        try{
            marqueProduit= SelectMarqueById.launchSelectMarqueById(String.valueOf(produit.getIdCategorie()));
        } catch (Exception e) {
            System.out.println("Erreur sur la récupération du nom de la categorie  : " + e.getMessage());
        }
        brandProdcut= new JLabel("Marque : "+ marqueProduit.getNomMarque() + "   ( Enseigne "+marqueProduit.getRse()+" )");

        //Positionnement des element------

        nameProduct.setBounds(200,250,100,40);
        nameProduct.setFont(Template.FONT_ECRITURE2);
        nameProduct.setForeground(Color.WHITE);
        panelProduct.add(nameProduct);

        scoreProduct.setBounds(35,340,50,50);
       // scoreProduct.setForeground(Color.decode(Template.COULEUR_SECONDAIRE));
        scoreProduct.setOpaque(false);
        panelProduct.add(scoreProduct);

        panelProduct.add(scoreProduct);
        footprintProduct.setBounds(110,340,100,50);

        footprintProduct.setFont(Template.FONT_ECRITURE);
        footprintProduct.setForeground(Color.WHITE);
        panelProduct.add(footprintProduct);

        priceProduct.setBounds(110,355,400,100);
        priceProduct.setFont(Template.FONT_ECRITURE);
        priceProduct.setForeground(Color.WHITE);
        panelProduct.add(priceProduct);

        countryProduct.setBounds(110,380,400,100);
        countryProduct.setFont(Template.FONT_ECRITURE);
        countryProduct.setForeground(Color.WHITE);
        panelProduct.add(countryProduct);

        categorieProduct.setBounds(110,405,400,100);
        categorieProduct.setFont(Template.FONT_ECRITURE);
        categorieProduct.setForeground(Color.WHITE);
        panelProduct.add(categorieProduct);

        brandProdcut.setBounds(110,430,400,100);
        brandProdcut.setFont(Template.FONT_ECRITURE);
        brandProdcut.setForeground(Color.WHITE);
        panelProduct.add(brandProdcut);


    //Code Nawel :
        JPanel infosMap = new JPanel();
        infosMap.setBackground(Color.decode(Template.COULEUR_SECONDAIRE));
        infosMap.setLayout(null);
        infosMap.setBounds(530, 450, 830, 180);
        mainPanel.add(infosMap);





        frame.setVisible(true);
    }


}
