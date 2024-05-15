package edu.ezip.ing1.pds.front;

import edu.ezip.ing1.pds.business.dto.*;
import edu.ezip.ing1.pds.client.Categories.SelectCategorieByID;
import edu.ezip.ing1.pds.client.UC1.SelectMarqueById;
import edu.ezip.ing1.pds.client.UC1.SelectVilleById;
import edu.ezip.ing1.pds.client.UC2.SelectEmplacementById;
import edu.ezip.ing1.pds.client.UC2.SelectEtageById;
import edu.ezip.ing1.pds.client.UC2.SelectPointsByIdRayon;
import edu.ezip.ing1.pds.commons.Request;
import edu.ezip.ing1.pds.front.UC2.ProductMapping;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

import static java.lang.String.valueOf;

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

    private BufferedImage backgroundImage;
    private JPanel mapPanel;
    private PathPointChemin path;
    private Emplacement emplacement;
    private Etage etage;
    private boolean requestHaveFailed = false;


    public DetailsProduct(Produit produit) {
        this.produit = produit;
        frame = new JFrame();
        frame.setSize(Template.LONGUEUR, Template.LARGEUR);
        frame.setTitle("Détails Produit ");
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
        panelProduct.setBounds(35, 60, 500, 580);
        panelProduct.setBackground(Color.decode(Template.COULEUR_SECONDAIRE));
        mainPanel.add(panelProduct);

        panelPicture = new RoundedPanel(30, 30);
        panelPicture.setBounds(145, 45, 200, 200);
        panelProduct.add(panelPicture);

        // ---------Differents Label--------
        scoreProduct = MethodesFront.setlabelIconScore(produit.getScore());
        nameProduct = new JLabel(produit.getNomProduit() + " " + produit.getCouleur());
        footprintProduct = new JLabel(String.valueOf(new BigDecimal(produit.getEmpreinte()).setScale(1, RoundingMode.HALF_UP)
        ) + " gCO2e");

        priceProduct = new JLabel("Prix : " + String.valueOf(produit.getPrix()) + " €");

        //TODO:
//        try{
//        marque=SelectMarqueById.launchSelectMarquByRef(valueOf(produit.getReference());
////    } catch(Exception e){
////        System.out.println("Erreur sur la récupération des points : " + e.getMessage());
////    }

        try {
            villeProduit = SelectVilleById.launchSelectVilleById(String.valueOf(produit.getIdVilleDepart()));

        } catch (Exception e) {
            System.out.println("Erreur sur la récupération des de la ville de depart  : " + e.getMessage());
        }
        countryProduct = new JLabel("Pays d'origine : " + villeProduit.getNomPays());


        try {
            categorie = SelectCategorieByID.launchSelectCategorieById(String.valueOf(produit.getIdCategorie()));
            marqueProduit = SelectMarqueById.launchSelectMarqueById(String.valueOf(produit.getIdMarque()));


        } catch (Exception e) {
            System.out.println("Erreur sur la récupération du nom de la categorie ou de la marque  : " + e.getMessage());
        }
        categorieProduct = new JLabel("Categorie : " + categorie.getNomCategorie());


        try {
            marqueProduit = SelectMarqueById.launchSelectMarqueById(String.valueOf(produit.getIdCategorie()));
        } catch (Exception e) {
            System.out.println("Erreur sur la récupération du nom de la categorie  : " + e.getMessage());
        }
        brandProdcut = new JLabel("Marque : " + marqueProduit.getNomMarque() + "   ( Enseigne " + marqueProduit.getRse() + " )");

        //------------Positionnement des element------

        nameProduct.setBounds(200, 250, 100, 40);
        nameProduct.setFont(Template.FONT_ECRITURE2);
        nameProduct.setForeground(Color.WHITE);
        panelProduct.add(nameProduct);


        scoreProduct.setBounds(35, 340, 50, 50);
        scoreProduct.setOpaque(false);
        panelProduct.add(scoreProduct);

        panelProduct.add(scoreProduct);
        footprintProduct.setBounds(110, 340, 100, 50);

        footprintProduct.setFont(Template.FONT_ECRITURE);
        footprintProduct.setForeground(Color.WHITE);
        panelProduct.add(footprintProduct);

        priceProduct.setBounds(110, 355, 400, 100);
        priceProduct.setFont(Template.FONT_ECRITURE);
        priceProduct.setForeground(Color.WHITE);
        panelProduct.add(priceProduct);

        countryProduct.setBounds(110, 380, 400, 100);
        countryProduct.setFont(Template.FONT_ECRITURE);
        countryProduct.setForeground(Color.WHITE);
        panelProduct.add(countryProduct);

        categorieProduct.setBounds(110, 405, 400, 100);
        categorieProduct.setFont(Template.FONT_ECRITURE);
        categorieProduct.setForeground(Color.WHITE);
        panelProduct.add(categorieProduct);

        brandProdcut.setBounds(110, 430, 400, 100);
        brandProdcut.setFont(Template.FONT_ECRITURE);
        brandProdcut.setForeground(Color.WHITE);
        panelProduct.add(brandProdcut);


        //----- Code Nawel -----

        System.out.println("REFERENCE : " + produit.getReference());

        //=============Selection de l'emplacement via l'idEmplacement du produit===============
        try {
            emplacement = SelectEmplacementById.launchSelectEmplacementById(produit.getIdEmplacement());
        } catch (Exception e) {
            requestHaveFailed = true;
            JOptionPane.showMessageDialog(frame, "[ERREUR 404] Attention, une des valeurs que vous avez demandé n'a pas été toruvée.", "[ERROR 407] -Valeur nulle !", JOptionPane.ERROR_MESSAGE);

            System.out.println("Erreur sur l'idEmplacement : " + e.getMessage());
        }
        //======================================================================================

        //============Selection des points du chemin vers un rayon via l'id du rayon============
        if(!requestHaveFailed){
            try {
                path = SelectPointsByIdRayon.launchSelectPointsByIdRayon(valueOf(emplacement.getIdRayon()));
            } catch (Exception e) {
                requestHaveFailed = true;
                System.out.println("Erreur sur la récupération des points : " + e.getMessage());
            }
        }
        //=====================================================================================

        //=================Selection de l'étage via l'idEtage de l'emplacement==================
        if(!requestHaveFailed){
            try {
                etage = SelectEtageById.lauchSelectEtageById(valueOf(emplacement.getIdEtage()));
            } catch (Exception e) {
                requestHaveFailed = true;
                System.out.println("Erreur sur la récupération de l'étage : " + e.getMessage());
            }
        }

        JPanel infosMap = new JPanel();
        infosMap.setBackground(Color.decode(Template.COULEUR_SECONDAIRE));
        infosMap.setLayout(null);
        infosMap.setBounds(530, 460, 800, 170);
        //mainPanel.add(infosMap);

        //-------charger l'image de fond------
        try {
            backgroundImage = ImageIO.read(Objects.requireNonNull(MethodesFront.class.getResource("/mapV1.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //-----------------------------------

                if (path != null) {
            mapPanel = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    System.out.println("Dans override si path est pas null.");
                    super.paintComponent(g);
                    Graphics2D g2d = (Graphics2D) g;
                    // Épaisseur de la ligne (chemin)
                    g2d.setStroke(new BasicStroke(5));

                    // Dessiner l'image de fond
                    if (backgroundImage != null) {
                        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                    }
                    // Dessiner le chemin
                    g.setColor(Color.RED);
                    for (int i = 0; i < path.getPath().size() - 1; i++) {
                        PointChemin p1 = path.getPath().get(i);
                        PointChemin p2 = path.getPath().get(i + 1);
                        g.drawLine(p1.getCoordX(), p1.getCoordY(), p2.getCoordX(), p2.getCoordY());
                    }
                }
            };
            mapPanel.setLayout(null);
            mapPanel.setBounds(530, 60, 770, 580);
            mainPanel.add(mapPanel);
        } else {
        mapPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (backgroundImage != null) {
                    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };
        mapPanel.setLayout(null);
        mapPanel.setBounds(530, 60, 770, 580);
        mainPanel.add(mapPanel);
                }

        //Ajout des informmations de l'emplacement sur le panel du produit
        RoundedPanel panelEtage = new RoundedPanel(35, 35);
                JLabel labelEtage = new JLabel("Étage n°" + etage.getNumeroEtage());
                labelEtage.setFont(Template.FONT_ECRITURE2);
                panelEtage.add(labelEtage);
        panelEtage.setBounds(60, 515, 150, 37);
        panelEtage.setBackground(Color.WHITE);
        panelProduct.add(panelEtage);

        RoundedPanel panelRayon = new RoundedPanel(35, 35);
        JLabel labelRayon = new JLabel("Rayon n°" + emplacement.getIdRayon());
        labelRayon.setFont(Template.FONT_ECRITURE2);
        panelRayon.add(labelRayon);
        panelRayon.setBounds(280, 515, 150, 37);
        panelRayon.setBackground(Color.WHITE);
        panelProduct.add(panelRayon);

        frame.setVisible(true);
    }

}
