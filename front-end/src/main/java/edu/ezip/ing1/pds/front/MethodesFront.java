package edu.ezip.ing1.pds.front;

import edu.ezip.ing1.pds.business.dto.*;

import edu.ezip.ing1.pds.client.UC1.SelectAllScore;
import edu.ezip.ing1.pds.client.UC1.SelectMarqueById;
import edu.ezip.ing1.pds.front.UC1.ProductInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;
import static java.lang.String.valueOf;

public class MethodesFront {
    private final static String LoggingLabel = "F r o n t - M e t h o d e s ";
    private static final Logger logger = LoggerFactory.getLogger(LoggingLabel);
    // Header for frames

    public static void header(JFrame frame, String titre, int x) {
        // ----------panel header : Logo + label titre--------------
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(null);
        JLabel titreLabel = new JLabel(titre);
        titreLabel.setBounds(x, 13, 600, 60);
        titreLabel.setFont(Template.FONT_TITRES);

        ImageIcon logo = new ImageIcon(Objects.requireNonNull(MethodesFront.class.getResource("/logo.png")));

        JLabel logoLabel = new JLabel(logo);
        logoLabel.setBounds(10, 8, 70, 70);
        headerPanel.add(logoLabel);


        ImageIcon homeIcon= new ImageIcon(Objects.requireNonNull(MethodesFront.class.getResource("/home.png")));
        JButton homeButton=new JButton(homeIcon);
        homeButton.setBounds(1320,20,60,60);
        homeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EcranAcceuil ecranAcceuil=new EcranAcceuil();
                frame.dispose();

            }
        });
        headerPanel.add(homeButton);
        JPanel nomAppPanel = new JPanel();
        nomAppPanel.setBounds(100,25,200,35);
        nomAppPanel.setBackground(Color.decode(Template.COULEUR_SECONDAIRE));
        headerPanel.add(nomAppPanel);

        JLabel nomAppLabel = new JLabel("EPIGREEN-SHOP");
        nomAppLabel.setFont(Template.FONT_TITRES);
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

    // Faire test Technique
    public static double malusOUbonusEmpreinte(double empreinte, int idMarque) {
        Marque marque;
        double bonusMalus = 0.0;
        try {
            marque = SelectMarqueById.launchSelectMarqueById(valueOf(idMarque));
            String rse = marque.getRse();

            switch (rse) {
                case "Durable":
                    bonusMalus = -300;
                    break;
                case "Ethique":
                    bonusMalus = -200;
                    break;
                case "Responsable":
                    bonusMalus = -100;
                    break;
                case "Transparente":
                    bonusMalus = empreinte;
                    break;
                case "Polluante":
                    bonusMalus = 200;
                    break;
                default:

                    break;
            }
        } catch (IOException | InterruptedException e) {
            logger.warn(e.getMessage());
        }
        return empreinte + bonusMalus;
    }


    public static String attributeLetterScore(double carbonFootPrint) {

        Scores scores = null;
        try {
            scores = SelectAllScore.launchSelectAllScore();
        } catch (IOException | InterruptedException e) {
            logger.warn(e.getMessage());
        }


        if (scores != null) {

            for (Score score : scores.getScores()) { //parcours de la liste et pour chaque score on compare l'empreinte aux bornes du score
                if (carbonFootPrint >= score.getborneInf() && carbonFootPrint < score.getborneSup()) {
                    return score.getlettreScore();

                }
            }
        }



        logger.warn("ATRIBUATEZ SCORE FAILED");
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

    public static boolean isDouble(String str) {
        try {
            double d = Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }



}