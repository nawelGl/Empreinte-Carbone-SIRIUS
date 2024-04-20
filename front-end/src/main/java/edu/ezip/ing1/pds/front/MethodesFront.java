package edu.ezip.ing1.pds.front;

import edu.ezip.ing1.pds.business.dto.Score;
import edu.ezip.ing1.pds.business.dto.Scores;
import edu.ezip.ing1.pds.client.UC1.SelectAllScore;
import edu.ezip.ing1.pds.commons.Request;
import edu.ezip.ing1.pds.front.UC1.ProductInfo;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

//Classe qui contient des méthodes pour initialiser les principaux élémenst des frames.


public class MethodesFront {
    // Header for frames
    public static void header(JFrame frame, String titre, int x) {
        // ----------panel header : Logo + label titre--------------
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(null);
        JLabel titreLabel = new JLabel(titre);
        titreLabel.setBounds(x, 13, 600, 50);

        ImageIcon logo = new ImageIcon(Objects.requireNonNull(MethodesFront.class.getResource("/logo.png")));

        JLabel logoLabel = new JLabel(logo);
        logoLabel.setBounds(10, 8, 70, 70);
        headerPanel.add(logoLabel);


        ImageIcon homeIcon= new ImageIcon(Objects.requireNonNull(MethodesFront.class.getResource("/home.png")));
        JButton homeButton=new JButton(homeIcon);
                //new JButton(homeIcon);
        homeButton.setBounds(1320,20,60,60);
        homeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EcranAcceuil ecranAcceuil=new EcranAcceuil();
                frame.dispose();

            }
        });
        headerPanel.add(homeButton);

//
        JPanel nomAppPanel = new JPanel();
        nomAppPanel.setBounds(100,25,200,35);
        nomAppPanel.setBackground(Color.decode(Template.COULEUR_SECONDAIRE));
        headerPanel.add(nomAppPanel);

        JLabel nomAppLabel = new JLabel("EPIGREEN-SHOP");
        Font font = new Font(Template.POLICE, Font.BOLD, 20);
        nomAppLabel.setFont(font);
        nomAppLabel.setForeground(Color.WHITE);
        nomAppPanel.add(nomAppLabel);



        titreLabel.setFont(new Font(Template.POLICE, Font.BOLD, 24));
        titreLabel.setBorder(new EmptyBorder(25, 20, 0, 0));
        headerPanel.setBackground(Color.decode(Template.COULEUR_HEADER));
        headerPanel.setPreferredSize(new Dimension(Template.LONGUEUR, Template.HAUTEUR_HEADER));

        headerPanel.add(titreLabel);
        frame.getContentPane().add(BorderLayout.NORTH, headerPanel);
        // -----------------------------------------------
    }

    //Centrer les titres dans les panels :
    public static void centerLabel(JLabel label, int hauteurPanel){
        int labelWidth = label.getPreferredSize().width;
        int labelHeight = label.getPreferredSize().height;
        int panelWidth = Template.LONGUEUR;
        int panelHeight = hauteurPanel;
        int x = (panelWidth - labelWidth) / 2;
        int y = (panelHeight - labelHeight) / 2;

        // Définir les coordonnées et la taille du label
        label.setBounds(x, y, labelWidth, labelHeight);
    }

   //Source => https://fr.martech.zone/calculate-great-circle-distance/
   public static double getDistanceBetweenPointsNew(double latitude1, double longitude1, double latitude2, double longitude2) {
       double theta = longitude1 - longitude2;
       double distance = 60 * 1.1515 * (180/Math.PI) * Math.acos(
               Math.sin(latitude1 * (Math.PI/180)) * Math.sin(latitude2 * (Math.PI/180)) +
                       Math.cos(latitude1 * (Math.PI/180)) * Math.cos(latitude2 * (Math.PI/180)) * Math.cos(theta * (Math.PI/180))
       );

       return Math.round(distance * 1.609344);
   }

    public static double carbonFootPrintCalcul(double coordLat1, double coordLat2, double coordLong1, double coordLong2, double coeff,double poids ){
        double distance=  getDistanceBetweenPointsNew(coordLat1,coordLong1,coordLat2, coordLong2);
        double carbonFootPrint;
        carbonFootPrint=distance*coeff*(poids/1000)/1000;
        BigDecimal bd = new BigDecimal(carbonFootPrint).setScale(1, RoundingMode.HALF_UP);
        return bd.doubleValue();
       // return carbonFootPrint;

    }

    public static String attributeLetterScore(double carbonFootPrint) {
        // Appeler la méthode pour récupérer les bornes
        Scores scores = null;
        try {
            scores = SelectAllScore.launchSelectAllScore();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        // Vérifier si les bornes ont été récupérées avec succès
        if (scores != null) {
            // Parcourir les scores pour trouver la borne correspondante
            for (Score score : scores.getScores()) {
                if (carbonFootPrint >= score.getborneInf() && carbonFootPrint < score.getborneSup()) {
                    return score.getlettreScore();

                } System.out.println(score.getlettreScore());
            }
        }

        // Si aucune borne correspondante n'est trouvée, retourner une erreur
        System.out.println("FAIL SCORE");
        return "Erreur hors borne";
    }


    public static JLabel setlabelIconScore(String scoreLetter) {
        JLabel label = new JLabel(); // Crée un JLabel pour contenir l'icône


        switch (scoreLetter) {
            case "A":
                label.setIcon(new ImageIcon(Objects.requireNonNull(ProductInfo.class.getResource("/icon_A.png"))));
                break;
            case "B":
                label.setIcon(new ImageIcon(Objects.requireNonNull(ProductInfo.class.getResource("/icon_B.png"))));
                break;
            case "C":
                label.setIcon(new ImageIcon(Objects.requireNonNull(ProductInfo.class.getResource("/icon_C.png"))));
                break;
            case "D":
                label.setIcon(new ImageIcon(Objects.requireNonNull(ProductInfo.class.getResource("/icon_D.png"))));
                break;
            case "E":
                label.setIcon(new ImageIcon(Objects.requireNonNull(ProductInfo.class.getResource("/icon_E.png"))));
                break;

        }
        label.setBackground(Color.WHITE);
        label.setOpaque(true);

        return label;
    }



}