package edu.ezip.ing1.pds.front;


import java.awt.*;
import javax.swing.*;

public class Frame extends JFrame {
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
    JLabel logo;


    JButton boutonUC3;
    JButton boutonUC1;
    JButton boutonUC2;
    JButton boutonArriere;

    String titreBoutonUC1= "Faites-vous un achat responsable ? ";
    String titreBoutonUC2="Stat UC2";
    String titreBoutonUC3="Trouvez votre article dans le magasin >>";

    public Frame(String titre){
        this.titre=titre;
        setTitle(titre);
        setSize(longueur, largeur);

        // initialisation des panels
        panelCentre = new JPanel(null); // Utiliser un layout null pour pouvoir utiliser setBounds
        panelNord = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelSud = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelFrame = new Container();

        // Texte avec la première ligne en gras et la deuxième ligne normale
        String texteBoutonUC1 = "<html><div style='text-align: center;'><b>" + titreBoutonUC1 + "</b><br>Votre nouvelle ligne de texte</div></html>";

        boutonUC1 = new JButton(texteBoutonUC1);
        boutonUC2 = new JButton(titreBoutonUC2);
        boutonUC3 = new JButton(titreBoutonUC3);
        boutonArriere = new JButton();

        // BoutonUC1 avec setBounds pour le positionner et le redimensionner
        boutonUC1.setBounds(200, 200, 400, 200); // x, y, largeur, hauteur
        boutonUC1.setFont(new Font("Arial", Font.BOLD, 24)); // Taille de police en gras
        boutonUC1.setHorizontalAlignment(SwingConstants.CENTER); // Centre le texte horizontalement
        boutonUC1.setForeground(Color.BLACK);
        boutonUC1.setFocusPainted(false);
        boutonUC1.setBackground(couleurSecondaire);

        // Ajout des boutons aux panels
        panelCentre.add(boutonUC1);
        panelSud.add(boutonUC3);
        panelNord.add(boutonUC2);

        // Paramètres des panels
        panelCentre.setBackground(couleurPrincipale);
        panelSud.setBackground(couleurPrincipale);
        panelNord.setBackground(Color.WHITE);

        // Ajout des panels au panelFrame
        panelFrame = getContentPane();
        panelFrame.setLayout(new BorderLayout());
        panelFrame.add(panelNord, BorderLayout.NORTH);
        panelFrame.add(panelSud, BorderLayout.SOUTH);
        panelFrame.add(panelCentre, BorderLayout.CENTER);

        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);
    }

//    public static void main (String [] argv){
//        AcceuilFrame f= new AcceuilFrame("test");
//        f.setVisible(true);
//    }
}

