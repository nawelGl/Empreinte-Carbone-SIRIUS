package edu.ezip.ing1.pds.front;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CategoriesTemplate implements ActionListener{
    //Frame that will display all the first level categories to the user.

    JFrame categorieFrame;
    JPanel mainPanel;
    JButton backButton;
    GridBagConstraints gbc;
    //Size for buttons :
    int width = 260;
    int height = 100;
    

    public CategoriesTemplate(){
        categorieFrame = new JFrame();
        categorieFrame.setSize(Template.LONGUEUR, Template.LARGEUR);
        categorieFrame.setTitle("Choisissez une catégorie.");
        categorieFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        categorieFrame.setLocationRelativeTo(null);
        categorieFrame.setResizable(false);

        //Adding a header :
        Methodes.header(categorieFrame, "Choisissez une catégorie", 540);

        //Adding the main panel :
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        mainPanel.setBackground(Color.decode(Template.COULEUR_PRINCIPALE));

        // Marge de 10 pixels autour de chaque bouton
        Insets insets = new Insets(50, 10, 50, 10);
        gbc = new GridBagConstraints();
        gbc.insets = insets;

        backButton = new JButton("Retour à l'accueil");
        backButton.addActionListener(this);

        JPanel panelRetour = new JPanel();
        categorieFrame.getContentPane().add(panelRetour, BorderLayout.SOUTH);
        panelRetour.setLayout(new FlowLayout());
        panelRetour.setBackground(Color.decode(Template.COULEUR_PRINCIPALE));
        panelRetour.add(backButton);

        categorieFrame.getContentPane().add(mainPanel);
        categorieFrame.setVisible(true);
    }

    public static void setButtonStyle(JButton button, int width, int height){
        button.setPreferredSize(new Dimension(width, height));
        button.setFont(new Font(Template.POLICE, Font.BOLD, 18));
        button.setForeground(Color.decode(Template.COUELUR_SECONDAIRE));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == backButton){
            categorieFrame.dispose();
            EcranAcceuil ecranAcceuil = new EcranAcceuil();
        }
    }
}
