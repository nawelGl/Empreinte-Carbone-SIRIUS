package edu.ezip.ing1.pds.front;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class RechercheReference implements ActionListener {
    //Boutons :
    JButton boutonConfirmer;
    String titre;

    String titreLabelSecondaire ;

    String titreHeader;
    protected JTextField searchBar;
    int reference;
    int x;

    public JTextField getSearchBar() {
        return searchBar;
    }

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
        searchBar = new RoundJTextField(100);
        searchBar.setBounds(80, 150, 650, 40);
        boutonConfirmer = new JButton("Valider");
        boutonConfirmer.setBounds(360,230, 80, 30);
        boutonConfirmer.addActionListener(this);
        secondPanel.add(searchBar);
        secondPanel.add(boutonConfirmer);

        mainPanel.add(secondPanel);

        menuEmpreinteCarbone.setVisible(true);

        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String entree= searchBar.getText();

        if (entree != null && !entree.isEmpty()) {
            try {
                reference = Integer.parseInt(entree);
                System.out.println(reference);


            } catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(null, "Veuillez entrer un entier valide dans la searchBar.");
            }
        }

    }

    public Integer getReference(){
        
        return reference;

    }
}