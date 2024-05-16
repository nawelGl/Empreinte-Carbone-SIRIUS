package edu.ezip.ing1.pds.front.UC3;



import edu.ezip.ing1.pds.business.dto.VenteScore;
import edu.ezip.ing1.pds.business.dto.VenteScores;
import edu.ezip.ing1.pds.client.UC3.SelectVenteByScore;
import edu.ezip.ing1.pds.commons.Request;
import edu.ezip.ing1.pds.front.MethodesFront;
import edu.ezip.ing1.pds.front.Template;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class StatScore extends JFrame {

    JFrame scoreFrame;
    static VenteScores VentesA;
    static VenteScores VentesB;
    static VenteScores VentesC;
    static VenteScores VentesD;
    static VenteScores VentesE;


    public StatScore() {

        try {
            Request request = new Request();
            request.setRequestContent("A");
            VentesA= SelectVenteByScore.launchSelectVenteByScore(request);
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

        List<String> dateList = new ArrayList<>(Arrays.asList("2023-05", "2023-06","2023-07","2023-08",
                "2023-09","2023-10","2023-11","2023-12","2024-01","2024-02","2024-03","2024-04"));



        ArrayList<Integer> listA = generateSalesList(VentesA, dateList);
        ArrayList<Integer> listB = generateSalesList(VentesB, dateList);
        ArrayList<Integer> listC = generateSalesList(VentesC, dateList);
        ArrayList<Integer> listD = generateSalesList(VentesD, dateList);
        ArrayList<Integer> listE = generateSalesList(VentesE, dateList);


//
//        System.out.println("~~~~~~~~~confirm list ~~~~~~~~~~~");
//        System.out.println(listA);
//        System.out.println(listB);
//        System.out.println(listC);
//        System.out.println(listD);
//        System.out.println(listE);





        // installation de frame
        scoreFrame = new JFrame("Statisque - Stat par score");
        scoreFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        scoreFrame.setResizable(false);
        scoreFrame.setSize(Template.LONGUEUR, Template.LARGEUR);
        scoreFrame.setLocationRelativeTo(null);


        //-------------------panel header-------------
        MethodesFront.header(scoreFrame,"Ventes par score des produits",525);


        // main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBackground(Color.decode(Template.COULEUR_PRINCIPALE));


        //chartPanel setting
        DrawSalesGraph chartPanel = new DrawSalesGraph(listA,listB,listC,listD,listE);
        chartPanel.setBackground(Color.white);
        chartPanel.setBounds(350,50,800,600);


        //--------------ajout-----------------
        mainPanel.add(chartPanel);
        scoreFrame.add(mainPanel);

        scoreFrame.setVisible(true);



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

    private ArrayList<Integer> generateSalesList(VenteScores venteScores, List<String> dateList) {
        ArrayList<Integer> salesList = new ArrayList<>();
        for (String date : dateList) {
            boolean found = false;
            for (VenteScore venteScore : venteScores.getVenteScores()) {
                if (venteScore.getMonth().equals(date)) {
                    salesList.add(venteScore.getSum());
                    found = true;
                    break;
                }
            }
            if (!found) {
                salesList.add(0);
            }
        }
        return salesList;
    }



}
