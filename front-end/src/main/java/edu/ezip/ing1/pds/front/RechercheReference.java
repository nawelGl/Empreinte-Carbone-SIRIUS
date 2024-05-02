package edu.ezip.ing1.pds.front;

import edu.ezip.ing1.pds.business.dto.*;
import edu.ezip.ing1.pds.client.SelectProductByReference;
import edu.ezip.ing1.pds.client.commons.ClientRequest;
import edu.ezip.ing1.pds.front.Categories.MainCategorie;
import edu.ezip.ing1.pds.front.UC1.ProductInfo;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;


public class RechercheReference implements ActionListener {

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
    protected static Logger loggerRechercheReference;

    public static Produit getProduct() {
        return product;
    }

    //Bouton pour acceder aux categories :
    JButton boutonCategories = new JButton();

    //Constructeur :
    public RechercheReference(String titreFrame, String titreHeader,String titreLabelSecondaire,int x){
        loggerRechercheReference = LogManager.getLogger(RechercheReference.class);

        //Paramétrages de base :
        menuEmpreinteCarbone = new JFrame();
        menuEmpreinteCarbone.setTitle(titreFrame);
        menuEmpreinteCarbone.setSize(Template.LONGUEUR, Template.LARGEUR);
        menuEmpreinteCarbone.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menuEmpreinteCarbone.setLocationRelativeTo(null);
        menuEmpreinteCarbone.setResizable(false);

        //----------panel header--------------
        MethodesFront.header(menuEmpreinteCarbone, titreHeader, x);

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
                loggerRechercheReference.warn("La référence entrée n'est pas un String : " + ex.getMessage());
                referenceIsNotAnInt = true;
            }

            try{

                product = SelectProductByReference.launchSelectProductByReference(refEnString);
            } catch(Exception exception){
                loggerRechercheReference.warn(exception.getMessage());
            }

            if(ClientRequest.isConnectionRefused() == true){
                loggerRechercheReference.warn("Problème de connexion");
                searchBar.setText("");
                JOptionPane.showMessageDialog(menuEmpreinteCarbone, "[ERREUR 404] Attention, la connexion avec le serveur n'a pas pu être établie.", "[ERROR 404] - Connexion refusée", JOptionPane.ERROR_MESSAGE);
                referenceIsNotAnInt = false;
            } else {
                if(referenceIsNotAnInt){
                    JOptionPane.showMessageDialog(menuEmpreinteCarbone, "[ERREUR 405] Attention, la référence que vous avez entrée contient des caractères interdits. Veuillez réessayer en entrant des chiffres uniquement.", "[ERREUR 405] - Format de référence incorrect.", JOptionPane.ERROR_MESSAGE);
                    searchBar.setText("");
                    referenceIsNotAnInt = false;
                } else {
                    if(product != null){
                        ProductInfo productInfo=new ProductInfo();
                        menuEmpreinteCarbone.dispose();


                    }else {
                        JOptionPane.showMessageDialog(menuEmpreinteCarbone, "[ERREUR 406] Attention, cette référence produit n'existe pas. Veuillez réessayer.", "[ERREUR 406] - Référence produit inconnue", JOptionPane.ERROR_MESSAGE);
                        searchBar.setText("");
                    }
                }
    
            }
        } else if (e.getSource() == boutonCategories) {
            MainCategorie mainCategorie = new MainCategorie();
            menuEmpreinteCarbone.dispose();

        }
    }



}