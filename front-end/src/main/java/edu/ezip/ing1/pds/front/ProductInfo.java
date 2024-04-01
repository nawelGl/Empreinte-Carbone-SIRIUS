package edu.ezip.ing1.pds.front;

import edu.ezip.ing1.pds.client.SelectEmplacementById;
import edu.ezip.ing1.pds.commons.Request;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;
import javax.swing.*;

import static java.lang.String.valueOf;

public class ProductInfo implements ActionListener {

    JFrame productInfoFrame;
    private String productName = RechercheReference.product.getNomProduit();
    private String productScore = RechercheReference.product.getScore();
    private String productColor = RechercheReference.product.getCouleur();
    private Float productEmpreinte= RechercheReference.product.getEmpreinte();
    private double productPrice= RechercheReference.product.getPrix();
    private Integer idTransportMode= RechercheReference.product.getIdTransportMode();
    private Integer idVilleDepart= RechercheReference.product.getIdVilleDepart();
    private  Integer idVilleArrive= RechercheReference.product.getIdVilleArrive();


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

        Methodes.header(productInfoFrame, titreHeader, 540);


        //---------panel principal-----------
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(Color.decode(Template.COULEUR_PRINCIPALE));
        mainPanel.setLayout(null);
        productInfoFrame.getContentPane().add(mainPanel);



        try {
            // TODO:Faire 3 requetes.
            //  1. requetes sur idTransport ( recuperer coeffEmission)
            //  2. requetes sur idVilledepart ( recuperer coordLatitude , coordLongitude)
            //  3. requete sur idVilleArrivée ( recuperer coordLatitude , coordLongitude)

//            Request request = new Request();
//            request.setRequestContent(valueOf(idTransportMode));
//            emplacement = SelectEmplacementById.launchSelectEmplacementById(request);
        } catch(Exception e){
//            System.out.println("Erreur sur l'idEmplacement : " + e.getMessage());
        }

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

        JLabel empreinteLabel = new JLabel("Empreinte Carbon: "+  productEmpreinte +" kgCO2e ");
        empreinteLabel.setBounds(600,120,300,50);
        productPanel.add(empreinteLabel);


        JLabel scoreLabel = new JLabel("Carbon score: " );
        scoreLabel.setBounds(600,160,200,50);
        productPanel.add(scoreLabel);

        JLabel IconScore = labelIconScore(productScore);
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

    public static JLabel labelIconScore(String scoreLetter) {
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


    @Override
    public void actionPerformed(ActionEvent e) {
        productInfoFrame.dispose();
        ProductMapping productMapping=new ProductMapping();

    }
}
