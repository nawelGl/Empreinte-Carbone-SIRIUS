package edu.ezip.ing1.pds.front;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CategoriesTemplate{
    //Frame that will display all the first level categories to the user.

    JFrame categorieFrame;
    JPanel mainPanel;
    GridBagConstraints gbc;
    //Size for buttons :
    int width = 250;
    int height = 100;
    

    public CategoriesTemplate(){
        categorieFrame = new JFrame();
        categorieFrame.setSize(Template.LONGUEUR, Template.LARGEUR);
        categorieFrame.setTitle("Choisissez une catégorie.");
        categorieFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        categorieFrame.setLocationRelativeTo(null);
        categorieFrame.setResizable(false);

        //Adding the main panel :
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        mainPanel.setBackground(Color.decode(Template.COULEUR_PRINCIPALE));

        // Marge de 10 pixels autour de chaque bouton
        Insets insets = new Insets(50, 10, 50, 10);
        gbc = new GridBagConstraints();
        gbc.insets = insets;

        categorieFrame.getContentPane().add(mainPanel);
        categorieFrame.setVisible(true);
    }

    public static void setButtonStyle(JButton button, int width, int height){
        button.setPreferredSize(new Dimension(width, height));
        button.setFont(new Font("Arial", Font.BOLD, 18));
        button.setForeground(Color.decode(Template.COUELUR_SECONDAIRE));
    }
}
