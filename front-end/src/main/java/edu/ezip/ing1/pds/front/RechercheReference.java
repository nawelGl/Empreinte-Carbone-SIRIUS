package edu.ezip.ing1.pds.front;

import edu.ezip.ing1.pds.business.dto.*;
import edu.ezip.ing1.pds.commons.Request;

import edu.ezip.ing1.pds.client.SelectProductByReference;
import edu.ezip.ing1.pds.client.commons.ClientRequest;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

class RechercheReference implements ActionListener {

    static Produit product;
    static Vente vente;

    JFrame menuEmpreinteCarbone;
    JButton boutonConfirmer;
    JTextField searchBar;
    String titre;
    boolean referenceIsNotAnInt = false;
    String titreLabelSecondaire ;

    String titreHeader;
    int x;

    //Bouton pour acceder aux categories :
    JButton boutonCategories = new JButton();

    //Constructeur :
    public RechercheReference(String titreFrame, String titreHeader,String titreLabelSecondaire,int x){
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
        secondPanel.setBackground(Color.decode(Template.COUELUR_SECONDAIRE));
        //secondPanel.setSize(new Dimension(30, 100));
        secondPanel.setBounds(300, 200, 800, 250);

        //Ajout du JLabel :
        JLabel titrePanelSecondaire = new JLabel(titreLabelSecondaire);
        titrePanelSecondaire.setForeground(Color.WHITE);
        titrePanelSecondaire.setFont(new Font("Avenir", Font.BOLD, 22));
        titrePanelSecondaire.setBounds(40, 25, 760, 80);
        secondPanel.add(titrePanelSecondaire);

        //Ajout de la search bar :
        searchBar = new RoundJTextField(100);
        searchBar.setBounds(80, 120, 650, 40);
        boutonConfirmer = new JButton("Valider");
        boutonConfirmer.addActionListener(this);
        boutonConfirmer.setBounds(360,200, 80, 30);
        secondPanel.add(searchBar);
        secondPanel.add(boutonConfirmer);

        mainPanel.add(secondPanel);

        //Ajout du bouton pour les categories :
        boutonCategories.setText("Vous n'avez pas encore la référence de votre produit ?");
        boutonCategories.setBounds(460, 600, 470, 40);
        boutonCategories.addActionListener((this));
        mainPanel.add(boutonCategories);

        menuEmpreinteCarbone.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e){
        if(e.getSource() == boutonConfirmer){
            String refEnString = searchBar.getText();
   
            try{
                Integer refEnInt = Integer.parseInt(refEnString);
            } catch (Exception ex){
                System.out.println("La référence entrée n'est pas un String : " + ex.getMessage());
                referenceIsNotAnInt = true;
            }

            try{
                Request request = new Request();
                request.setRequestContent(refEnString);
                product = SelectProductByReference.launchSelectProductByReference(request);
            } catch(Exception exception){
                System.out.println(exception.getMessage());
            }

            if(ClientRequest.isConnectionRefused() == true){
                System.out.println("Problème de connexion");
                searchBar.setText("");
                JOptionPane.showMessageDialog(menuEmpreinteCarbone, "Attention, la connection avec le serveur n'a pas pu être établie.", "Connection refusée !", JOptionPane.ERROR_MESSAGE);
            }

            if(referenceIsNotAnInt){
                JOptionPane.showMessageDialog(menuEmpreinteCarbone, "Attention, la référence que vous avez entrée contient des caractères interdits. Veuillez réessayer en entrant des chiffres uniquement.", "Format de référence incorrect.", JOptionPane.ERROR_MESSAGE);
                searchBar.setText("");
                referenceIsNotAnInt = false;
            }

            if(product != null){
                menuEmpreinteCarbone.dispose();
                ProductInfo productInfo=new ProductInfo();
            }
        } else if (e.getSource() == boutonCategories) {
            menuEmpreinteCarbone.dispose();
            MainCategorie mainCategorie = new MainCategorie();
        }
    }



}