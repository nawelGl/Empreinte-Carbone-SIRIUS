package edu.ezip.ing1.pds.front.UC3;


import edu.ezip.ing1.pds.business.dto.BestSeller;
import edu.ezip.ing1.pds.business.dto.VenteScore;
import edu.ezip.ing1.pds.business.dto.VenteScores;
import edu.ezip.ing1.pds.client.UC3.SelectVenteByScore;
import edu.ezip.ing1.pds.commons.Request;
import edu.ezip.ing1.pds.front.MethodesFront;
import edu.ezip.ing1.pds.front.Template;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.lang.String.valueOf;

public class StatScore extends JFrame {

    JFrame salesFrame;
    static VenteScores VentesA;
    static VenteScores VentesB;
    static VenteScores VentesC;
    static VenteScores VentesD;
    static VenteScores VentesE;




    private JLabel text;
    public StatScore() {

        try {
            Request request = new Request();
            request.setRequestContent("A");
            VentesA= SelectVenteByScore.launchSelectVenteByScore(request);
            List<VenteScore> saleslisteA = VentesA.getVenteScores();
            System.out.println(saleslisteA.toString());

            request.setRequestContent("B");
            VentesB= SelectVenteByScore.launchSelectVenteByScore(request);
            request.setRequestContent("C");
            VentesC= SelectVenteByScore.launchSelectVenteByScore(request);
            request.setRequestContent("D");
            VentesD= SelectVenteByScore.launchSelectVenteByScore(request);
            request.setRequestContent("E");
            VentesE= SelectVenteByScore.launchSelectVenteByScore(request);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        List<String> dateList = new ArrayList<>();
        dateList.add("2023-04");




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






        //chartPanel setting
        JPanel chartPanel = new JPanel(null);
        chartPanel.setBackground(Color.white);
        chartPanel.setBounds(350,50,800,600);


        //--------------ajout-----------------
        mainPanel.add(chartPanel);
        salesFrame.add(mainPanel);

        salesFrame.setVisible(true);



    }
    private ImageIcon selectScoreImage(String score) {
        ImageIcon icon;
        switch (score) {
            case "A":
                icon = new ImageIcon(getClass().getResource("/icon_A.png"));
                break;
            case "B":
                icon = new ImageIcon(getClass().getResource("/icon_B.png"));
                break;
            case "C":
                icon = new ImageIcon(getClass().getResource("/icon_C.png"));
                break;
            case "D":
                icon = new ImageIcon(getClass().getResource("/icon_D.png"));
                break;
            case "E":
                icon = new ImageIcon(getClass().getResource("/icon_E.png"));
                break;
            default:
                icon = null;
                break;
        }
        return icon;
    }

}
