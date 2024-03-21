package edu.ezip.ing1.pds.front;

import javax.swing.*;
import java.awt.*;

public class TestAffichageProduit {
    JFrame testAffichageProduit;

    JTextArea textArea;

    public TestAffichageProduit(){
        testAffichageProduit = new JFrame();
        testAffichageProduit.setTitle("Test affichage produit by reference");
        testAffichageProduit.setSize(Template.LONGUEUR, Template.LARGEUR);
        testAffichageProduit.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        testAffichageProduit.setLocationRelativeTo(null);
        testAffichageProduit.setResizable(false);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBackground(Color.decode(Template.COULEUR_PRINCIPALE));

        textArea = new JTextArea();
        textArea.setBounds(140, 70, 670, 600);
        textArea.setBackground(Color.decode(Template.COULEUR_PRINCIPALE));
        textArea.append(RechercheReference.product.toString());
        mainPanel.add(textArea);

        testAffichageProduit.getContentPane().add(mainPanel);

        testAffichageProduit.setVisible(true);
    }
}
