package edu.ezip.ing1.pds.front;

import javax.swing.*;
import java.awt.*;

public class CategoriesFrame {
    JFrame categorieFrame = new JFrame();

    public CategoriesFrame(){
        categorieFrame.setTitle("Choisissez un produit");
        categorieFrame.setSize(Template.LONGUEUR, Template.LARGEUR);
        categorieFrame.setResizable(false);
        categorieFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        categorieFrame.setLocationRelativeTo(null);

        //-------- title panel ---------
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(Color.decode(Template.COULEUR_SECONDAIRE));
        titlePanel.setPreferredSize(new Dimension(Template.LONGUEUR, 100));
        categorieFrame.add(BorderLayout.CENTER, titlePanel);

        categorieFrame.setVisible(true);
    }
}
