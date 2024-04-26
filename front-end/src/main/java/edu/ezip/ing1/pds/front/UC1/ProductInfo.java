package edu.ezip.ing1.pds.front.UC1;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.ezip.ing1.pds.business.dto.*;
import edu.ezip.ing1.pds.client.Categories.SelectCategorieByID;
import edu.ezip.ing1.pds.client.InsertPointsRequest;
import edu.ezip.ing1.pds.client.UC1.*;
import edu.ezip.ing1.pds.client.UpdateInfoProduct;
import edu.ezip.ing1.pds.commons.Request;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.*;

import edu.ezip.ing1.pds.front.*;
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
    private double carbonFootPrint;

    private double productPrice= RechercheReference.getProduct().getPrix();
    private double prodcutWeight= RechercheReference.getProduct().getPoids();
    private Integer idTransportMode= RechercheReference.getProduct().getIdTransportMode();
    private Integer idVilleDepart= RechercheReference.getProduct().getIdVilleDepart();
    private  Integer idVilleArrive= RechercheReference.getProduct().getIdVilleArrive();
    private Integer idCategorie= RechercheReference.getProduct().getIdCategorie();
    private Integer idSousCatA=RechercheReference.getProduct().getIdSousCatA();
    private Integer idSousCatB=RechercheReference.getProduct().getIdsouscatB();
    private String colorProduct= RechercheReference.getProduct().getCouleur();
    private int reference = RechercheReference.getProduct().getReference();

    private String score;

    private Ville villeArrive;
    private Ville villeDepart;
    private TransportMode transportMode;
    private Marque marque;
    private Categorie categorie;

    private Produit suggestProduct1;
    private Produit suggestProduct2;
    private Produit suggestProduct3;
    private JButton UC2button;
    private JButton suggest2Button;
    private JButton suggest1Button;
    private JButton suggest3Button;
//    private  RoundedPanel roundedPanel;
    private GridBagConstraints gbc;
    private JPanel gridPanel ;

    private JPanel suggestPanel1;
    private JPanel suggestPanel2;
    private JPanel suggestPanel3;





    public ProductInfo(){

        productInfoFrame  = new JFrame();
        productInfoFrame.setSize(Template.LONGUEUR, Template.LARGEUR);
        productInfoFrame.setTitle("Faites vous un achat responsable?");
        productInfoFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        productInfoFrame.setLocationRelativeTo(null);
        productInfoFrame.setResizable(false);


        //----------panel header--------------
        String titreHeader = "Produit :  \"" + productName + " " + productColor + "\"";
        Font fontHeader= new Font(Template.POLICE,Font.CENTER_BASELINE,25);


        MethodesFront.header(productInfoFrame, titreHeader, 540);


        //---------panel principal-----------
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(Color.decode(Template.COULEUR_PRINCIPALE));
        mainPanel.setLayout(null);
        productInfoFrame.getContentPane().add(mainPanel);




        try {
//            try {
                marque= SelectMarqueById.launchSelectMarqueById(valueOf(RechercheReference.getProduct().getIdMarque()));

                transportMode = SelectTransportModeByID.launchSelectTransportModeById(String.valueOf(idTransportMode));
                villeArrive = SelectVilleById.launchSelectVilleById(String.valueOf(idVilleArrive));
                villeDepart = SelectVilleById.launchSelectVilleById(String.valueOf(idVilleDepart));
                categorie= SelectCategorieByID.launchSelectCategorieById(String.valueOf(idCategorie));
//            } catch (Exception e) {
//                System.out.println("Erreur sur l'idTransportMode ou idMarque");
//            }

//            try {
//
//                villeArrive = SelectVilleById.launchSelectVilleById(String.valueOf(idVilleArrive));
//            } catch (Exception e) {
//                System.out.println("Erreur sur l'idVilleArrivée");
//            }

//            try {
//                villeDepart = SelectVilleById.launchSelectVilleById(String.valueOf(idVilleDepart));
//            } catch (Exception e) {
//                System.out.println("Erreur sur l'idVilleDepart");


//            }

            carbonFootPrint = carbonFootPrintCalcul(villeDepart.getCoordLatitude(), villeDepart.getCoordLongitude(), villeArrive.getCoordLatitude(), villeArrive.getCoordLongitude(), transportMode.getCoeffEmission(), prodcutWeight);
           
        }catch (Exception e){System.out.println("Impossible de calculer empreinte carbon car pb avec les requetes");}

        score=attributeLetterScore(carbonFootPrint);

        //Faire UPDATE BASE ---------------------------
        ObjectMapper objectMapper = new ObjectMapper();
        Produit produit= new Produit();
        produit.setEmpreinte(carbonFootPrint);
        produit.setScore(score);
        produit.setReference(reference);
        try {
            String responseBody = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(produit);
            UpdateInfoProduct.launchUpdateProduit(responseBody);

        } catch (IOException | InterruptedException ex) {

        }

        //-------panel item chosen---------
        JPanel productPanel = new JPanel();
        productPanel.setLayout(null);
        productPanel.setBounds(180,40,1030,300);
        productPanel.setBackground(Color.WHITE);

        JLabel label1 = new JLabel("Votre produit ");
        label1.setFont(Template.FONT_ECRITURE);
        label1.setBounds(450,5,100,50);
        productPanel.add(label1);


        JLabel priceLabel = new JLabel("Prix: "+productPrice +" € ");
        priceLabel.setFont(Template.FONT_ECRITURE);
        priceLabel.setBounds(600,66,300,50);
        productPanel.add(priceLabel);

        JLabel brandLabel = new JLabel("Marque : "+ marque.getNomMarque() +"    ( Enseigne "+marque.getRse()+" )");
        brandLabel.setFont(Template.FONT_ECRITURE);
        brandLabel.setBounds(600,93,300,50);
        productPanel.add(brandLabel);

        JLabel categorieLabel = new JLabel("Categorie : "+ categorie.getNomCategorie());
        categorieLabel.setFont(Template.FONT_ECRITURE);
        categorieLabel.setBounds(600,120,300,50);
        productPanel.add(categorieLabel);



        JLabel empreinteLabel = new JLabel("Empreinte Carbone : "+  carbonFootPrint +" gCO2e ");
        empreinteLabel.setFont(Template.FONT_ECRITURE);
        empreinteLabel.setBounds(600,147,300,50);
        productPanel.add(empreinteLabel);


        JLabel scoreLabel = new JLabel("Score carbone : " );
        scoreLabel.setFont(Template.FONT_ECRITURE);
        scoreLabel.setBounds(600,175,200,50);
        productPanel.add(scoreLabel);


        JLabel IconScore = setlabelIconScore(score);
        IconScore.setBounds(730,177,80,80);
        productPanel.add(IconScore);

        UC2button= new JButton("Trouver l'article dans le magasin >> ");
        UC2button.addActionListener(this);
        UC2button.setBounds(730,270,300,30);
        productPanel.add(UC2button);
        UC2button.setOpaque(false);


        RoundedPanel roundedPanel= new RoundedPanel(40,40);
        roundedPanel.setBackground(Color.decode(Template.COULEUR_SECONDAIRE));
        roundedPanel.setBounds(100,70,300,200);
        productPanel.add(roundedPanel);
        mainPanel.add(productPanel);


        //-------panel suggestion---------

        JPanel suggestionPanel = new JPanel();
        suggestionPanel.setLayout(null);
        suggestionPanel.setBounds(180,350,1030,330);
        suggestionPanel.setBackground(Color.WHITE);


        suggestPanel1=new JPanel(new BoxLayout(suggestPanel1, BoxLayout.Y_AXIS));
        suggestPanel2=new JPanel(new BoxLayout(suggestPanel2, BoxLayout.Y_AXIS));
        suggestPanel3=new JPanel(new BoxLayout(suggestPanel3, BoxLayout.Y_AXIS));





        JLabel label2 = new JLabel("Suggestions ");
        label2.setBounds(450,5,100,50);
        label2.setFont(Template.FONT_ECRITURE);
        suggestionPanel.add(label2);


//



        Produits suggestions= null;




        try {
            suggestions = Select3Suggestions.launchSelect3Suggestions(idCategorie + "," + idSousCatA + "," + idSousCatB + "," + carbonFootPrint + "," + colorProduct);

            if (suggestions==null) {
                JLabel NoSuggestionMessage= new JLabel("Pour cet article il n'y pas de suggestion plus durable... Bon shopping!");
                NoSuggestionMessage.setBounds(200,80,900,200);
                Font font = new Font(Template.POLICE, Font.BOLD, 20);
                NoSuggestionMessage.setForeground(Color.decode(Template.COULEUR_SECONDAIRE));
                NoSuggestionMessage.setFont(font);
                suggestionPanel.add(NoSuggestionMessage);

            } else {

                gridPanel=new JPanel(new GridBagLayout());
                gridPanel.setBounds(0,50,1030,330);
                gridPanel.setBackground(Color.decode(Template.COULEUR_SECONDAIRE));
                suggestionPanel.add(gridPanel);

                // Définition des contraintes pour chaque case
                GridBagConstraints constraints = new GridBagConstraints();
                constraints.fill = GridBagConstraints.BOTH;
                constraints.weightx = 1.0; // Poids horizontal égal pour chaque case
                constraints.weighty = 1.0; // Poids vertical égal pour chaque case



                List<Produit> suggestList = new ArrayList<>(suggestions.getProduits());
                int numberOfSuggestions = suggestList.size();
                 if (numberOfSuggestions == 1) {
                    // Afficher la seule suggestion disponible
                    suggestProduct1 = suggestList.get(0);
                    //----------Product suggested 1
                     constraints.gridx = 1;
                     constraints.gridy = 0;
                    suggest1Button = new JButton(suggestProduct1.getNomProduit());
                    suggest1Button.setSize(150,150);
                    suggest1Button.addActionListener(this);
                    gridPanel.add(suggest1Button);

                    constraints.gridy++;
                    JLabel priceLabel1 = new JLabel("Prix:"+ suggestProduct1.getPrix() +"€ ");
                    priceLabel1.setSize(300,50);
                     gridPanel.add(priceLabel1);

                     constraints.gridy++;
                    JLabel IconScoreP1 = setlabelIconScore(suggestProduct1.getScore());
                    IconScoreP1.setSize(80,80);
                     gridPanel.add(IconScoreP1);

                } else if (numberOfSuggestions == 2) {
                    suggestProduct1 = suggestList.get(0);
                    suggestProduct2 = suggestList.get(1);

                    //----------Product suggested 1
                     suggestProduct1 = suggestList.get(0);
                     //----------Product suggested 1
                     constraints.gridx = 0;
                     constraints.gridy = 0;
                     suggest1Button = new JButton(suggestProduct1.getNomProduit());
                     suggest1Button.setSize(150,150);
                     suggest1Button.addActionListener(this);
                     gridPanel.add(suggest1Button);

                     constraints.gridy++;
                     JLabel priceLabel1 = new JLabel("Prix:"+ suggestProduct1.getPrix() +"€ ");
                     priceLabel1.setSize(300,50);
                     gridPanel.add(priceLabel1);

                     constraints.gridy++;
                     JLabel IconScoreP1 = setlabelIconScore(suggestProduct1.getScore());
                     IconScoreP1.setSize(80,80);
                     gridPanel.add(IconScoreP1);

                    // JLabel carbonFootPrintP1= new JLabel(suggestProduct1.getEmpreinte()+" gCO2e");



                    //----------Product suggested 2----------------------------------------

                     constraints.gridx = 2;
                     constraints.gridy = 0;
                     suggest2Button = new JButton(suggestProduct2.getNomProduit());
                    suggest2Button.setBounds(420,65,150,150);
                     suggest2Button.addActionListener(this);
                     gridPanel.add(suggest2Button);

                     constraints.gridy++;
                    JLabel priceLabel2 = new JLabel("Prix:"+ suggestProduct2.getPrix() +"€ ");
                    priceLabel2.setBounds(460,210,300,50);
                     gridPanel.add(priceLabel2);

                     constraints.gridy++;
                    JLabel IconScoreP2 = setlabelIconScore(suggestProduct2.getScore());
                    IconScoreP2.setBounds(470,240,80,80);
                     gridPanel.add(IconScoreP2);

                } else if (numberOfSuggestions >= 3) {
                    suggestProduct1 = suggestList.get(0);
                    suggestProduct2 = suggestList.get(1);
                    suggestProduct3 = suggestList.get(2);

                    //----------Product suggested 1
                     constraints.gridx = 0;
                     constraints.gridy = 0;
                    suggest1Button = new JButton(suggestProduct1.getNomProduit());
                    suggest1Button.setBounds(80,65,150,150);
                     suggest1Button.addActionListener(this);
                   gridPanel.add(suggest1Button);

                   constraints.gridy++;
                    JLabel priceLabel1 = new JLabel("Prix:"+ suggestProduct1.getPrix() +"€ ");
                    priceLabel1.setBounds(140,210,300,50);
                   gridPanel.add(priceLabel1);

                     constraints.gridy++;
                     JLabel IconScoreP1 = setlabelIconScore(suggestProduct1.getScore());
                    IconScoreP1.setBounds(150,240,80,80);
                    gridPanel.add(IconScoreP1);





                    //----------Product suggested 2----------------------------------------

                     constraints.gridx = 1;
                     constraints.gridy = 0;
                     suggest2Button = new JButton(suggestProduct2.getNomProduit());
                    suggest2Button.setBounds(420,65,150,150);
                     suggest2Button.addActionListener(this);
                    gridPanel.add(suggest2Button);

                    constraints.gridy++;
                    JLabel priceLabel2 = new JLabel("Prix:"+ suggestProduct2.getPrix() +"€ ");
                    priceLabel2.setBounds(460,210,300,50);
                    gridPanel.add(priceLabel2);

                    constraints.gridy++;
                    JLabel IconScoreP2 = setlabelIconScore(suggestProduct2.getScore());
                    IconScoreP2.setBounds(470,240,80,80);
                    gridPanel.add(IconScoreP2);

                    //----------Product suggested 3----------------------------------------

                     constraints.gridx = 1;
                     constraints.gridy = 0;
                     suggest3Button = new JButton(suggestProduct3.getNomProduit());
                    suggest3Button.setBounds(755,65,150,150);
                    suggest3Button.addActionListener(this);
                    gridPanel.add(suggest3Button);

                    constraints.gridy++;
                    JLabel priceLabel3 = new JLabel("Prix:"+ suggestProduct3.getPrix() +"€ ");
                    priceLabel3.setBounds(810,210,300,50);
                    gridPanel.add(priceLabel3);


                    constraints.gridy++;
                    JLabel IconScoreP3 = setlabelIconScore(suggestProduct3.getScore());
                    IconScoreP3.setBounds(820,240,80,80);
                    gridPanel.add(IconScoreP3);

                }
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }





        mainPanel.add(suggestionPanel);





        productInfoFrame.setVisible(true);

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == UC2button) {
            ProductMapping productMapping = new ProductMapping();
            productInfoFrame.dispose();
        } else if (e.getSource()==suggest1Button) {

            DetailsProduct detailsProduct= new DetailsProduct(suggestProduct1);
            productInfoFrame.dispose();
        } else if (e.getSource()==suggest2Button) {
            DetailsProduct detailsProduct= new DetailsProduct(suggestProduct2);
            productInfoFrame.dispose();
        } else if (e.getSource()==suggest3Button) {
            DetailsProduct detailsProduct= new DetailsProduct(suggestProduct3);
            productInfoFrame.dispose();
        }
    }
}
