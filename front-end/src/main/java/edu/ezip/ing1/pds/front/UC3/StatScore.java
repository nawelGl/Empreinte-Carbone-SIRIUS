package edu.ezip.ing1.pds.front.UC3;


import edu.ezip.ing1.pds.business.dto.VenteScores;
import edu.ezip.ing1.pds.client.UC3.SelectVenteByScore;
import edu.ezip.ing1.pds.commons.Request;
import edu.ezip.ing1.pds.front.MethodesFront;
import edu.ezip.ing1.pds.front.Template;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.IOException;

import static java.lang.String.valueOf;

public class StatScore extends JFrame {

    JFrame salesFrame;
    static VenteScores VenteA;




    private JLabel text;
    public StatScore() {

        try {
            String score ="A";
            Request request = new Request();
            request.setRequestContent(score);
            VenteA= SelectVenteByScore.launchSelectVenteByScore(request);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }



        // installation de frame
        salesFrame = new JFrame("Statisque - Stat par score");
        salesFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        salesFrame.setResizable(false);
        salesFrame.setSize(Template.LONGUEUR, Template.LARGEUR);
        salesFrame.setLocationRelativeTo(null);


        //-------------------panel header-------------
        MethodesFront.header(salesFrame,"Ventes par score des produits",525);


        // main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBackground(Color.decode(Template.COULEUR_PRINCIPALE));



        //beforPanel setting
        JPanel beforePanel = new JPanel(null);
        beforePanel.setBackground(Color.white);
        beforePanel.setBounds(50,50,600,600);

        //afterPanel setting
        JPanel afterPanel = new JPanel(null);
        afterPanel.setBackground(Color.white);
        afterPanel.setBounds(750,50,600,600);


        //--------------ajout-----------------
        mainPanel.add(beforePanel);
        mainPanel.add(afterPanel);
        salesFrame.add(mainPanel);

        salesFrame.setVisible(true);



    }
}
