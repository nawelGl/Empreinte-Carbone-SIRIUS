package edu.ezip.ing1.pds.front;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AccueilUC3 extends JFrame implements ActionListener {

    JFrame accueilUC3;
    private JButton salesBtt;
    private JButton productBtt;
    private JButton categoryBtt;
    private JButton bilanBtt;


    String titre = "Home UC3 - Epigreen";
    String titreLabelSecondaire ="Entrez la référence de votre produit";
    String titreHeader="Saisiez votre article";

    int x=525;


    //constructeur de UC3
    public AccueilUC3() {


        // settings de bases
        accueilUC3 = new JFrame("Usecase2: Statistique");
        accueilUC3.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        accueilUC3.setLocationRelativeTo(null);
        accueilUC3.setResizable(false);
        accueilUC3.setSize(Template.LONGUEUR, Template.LARGEUR);


        //accueilUC3.getContentPane().setBackground(new Color(0xE7EBE4));
        //Initialisation des btt
        salesBtt = new JButton("Vos meilleurs ventes");
        productBtt = new JButton("Statistiques par produit");
        categoryBtt = new JButton("Statistiques par categorie");
       // bilanBtt = new JButton("Votre bilan total");

        // Ajout de ActionListener
        salesBtt.addActionListener(this);
        productBtt.addActionListener(this);
        categoryBtt.addActionListener(this);
        //bilanBtt.addActionListener(this);



        //------------Panel header-------------------
        Methodes.header(accueilUC3,"Statistique pour votre business",525);

        //mainPanel (principal)
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBackground(Color.decode(Template.COULEUR_PRINCIPALE));



        //ajout des btts
        salesBtt.setBounds(170,170,300,330);
        productBtt.setBounds(520,170,300,330);
        categoryBtt.setBounds(870,170,300,330);


        mainPanel.add(salesBtt);
        mainPanel.add(productBtt);
        mainPanel.add(categoryBtt);

        accueilUC3.getContentPane().add(BorderLayout.CENTER,mainPanel);


        //--------------------------------
        accueilUC3.setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == salesBtt) {
            accueilUC3.dispose();
            SalesFrame salesFrame = new SalesFrame();
            salesFrame.createSalesFrame();
        }
        if(e.getSource() == productBtt) {
            accueilUC3.dispose();
            RechercheReferenceUC3 rechercheReferenceUC3 = new RechercheReferenceUC3(titre,titreHeader,titreLabelSecondaire,x);
            //StatProduct statProduct = new StatProduct(2,8);
        }
        if(e.getSource() == categoryBtt) {
            //accueilUC3.dispose();

        }
    }
}
