package edu.ezip.ing1.pds.front;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminMenu implements ActionListener {
    JFrame frame;
    JButton configButton;
    JButton UC3button;
    String titreHeader= "Menu Administrateur";
    int x= 550;
    JPanel panelCentre;
    ImageIcon configIcon;
    ImageIcon STATicon;


    public AdminMenu(){
        frame= new JFrame();
        frame.setTitle("Admin Menu");
        frame.setSize(Template.LONGUEUR,Template.LARGEUR);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        //-----panel header
        MethodesFront.header(frame,titreHeader,x);
        panelCentre = new JPanel(null);
        panelCentre.setBackground(Color.decode(Template.COULEUR_PRINCIPALE));



        configButton=new JButton("Configuration");
        configButton.setBounds(300, 300, 300, 200);
        UC3button= new JButton("Statistiques du magasin");
        UC3button.setBounds(700, 300, 300, 200);
        panelCentre.add(configButton);
        panelCentre.add(UC3button);
        frame.add(panelCentre);



        frame.setVisible(true);

    }


    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
