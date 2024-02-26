package edu.ezip.ing1.pds.front;

//import javax.swing.*;
//import java.awt.*;
//
//public class AcceuilApp extends JFrame {
//
//    private int longueur = 1000;
//    private int largeur = 700;
//    private String titre;
//    private Color couleurPrincipale = Color.decode("#E7EBE4");
//    private Color couleurSecondaire = Color.decode("#7B8275");
//
//    public AcceuilApp(String titre) {
//        this.titre = titre;
//        setTitle(titre);
//        setSize(longueur, largeur);
//
//        JPanel panelCentre = new JPanel(new FlowLayout(FlowLayout.CENTER));
//        JPanel panelNord = new JPanel(new FlowLayout(FlowLayout.RIGHT));
//        JPanel panelSud = new JPanel(new FlowLayout(FlowLayout.RIGHT));
//
//        // Création du bouton personnalisé
//        BoutonPersonnalise boutonUC1 = new BoutonPersonnalise("Faites-vous un achat responsable ?");
//        boutonUC1.setPreferredSize(new Dimension(300, 100));
//
//        // Ajout du bouton personnalisé au panelCentre
//        panelCentre.add(boutonUC1);
//
//        // Paramètres des couleurs des panels
//        panelCentre.setBackground(couleurPrincipale);
//        panelSud.setBackground(couleurPrincipale);
//        panelNord.setBackground(Color.WHITE);
//
//        // Ajout des panels au panelFrame
//        Container panelFrame = getContentPane();
//        panelFrame.setLayout(new BorderLayout());
//        panelFrame.add(panelNord, BorderLayout.NORTH);
//        panelFrame.add(panelSud, BorderLayout.SOUTH);
//        panelFrame.add(panelCentre, BorderLayout.CENTER);
//
//        setLocationRelativeTo(null);
//        setDefaultCloseOperation(EXIT_ON_CLOSE);
//        setVisible(true);
//        setResizable(false);
//    }
//
////    public static void main(String[] args) {
////        AcceuilFrame f = new AcceuilFrame("Test");
////        f.setVisible(true);
////    }
//
//    // Classe interne pour le bouton personnalisé
//    private class BoutonPersonnalise extends JButton {
//
//        public BoutonPersonnalise(String text) {
//            super(text);
//            setOpaque(false); // Rendre le bouton transparent pour voir l'arrière-plan dessiné
//        }
//
//        @Override
//        protected void paintComponent(Graphics g) {
//            super.paintComponent(g);
//
//            // Dessiner l'arrière-plan du bouton
//            g.setColor(Color.decode("#7B8275")); // Couleur secondaire
//            g.fillRect(0, 0, getWidth(), getHeight());
//
//            // Dessiner le texte au centre du bouton
//            g.setColor(Color.BLACK);
//            g.drawString(getText(), (getWidth() - g.getFontMetrics().stringWidth(getText())) / 2, getHeight() / 2 + g.getFontMetrics().getHeight() / 4);
//        }
//    }
//}
import javax.swing.*;
import java.awt.*;

public class AcceuilApp extends JFrame {

    private int longueur = 1000;
    private int largeur = 700;
    private String titre;
    private Color couleurPrincipale = Color.decode("#E7EBE4");
    private Color couleurSecondaire = Color.decode("#7B8275");

    public AcceuilApp(String titre) {
        this.titre = titre;
        setTitle(titre);
        setSize(longueur, largeur);

        JPanel panelCentre = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JPanel panelNord = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JPanel panelSud = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        // Création du bouton personnalisé
        BoutonPersonnalise boutonUC1 = new BoutonPersonnalise("Faites-vous un achat responsable ?");
        boutonUC1.setPreferredSize(new Dimension(300, 100));

        // Ajout du bouton personnalisé au panelCentre
        panelCentre.add(boutonUC1);

        // Paramètres des couleurs des panels
        panelCentre.setBackground(couleurPrincipale);
        panelSud.setBackground(couleurPrincipale);
        panelNord.setBackground(Color.WHITE);

        // Ajout des panels au panelFrame
        Container panelFrame = getContentPane();
        panelFrame.setLayout(new BorderLayout());
        panelFrame.add(panelNord, BorderLayout.NORTH);
        panelFrame.add(panelSud, BorderLayout.SOUTH);
        panelFrame.add(panelCentre, BorderLayout.CENTER);

        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);
    }

//    public static void main(String[] args) {
//        AcceuilFrame f = new AcceuilFrame("Test");
//        f.setVisible(true);
//    }

    // Classe interne pour le bouton personnalisé
    private class BoutonPersonnalise extends JButton {

        public BoutonPersonnalise(String text) {
            super(text);
            setOpaque(false); // Rendre le bouton transparent pour voir l'arrière-plan dessiné
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            // Dessiner l'arrière-plan du bouton
            g.setColor(Color.decode("#7B8275")); // Couleur secondaire
            g.fillRect(0, 0, getWidth(), getHeight());

            // Dessiner le texte au centre du bouton
            g.setColor(Color.BLACK);
            g.drawString(getText(), (getWidth() - g.getFontMetrics().stringWidth(getText())) / 2, getHeight() / 2 + g.getFontMetrics().getHeight() / 4);
        }
    }
}
