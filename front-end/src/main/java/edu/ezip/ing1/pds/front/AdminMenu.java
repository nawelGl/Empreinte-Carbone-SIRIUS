package edu.ezip.ing1.pds.front;

import edu.ezip.ing1.pds.front.UC3.AccueilUC3;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class AdminMenu implements ActionListener {
    JFrame frame;
    JButton configButton;
    JButton UC3button;
    String titreHeader= "Menu Administrateur";
    int x= 580;
    JPanel panelCentre;
   JButton boutonArriere1;



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

        //Pas encore au point niveau esthetique a voir 
//        boutonArriere1=new JButton();
//        boutonArriere1.setBounds(280, 250, 300, 230);
//        boutonArriere1.setBackground(Color.decode(Template.COULEUR_SECONDAIRE));
//       // boutonArriere1.setHorizontalAlignment(SwingConstants.CENTER); // Centre le texte horizontalement
//        boutonArriere1.setForeground(Color.BLACK);
//        boutonArriere1.setFocusPainted(false);
//        boutonArriere1.setEnabled(false);
//        panelCentre.add(boutonArriere1);

        configButton = new JButton("<html><center><br><img src='" +
                getClass().getResource("/configGRANDE.png") +
                "'><br><br><font size='5' face='Avenir'>Configuration</font></center></html>");
        configButton.setBounds(300, 230, 300, 230);
        UC3button= new JButton("<html><center><br><img src='" +
                getClass().getResource("/statBisGrande.png") +
                "'><br><br><font size='5' face='Avenir'>Statistique du magasin</font></center></html>");

        UC3button.setBounds(800, 230, 300, 230);
        configButton.addActionListener(this);
        UC3button.addActionListener(this);
        panelCentre.add(configButton);
        panelCentre.add(UC3button);
        frame.add(panelCentre);




        frame.setVisible(true);

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==UC3button){
            frame.dispose();
            AccueilUC3 accueilUC3=new AccueilUC3();
        }

    }
}
