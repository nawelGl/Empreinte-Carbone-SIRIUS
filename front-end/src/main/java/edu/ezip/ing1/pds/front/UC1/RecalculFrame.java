package edu.ezip.ing1.pds.front.UC1;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.ezip.ing1.pds.business.dto.Marque;
import edu.ezip.ing1.pds.business.dto.Produit;
import edu.ezip.ing1.pds.business.dto.TransportMode;
import edu.ezip.ing1.pds.business.dto.Ville;
import edu.ezip.ing1.pds.client.SelectProductByReference;
import edu.ezip.ing1.pds.client.UC1.SelectAllReferences;
import edu.ezip.ing1.pds.client.UC1.SelectTransportModeByID;
import edu.ezip.ing1.pds.client.UC1.SelectVilleById;
import edu.ezip.ing1.pds.client.UpdateInfoProduct;
import edu.ezip.ing1.pds.front.MethodesFront;
import edu.ezip.ing1.pds.front.RoundedPanel;
import edu.ezip.ing1.pds.front.Template;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import static edu.ezip.ing1.pds.front.MethodesFront.*;

public class RecalculFrame implements ActionListener {

    JFrame frame;
    JButton calculButton;

    ArrayList<Integer> referencesList;
    Produit product;
    Marque marque;
    Ville villeArrive;
    Ville villeDepart;
    TransportMode transportMode;

    Double carbonFootPrint;
    String score;










    public RecalculFrame() {
        //-----setting de base--------

        frame = new JFrame("Recalculs empreintes carbone");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setSize(Template.LONGUEUR, Template.LARGEUR);
        frame.setLocationRelativeTo(null);

        //------header-----------
        MethodesFront.header(frame, "Recalculer les empreintes carbone des produit ", 500);

        //----------panel principal--------------
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBackground(Color.decode(Template.COULEUR_PRINCIPALE));
        frame.getContentPane().add(BorderLayout.CENTER, mainPanel);
        calculButton=new JButton("reCalculer les empreinte");
        calculButton.setBounds(100, 100, 100, 100);
        calculButton.addActionListener(this);
        mainPanel.add(calculButton);


        //----------panel Recalcule bouton
        RoundedPanel calculPanel=new RoundedPanel(40,40);
        calculPanel.setBackground(Color.decode(Template.COULEUR_SECONDAIRE));
        calculPanel.setBounds(35, 60,400,600);
        calculPanel.add(calculButton);
        mainPanel.add(calculPanel);

        //---------panel SCORE
        JPanel scorePanel= new JPanel();
        scorePanel.setLayout(null);
        scorePanel.setBounds(438,60,932,600);
        scorePanel.setBackground(Color.decode(Template.COULEUR_SECONDAIRE));


        JLabel modifScoreLabel= new JLabel("Modifier les bornes des scores carbone");
        modifScoreLabel.setBounds(300,20,400,50);
        modifScoreLabel.setFont(Template.FONT_TITRES);
        modifScoreLabel.setForeground(Color.WHITE);

        JLabel scoreA= setlabelIconScore("A");
        scoreA.setOpaque(false);
        scoreA.setLocation(100,100);
        scorePanel.add(scoreA);

        JLabel borneInf= new JLabel("Borne inf: ");
        borneInf.setFont(Template.FONT_ECRITURE2);
        borneInf.setForeground(Color.WHITE);
        borneInf.setBounds(7,250,200,50);
        JLabel borneSup= new JLabel("Borne sup: ");
        borneSup.setFont(Template.FONT_ECRITURE2);
        borneSup.setForeground(Color.WHITE);
        borneSup.setBounds(7,350,200,50);
        scorePanel.add(borneInf);
        scorePanel.add(borneSup);

        JLabel unityInf= new JLabel("gCO2e ");
        unityInf.setFont(Template.FONT_ECRITURE2);
        unityInf.setForeground(Color.WHITE);
        unityInf.setBounds(830,250,200,50);
        JLabel unitySup= new JLabel("gCO2e ");
        unitySup.setFont(Template.FONT_ECRITURE2);
        unitySup.setForeground(Color.WHITE  );
        unitySup.setBounds(830,350,200,50);
        scorePanel.add(unityInf);
        scorePanel.add(unitySup);




        scorePanel.add(modifScoreLabel);


        mainPanel.add(scorePanel);





        frame.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e)
    {

        try {
            referencesList = SelectAllReferences.launchSelectAllReferences();
            for (final int reference : referencesList) {
                try {
                    product = SelectProductByReference.launchSelectProductByReference(String.valueOf(reference));
                    transportMode = SelectTransportModeByID.launchSelectTransportModeById(String.valueOf(product.getIdTransportMode()));
                    villeArrive = SelectVilleById.launchSelectVilleById(String.valueOf(product.getIdVilleArrive()));
                    villeDepart = SelectVilleById.launchSelectVilleById(String.valueOf(product.getIdVilleDepart()));
                    carbonFootPrint = carbonFootPrintCalcul(villeDepart.getCoordLatitude(), villeDepart.getCoordLongitude(), villeArrive.getCoordLatitude(), villeArrive.getCoordLongitude(), transportMode.getCoeffEmission(), product.getPoids());
                    score = attributeLetterScore(carbonFootPrint);

                    //Faire UPDATE BASE ---------------------------
                    ObjectMapper objectMapper = new ObjectMapper();
                    Produit produit = new Produit();
                    produit.setEmpreinte(carbonFootPrint);
                    produit.setScore(score);
                    produit.setReference(reference);
                    try {
                        String responseBody = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(produit);
                        UpdateInfoProduct.launchUpdateProduit(responseBody);

                    } catch (IOException | InterruptedException ex) {
                        System.out.println("Eerreur de l'update pour le produit de referebce " + reference);
                    }

                    System.out.println("SUCCES DE L'UPDATE");
                }catch (Exception exe){
                    System.out.println("erreur sur une des requete "+ exe.getMessage());
                }

            }

        } catch (Exception ex) {
            System.out.println("Erreur dans la ");
        }
    }



}
