package edu.ezip.ing1.pds.front.UC1;

import edu.ezip.ing1.pds.business.dto.Produit;
import edu.ezip.ing1.pds.business.dto.Produits;
import edu.ezip.ing1.pds.business.dto.TransportMode;
import edu.ezip.ing1.pds.client.UC1.Select3Suggestions;
import edu.ezip.ing1.pds.client.UC1.SelectAllScore;
import edu.ezip.ing1.pds.client.UC1.SelectTransportModeByID;
import edu.ezip.ing1.pds.commons.Request;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.*;
import edu.ezip.ing1.pds.business.dto.Ville;
import edu.ezip.ing1.pds.client.UC1.SelectVilleById;
import edu.ezip.ing1.pds.front.MethodesFront;
import edu.ezip.ing1.pds.front.RechercheReference;
import edu.ezip.ing1.pds.front.Template;
import edu.ezip.ing1.pds.front.UC2.ProductMapping;

import static edu.ezip.ing1.pds.commons.Methodes.*;
import static edu.ezip.ing1.pds.front.MethodesFront.*;
import static java.lang.String.valueOf;
import java.util.*;
import java.util.List;

public class ProductInfo implements ActionListener {

    JFrame productInfoFrame;
    private String productName = RechercheReference.getProduct().getNomProduit();

    private String productColor = RechercheReference.getProduct().getCouleur();
    private Double carbonFootPrint;

    private double productPrice= RechercheReference.getProduct().getPrix();
    private double prodcutWeight= RechercheReference.getProduct().getPoids();
    private Integer idTransportMode= RechercheReference.getProduct().getIdTransportMode();
    private Integer idVilleDepart= RechercheReference.getProduct().getIdVilleDepart();
    private  Integer idVilleArrive= RechercheReference.getProduct().getIdVilleArrive();
    private Integer idCategorie= RechercheReference.getProduct().getIdCategorie();
    private Integer idSousCatA=RechercheReference.getProduct().getIdSousCatA();
    private Integer idSousCatB=RechercheReference.getProduct().getIdsouscatB();
    private String colorProduct= RechercheReference.getProduct().getCouleur();

    private Ville villeArrive;
    private Ville villeDepart;
    private TransportMode transportMode;

    private Produit suggestProduct1;
    private Produit suggestProduct2;
    private Produit suggestProduct3;



    public ProductInfo(){

        productInfoFrame  = new JFrame();
        productInfoFrame.setSize(Template.LONGUEUR, Template.LARGEUR);
        productInfoFrame.setTitle("Faites vous un achat responsable?");
        productInfoFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        productInfoFrame.setLocationRelativeTo(null);
        productInfoFrame.setResizable(false);


        //----------panel header--------------
        String titreHeader = "Produit :  \"" + productName + " " + productColor + "\"";

        MethodesFront.header(productInfoFrame, titreHeader, 540);


        //---------panel principal-----------
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(Color.decode(Template.COULEUR_PRINCIPALE));
        mainPanel.setLayout(null);
        productInfoFrame.getContentPane().add(mainPanel);




        try {
            try {
                Request request = new Request();
                request.setRequestContent(valueOf(idTransportMode));
                transportMode = SelectTransportModeByID.launchSelectTransportModeById(request);
            } catch (Exception e) {
                System.out.println("Erreur sur l'idTransportMode");
            }

            try {
                Request request = new Request();
                request.setRequestContent(valueOf(idVilleArrive));
                villeArrive = SelectVilleById.launchSelectVilleById(request);
            } catch (Exception e) {
                System.out.println("Erreur sur l'idVilleArrivée");
            }

            try {
                Request request = new Request();
                request.setRequestContent(valueOf(idVilleDepart));
                villeDepart = SelectVilleById.launchSelectVilleById(request);
            } catch (Exception e) {
                System.out.println("Erreur sur l'idVilleDepart");


            }
            carbonFootPrint = carbonFootPrintCalcul(villeDepart.getCoordLatitude(), villeDepart.getCoordLongitude(), villeArrive.getCoordLatitude(), villeArrive.getCoordLongitude(), transportMode.getCoeffEmission(), prodcutWeight);

        }catch (Exception e){System.out.println("Impossible de calculer empreinte carbon car pb avec les requetes");}


        //-------panel item chosen---------
        JPanel productPanel = new JPanel();
        productPanel.setLayout(null);
        productPanel.setBounds(180,40,1030,300);
        productPanel.setBackground(Color.WHITE);

        JLabel label1 = new JLabel("Votre produit ");
        label1.setBounds(450,5,100,50);
        productPanel.add(label1);


        JLabel priceLabel = new JLabel("Prix: "+productPrice +" € ");
        priceLabel.setBounds(600,70,300,50);
        productPanel.add(priceLabel);

        JLabel empreinteLabel = new JLabel("Empreinte Carbone : "+  carbonFootPrint +" gCO2e ");
        empreinteLabel.setBounds(600,120,300,50);
        productPanel.add(empreinteLabel);


        JLabel scoreLabel = new JLabel("Carbon score: " );
        scoreLabel.setBounds(600,160,200,50);
        productPanel.add(scoreLabel);


        JLabel IconScore = setlabelIconScore(attributeLetterScore(carbonFootPrint));
        IconScore.setBounds(760,160,80,80);
        productPanel.add(IconScore);

        JButton UC2button= new JButton("Trouver l'article dans le magasin >> ");
        UC2button.addActionListener(this);
        UC2button.setBounds(730,270,300,30);
        productPanel.add(UC2button);
        UC2button.setOpaque(false);
        mainPanel.add(productPanel);

        //-------panel suggestion---------


        JPanel suggestionPanel = new JPanel();
        suggestionPanel.setLayout(null);
        suggestionPanel.setBounds(180,350,1030,330);
        suggestionPanel.setBackground(Color.WHITE);


        JLabel label2 = new JLabel("Suggestions ");
        label2.setBounds(450,5,100,50);
        suggestionPanel.add(label2);

        Produits suggestions= null;

        try {
            suggestions = Select3Suggestions.launchSelect3Suggestions(idCategorie + "," + idSousCatA + "," + idSousCatB + "," + carbonFootPrint + "," + colorProduct);
            List<Produit> suggestList = new ArrayList<>(suggestions.getProduits());

            // Vérifier le nombre de suggestions
            int numberOfSuggestions = suggestList.size();
            if (numberOfSuggestions == 0) {
                JLabel NoSuggestionMessage= new JLabel("Oupss...Malheureusement pour cet article il n'y pas d'alternative plus durable... Bon shopping");
                suggestionPanel.add(NoSuggestionMessage);

            } else if (numberOfSuggestions == 1) {
                // Afficher la seule suggestion disponible
                suggestProduct1 = suggestList.get(0);
                //----------Product suggested 1
                JButton suggest1Button = new JButton("PRODUIT 1");
                suggest1Button.setBounds(420,65,150,150);
                suggestionPanel.add(suggest1Button);

                JLabel priceLabel1 = new JLabel("Prix:"+ suggestProduct1.getPrix() +"€ ");
                priceLabel1.setBounds(460,210,300,50);
                suggestionPanel.add(priceLabel1);

                JLabel IconScoreP1 = setlabelIconScore(suggestProduct1.getScore());
                IconScoreP1.setBounds(470,240,80,80);
                suggestionPanel.add(IconScoreP1);

            } else if (numberOfSuggestions == 2) {
                suggestProduct1 = suggestList.get(0);
                suggestProduct2 = suggestList.get(1);

                //----------Product suggested 1
                JButton suggest1Button = new JButton("PRODUIT 1");
                suggest1Button.setBounds(80,65,150,150);
                suggestionPanel.add(suggest1Button);

                JLabel priceLabel1 = new JLabel("Prix:"+ suggestProduct1.getPrix() +"€ ");
                priceLabel1.setBounds(140,210,300,50);
                suggestionPanel.add(priceLabel1);

                JLabel IconScoreP1 = setlabelIconScore(suggestProduct1.getScore());
                IconScoreP1.setBounds(150,240,80,80);
                suggestionPanel.add(IconScoreP1);

                // JLabel carbonFootPrintP1= new JLabel(suggestProduct1.getEmpreinte()+" gCO2e");



                //----------Product suggested 2----------------------------------------
                JButton suggest2Button = new JButton("PRODUIT 2");
                suggest2Button.setBounds(420,65,150,150);
                suggestionPanel.add(suggest2Button);

                JLabel priceLabel2 = new JLabel("Prix:"+ suggestProduct2.getPrix() +"€ ");
                priceLabel2.setBounds(460,210,300,50);
                suggestionPanel.add(priceLabel2);

                JLabel IconScoreP2 = setlabelIconScore(suggestProduct2.getScore());
                IconScoreP2.setBounds(470,240,80,80);
                suggestionPanel.add(IconScoreP2);

            } else if (numberOfSuggestions >= 3) {
                suggestProduct1 = suggestList.get(0);
                suggestProduct2 = suggestList.get(1);
                suggestProduct3 = suggestList.get(2);

                //----------Product suggested 1
                JButton suggest1Button = new JButton("PRODUIT 1");
                suggest1Button.setBounds(80,65,150,150);
                suggestionPanel.add(suggest1Button);

                JLabel priceLabel1 = new JLabel("Prix:"+ suggestProduct1.getPrix() +"€ ");
                priceLabel1.setBounds(140,210,300,50);
                suggestionPanel.add(priceLabel1);

                JLabel IconScoreP1 = setlabelIconScore(suggestProduct1.getScore());
                IconScoreP1.setBounds(150,240,80,80);
                suggestionPanel.add(IconScoreP1);

                // JLabel carbonFootPrintP1= new JLabel(suggestProduct1.getEmpreinte()+" gCO2e");



                //----------Product suggested 2----------------------------------------
                JButton suggest2Button = new JButton("PRODUIT 2");
                suggest2Button.setBounds(420,65,150,150);
                suggestionPanel.add(suggest2Button);

                JLabel priceLabel2 = new JLabel("Prix:"+ suggestProduct2.getPrix() +"€ ");
                priceLabel2.setBounds(460,210,300,50);
                suggestionPanel.add(priceLabel2);

                JLabel IconScoreP2 = setlabelIconScore(suggestProduct2.getScore());
                IconScoreP2.setBounds(470,240,80,80);
                suggestionPanel.add(IconScoreP2);

                //----------Product suggested 3----------------------------------------
                JButton suggest3Button = new JButton("PRODUIT 3 ");
                suggest3Button.setBounds(750,65,90,90);
                suggestionPanel.add(suggest3Button);


                JLabel priceLabel3 = new JLabel("Prix:"+ suggestProduct3.getPrix() +"€ ");
                priceLabel3.setBounds(810,210,300,50);
                suggestionPanel.add(priceLabel3);

                JLabel IconScoreP3 = setlabelIconScore(suggestProduct3.getScore());
                IconScoreP3.setBounds(820,240,80,80);
                suggestionPanel.add(IconScoreP3);

            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        
        mainPanel.add(suggestionPanel);



        productInfoFrame.setVisible(true);

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        ProductMapping productMapping=new ProductMapping();
        productInfoFrame.dispose();
    }
}
