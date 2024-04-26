package edu.ezip.ing1.pds.front;

import edu.ezip.ing1.pds.front.UC2.PathManagement;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ConfigurationFrame implements ActionListener {
    JFrame configurationFrame;
    String titreHeader = "Options de configuration";
    JButton boutonSuggestions;
    JButton boutonConfigurationChemins;

    public ConfigurationFrame(){
        configurationFrame = new JFrame();
        configurationFrame.setTitle("Options de configuration");
        configurationFrame.setSize(Template.LONGUEUR,Template.LARGEUR);
        configurationFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        configurationFrame.setLocationRelativeTo(null);
        configurationFrame.setResizable(false);

        //======== header =========
        MethodesFront.header(configurationFrame, titreHeader,550);
        //=========================

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(Color.decode(Template.COULEUR_PRINCIPALE));
        mainPanel.setLayout(null);
        configurationFrame.getContentPane().add(mainPanel);

        boutonSuggestions = new JButton("Charger les empreintes carbone");
        boutonSuggestions.addActionListener(this);
        boutonSuggestions.setBounds(300, 230, 300, 230);
        mainPanel.add(boutonSuggestions);

        boutonConfigurationChemins =  new JButton("Configurez les chemins de votre magasin");
        boutonConfigurationChemins.addActionListener(this);
        boutonConfigurationChemins.setBounds(800, 230, 300, 230);
        mainPanel.add(boutonConfigurationChemins);

        configurationFrame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e){
        if(e.getSource() == boutonConfigurationChemins){
            PathManagement pathManagement = new PathManagement();
            configurationFrame.dispose();
        }
    }
}
