package edu.ezip.ing1.pds.front;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Test  implements ActionListener {
//    protected JFrame acceuilApp;


    JPanel panelSud;
    JPanel panelCentre;
    Container panelFrame;
    JLabel logo;


    JButton boutonUC3;
    JButton boutonUC1;
    JButton boutonUC2;
    JButton boutonArriere;


    String titre= "Home UC1 - Empreinte Carbone";
    String titreLabelSecondaire ="Entrez la référence de votre produit pour savoir quel esT son empreinte carbon :";
    String titreHeader="Empreinte Carbon du produit :";

    String titreBoutonUC1= "Faites-vous un achat responsable ? ";
    String titreBoutonUC2="Stat UC2";
    String titreBoutonUC3="Trouvez votre article dans le magasin >>";

    public Test() {
        //Paramétrages de base :
        JFrame acceuilApp = new JFrame();
        acceuilApp.setTitle("test acceuil");
        acceuilApp.setSize(Template.LONGUEUR, Template.LARGEUR);
        acceuilApp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        acceuilApp.setLocationRelativeTo(null);
        acceuilApp.setResizable(false);

        //----------panel header--------------
        Methodes.header(acceuilApp, "Empreinte carbon", 365);

        // initialisation des panels
        panelCentre = new JPanel(null); // Utiliser un layout null pour pouvoir utiliser setBounds

        panelSud = new JPanel(new FlowLayout(FlowLayout.RIGHT));


        // Texte avec la première ligne en gras et la deuxième ligne normale
        String texteBoutonUC1 = "<html><div style='text-align: center;'><b>" + titreBoutonUC1 + "</b><br><br>\nCliquez ici</div></html>";

//        boutonUC1 = new JButton(texteBoutonUC1);
        boutonUC2 = new JButton(titreBoutonUC2);
        boutonUC3 = new JButton(titreBoutonUC3);
        boutonArriere = new RoundBtn("",30);
        boutonUC1=new RoundBtn(texteBoutonUC1,30);

        // BoutonUC1 avec setBounds pour le positionner et le redimensionner
        boutonUC1.setBounds(250, 180, 500, 200); // x, y, largeur, hauteur
        boutonUC1.setFont(new Font("Arial", Font.BOLD, 24)); // Taille de police en gras
//        boutonUC1.setBorder(new RoundBtn(texteBoutonUC1,30));
        boutonUC1.setBackground(Color.WHITE);
        boutonUC1.setHorizontalAlignment(SwingConstants.CENTER); // Centre le texte horizontalement
        boutonUC1.setForeground(Color.BLACK);
        boutonUC1.setFocusPainted(false);
//        boutonUC1.setOpaque(true);
//        boutonUC1.setBackground(Color.decode(Template.COUELUR_SECONDAIRE));

        //boutonarriere plan
        boutonArriere.setBounds(230, 165, 500, 200); // x, y, largeur, hauteur


        boutonArriere.setBackground(Color.decode(Template.COUELUR_SECONDAIRE));
        boutonArriere.setHorizontalAlignment(SwingConstants.CENTER); // Centre le texte horizontalement
        boutonArriere.setForeground(Color.BLACK);
        boutonArriere.setFocusPainted(false);
        boutonArriere.setEnabled(false);




        //Ajout des ecouteurs
        boutonUC1.addActionListener(this);


        // Ajout des boutons aux panels
        panelCentre.add(boutonUC1);
        panelCentre.add(boutonArriere);

        panelSud.add(boutonUC3);


        // Paramètres des panels
        panelCentre.setBackground(Color.decode(Template.COULEUR_PRINCIPALE));
        panelSud.setBackground(Color.decode(Template.COULEUR_PRINCIPALE));


        // Ajout des panels au panelFrame
        panelFrame = acceuilApp.getContentPane();

        panelFrame.add(panelSud, BorderLayout.SOUTH);
        panelFrame.add(panelCentre, BorderLayout.CENTER);

        acceuilApp.setVisible(true);


    }


    @Override
    public void actionPerformed(ActionEvent e) {
       ReferenceUC1 referenceUC1=new ReferenceUC1(titre,titreHeader,titreLabelSecondaire);

    }
}

