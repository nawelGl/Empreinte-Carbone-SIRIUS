package edu.ezip.ing1.pds.front;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;
import javax.swing.*;

public class ProductInfo implements ActionListener {

    JFrame productInfoFrame;
    private String productName = RechercheReference.product.getNomProduit();
    private String productScore = RechercheReference.product.getScore();
    private String productColor = RechercheReference.product.getCouleur();
    private Float productEmpreinte= RechercheReference.product.getEmpreinte();


    public ProductInfo(){
        //TODO: Requetes sur la BD qui recupere suggestions "SELECT ...."


        productInfoFrame  = new JFrame();
        productInfoFrame.setSize(Template.LONGUEUR, Template.LARGEUR);
        productInfoFrame.setTitle("Faites vous un achat responsable?");
        productInfoFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        productInfoFrame.setLocationRelativeTo(null);
        productInfoFrame.setResizable(false);


        //----------panel header--------------
        String titreHeader = "Produit :  \"" + productName + " " + productColor + "\"";
       // String titreHeader="Produit nom couleur";

        Methodes.header(productInfoFrame, titreHeader, 540);


        //---------panel principal-----------
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(Color.decode(Template.COULEUR_PRINCIPALE));
        mainPanel.setLayout(null);
        productInfoFrame.getContentPane().add(mainPanel);

        //-------panel produit choisi---------
        JPanel productPanel = new JPanel();
        productPanel.setLayout(null);
        productPanel.setBounds(180,60,1030,300);
        productPanel.setBackground(Color.WHITE);

        JLabel label1 = new JLabel("Votre produit ");
        label1.setBounds(450,5,100,50);
        productPanel.add(label1);


        JLabel priceLabel = new JLabel("Prix: xxxx € ");
        priceLabel.setBounds(600,70,300,50);
        productPanel.add(priceLabel);

        JLabel empreinteLabel = new JLabel("Empreinte Carbon: "+  productEmpreinte +" kgCO2e ");
        empreinteLabel.setBounds(600,120,300,50);
        productPanel.add(empreinteLabel);


        JLabel scoreLabel = new JLabel("Carbon score: " );
        scoreLabel.setBounds(600,160,200,50);
        productPanel.add(scoreLabel);

        JLabel IconScore = labelIconScore(productScore);
        IconScore.setBounds(760,160,80,80);
        productPanel.add(IconScore);

        JButton UC2button= new JButton("Trouver l'article dans le magasin >> ");
        UC2button.setBounds(730,270,300,30);
        productPanel.add(UC2button);
        UC2button.setOpaque(false);
        mainPanel.add(productPanel);

        //-------panel suggestion---------

        JPanel suggestionPanel = new JPanel();
        suggestionPanel.setLayout(null);
        suggestionPanel.setBounds(180,400,1030,300);
        suggestionPanel.setBackground(Color.WHITE);
        mainPanel.add(suggestionPanel);








        productInfoFrame.setVisible(true);

    }

    public static JLabel labelIconScore(String scoreLetter) {
        JLabel label = new JLabel(); // Crée un JLabel pour contenir l'icône



        switch (scoreLetter) {
            case "A":
                label.setIcon(new ImageIcon(Objects.requireNonNull(ProductInfo.class.getResource("/icon_A.png"))));
                break;
            case "B":
                label.setIcon(new ImageIcon(Objects.requireNonNull(ProductInfo.class.getResource("/icon_B.png"))));
                break;
            case "C":
                label.setIcon(new ImageIcon(Objects.requireNonNull(ProductInfo.class.getResource("/icon_C.png"))));
                break;
            case "D":
                label.setIcon(new ImageIcon(Objects.requireNonNull(ProductInfo.class.getResource("/icon_D.png"))));
                break;
            case "E":
                label.setIcon(new ImageIcon(Objects.requireNonNull(ProductInfo.class.getResource("/icon_E.png"))));
                break;

        }
        label.setBackground(Color.WHITE);
        label.setOpaque(true);

        return label;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        productInfoFrame.dispose();
        ProductMapping productMapping=new ProductMapping();

    }
}
