package edu.ezip.ing1.pds.front;

import javax.swing.*;
import java.awt.*;

public class StatProduct {

    JFrame statUC3;
    String chartTitle ="Vos statistiques";
    String[] labels = {"Avant", "Après"};

    public StatProduct(int salesBefore, int salesAfter ){

        statUC3 = new JFrame("Statistiques");
        statUC3.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        statUC3.setLocationRelativeTo(null);
        statUC3.setResizable(false);
        statUC3.setSize(Template.LONGUEUR,Template.LARGEUR);

        //------------------panel header---------------------------
        Methodes.header(statUC3,"Vos statistiques par produit",525);

        //mainPanel(principal)
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBackground(Color.decode(Template.COULEUR_PRINCIPALE));

        //infoPanel

        JPanel infoPanel = new JPanel();
        JLabel testLabel = new JLabel("Votre produit est");
        infoPanel.add(testLabel);



        //chartPanel
        JPanel chartPanel = new JPanel(new BorderLayout());

        double[] values = {salesBefore, salesAfter};
        Color[] colors = {Color.RED, Color.GREEN};
        StatTest barChart = new StatTest(chartTitle, labels, values, colors);
        chartPanel.add(BorderLayout.CENTER,barChart);



        chartPanel.setPreferredSize(new Dimension(400, 300));

        statUC3.getContentPane().add(BorderLayout.CENTER, infoPanel);
        statUC3.getContentPane().add(BorderLayout.CENTER,chartPanel);


        //-----------------------------------------------
        statUC3.setVisible(true);


    }


}
