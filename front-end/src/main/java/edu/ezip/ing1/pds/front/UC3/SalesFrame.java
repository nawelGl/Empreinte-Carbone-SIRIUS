package edu.ezip.ing1.pds.front.UC3;

import edu.ezip.ing1.pds.front.MethodesFront;
import edu.ezip.ing1.pds.front.Template;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class SalesFrame extends JFrame {

    JFrame salesFrame;


    private JLabel text;
    public void createSalesFrame() {
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
