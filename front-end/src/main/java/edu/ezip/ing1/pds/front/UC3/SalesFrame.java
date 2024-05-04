package edu.ezip.ing1.pds.front.UC3;

import edu.ezip.ing1.pds.business.dto.BestSeller;
import edu.ezip.ing1.pds.client.UC3.SelectBestSellerAfter;
import edu.ezip.ing1.pds.client.UC3.SelectBestSellerBefore;
import edu.ezip.ing1.pds.commons.Request;
import edu.ezip.ing1.pds.front.MethodesFront;
import edu.ezip.ing1.pds.front.Template;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.IOException;

public class SalesFrame extends JFrame {

    JFrame salesFrame;
    private static BestSeller bestSellerBefore;
    private static BestSeller bestSellerAfter;


    private JLabel text;
    public SalesFrame() {

        Request request = new Request();

        try {
            bestSellerBefore = SelectBestSellerBefore.launchSelectBestSellerBefore(request);
            bestSellerAfter = SelectBestSellerAfter.launchSelectBestSellerBefore(request);
            System.out.println(bestSellerBefore.toString());
            System.out.println(bestSellerAfter.toString());

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
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



        //beforPanel setting
        JPanel beforePanel = new JPanel(null);
        beforePanel.setBackground(Color.white);
        beforePanel.setBounds(50,50,600,600);

        //afterPanel setting
        JPanel afterPanel = new JPanel(null);
        afterPanel.setBackground(Color.white);
        afterPanel.setBounds(750,50,600,600);


        //--------------ajout-----------------
        mainPanel.add(beforePanel);
        mainPanel.add(afterPanel);
        salesFrame.add(mainPanel);

        salesFrame.setVisible(true);



    }
}
