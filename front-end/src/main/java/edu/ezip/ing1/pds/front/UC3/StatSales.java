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

            List<BestSeller> bestSellerList1 = (List<BestSeller>) bestSellersBefore.getBestSellers();
            bestProductBefore1= bestSellerList1.get(0);
            bestProductBefore2= bestSellerList1.get(1);
            bestProductBefore3= bestSellerList1.get(2);

            List<BestSeller> bestSellerList2 = (List<BestSeller>) bestSellersAfter.getBestSellers();
            bestProductBefore1= bestSellerList2.get(0);
            bestProductBefore2= bestSellerList2.get(1);
            bestProductBefore3= bestSellerList2.get(2);


        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        //----------------------------------------------------
        System.out.println("============================================================");
        System.out.println(bestProductBefore1.getReference());
        System.out.println(bestProductBefore1.getScore());
        System.out.println(bestProductBefore1.getSum());
        System.out.println("============================================================");

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
