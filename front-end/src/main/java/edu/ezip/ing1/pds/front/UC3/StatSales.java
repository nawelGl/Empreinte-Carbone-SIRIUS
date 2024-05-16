package edu.ezip.ing1.pds.front.UC3;

import edu.ezip.ing1.pds.business.dto.BestSeller;
import edu.ezip.ing1.pds.business.dto.BestSellers;
import edu.ezip.ing1.pds.client.UC3.SelectBestSellerAfter;
import edu.ezip.ing1.pds.client.UC3.SelectBestSellerBefore;
import edu.ezip.ing1.pds.commons.Request;
import edu.ezip.ing1.pds.front.MethodesFront;
import edu.ezip.ing1.pds.front.Template;

import javax.swing.*;
import java.awt.*;
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
        beforePanel.setBounds(50,20,600,650);


        //---- add information BestSeller1 before installation of service----

        //photo
        JLabel photoLabelB1 = createPhotoLabel(bestProductBefore1.getReference());

        //score
        JLabel scoreLabelB1 = new JLabel();
        ImageIcon scoreIconB1 = selectScoreImage(bestProductBefore1.getScore());
        scoreLabelB1.setIcon(scoreIconB1);


        //---- add information BestSeller1 before installation of service----

        //photo
        JLabel photoLabelB2 = createPhotoLabel(bestProductBefore2.getReference());
        //score
        JLabel scoreLabelB2 = new JLabel();
        ImageIcon scoreIconB2 = selectScoreImage(bestProductBefore2.getScore());
        scoreLabelB2.setIcon(scoreIconB2);


        //---- add information BestSeller1 before installation of service----

        //photo
        JLabel photoLabelB3 = createPhotoLabel(bestProductBefore3.getReference());
        //score
        JLabel scoreLabelB3 = new JLabel();
        ImageIcon scoreIconB3 = selectScoreImage(bestProductBefore3.getScore());
        scoreLabelB3.setIcon(scoreIconB3);





        photoLabelB1.setBounds(30,15,180,185);
        photoLabelB2.setBounds(30,230,180,185);
        photoLabelB3.setBounds(30,445,180,185);

        scoreLabelB1.setBounds(420,60,50,50);
        scoreLabelB2.setBounds(420,285,50,50);
        scoreLabelB3.setBounds(420,500,50,50);


        beforePanel.add(photoLabelB1);
        beforePanel.add(scoreLabelB1);

        beforePanel.add(photoLabelB2);
        beforePanel.add(scoreLabelB2);

        beforePanel.add(photoLabelB3);
        beforePanel.add(scoreLabelB3);


        //afterPanel setting
        JPanel afterPanel = new JPanel(null);
        afterPanel.setBackground(Color.white);
        afterPanel.setBounds(750,20,600,650);


        //---- add information BestSeller1 after installation of service----

        //photo
        JLabel photoLabelA1 = createPhotoLabel(bestProductAfter1.getReference());
        //score
        JLabel scoreLabelA1 = new JLabel();
        ImageIcon scoreIconA1 = selectScoreImage(bestProductAfter1.getScore());
        scoreLabelA1.setIcon(scoreIconA1);


        //---- add information BestSeller1 after installation of service----

        //photo
        JLabel photoLabelA2 = createPhotoLabel(bestProductAfter2.getReference());
        //score
        JLabel scoreLabelA2 = new JLabel();
        ImageIcon scoreIconA2 = selectScoreImage(bestProductAfter2.getScore());
        scoreLabelA2.setIcon(scoreIconA2);


        //---- add information BestSeller1 after installation of service----

        //photo
        JLabel photoLabelA3 = createPhotoLabel(bestProductAfter3.getReference());
        //score
        JLabel scoreLabelA3 = new JLabel();
        ImageIcon scoreIconA3 = selectScoreImage(bestProductAfter3.getScore());
        scoreLabelA3.setIcon(scoreIconA3);





        photoLabelA1.setBounds(30,15,180,185);
        photoLabelA2.setBounds(30,230,180,185);
        photoLabelA3.setBounds(30,445,180,185);

        scoreLabelA1.setBounds(420,60,50,50);
        scoreLabelA2.setBounds(420,285,50,50);
        scoreLabelA3.setBounds(420,500,50,50);


        afterPanel.add(photoLabelA1);
        afterPanel.add(scoreLabelA1);

        afterPanel.add(photoLabelA2);
        afterPanel.add(scoreLabelA2);

        afterPanel.add(photoLabelA3);
        afterPanel.add(scoreLabelA3);


        //--------------ajout-----------------
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
