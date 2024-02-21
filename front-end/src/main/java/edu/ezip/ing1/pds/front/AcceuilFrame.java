package edu.ezip.ing1.pds.front;





import javax.swing.plaf.ButtonUI;

import java.awt.*;
import java.awt.event.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;



public class AcceuilFrame extends JFrame implements ActionListener{
    JFrame f;
    int longueur = 1000;
    int largeur = 700;
    String titre;
    Color couleurPrincipale = Color.decode("#E7EBE4");
    Color couleurSecondaire = Color.decode("#7B8275");

    JPanel panelNord;
    JPanel panelSud;
    JPanel panelCentre;
    Container panelFrame;

    JButton boutonUC3;
    JButton boutonUC1;
    JButton boutonUC2;
    JButton boutonArriere;

    String titreBoutonUC1= "Faites-vous un achat responsable ? ";
    String titreBoutonUC2="Stat UC2";
    String titreBoutonUC3="Trouvez votre article dans le magasin >>";



    public  AcceuilFrame(String titre){
        this.titre=titre;
        setTitle(titre);
        setSize(longueur, largeur);

        // initialisation ====================

        panelCentre=new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelNord=new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelSud=new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelFrame= new Container();

        boutonUC1=new JButton(titreBoutonUC1);
        boutonUC2 = new JButton(titreBoutonUC2);
        boutonUC3 = new JButton(titreBoutonUC3);
        boutonArriere= new JButton();

        // ===================================

        // taille et police des boutons ============


        boutonUC3.setPreferredSize(new Dimension(300, 50)); // Définir une taille plus grande

        boutonUC3.setForeground(Color.BLACK); // Définir la couleur du texte

        boutonUC3.setFocusPainted(false); // Désactiver le contour de focus

        // Définir la couleur de fond manuellement sur le boutonUC3
       boutonUC3.setBackground(couleurSecondaire);




        //======================================================

        panelCentre.add(boutonUC1);
        panelSud.add(boutonUC3);
        panelNord.add(boutonUC2);




        panelCentre.setBackground(couleurPrincipale);
        panelSud.setBackground(couleurPrincipale);
        panelNord.setBackground(Color.WHITE);




        panelFrame=getContentPane();
        panelFrame.setLayout(new BorderLayout());
        panelFrame.add(panelNord,BorderLayout.NORTH);
        panelFrame.add(panelSud,BorderLayout.SOUTH);
        panelFrame.add(panelCentre,BorderLayout.CENTER);





        setLocationRelativeTo(null);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);


    }

    public static void main (String [] argv){
        AcceuilFrame f= new AcceuilFrame("test");
        f.setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent e) {

    }
}