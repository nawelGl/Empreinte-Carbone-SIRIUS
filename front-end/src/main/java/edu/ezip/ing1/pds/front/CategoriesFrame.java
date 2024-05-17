package edu.ezip.ing1.pds.front;

import edu.ezip.ing1.pds.business.dto.Produit;
import edu.ezip.ing1.pds.business.dto.Produits;
import edu.ezip.ing1.pds.business.dto.SousCategorieB;
import edu.ezip.ing1.pds.business.dto.SousCategoriesB;
import edu.ezip.ing1.pds.client.Categories.SelectAllProductByCategorie;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.IOException;

public class CategoriesFrame {
    JFrame categorieFrame = new JFrame();
    private int idSousCatA;
    private int idCat;
    Produits produits;
    private int y = 0;
    SousCategorieB sous_categorie_B;

    public CategoriesFrame(SousCategorieB sous_categorie_B, int isSousCatA, int idCat){
        this.sous_categorie_B = sous_categorie_B;
        this.idSousCatA = isSousCatA;
        this.idCat = idCat;

        String parametres = String.valueOf(idCat) + "," + String.valueOf(idSousCatA) + "," + String.valueOf(sous_categorie_B.getIdSousCategorieB());
        try{
            produits = SelectAllProductByCategorie.launchSelectAllProductsByCategorie(parametres);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println(produits.toString());

        categorieFrame.setTitle("Choisissez un produit");
        categorieFrame.setSize(Template.LONGUEUR, Template.LARGEUR);
        categorieFrame.setResizable(false);
        categorieFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        categorieFrame.setLocationRelativeTo(null);

        //---------- header panel --------------
        String titreHeader = "Choix de la catégorie de votre produit";
        MethodesFront.header(categorieFrame, titreHeader, 490);
        //------------------------------------

        //-------- title panel ---------
        JPanel mainTitre = new JPanel();
        mainTitre.setLayout(null);
        mainTitre.setPreferredSize(new Dimension(Template.LONGUEUR, 100));
        mainTitre.setBackground(Color.decode(Template.COULEUR_PRINCIPALE));
        RoundedPanel titlePanel = new RoundedPanel(40, 40);
        titlePanel.setBackground(Color.decode(Template.COULEUR_SECONDAIRE));
        JLabel titleLabel = new JLabel();
        titleLabel.setText("Produits correspondants à " + sous_categorie_B.getNomSouscatB() + " :");
        titleLabel.setFont(Template.FONT_TITRES);
        titleLabel.setForeground(Color.WHITE);
        titlePanel.add(titleLabel);
        titlePanel.setBounds(430, 30, 580, 40);
        mainTitre.add(titlePanel);

        //------- main panel that contains products -------
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(Color.decode(Template.COULEUR_PRINCIPALE));
        mainPanel.setLayout(new BorderLayout());
        categorieFrame.getContentPane().add(BorderLayout.CENTER, mainPanel);
        mainPanel.add(BorderLayout.NORTH, mainTitre);

        JPanel panelPanelProduct = new JPanel();
        panelPanelProduct.setBackground(Color.decode(Template.COULEUR_PRINCIPALE));
        panelPanelProduct.setLayout(null);
        // Créer un JScrollPane et y ajouter le panel
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        // Ajouter le JScrollPane au cadre
        panelPanelProduct.add(scrollPane);
        mainPanel.add(panelPanelProduct);

        //-------- faire un foreach pour faire un panel pour chaque produit avec ses infos
        for (int i = 0; i < produits.getProduits().size(); i++){
            JPanel productPanel = new JPanel();
            productPanel.setBackground(Color.WHITE);
            productPanel.setBounds(110, 20+y, 1200, 200);
            panelPanelProduct.add(productPanel);
            y+=220;
        }

        categorieFrame.setVisible(true);
    }
}
