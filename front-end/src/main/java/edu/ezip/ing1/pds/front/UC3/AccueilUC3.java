package edu.ezip.ing1.pds.front.UC3;
//import edu.ezip.ing1.pds.front.*;

import edu.ezip.ing1.pds.front.AdminMenu;
import edu.ezip.ing1.pds.front.EcranAcceuil;
import edu.ezip.ing1.pds.front.MethodesFront;
import edu.ezip.ing1.pds.front.Template;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class AccueilUC3 extends JFrame implements ActionListener {




    JFrame accueilUC3;
    private JButton salesBtt;
    private JButton productBtt;
    private JButton categoryBtt;
    private JButton bilanBtt;
    private JButton backButton;


    String titre = "Home UC3 - Epigreen";
    String titreLabelSecondaire ="Entrez la référence de votre produit";
    String titreHeader="Saisiez votre article";

    int x=525;


    //constructeur de UC3
    public AccueilUC3() {


        // settings de bases
        accueilUC3 = new JFrame("Usecase2: Statistique");
        accueilUC3.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        accueilUC3.setResizable(false);
        accueilUC3.setSize(Template.LONGUEUR, Template.LARGEUR);
        accueilUC3.setLocationRelativeTo(null);


        //accueilUC3.getContentPane().setBackground(new Color(0xE7EBE4));
        //Initialisation des btt
        salesBtt = new JButton("Vos meilleurs ventes");
        productBtt = new JButton("Statistiques par produit");
        categoryBtt = new JButton("Statistiques par Empreinte Score");


        // Ajout de ActionListener
        salesBtt.addActionListener(this);
        productBtt.addActionListener(this);
        categoryBtt.addActionListener(this);




        //------------Panel header-------------------
        MethodesFront.header(accueilUC3,"Statistique pour votre business",525);

        //mainPanel (principal)
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBackground(Color.decode(Template.COULEUR_PRINCIPALE));

        ImageIcon backIcon= new ImageIcon(Objects.requireNonNull(MethodesFront.class.getResource("/back.png")));
        JButton backButton=new JButton(backIcon);
        //new JButton(backIcon);
        backButton.setBounds(1330,640,60,60);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            AdminMenu adminMenu= new AdminMenu();
            accueilUC3.dispose();

            }
        });
        mainPanel.add(backButton);



        //ajout des btts
        salesBtt.setBounds(200,250,300,200);
        productBtt.setBounds(550,250,300,200);
        categoryBtt.setBounds(900,250,300,200);
        backButton = new JButton("Retour à l'accueil");
        backButton.addActionListener(this);
        backButton.setBounds(650, 800, 150, 30);

        mainPanel.add(backButton);
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
            StatSales salesFrame = new StatSales();
            accueilUC3.dispose();
        }
        if(e.getSource() == productBtt) {
            RechercheReferenceUC3 rechercheReferenceUC3 = new RechercheReferenceUC3(titre,titreHeader,titreLabelSecondaire,x);
            accueilUC3.dispose();

        }
        if(e.getSource() == categoryBtt) {
            StatScore statScore = new StatScore();
            accueilUC3.dispose();
        }
        if(e.getSource() == backButton){
            EcranAcceuil ecranAcceuil = new EcranAcceuil();
            accueilUC3.dispose();
        }
    }
}
