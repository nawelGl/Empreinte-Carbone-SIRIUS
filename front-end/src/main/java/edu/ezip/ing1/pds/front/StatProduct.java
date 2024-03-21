package edu.ezip.ing1.pds.front;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class StatProduct {

    JFrame statUC3;
    String chartTitle ="Vos statistiques";
    String[] labels = {"Avant", "Après"};
    String score;

    public StatProduct(int salesBefore, int salesAfter ){

        statUC3 = new JFrame("Statistiques");
        statUC3.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        statUC3.setLocationRelativeTo(null);
        statUC3.setResizable(false);
        statUC3.setSize(Template.LONGUEUR,Template.LARGEUR);

        //------------------panel header---------------------------
        Methodes.header(statUC3,"Vos statistiques par produit",525);

        //mainPanel(principal)
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.decode(Template.COULEUR_PRINCIPALE));

        //infoPanel

        JPanel infoPanel = new JPanel();
        infoPanel.setBackground(Color.white);
        JLabel scoreLabel = new JLabel();

        score ="E";
        switch (score) {
            case "A":
                scoreLabel.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/icon_A.png"))));
                break;
            case "B":
                scoreLabel.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/icon_B.png"))));
                break;
            case "C":
                scoreLabel.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/icon_C.png"))));
                break;
            case "D":
                scoreLabel.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/icon_D.png"))));
                break;
            case "E":
                scoreLabel.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/icon_E.png"))));
                break;
        }

        infoPanel.add(scoreLabel);

        //chartPanel
        JPanel chartPanel = new JPanel(new BorderLayout());

        double[] values = {salesBefore, salesAfter};
        Color[] colors = {Color.RED, Color.GREEN};
        StatTest barChart = new StatTest(chartTitle, labels, values, colors);
        chartPanel.add(BorderLayout.CENTER, barChart);



        chartPanel.setPreferredSize(new Dimension(400, 300));

        mainPanel.add(BorderLayout.WEST,infoPanel);
        mainPanel.add(BorderLayout.CENTER,chartPanel);
        statUC3.getContentPane().add(mainPanel);

        //-----------------------------------------------
        statUC3.setVisible(true);


    }




}
