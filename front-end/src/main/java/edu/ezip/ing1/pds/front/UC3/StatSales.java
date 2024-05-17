package edu.ezip.ing1.pds.front.UC3;

import edu.ezip.ing1.pds.business.dto.BestSeller;
import edu.ezip.ing1.pds.business.dto.BestSellers;
import edu.ezip.ing1.pds.client.UC3.SelectBestSellerAfter;
import edu.ezip.ing1.pds.client.UC3.SelectBestSellerBefore;
import edu.ezip.ing1.pds.commons.Request;
import edu.ezip.ing1.pds.front.MethodesFront;
import edu.ezip.ing1.pds.front.RoundedPanel;
import edu.ezip.ing1.pds.front.Template;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class StatSales extends JFrame {

    JFrame salesFrame;
    static BestSellers bestSellersBefore;
    static BestSellers bestSellersAfter;
    static BestSeller bestProductBefore1;
    static BestSeller bestProductBefore2;
    static BestSeller bestProductBefore3;
    static BestSeller bestProductAfter1;
    static BestSeller bestProductAfter2;
    static BestSeller bestProductAfter3;



    private JLabel text;
    public StatSales() {

        try {
            Request request = new Request();
            bestSellersBefore = SelectBestSellerBefore.launchSelectBestSellerBefore(request);
            bestSellersAfter = SelectBestSellerAfter.launchSelectBestSellerAfter(request);

            List<BestSeller> bestSellerList1 = bestSellersBefore.getBestSellers();
            bestProductBefore1= bestSellerList1.get(0);
            bestProductBefore2= bestSellerList1.get(1);
            bestProductBefore3= bestSellerList1.get(2);

            List<BestSeller> bestSellerList2 = bestSellersAfter.getBestSellers();
            bestProductAfter1= bestSellerList2.get(0);
            bestProductAfter2= bestSellerList2.get(1);
            bestProductAfter3= bestSellerList2.get(2);


        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }


        // installation de frame
        salesFrame = new JFrame("Statisque - best seller");
        salesFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        salesFrame.setResizable(false);
        salesFrame.setSize(Template.LONGUEUR, Template.LARGEUR);
        salesFrame.setLocationRelativeTo(null);


        //-------------------panel header-------------
        MethodesFront.header(salesFrame,"Vos meilleures ventes",525);


        // main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBackground(Color.decode(Template.COULEUR_PRINCIPALE));

        //-----BEFORE PANEL SETTING-------
        JPanel beforePanel = new JPanel(null);
        beforePanel.setBackground(Color.white);
        beforePanel.setBounds(50,40,600,650);


        //add a panel for title before
        RoundedPanel beforeTitlePanel = new RoundedPanel(30,30);
        beforeTitlePanel.setBackground(Color.decode(Template.COULEUR_SECONDAIRE));
        JLabel beforeTitle = new JLabel("Avant l'installation de notre service");
        beforeTitle.setFont(new Font("Avenir",Font.BOLD,17));
        beforeTitle.setForeground(Color.white);
        beforeTitlePanel.add(beforeTitle);

        beforeTitlePanel.setBounds(200,15,300,30);

        //---- add information BestSeller1 before installation of service----

        //photo
        JLabel photoLabelB1 = createPhotoLabel(bestProductBefore1.getReference());

        //score
        JLabel scoreLabelB1 = new JLabel();
        ImageIcon scoreIconB1 = selectScoreImage(bestProductBefore1.getScore());
        scoreLabelB1.setIcon(scoreIconB1);


        //reference
        JLabel referenceLabelB1 = new JLabel("Reference: "+ bestProductBefore1.getReference());
        referenceLabelB1.setFont(Template.FONT_ECRITURE);

        //genre
        JLabel genreLabelB1 = new JLabel("Genre: "+ bestProductBefore1.getGenre());
        genreLabelB1.setFont(Template.FONT_ECRITURE);

        //prix
        JLabel priceLabelB1 = new JLabel("Prix: "+ bestProductBefore1.getPrix()+" € ");
        priceLabelB1.setFont(Template.FONT_ECRITURE);

        //empreinte
        JLabel empreinteLabelB1 = new JLabel("Empreinte: "+ bestProductBefore1.getEmpreinte()+" gCO2e ");
        empreinteLabelB1.setFont(Template.FONT_ECRITURE);


        //---- add information BestSeller2 before installation of service----

        //photo
        JLabel photoLabelB2 = createPhotoLabel(bestProductBefore2.getReference());
        //score
        JLabel scoreLabelB2 = new JLabel();
        ImageIcon scoreIconB2 = selectScoreImage(bestProductBefore2.getScore());
        scoreLabelB2.setIcon(scoreIconB2);

        //reference
        JLabel referenceLabelB2 = new JLabel("Reference: "+ bestProductBefore2.getReference());
        referenceLabelB2.setFont(Template.FONT_ECRITURE);

        //genre
        JLabel genreLabelB2 = new JLabel("Genre: "+ bestProductBefore2.getGenre());
        genreLabelB2.setFont(Template.FONT_ECRITURE);

        //prix
        JLabel priceLabelB2 = new JLabel("Prix: "+ bestProductBefore2.getPrix()+" € ");
        priceLabelB2.setFont(Template.FONT_ECRITURE);

        //empreinte
        JLabel empreinteLabelB2 = new JLabel("Empreinte: "+ bestProductBefore2.getEmpreinte()+" gCO2e ");
        empreinteLabelB2.setFont(Template.FONT_ECRITURE);




        //---- add information BestSeller3 before installation of service----

        //photo
        JLabel photoLabelB3 = createPhotoLabel(bestProductBefore3.getReference());
        //score
        JLabel scoreLabelB3 = new JLabel();
        ImageIcon scoreIconB3 = selectScoreImage(bestProductBefore3.getScore());
        scoreLabelB3.setIcon(scoreIconB3);

        //reference
        JLabel referenceLabelB3 = new JLabel("Reference: "+ bestProductBefore3.getReference());
        referenceLabelB3.setFont(Template.FONT_ECRITURE);

        //genre
        JLabel genreLabelB3 = new JLabel("Genre: "+ bestProductBefore3.getGenre());
        genreLabelB3.setFont(Template.FONT_ECRITURE);

        //prix
        JLabel priceLabelB3 = new JLabel("Prix: "+ bestProductBefore3.getPrix()+" € ");
        priceLabelB3.setFont(Template.FONT_ECRITURE);

        //empreinte
        JLabel empreinteLabelB3 = new JLabel("Empreinte: "+ bestProductBefore3.getEmpreinte()+" gCO2e ");
        empreinteLabelB3.setFont(Template.FONT_ECRITURE);



        //Setbounds for product informations

        photoLabelB1.setBounds(30,15,180,185);
        photoLabelB2.setBounds(30,230,180,185);
        photoLabelB3.setBounds(30,445,180,185);

        scoreLabelB1.setBounds(435,60,50,50);
        scoreLabelB2.setBounds(435,285,50,50);
        scoreLabelB3.setBounds(435,500,50,50);

        referenceLabelB1.setBounds(250,60,300,50);
        priceLabelB1.setBounds(250,80,300,50);
        genreLabelB1.setBounds(250,100,300,50);
        empreinteLabelB1.setBounds(250,120,300,50);

        referenceLabelB2.setBounds(250,285,300,50);
        priceLabelB2.setBounds(250,305,300,50);
        genreLabelB2.setBounds(250,325,300,50);
        empreinteLabelB2.setBounds(250,345,300,50);


        referenceLabelB3.setBounds(250,500,300,50);
        priceLabelB3.setBounds(250,520,300,50);
        genreLabelB3.setBounds(250,540,300,50);
        empreinteLabelB3.setBounds(250,560,300,50);




        beforePanel.add(photoLabelB1);
        beforePanel.add(scoreLabelB1);

        beforePanel.add(referenceLabelB1);
        beforePanel.add(priceLabelB1);
        beforePanel.add(genreLabelB1);
        beforePanel.add(empreinteLabelB1);

        beforePanel.add(photoLabelB2);
        beforePanel.add(scoreLabelB2);

        beforePanel.add(referenceLabelB2);
        beforePanel.add(priceLabelB2);
        beforePanel.add(genreLabelB2);
        beforePanel.add(empreinteLabelB2);

        beforePanel.add(photoLabelB3);
        beforePanel.add(scoreLabelB3);

        beforePanel.add(referenceLabelB3);
        beforePanel.add(priceLabelB3);
        beforePanel.add(genreLabelB3);
        beforePanel.add(empreinteLabelB3);


        //afterPanel setting
        JPanel afterPanel = new JPanel(null);
        afterPanel.setBackground(Color.white);
        afterPanel.setBounds(750,40,600,650);

        //add a panel for title after
        RoundedPanel afterTitlePanel = new RoundedPanel(30,30);
        afterTitlePanel.setBackground(Color.decode(Template.COULEUR_SECONDAIRE));
        JLabel afterTitle = new JLabel("Après l'installation de notre service");
        afterTitle.setFont(new Font("Avenir",Font.BOLD,17));
        afterTitle.setForeground(Color.white);
        afterTitlePanel.add(afterTitle);

        afterTitlePanel.setBounds(900,15,300,30);


        //---- add information BestSeller1 after installation of service----

        //photo
        JLabel photoLabelA1 = createPhotoLabel(bestProductAfter1.getReference());
        //score
        JLabel scoreLabelA1 = new JLabel();
        ImageIcon scoreIconA1 = selectScoreImage(bestProductAfter1.getScore());
        scoreLabelA1.setIcon(scoreIconA1);

        //reference
        JLabel referenceLabelA1 = new JLabel("Reference: "+ bestProductAfter1.getReference());
        referenceLabelA1.setFont(Template.FONT_ECRITURE);

        //genre
        JLabel genreLabelA1 = new JLabel("Genre: "+ bestProductAfter1.getGenre());
        genreLabelA1.setFont(Template.FONT_ECRITURE);

        //prix
        JLabel priceLabelA1 = new JLabel("Prix: "+ bestProductAfter1.getPrix()+" € ");
        priceLabelA1.setFont(Template.FONT_ECRITURE);

        //empreinte
        JLabel empreinteLabelA1 = new JLabel("Empreinte: "+ bestProductAfter1.getEmpreinte()+" gCO2e ");
        empreinteLabelA1.setFont(Template.FONT_ECRITURE);


        //---- add information BestSeller1 after installation of service----

        //photo
        JLabel photoLabelA2 = createPhotoLabel(bestProductAfter2.getReference());
        //score
        JLabel scoreLabelA2 = new JLabel();
        ImageIcon scoreIconA2 = selectScoreImage(bestProductAfter2.getScore());
        scoreLabelA2.setIcon(scoreIconA2);
        //reference
        JLabel referenceLabelA2 = new JLabel("Reference: "+ bestProductAfter2.getReference());
        referenceLabelA2.setFont(Template.FONT_ECRITURE);

        //genre
        JLabel genreLabelA2 = new JLabel("Genre: "+ bestProductAfter2.getGenre());
        genreLabelA2.setFont(Template.FONT_ECRITURE);

        //prix
        JLabel priceLabelA2 = new JLabel("Prix: "+ bestProductAfter2.getPrix()+" € ");
        priceLabelA2.setFont(Template.FONT_ECRITURE);

        //empreinte
        JLabel empreinteLabelA2 = new JLabel("Empreinte: "+ bestProductAfter2.getEmpreinte()+" gCO2e ");
        empreinteLabelA2.setFont(Template.FONT_ECRITURE);


        //---- add information BestSeller1 after installation of service----

        //photo
        JLabel photoLabelA3 = createPhotoLabel(bestProductAfter3.getReference());
        //score
        JLabel scoreLabelA3 = new JLabel();
        ImageIcon scoreIconA3 = selectScoreImage(bestProductAfter3.getScore());
        scoreLabelA3.setIcon(scoreIconA3);
        //reference
        JLabel referenceLabelA3 = new JLabel("Reference: "+ bestProductAfter3.getReference());
        referenceLabelA3.setFont(Template.FONT_ECRITURE);

        //genre
        JLabel genreLabelA3 = new JLabel("Genre: "+ bestProductAfter3.getGenre());
        genreLabelA3.setFont(Template.FONT_ECRITURE);

        //prix
        JLabel priceLabelA3 = new JLabel("Prix: "+ bestProductAfter3.getPrix()+" € ");
        priceLabelA3.setFont(Template.FONT_ECRITURE);

        //empreinte
        JLabel empreinteLabelA3 = new JLabel("Empreinte: "+ bestProductAfter3.getEmpreinte()+" gCO2e ");
        empreinteLabelA3.setFont(Template.FONT_ECRITURE);





        photoLabelA1.setBounds(30,15,180,185);
        photoLabelA2.setBounds(30,230,180,185);
        photoLabelA3.setBounds(30,445,180,185);

        scoreLabelA1.setBounds(435,60,50,50);
        scoreLabelA2.setBounds(435,285,50,50);
        scoreLabelA3.setBounds(435,500,50,50);

        referenceLabelA1.setBounds(250,60,300,50);
        priceLabelA1.setBounds(250,80,300,50);
        genreLabelA1.setBounds(250,100,300,50);
        empreinteLabelA1.setBounds(250,120,300,50);

        referenceLabelA2.setBounds(250,285,300,50);
        priceLabelA2.setBounds(250,305,300,50);
        genreLabelA2.setBounds(250,325,300,50);
        empreinteLabelA2.setBounds(250,345,300,50);


        referenceLabelA3.setBounds(250,500,300,50);
        priceLabelA3.setBounds(250,520,300,50);
        genreLabelA3.setBounds(250,540,300,50);
        empreinteLabelA3.setBounds(250,560,300,50);


        afterPanel.add(photoLabelA1);
        afterPanel.add(scoreLabelA1);

        afterPanel.add(referenceLabelA1);
        afterPanel.add(priceLabelA1);
        afterPanel.add(genreLabelA1);
        afterPanel.add(empreinteLabelA1);

        afterPanel.add(photoLabelA2);
        afterPanel.add(scoreLabelA2);

        afterPanel.add(referenceLabelA2);
        afterPanel.add(priceLabelA2);
        afterPanel.add(genreLabelA2);
        afterPanel.add(empreinteLabelA2);

        afterPanel.add(photoLabelA3);
        afterPanel.add(scoreLabelA3);

        afterPanel.add(referenceLabelA3);
        afterPanel.add(priceLabelA3);
        afterPanel.add(genreLabelA3);
        afterPanel.add(empreinteLabelA3);


        //---------ajout de back btt

        ImageIcon backIcon= new ImageIcon(Objects.requireNonNull(MethodesFront.class.getResource("/back.png")));
        JButton backButton=new JButton(backIcon);
        //new JButton(backIcon);
        backButton.setBounds(1330,633,60,60);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AccueilUC3 accueilUC3=new AccueilUC3();
                salesFrame.dispose();

            }
        });

        //--------------ajout-----------------

        mainPanel.add(afterTitlePanel);
        mainPanel.add(beforeTitlePanel);
        mainPanel.add(backButton);
        mainPanel.add(beforePanel);
        mainPanel.add(afterPanel);

        salesFrame.add(mainPanel);

        salesFrame.setVisible(true);



    }
    private ImageIcon selectScoreImage(String score) {
        ImageIcon icon;
        switch (score) {
            case "A":
                icon = new ImageIcon(getClass().getResource("/icon_A.png"));
                break;
            case "B":
                icon = new ImageIcon(getClass().getResource("/icon_B.png"));
                break;
            case "C":
                icon = new ImageIcon(getClass().getResource("/icon_C.png"));
                break;
            case "D":
                icon = new ImageIcon(getClass().getResource("/icon_D.png"));
                break;
            case "E":
                icon = new ImageIcon(getClass().getResource("/icon_E.png"));
                break;
            default:
                icon = null;
                break;
        }
        return icon;
    }

    private JLabel createPhotoLabel(int reference) {
        String photoName = "/" + Integer.toString(reference) + ".png";
        ImageIcon imageIcon = new ImageIcon(getClass().getResource(photoName));
        Image image = imageIcon.getImage();
        Image scaledImage = image.getScaledInstance(180, 185, Image.SCALE_SMOOTH);
        ImageIcon scaledImageIcon = new ImageIcon(scaledImage);
        JLabel photoLabel = new JLabel();
        photoLabel.setIcon(scaledImageIcon);
        return photoLabel;
    }

}
