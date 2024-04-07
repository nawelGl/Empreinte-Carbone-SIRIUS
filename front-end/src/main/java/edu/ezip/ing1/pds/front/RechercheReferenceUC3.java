package edu.ezip.ing1.pds.front;


import edu.ezip.ing1.pds.business.dto.Vente;
import edu.ezip.ing1.pds.client.SelectAfterVenteByReference;
import edu.ezip.ing1.pds.client.SelectBeforeVenteByReference;
import edu.ezip.ing1.pds.commons.Request;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class RechercheReferenceUC3 implements ActionListener {

    //Boutons :


    static Vente venteBefore;
    static Vente venteAfter;

    static int quantiteBefore;
    static int quantiteAfter;

    JFrame menuEmpreinteCarbone;
    JButton boutonConfirmer;
    JTextField searchBar;
    String titre;
    Boolean referenceIsNotAnInt = false;


    String titreLabelSecondaire ;

    String titreHeader;
    int x;

    //Bouton pour acceder aux categories :
    JButton boutonCategories = new JButton();

    //Constructeur :
    public RechercheReferenceUC3(String titreFrame, String titreHeader, String titreLabelSecondaire, int x){
        //Paramétrages de base :
        menuEmpreinteCarbone = new JFrame();
        menuEmpreinteCarbone.setTitle(titreFrame);
        menuEmpreinteCarbone.setSize(Template.LONGUEUR, Template.LARGEUR);
        menuEmpreinteCarbone.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menuEmpreinteCarbone.setLocationRelativeTo(null);
        menuEmpreinteCarbone.setResizable(false);

        //----------panel header--------------
        Methodes.header(menuEmpreinteCarbone, titreHeader, x);

        //----------panel principal--------------
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBackground(Color.decode(Template.COULEUR_PRINCIPALE));
        menuEmpreinteCarbone.getContentPane().add(BorderLayout.CENTER, mainPanel);

        //----------panel secondaire--------------
        //JPanel secondPanel = new JPanel();
        RoundedPanel secondPanel = new RoundedPanel(60,60);
        secondPanel.setLayout(null);
        secondPanel.setBackground(Color.decode(Template.COULEUR_SECONDAIRE));
        //secondPanel.setSize(new Dimension(30, 100));
        secondPanel.setBounds(300, 200, 800, 350);

        //Ajout du JLabel :
        JLabel titrePanelSecondaire = new JLabel(titreLabelSecondaire);
        titrePanelSecondaire.setForeground(Color.WHITE);
        titrePanelSecondaire.setFont(new Font("Avenir", Font.BOLD, 22));
        titrePanelSecondaire.setBounds(40, 25, 760, 80);
        secondPanel.add(titrePanelSecondaire);

        //Ajout de la search bar :
        searchBar = new RoundJTextField(100);
        searchBar.setBounds(80, 150, 650, 40);
        boutonConfirmer = new JButton("Valider");
        boutonConfirmer.addActionListener(this);
        boutonConfirmer.setBounds(360,230, 80, 30);
        secondPanel.add(searchBar);
        secondPanel.add(boutonConfirmer);

        mainPanel.add(secondPanel);

        //Ajout du bouton pour les categories :
        boutonCategories.setText("Vous n'avez pas encore la référence de votre produit ?");
        boutonCategories.setBounds(460, 740, 470, 40);
        boutonCategories.addActionListener((this));
        mainPanel.add(boutonCategories);

        menuEmpreinteCarbone.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e){
        if(e.getSource() == boutonConfirmer){
            try {
                String refEnString = searchBar.getText();
                try{
                    Integer refEnInt = Integer.parseInt(refEnString);
                } catch (Exception ex){
                    System.out.println("La référence entrée n'est pas un String : " + ex.getMessage());
                    referenceIsNotAnInt = true;
                }

                if(referenceIsNotAnInt){
                    JOptionPane.showMessageDialog(menuEmpreinteCarbone, "Attention, la référence que vous avez entrée contient des caractères interdits. Veuillez réessayer en entrant des chiffres uniquement.", "Format de référence incorrect.", JOptionPane.ERROR_MESSAGE);
                    searchBar.setText("");
                    referenceIsNotAnInt = false;
                } else {
                    Request request = new Request();
                    request.setRequestContent(refEnString);
                    venteBefore = SelectBeforeVenteByReference.launchSelectVenteByReference(request);
                    venteAfter= SelectAfterVenteByReference.launchSelectVenteByReference(request);

                    quantiteBefore = SelectBeforeVenteByReference.getTotalQuantite();
                    quantiteAfter = SelectAfterVenteByReference.getTotalQuantite();

                    System.out.println(quantiteBefore);
                    System.out.println(quantiteAfter);

                    if(venteBefore != null && venteAfter != null){
                        StatProduct statProduct = new StatProduct(quantiteBefore,quantiteAfter);
                        menuEmpreinteCarbone.dispose();
                } else{
                    JOptionPane.showMessageDialog(menuEmpreinteCarbone, "Attention, cette référence produit n'existe pas. Veuillez réessayer.", "Référence produit inconnue", JOptionPane.ERROR_MESSAGE);
                    searchBar.setText("");
                }
                }
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
    }



}