package edu.ezip.ing1.pds.front;

import edu.ezip.ing1.pds.business.dto.*;
import edu.ezip.ing1.pds.client.Categories.SelectAllProductByCategorie;
import edu.ezip.ing1.pds.client.Categories.SelectCategorieByID;
import edu.ezip.ing1.pds.client.UC1.SelectMarqueById;
import edu.ezip.ing1.pds.front.UC1.ProductInfo;
import edu.ezip.ing1.pds.front.UC1.ProductInfoBis;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Objects;

public class CategoriesFrame implements ActionListener {
    JFrame categorieFrame = new JFrame();
    private int idSousCatA;
    private int idCat;
    Produits produits;
    Produit produit;
    private int y = 0;
    SousCategorieB sous_categorie_B;
    Marque marqueProduit = new Marque();

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
        panelPanelProduct.setLayout(new BoxLayout(panelPanelProduct, BoxLayout.Y_AXIS));
        panelPanelProduct.setBorder(new EmptyBorder(20, 50, 20, 50));

        // Créer le JScrollPane et y ajouter panelPanelProduct
        JScrollPane scrollPane = new JScrollPane(panelPanelProduct);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        // Ajouter le JScrollPane au mainPanel
        mainPanel.add(BorderLayout.CENTER, scrollPane);

        //-------- faire un foreach pour faire un panel pour chaque produit avec ses infos
        for (int i = 0; i < produits.getProduits().size(); i++){
            JPanel productPanel = new JPanel();
            productPanel.setLayout(null);
            productPanel.setBackground(Color.WHITE);
            productPanel.setBounds(110, 15+y, 1200, 200);
            panelPanelProduct.add(productPanel);
            y+=220;
            produit = produits.getProduits().get(i);

            ImageIcon pictureProduct = new ImageIcon(Objects.requireNonNull(MethodesFront.class.getResource("/"+produit.getReference()+".png")));
            Image image = pictureProduct.getImage();
            Image nouvelleImage = image.getScaledInstance(150, 154, Image.SCALE_SMOOTH);

            ImageIcon nouvelleIcon = new ImageIcon(nouvelleImage);

            JLabel pictureProductLabel = new JLabel(nouvelleIcon);
            pictureProductLabel.setBounds(20,20,150,154);
            productPanel.add(pictureProductLabel);

            JLabel nomProduit = new JLabel();
            nomProduit.setText(produit.getNomProduit() + " (référence : " + produit.getReference() + ")");
            nomProduit.setFont(Template.FONT_ECRITURE2);
            nomProduit.setBounds(220, 15, 400, 40);
            productPanel.add(nomProduit);

            try {
                marqueProduit = SelectMarqueById.launchSelectMarqueById(String.valueOf(produit.getIdMarque()));
            } catch (Exception e) {
                System.out.println("Erreur sur la récupération du nom de la categorie ou de la marque  : " + e.getMessage());
            }

            JLabel marque = new JLabel();
            marque.setText("Marque : " + marqueProduit.getNomMarque());
            marque.setFont(Template.FONT_ECRITURE2);
            marque.setBounds(220, 45, 200, 40);
            productPanel.add(marque);

            JLabel taille = new JLabel();
            taille.setText("Taille : " + produit.getTaille());
            taille.setFont(Template.FONT_ECRITURE2);
            taille.setBounds(220, 75, 200, 40);
            productPanel.add(taille);

            JLabel prix = new JLabel();
            prix.setText("Prix : " + produit.getPrix() + "€");
            prix.setFont(Template.FONT_ECRITURE2);
            prix.setBounds(220, 105, 200, 40);
            productPanel.add(prix);

            JButton productInfoButton = new JButton("Informations produit");
            productInfoButton.setBounds(1100, 140, 170, 37);
            productInfoButton.addActionListener(this);
            productPanel.add(productInfoButton);

        }

        categorieFrame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof JButton) {
            JButton button = (JButton) e.getSource();
            ProductInfoBis productInfo = new ProductInfoBis(produit, idCat, idSousCatA, sous_categorie_B.getIdSousCategorieB());
            categorieFrame.dispose();
        }
    }
}
