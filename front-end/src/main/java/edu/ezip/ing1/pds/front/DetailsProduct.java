package edu.ezip.ing1.pds.front;

import edu.ezip.ing1.pds.business.dto.Produit;

import javax.swing.*;
import java.awt.*;

public class DetailsProduct {

    JFrame frame;
    JPanel mainPanel;
    Produit produit;

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
        MethodesFront.header(frame, titreHeader, 520);
        //------------------------------------


        //-------main panel-------------------
        mainPanel = new JPanel();
        mainPanel.setBackground(Color.decode(Template.COULEUR_PRINCIPALE));
        mainPanel.setLayout(null);
        frame.add(mainPanel);




        frame.setVisible(true);
    }


}
