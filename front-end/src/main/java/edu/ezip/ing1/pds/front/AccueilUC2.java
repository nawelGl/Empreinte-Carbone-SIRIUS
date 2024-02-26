package edu.ezip.ing1.pds.front;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//Pas d'extend de JFrame
public class AccueilUC2 implements ActionListener {
    //Déclaration de la frame
    JFrame accueilUC2;
    //Déclaration des boutons
    JButton boutonTrouverArticle;
    JButton boutonEmpreinteCarbone;

// Info pour la frame suivante
    String titre= "Home UC2 - Empreinte Carbone";;
    String titreLabelSecondaire ="Entrez la référence de votre produit pour savoir où il se trouve :";
    String titreHeader="Trouvez votre article :";

    //Constructeur :
    public AccueilUC2(){
        //Paramétrages de base :
        accueilUC2 = new JFrame();
        accueilUC2.setTitle("Home UC2");
        accueilUC2.setSize(Template.LONGUEUR, Template.LARGEUR);
        accueilUC2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        accueilUC2.setLocationRelativeTo(null);
        accueilUC2.setResizable(false);

        //Initialisation des boutons :
        boutonTrouverArticle = new JButton("Trouver un article");
        boutonEmpreinteCarbone = new JButton("Empreinte carbone d'un article");
        boutonTrouverArticle.addActionListener(this);
        boutonEmpreinteCarbone.addActionListener(this);

        //Ajout de panels :
        //----------panel header--------------
        Methodes.header(accueilUC2, "Retrouvez votre chemin !", 345);

        //----------panel principal (milieu)--------------
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBackground(Color.decode(Template.COULEUR_PRINCIPALE));

        //Ajout des boutons :
        boutonTrouverArticle.setBounds(170, 170, 280, 180);
        boutonEmpreinteCarbone.setBounds(570, 170, 280, 180);

        mainPanel.add(boutonTrouverArticle);
        mainPanel.add(boutonEmpreinteCarbone);
        accueilUC2.getContentPane().add(BorderLayout.CENTER,mainPanel);
        //-----------------------------------------------
        accueilUC2.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == boutonTrouverArticle){
            accueilUC2.dispose();
           ReferenceUC3 referenceUC3=new ReferenceUC3(titre,titreHeader,titreLabelSecondaire);
        }
    }
}
