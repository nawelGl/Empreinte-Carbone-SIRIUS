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
        salesFrame = new JFrame("Vos meilleures ventes");
        salesFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        salesFrame.setResizable(false);
        salesFrame.setSize(Template.LONGUEUR, Template.LARGEUR);
        salesFrame.setLocationRelativeTo(null);


        //-------------------panel header-------------
        MethodesFront.header(salesFrame,"Vos statistiques par produit",525);


        // main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBackground(Color.decode(Template.COULEUR_PRINCIPALE));




        JPanel beforePanel = new JPanel();


        //--------------ajout-----------------
        mainPanel.add(beforePanel);
        salesFrame.add(mainPanel);

        salesFrame.setVisible(true);



    }
}
