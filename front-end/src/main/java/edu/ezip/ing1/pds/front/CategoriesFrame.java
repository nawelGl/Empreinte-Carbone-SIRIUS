package edu.ezip.ing1.pds.front;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class CategoriesFrame {
    JFrame categorieFrame = new JFrame();

    public CategoriesFrame(){
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
        titleLabel.setText("Produits correspondants à [completer categories] :");
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

        //-------- faire un foreach pour faire un panel pour chaque produit avec ses infos

        categorieFrame.setVisible(true);
    }
}
