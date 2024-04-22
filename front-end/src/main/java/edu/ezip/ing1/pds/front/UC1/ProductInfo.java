package edu.ezip.ing1.pds.front.UC1;

import edu.ezip.ing1.pds.business.dto.Produits;
import edu.ezip.ing1.pds.business.dto.TransportMode;
import edu.ezip.ing1.pds.client.UC1.SelectTransportModeByID;
import edu.ezip.ing1.pds.commons.Request;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;
import edu.ezip.ing1.pds.business.dto.Ville;
import edu.ezip.ing1.pds.client.UC1.SelectVilleById;
import edu.ezip.ing1.pds.front.MethodesFront;
import edu.ezip.ing1.pds.front.RechercheReference;
import edu.ezip.ing1.pds.front.Template;
import edu.ezip.ing1.pds.front.UC2.ProductMapping;

import static edu.ezip.ing1.pds.front.MethodesFront.*;
import static java.lang.String.valueOf;

public class ProductInfo implements ActionListener {

    JFrame productInfoFrame;
    private String productName = RechercheReference.getProduct().getNomProduit();
    private String productScore = RechercheReference.getProduct().getScore();
    private String productColor = RechercheReference.getProduct().getCouleur();
    private Double carbonFootPrint;
            //= RechercheReference.product.getEmpreinte();
    private double productPrice= RechercheReference.getProduct().getPrix();
    private double prodcutWeight= RechercheReference.getProduct().getPoids();
    private Integer idTransportMode= RechercheReference.getProduct().getIdTransportMode();
    private Integer idVilleDepart= RechercheReference.getProduct().getIdVilleDepart();
    private  Integer idVilleArrive= RechercheReference.getProduct().getIdVilleArrive();
    private Ville villeArrive;
    private Ville villeDepart;
    private TransportMode transportMode;
    private ArrayList<Produits> produits;



    public ProductInfo(){
        //TODO: Requetes sur la BD qui recupere suggestions "SELECT ...."


        productInfoFrame  = new JFrame();
        productInfoFrame.setSize(Template.LONGUEUR, Template.LARGEUR);
        productInfoFrame.setTitle("Faites vous un achat responsable?");
        productInfoFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        productInfoFrame.setLocationRelativeTo(null);
        productInfoFrame.setResizable(false);


        //----------panel header--------------
        String titreHeader = "Produit :  \"" + productName + " " + productColor + "\"";
       // String titreHeader="Produit nom couleur";

        MethodesFront.header(productInfoFrame, titreHeader, 540);


        //---------panel principal-----------
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(Color.decode(Template.COULEUR_PRINCIPALE));
        mainPanel.setLayout(null);
        productInfoFrame.getContentPane().add(mainPanel);




            // TODO:Faire 3 requetes.
            //  1. requetes sur idTransport ( recuperer coeffEmission)
            //  2. requetes sur idVilledepart ( recuperer coordLatitude , coordLongitude)
            //  3. requete sur idVilleArrivée ( recuperer coordLatitude , coordLongitude)

        System.out.println("==========================");
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
            System.out.println("=================================================");
            System.out.println(carbonFootPrint);
            System.out.println("================================================");

        }catch (Exception e){System.out.println("Impossible de calculer empreinte carbon car pb avec les requetes");}


//TODO:Recuperer les donnée necessaire au calcule et les mettre en paramètre d'une fonction

        //-------panel item chosen---------
        JPanel productPanel = new JPanel();
        productPanel.setLayout(null);
        productPanel.setBounds(180,60,1030,300);
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

//        JLabel IconScore = labelIconScore(productScore);
//        IconScore.setBounds(760,160,80,80);
//        productPanel.add(IconScore);
        JLabel IconScore = setlabelIconScore(attributeLetterScore(carbonFootPrint));
//        System.out.println(carbonFootPrint);
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
        suggestionPanel.setBounds(180,370,1030,330);
        suggestionPanel.setBackground(Color.WHITE);



        JLabel label2 = new JLabel("Suggestions ");
        label2.setBounds(450,5,100,50);
        suggestionPanel.add(label2);

        //------- Suggestion items --------------------------------------------
        JButton suggest1Button = new JButton("PRODUIT 1");
        suggest1Button.setBounds(80,65,200,150);
        suggestionPanel.add(suggest1Button);


        JLabel priceLabel1 = new JLabel("Prix: xxxx € ");
        priceLabel1.setBounds(140,210,300,50);
        suggestionPanel.add(priceLabel1);

        JButton suggest2Button = new JButton("PRODUIT 2");
        suggest2Button.setBounds(420,65,200,150);
        suggestionPanel.add(suggest2Button);

        JLabel priceLabel2 = new JLabel("Prix: xxxx € ");
        priceLabel2.setBounds(460,210,300,50);
        suggestionPanel.add(priceLabel2);

        JButton suggest3Button = new JButton("PRODUIT 3");
        suggest3Button.setBounds(750,65,200,150);
        suggestionPanel.add(suggest3Button);


        JLabel priceLabel3 = new JLabel("Prix: xxxx € ");
        priceLabel3.setBounds(810,210,300,50);
        suggestionPanel.add(priceLabel3);


        mainPanel.add(suggestionPanel);



        productInfoFrame.setVisible(true);

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        ProductMapping productMapping=new ProductMapping();
        productInfoFrame.dispose();
    }
}
