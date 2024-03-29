package edu.ezip.ing1.pds.front;

import edu.ezip.ing1.pds.business.dto.Vente;
import edu.ezip.ing1.pds.client.SelectBeforeVenteByReference;
import edu.ezip.ing1.pds.commons.Request;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;
import static java.lang.String.valueOf;

public class StatProduct {

    JFrame statUC3;
    String[] labels = {"Avant", "Après"};

    private int reference = RechercheReferenceUC3.venteBefore.getReference();
    private String score = RechercheReferenceUC3.venteBefore.getScore();
    private Vente vente;
    private  int textSize=20;

    public StatProduct(int salesBefore, int salesAfter ){

        // recherche de information vente pour dessiner les charts et afficher

        try{
            Request request = new Request();
            request.setRequestContent(valueOf(reference));
            vente = SelectBeforeVenteByReference.launchSelectVenteByReference(request);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

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
        JPanel infoPanel = new JPanel(new BorderLayout());
        infoPanel.setBackground(Color.white);

        RoundedPanel infoTitlePanel = new RoundedPanel(30,30);
        JLabel infoTitle = new JLabel("Information de votre produit "+vente.getReference());
        infoTitle.setFont(new Font("Avenir", Font.BOLD,textSize));
        infoTitlePanel.add(infoTitle);

//        JLabel refLabel = new JLabel();
//        refLabel.setText("Information de votre produit "+vente.getReference());
//        refLabel.setFont(new Font("Avenir", Font.BOLD,textSize));
        //refLabel.setBorder(new EmptyBorder());

        JLabel textLabel = new JLabel("le score de votre produit est ");
        textLabel.setFont(new Font("Avenir", Font.BOLD,textSize));

        JLabel scoreLabel = new JLabel();
        scoreLabel.setFont(new Font("Avenir", Font.BOLD,textSize));

        // Switch case pour recuperer l'icone correspondqnt

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

        //-------ajout de labels----------
        infoPanel.add(BorderLayout.NORTH,infoTitlePanel);
        infoPanel.add(BorderLayout.WEST,textLabel);
        infoPanel.add(BorderLayout.EAST,scoreLabel);

        infoPanel.setBounds(150,100,500,550);

        //chartPanel
        JPanel chartPanel = new JPanel(new BorderLayout());

        // --ajout de title panel
        RoundedPanel chartTitlePanel = new RoundedPanel(30,30);
        JLabel charTitle = new JLabel("Statistiques des ventes");
        chartTitlePanel.add(charTitle);
        chartTitlePanel.setBackground(Color.decode(Template.COUELUR_SECONDAIRE));
        chartTitlePanel.setBorder(new EmptyBorder(20,20,20,20));


        double[] values = {salesBefore, salesAfter};
        Color[] colors = {Color.decode("#6CE5E8"), Color.decode("#41B8D5")};
        DrawChart barChart = new DrawChart(labels, values, colors);
        chartPanel.add(BorderLayout.NORTH,chartTitlePanel);
        chartPanel.add(BorderLayout.CENTER, barChart);



        chartPanel.setBounds(780,100,500,550);

        mainPanel.add(infoPanel);
        mainPanel.add(chartPanel);
        statUC3.getContentPane().add(mainPanel);

        //-----------------------------------------------
        statUC3.setVisible(true);


    }


}
