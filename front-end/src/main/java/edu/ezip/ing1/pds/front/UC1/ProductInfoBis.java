package edu.ezip.ing1.pds.front.UC1;

import edu.ezip.ing1.pds.business.dto.*;
import edu.ezip.ing1.pds.client.Categories.SelectCategorieByID;
import edu.ezip.ing1.pds.client.UC1.Select3Suggestions;
import edu.ezip.ing1.pds.client.UC1.SelectMarqueById;
import edu.ezip.ing1.pds.client.UC1.SelectVilleById;
import edu.ezip.ing1.pds.front.*;
import edu.ezip.ing1.pds.front.UC2.ProductMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static edu.ezip.ing1.pds.front.MethodesFront.setlabelIconScore;
import static java.lang.String.valueOf;

public class ProductInfoBis implements ActionListener {

    private final static String LoggingLabel = "F r o n t - U C 1 - P r o d u c t - I n f o";
    private final Logger logger = LoggerFactory.getLogger(LoggingLabel);
    JFrame productInfoFrame;

    private Ville villeDepart;

    private Marque marque;
    private Categorie categorie;
    private double carbonFootPrint;
    Produit produit;

    private Produit suggestProduct1;
    private Produit suggestProduct2;
    private Produit suggestProduct3;
    private JButton UC2button;
    private JButton suggest2Button;
    private JButton suggest1Button;
    private JButton suggest3Button;
    private int idCat, idCatA, idCatB;



    public ProductInfoBis(Produit produit, int idCat, int idCatA, int idCatB){
        this.produit = produit;
        this.idCat = idCat;
        this.idCatB = idCatB;
        this.idCatA = idCatA;

        productInfoFrame  = new JFrame();
        productInfoFrame.setSize(Template.LONGUEUR, Template.LARGEUR);
        productInfoFrame.setTitle("Faites vous un achat responsable?");
        productInfoFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        productInfoFrame.setLocationRelativeTo(null);
        productInfoFrame.setResizable(false);


        //----------panel header--------------
        String titreHeader =  produit.getNomProduit() +" "+ produit.getCouleur() + "   ( "+produit.getReference()+" )";
        Font fontHeader= new Font(Template.POLICE,Font.CENTER_BASELINE,25);


        MethodesFront.header(productInfoFrame, titreHeader, 540);


        //---------panel principal-----------
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(Color.decode(Template.COULEUR_PRINCIPALE));
        mainPanel.setLayout(null);
        productInfoFrame.getContentPane().add(mainPanel);



        try {
                marque= SelectMarqueById.launchSelectMarqueById(valueOf(produit.getIdMarque()));
        }catch (Exception e){logger.error(" pb avec les requetes");}


        //-------panel item chosen---------
        JPanel productPanel = new JPanel();
        productPanel.setLayout(null);
        productPanel.setBounds(180,40,1030,300);
        productPanel.setBackground(Color.WHITE);

        JLabel label1 = new JLabel("Votre produit ");
        label1.setFont(Template.FONT_ECRITURE);
        label1.setBounds(450,5,100,50);
        productPanel.add(label1);


        JLabel priceLabel = new JLabel("Prix: "+produit.getPrix() +" € ");
        priceLabel.setFont(Template.FONT_ECRITURE);
        priceLabel.setBounds(600,93,300,50);
        productPanel.add(priceLabel);

        JLabel brandLabel = new JLabel("Marque : "+ marque.getNomMarque() +"    ( Enseigne "+marque.getRse()+" )");
        brandLabel.setFont(Template.FONT_ECRITURE);
        brandLabel.setBounds(600,120,300,50);
        productPanel.add(brandLabel);

//        JLabel categorieLabel = new JLabel("Categorie : "+ categorie.getNomCategorie());
//        categorieLabel.setFont(Template.FONT_ECRITURE);
//        categorieLabel.setBounds(600,120,300,50);
//        productPanel.add(categorieLabel);


        Double bonneCarbonFootPrint= new BigDecimal(produit.getEmpreinte()).setScale(1, RoundingMode.HALF_UP).doubleValue();

        JLabel empreinteLabel = new JLabel("Empreinte Carbone : "+  bonneCarbonFootPrint +" gCO2e ");
        empreinteLabel.setFont(Template.FONT_ECRITURE);
        empreinteLabel.setBounds(600,147,300,50);
        productPanel.add(empreinteLabel);


        JLabel scoreLabel = new JLabel("Score carbone : " );
        scoreLabel.setFont(Template.FONT_ECRITURE);
        scoreLabel.setBounds(600,175,200,50);
        productPanel.add(scoreLabel);

        JLabel IconScore = setlabelIconScore(produit.getScore());
        IconScore.setBounds(730,177,80,80);
        productPanel.add(IconScore);

        UC2button= new JButton("Trouver l'article dans le magasin >> ");
        UC2button.addActionListener(this);
        UC2button.setBounds(730,270,300,30);
        productPanel.add(UC2button);
        UC2button.setOpaque(false);


        RoundedPanel roundedPanel= new RoundedPanel(40,40);
        roundedPanel.setBackground(Color.decode(Template.COULEUR_SECONDAIRE));
        roundedPanel.setLayout(new BorderLayout());
        roundedPanel.setBounds(100,70,300,200);

        ImageIcon pictureProduct = new ImageIcon(Objects.requireNonNull(MethodesFront.class.getResource("/"+produit.getReference()+".png")));
        Image image = pictureProduct.getImage();
        Image nouvelleImage = image.getScaledInstance(150, 154, Image.SCALE_SMOOTH);

        ImageIcon nouvelleIcon = new ImageIcon(nouvelleImage);

        JLabel pictureProductLabel = new JLabel(nouvelleIcon);
        //JLabel pictureProductLabel= new JLabel(pictureProduct);
        roundedPanel.add (pictureProductLabel);

        productPanel.add(roundedPanel);
        mainPanel.add(productPanel);


        //-------panel suggestion---------

        JPanel suggestionPanel = new JPanel();
        suggestionPanel.setLayout(null);
        suggestionPanel.setBounds(180,350,1030,330);
        suggestionPanel.setBackground(Color.WHITE);


        JLabel label2 = new JLabel("Suggestions ");
        label2.setBounds(450,5,100,50);
        label2.setFont(Template.FONT_ECRITURE);
        suggestionPanel.add(label2);

        Produits suggestions= null;
        carbonFootPrint=produit.getEmpreinte();


        try {
            suggestions = Select3Suggestions.launchSelect3Suggestions(idCat + "," + idCatA + "," + idCatB + "," + carbonFootPrint + "," + produit.getCouleur()+ ","+produit.getReference());

            if (suggestions==null) {
                JLabel NoSuggestionMessage= new JLabel("Pour cet article il n'y pas de suggestion plus durable... Bon shopping!");
                NoSuggestionMessage.setBounds(200,80,900,200);
                Font font = new Font(Template.POLICE, Font.BOLD, 20);
                NoSuggestionMessage.setForeground(Color.decode(Template.COULEUR_SECONDAIRE));
                NoSuggestionMessage.setFont(font);
                suggestionPanel.add(NoSuggestionMessage);

            } else {
                JPanel gridPanel= new JPanel();
                gridPanel.setLayout(new GridBagLayout());
                gridPanel.setBackground(Color.BLACK);
                suggestionPanel.add(gridPanel,BorderLayout.CENTER);

                List<Produit> suggestList = new ArrayList<>(suggestions.getProduits());
                int numberOfSuggestions = suggestList.size();
                 if (numberOfSuggestions == 1) {
                    // Afficher la seule suggestion disponible
                    suggestProduct1 = suggestList.get(0);
                    //----------Product suggested 1
                     ImageIcon pictureS1 = new ImageIcon(Objects.requireNonNull(MethodesFront.class.getResource("/"+suggestProduct1.getReference()+".png")));
                     Image imageS1 = pictureS1.getImage();
                     Image nouvelleImageS1 = imageS1.getScaledInstance(85, 89, Image.SCALE_SMOOTH);

                     ImageIcon nouvelleIconS1 = new ImageIcon(nouvelleImageS1);

                    suggest1Button = new JButton(nouvelleIconS1);
                    suggest1Button.setBounds(420,65,150,150);
                     suggest1Button.addActionListener(this);
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
                     ImageIcon pictureS1 = new ImageIcon(Objects.requireNonNull(MethodesFront.class.getResource("/"+suggestProduct1.getReference()+".png")));
                     Image imageS1 = pictureS1.getImage();
                     Image nouvelleImageS1 = imageS1.getScaledInstance(85, 89, Image.SCALE_SMOOTH);

                     ImageIcon nouvelleIconS1 = new ImageIcon(nouvelleImageS1);

                     suggest1Button = new JButton(nouvelleIconS1);

                     suggest1Button.setBounds(80,65,150,150);
                     suggest1Button.addActionListener(this);
                     suggestionPanel.add(suggest1Button);

                     JLabel priceLabel1 = new JLabel("Prix:"+ suggestProduct1.getPrix() +"€ ");
                     priceLabel1.setBounds(140,210,300,50);
                     suggestionPanel.add(priceLabel1);

                     JLabel IconScoreP1 = setlabelIconScore(suggestProduct1.getScore());
                     IconScoreP1.setBounds(150,240,80,80);
                     suggestionPanel.add(IconScoreP1);




                    //----------Product suggested 2----------------------------------------
                     ImageIcon pictureS2 = new ImageIcon(Objects.requireNonNull(MethodesFront.class.getResource("/"+suggestProduct2.getReference()+".png")));
                     Image imageS2 = pictureS2.getImage();
                     Image nouvelleImageS2 = imageS2.getScaledInstance(85, 89, Image.SCALE_SMOOTH);

                     ImageIcon nouvelleIconS2 = new ImageIcon(nouvelleImageS1);

                     suggest2Button = new JButton(nouvelleIconS2);
                     suggest2Button.setBounds(755,65,150,150);
                     suggest2Button.addActionListener(this);
                    suggestionPanel.add(suggest2Button);

                    JLabel priceLabel2 = new JLabel("Prix:"+ suggestProduct2.getPrix() +"€ ");
                     priceLabel2.setBounds(810,210,300,50);
                    suggestionPanel.add(priceLabel2);

                    JLabel IconScoreP2 = setlabelIconScore(suggestProduct2.getScore());
                    IconScoreP2.setBounds(820,240,80,80);
                    suggestionPanel.add(IconScoreP2);

                } else if (numberOfSuggestions >= 3) {
                    suggestProduct1 = suggestList.get(0);
                    suggestProduct2 = suggestList.get(1);
                    suggestProduct3 = suggestList.get(2);

                    //----------Product suggested 1
                     ImageIcon pictureS1 = new ImageIcon(Objects.requireNonNull(MethodesFront.class.getResource("/"+suggestProduct1.getReference()+".png")));
                     Image imageS1 = pictureS1.getImage();
                     Image nouvelleImageS1 = imageS1.getScaledInstance(85, 89, Image.SCALE_SMOOTH);

                     ImageIcon nouvelleIconS1 = new ImageIcon(nouvelleImageS1);

                     suggest1Button = new JButton(nouvelleIconS1);

                    suggest1Button.setBounds(80,65,150,150);
                     suggest1Button.addActionListener(this);
                    suggestionPanel.add(suggest1Button);

                    JLabel priceLabel1 = new JLabel("Prix:"+ suggestProduct1.getPrix() +"€ ");
                    priceLabel1.setBounds(140,210,300,50);
                    suggestionPanel.add(priceLabel1);

                    JLabel IconScoreP1 = setlabelIconScore(suggestProduct1.getScore());
                    IconScoreP1.setBounds(150,240,80,80);
                    suggestionPanel.add(IconScoreP1);





                    //----------Product suggested 2----------------------------------------
                     ImageIcon pictureS2 = new ImageIcon(Objects.requireNonNull(MethodesFront.class.getResource("/"+suggestProduct2.getReference()+".png")));
                     Image imageS2 = pictureS2.getImage();
                     Image nouvelleImageS2 = imageS2.getScaledInstance(85, 89, Image.SCALE_SMOOTH);

                     ImageIcon nouvelleIconS2 = new ImageIcon(nouvelleImageS1);

                     suggest2Button = new JButton(nouvelleIconS2);
                    suggest2Button.setBounds(420,65,150,150);
                     suggest2Button.addActionListener(this);
                    suggestionPanel.add(suggest2Button);

                    JLabel priceLabel2 = new JLabel("Prix:"+ suggestProduct2.getPrix() +"€ ");
                    priceLabel2.setBounds(460,210,300,50);
                    suggestionPanel.add(priceLabel2);

                    JLabel IconScoreP2 = setlabelIconScore(suggestProduct2.getScore());
                    IconScoreP2.setBounds(470,240,80,80);
                    suggestionPanel.add(IconScoreP2);

                    //----------Product suggested 3----------------------------------------
                     ImageIcon pictureS3 = new ImageIcon(Objects.requireNonNull(MethodesFront.class.getResource("/"+suggestProduct3.getReference()+".png")));
                     Image imageS3 = pictureS3.getImage();
                     Image nouvelleImageS3 = imageS3.getScaledInstance(85, 89, Image.SCALE_SMOOTH);

                     ImageIcon nouvelleIconS3 = new ImageIcon(nouvelleImageS3);

                     suggest3Button = new JButton(nouvelleIconS3);
                    suggest3Button.setBounds(755,65,150,150);
                    suggest3Button.addActionListener(this);
                    suggestionPanel.add(suggest3Button);


                    JLabel priceLabel3 = new JLabel("Prix:"+ suggestProduct3.getPrix() +"€ ");
                    priceLabel3.setBounds(810,210,300,50);
                    suggestionPanel.add(priceLabel3);

                    JLabel IconScoreP3 = setlabelIconScore(suggestProduct3.getScore());
                    IconScoreP3.setBounds(820,240,80,80);
                    suggestionPanel.add(IconScoreP3);

                }
            }

        } catch (IOException | InterruptedException e) {
            logger.error("ERROR QUERY SUGGESTIONS : "+e.getMessage());
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
