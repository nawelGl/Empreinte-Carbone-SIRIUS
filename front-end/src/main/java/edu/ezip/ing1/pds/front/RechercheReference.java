package edu.ezip.ing1.pds.front;

import edu.ezip.ing1.pds.business.dto.Produit;
import edu.ezip.ing1.pds.business.dto.Vente;
import edu.ezip.ing1.pds.commons.Request;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class RechercheReference implements ActionListener {
    //Boutons :

    static Produit product;
    static Vente vente;

    JPanel menuEmpreinteCarbone;
    JButton boutonConfirmer;
    JTextField searchBar;
    String titre;
    Boolean referenceIsNotAnInt = false;

    String titreLabelSecondaire ;

    String titreHeader;
    int x;

    //Constructeur :
    public RechercheReference(String titreFrame, String titreHeader,String titreLabelSecondaire,int x){
        //Paramétrages de base :
        JFrame menuEmpreinteCarbone = new JFrame();
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
        secondPanel.setBounds(100, 100, 800, 350);

        //Ajout du JLabel :
        JLabel titrePanelSecondaire = new JLabel(titreLabelSecondaire);
        titrePanelSecondaire.setForeground(Color.WHITE);
        titrePanelSecondaire.setFont(new Font("Avenir", Font.BOLD, 22));
        titrePanelSecondaire.setBounds(55, 25, 760, 80);
        secondPanel.add(titrePanelSecondaire);

        //Ajout de la search bar :
        JTextField searchBar = new RoundJTextField(100);
        searchBar.setBounds(80, 150, 650, 40);
        boutonConfirmer = new JButton("Valider");
        boutonConfirmer.setBounds(360,230, 80, 30);
        secondPanel.add(searchBar);
        secondPanel.add(boutonConfirmer);

        mainPanel.add(secondPanel);

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

//                if(referenceIsNotAnInt){
//                    JOptionPane.showMessageDialog(menuEmpreinteCarbone, "Attention, la référence que vous avez entrée contient des caractères interdits. Veuillez réessayer en entrant des chiffres uniquement.", "Format de référence incorrect.", JOptionPane.ERROR_MESSAGE);
//                    searchBar.setText("");
//                    referenceIsNotAnInt = false;
//                } else {
//                    Request request = new Request();
//                    request.setRequestContent(refEnString);
//                    product = SelectProductByReference.launchSelectProductByReference(request);
//                    if(product != null){
//                        menuEmpreinteCarbone.dispose();
//                        ProductMapping productMapping = new ProductMapping();
//                    } else{
//                        JOptionPane.showMessageDialog(menuEmpreinteCarbone, "Attention, cette référence produit n'existe pas. Veuillez réessayer.", "Référence produit inconnue", JOptionPane.ERROR_MESSAGE);
//                        searchBar.setText("");
//                    }
//                }
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
    }





}