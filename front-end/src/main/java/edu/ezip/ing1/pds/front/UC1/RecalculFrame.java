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
import edu.ezip.ing1.pds.front.Template;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import static edu.ezip.ing1.pds.front.MethodesFront.attributeLetterScore;
import static edu.ezip.ing1.pds.front.MethodesFront.carbonFootPrintCalcul;

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
        calculButton.setBounds(300, 500, 100, 100);
        calculButton.addActionListener(this);
        frame.add(calculButton);

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

                JLabel reussiteLabel=new JLabel("REUSSITE update");
                reussiteLabel.setBounds(200,200,600,100);
                frame.add(reussiteLabel);





            }

        } catch (Exception ex) {
            System.out.println("Erreur dans la ");
        }
    }



}
