package edu.ezip.ing1.pds.front.UC1;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.ezip.ing1.pds.business.dto.*;
import edu.ezip.ing1.pds.client.SelectProductByReference;
import edu.ezip.ing1.pds.client.UC1.SelectAllReferences;
import edu.ezip.ing1.pds.client.UC1.SelectAllScore;
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
import java.util.Objects;
import java.util.Set;

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


        //----------panel Recalcule bouton
        JPanel calculPanel=new JPanel();
        calculPanel.setLayout(null);
        calculPanel.setBackground(Color.decode(Template.COULEUR_SECONDAIRE));
        calculPanel.setBounds(35, 60,400,600);


        JLabel empreinteLabel= new JLabel("Re-calcul des empreintes");
        empreinteLabel.setFont(Template.FONT_TITRES);
        empreinteLabel.setForeground(Color.WHITE);
        empreinteLabel.setBounds(100,20,300,50);
        calculPanel.add(empreinteLabel);

        calculButton=new JButton("<html><center><br><img src='" +
                getClass().getResource("/refresh.png") +
                "'><br><font size='5' face='Avenir'>Mettre à jour</font></center></html>");
                //new JButton(new ImageIcon(Objects.requireNonNull(MethodesFront.class.getResource("/refresh.png"))));
        calculButton.setBounds(100, 200, 180, 180);
        calculButton.addActionListener(this);
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


        JLabel borneInf= new JLabel("Borne inf: ");
        borneInf.setFont(Template.FONT_ECRITURE2);
        borneInf.setForeground(Color.WHITE);
        borneInf.setBounds(7,280,200,50);
        JLabel borneSup= new JLabel("Borne sup: ");
        borneSup.setFont(Template.FONT_ECRITURE2);
        borneSup.setForeground(Color.WHITE);
        borneSup.setBounds(7,350,200,50);
        scorePanel.add(borneInf);
        scorePanel.add(borneSup);

        JLabel unityInf= new JLabel("gCO2e ");
        unityInf.setFont(Template.FONT_ECRITURE2);
        unityInf.setForeground(Color.WHITE);
        unityInf.setBounds(830,280,200,50);
        JLabel unitySup= new JLabel("gCO2e ");
        unitySup.setFont(Template.FONT_ECRITURE2);
        unitySup.setForeground(Color.WHITE  );
        unitySup.setBounds(830,350,200,50);
        scorePanel.add(unityInf);
        scorePanel.add(unitySup);

        //recuperation des scores------
        Score scoreA = null;
        Score scoreB = null;
        Score scoreC = null;
        Score scoreD = null;
        Score scoreE = null;

        Scores scores = null;
        try {
            scores = SelectAllScore.launchSelectAllScore(); //fait la requete qui renvoit une liste de score
            Set<Score> scoreSet = scores.getScores();

            if (scoreSet != null) {

                for (Score score : scoreSet) {
                    String lettreScore = score.getlettreScore();
                    if (lettreScore.equals("A")) {
                        scoreA = score;
                    } else if (lettreScore.equals("B")) {
                        scoreB = score;
                    } else if (lettreScore.equals("C")) {
                        scoreC = score;
                    } else if (lettreScore.equals("D")) {
                        scoreD = score;
                    } else if (lettreScore.equals("E")) {
                        scoreE = score;
                    }
                }
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }


        JLabel A=setlabelIconScore(scoreA.getlettreScore());
        JLabel B=setlabelIconScore(scoreB.getlettreScore());
        JLabel C=setlabelIconScore(scoreC.getlettreScore());
        JLabel D=setlabelIconScore(scoreD.getlettreScore());
        JLabel E=setlabelIconScore(scoreE.getlettreScore());

        JLabel bornesInfValues= new JLabel(scoreA.getborneInf()+ "                  "+ scoreB.getborneInf()+"                 "+scoreC.getborneInf()+"                 "+scoreD.getborneInf()+"                 "+scoreE.getborneInf());
        bornesInfValues.setBounds(170,290,600,30);
        bornesInfValues.setFont(Template.FONT_ECRITURE2);
        bornesInfValues.setForeground(Color.WHITE);
        scorePanel.add(bornesInfValues);

        JLabel bornesSupValues= new JLabel(scoreA.getborneSup()+ "               "+ scoreB.getborneSup()+"               "+scoreC.getborneSup()+"                "+scoreD.getborneSup()+"                "+scoreE.getborneSup());
        bornesSupValues.setBounds(170,360,600,30);
        bornesSupValues.setFont(Template.FONT_ECRITURE2);
        bornesSupValues.setForeground(Color.WHITE);
        scorePanel.add(bornesSupValues);

        A.setBounds(170,150,100,100);
        A.setOpaque(false);
        scorePanel.add(A);

        B.setBounds(300,150,100,100);
        B.setOpaque(false);
        scorePanel.add(B);

        C.setBounds(430,150,100,100);
        C.setOpaque(false);
        scorePanel.add(C);

        D.setBounds(560,150,100,100);
        D.setOpaque(false);
        scorePanel.add(D);

        E.setBounds(690,150,100,100);
        E.setOpaque(false);
        scorePanel.add(E);



        RoundedPanel roundedPanelChoice= new RoundedPanel(40,40);
        roundedPanelChoice.setLayout(null);
        roundedPanelChoice.setBackground(Color.WHITE);
        roundedPanelChoice.setBounds(120,470,700,80);

        JLabel selection= new JLabel("Selectionner le score à modifier : ");
        selection.setFont(Template.FONT_ECRITURE2);
        selection.setBounds(150,15,300,50);
        roundedPanelChoice.add(selection);

        String [] scoresLettreList= {scoreA.getlettreScore(),scoreB.getlettreScore(),scoreC.getlettreScore(),scoreD.getlettreScore(),scoreE.getlettreScore()};
        JComboBox<String> scoreBox= new JComboBox<>(scoresLettreList);
        scoreBox.setBounds(430,13,60,60);


        Score finalScoreA = scoreA;
        Score finalScoreB = scoreB;
        Score finalScoreC = scoreC;
        Score finalScoreD = scoreD;
        Score finalScoreE = scoreE;
        scoreBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedScore = (String) scoreBox.getSelectedItem();

                JPanel panel = new JPanel();
                panel.setLayout(new GridLayout(2, 2));


                JTextField borneInfField;
                JTextField borneSupField ;

                if(scoreBox.getSelectedItem().equals("A")){
                    borneInfField=new JTextField(String.valueOf(finalScoreA.getborneInf()));
                    borneSupField=new JTextField(String.valueOf(finalScoreA.getborneSup()));

                } else if (scoreBox.getSelectedItem().equals("B")) {
                    borneInfField=new JTextField(String.valueOf(finalScoreB.getborneInf()));
                    borneSupField=new JTextField(String.valueOf(finalScoreB.getborneSup()));

                } else if (scoreBox.getSelectedItem().equals("C")) {
                    borneInfField=new JTextField(String.valueOf(finalScoreC.getborneInf()));
                    borneSupField=new JTextField(String.valueOf(finalScoreC.getborneSup()));


                } else if (scoreBox.getSelectedItem().equals("D")) {
                    borneInfField=new JTextField(String.valueOf(finalScoreD.getborneInf()));
                    borneSupField=new JTextField(String.valueOf(finalScoreD.getborneSup()));


                } else if (scoreBox.getSelectedItem().equals("E")) {
                    borneInfField=new JTextField(String.valueOf(finalScoreE.getborneInf()));
                    borneSupField=new JTextField(String.valueOf(finalScoreE.getborneSup()));


                } else{
                    borneInfField=new JTextField();
                    borneSupField=new JTextField();}


                // Vous pouvez initialiser les valeurs des champs de texte avec les bornes actuelles
                // par exemple, si vous avez des objets Score pour chaque score sélectionné,
                // vous pouvez utiliser score.getborneInf() et score.getborneSup() pour cela

                panel.add(new JLabel("Borne inférieure: "));
                panel.add(borneInfField);
                panel.add(new JLabel("Borne supérieure: "));
                panel.add(borneSupField);

                int result = JOptionPane.showConfirmDialog(null, panel, "Modifier les bornes du score " + selectedScore,JOptionPane.OK_CANCEL_OPTION);
                        //new ImageIcon(Objects.requireNonNull(RecalculFrame.class.getResource("/icon_"+selectedScore+".png"))));
                if (result == JOptionPane.OK_OPTION) {


                    String borneInfText = borneInfField.getText();
                    String borneSupText = borneSupField.getText();

                    //TODO: Faire la requete update des bornes where scoreLettre='lettre'
                    //TODO: recharger la page une fois que la borne est update

                }
            }
        });

        roundedPanelChoice.add(scoreBox);


        scorePanel.add(roundedPanelChoice);









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
