package edu.ezip.ing1.pds.front.UC2;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import edu.ezip.ing1.pds.business.dto.Emplacement;
import edu.ezip.ing1.pds.business.dto.Etage;
import edu.ezip.ing1.pds.business.dto.PathPointChemin;
import edu.ezip.ing1.pds.business.dto.PointChemin;
import edu.ezip.ing1.pds.client.UC2.SelectEmplacementById;
import edu.ezip.ing1.pds.client.UC2.SelectEtageById;
import edu.ezip.ing1.pds.client.UC2.SelectPointsByIdRayon;
import edu.ezip.ing1.pds.commons.Request;
import edu.ezip.ing1.pds.front.*;
import static java.lang.String.valueOf;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class ProductMapping implements ActionListener{

    JFrame productMappingFrame;
    private String productName = RechercheReference.getProduct().getNomProduit();
    private String productColor = RechercheReference.getProduct().getCouleur();
    private JButton setPath;
    private JButton flecheGauche;
    private JButton flecheDroite;
    private JLabel etageTitre;
    private Emplacement emplacement;
    private Etage etage;
    private PathPointChemin path;
    private JPanel mainPanel;
    private JPanel mapPanel;
    private JPanel panelPlan;
    private BufferedImage backgroundImage;
    private int etageActuel = 1;
    private int idEmplacement = RechercheReference.getProduct().getIdEmplacement();
    //private String productAisle = RechercheReference.product.getIdEmplacement();
    private int textSize = 20;
    private int borderTop = 20;

    public ProductMapping(){
        //=============Selection de l'emplacement via l'idEmplacement du produit===============
        try {
            Request request = new Request();
            request.setRequestContent(valueOf(idEmplacement));
            emplacement = SelectEmplacementById.launchSelectEmplacementById(request);
        } catch(Exception e){
            System.out.println("Erreur sur l'idEmplacement : " + e.getMessage());
        }

        try{
            if(emplacement.getIdEtage() == 0 || emplacement.getAllee() == null || emplacement.getIdRayon() == 0){
                throw new Exception();
            }
        } catch(Exception exc){
            System.out.println(exc.getMessage());
            EcranAcceuil ecranAcceuil = new EcranAcceuil();
            JOptionPane.showMessageDialog(productMappingFrame, "[ERREUR 404] Attention, une des valeurs que vous avez demandé n'a pas été renseignée.", "[ERROR 407] -Valeur nulle !", JOptionPane.ERROR_MESSAGE);
        }
        //======================================================================================

        //=================Selection de l'étage via l'idEtage de l'emplacement==================
        try {
            etage = SelectEtageById.lauchSelectEtageById(valueOf(emplacement.getIdEtage()));
        } catch (Exception e) {
            System.out.println("Erreur sur l'idEtage : " + e.getMessage());
        }

        try {
            if (etage.getIdEtage() == 0 || etage.getNumeroEtage() == 0) {
                throw new Exception();
            }
        } catch (Exception exc) {
            System.out.println(exc.getMessage());
            EcranAcceuil ecranAcceuil = new EcranAcceuil();
            JOptionPane.showMessageDialog(productMappingFrame, "[ERREUR 404] Attention, la connection avec le serveur n'a pas pu être établie.", "[ERROR 404] - Connection refusée !", JOptionPane.ERROR_MESSAGE);
        }
        //======================================================================================

        //============Selection des points du chemin vers un rayon via l'id du rayon============
        try {
            path = SelectPointsByIdRayon.launchSelectPointsByIdRayon(valueOf(emplacement.getIdRayon()));
        } catch(Exception e){
            System.out.println("Erreur sur la récupération des points : " + e.getMessage());
        }

        try{
            if(emplacement.getIdEtage() == 0 || emplacement.getAllee() == null || emplacement.getIdRayon() == 0){
                throw new Exception();
            }
        } catch(Exception exc){
            System.out.println(exc.getMessage());
            EcranAcceuil ecranAcceuil = new EcranAcceuil();
            JOptionPane.showMessageDialog(productMappingFrame, "[ERREUR 404] Attention, la connection avec le serveur n'a pas pu être établie.", "[ERROR 404] - Connection refusée !", JOptionPane.ERROR_MESSAGE);
        }
        //======================================================================================

        productMappingFrame  = new JFrame();
        productMappingFrame.setSize(Template.LONGUEUR, Template.LARGEUR);
        productMappingFrame.setTitle("Trouvez votre produit.");
        productMappingFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        productMappingFrame.setLocationRelativeTo(null);
        productMappingFrame.setResizable(false);

        //----------panel header--------------
        String titreHeader = "Se rendre au produit \"" + productName + " " + productColor + "\"";
        MethodesFront.header(productMappingFrame, titreHeader, 480);
        //------------------------------------

        //-------charger l'image de fond------
        try {
            backgroundImage = ImageIO.read(Objects.requireNonNull(MethodesFront.class.getResource("/mapV1.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //-----------------------------------

        //---------panel principal-----------
        mainPanel = new JPanel();
        mainPanel.setBackground(Color.decode(Template.COULEUR_PRINCIPALE));
        mainPanel.setLayout(null);

        ImageIcon leftArrow= new ImageIcon(Objects.requireNonNull(MethodesFront.class.getResource("/flecheGauche.png")));
        flecheGauche = new JButton(leftArrow);
        flecheGauche.addActionListener(this);
        flecheGauche.setBounds(355, 645, 50, 50);
        mainPanel.add(flecheGauche);

        ImageIcon rightArrow= new ImageIcon(Objects.requireNonNull(MethodesFront.class.getResource("/flecheDroite.png")));
        flecheDroite = new JButton(rightArrow);
        flecheDroite.addActionListener(this);
        flecheDroite.setBounds(455, 645, 50, 50);
        mainPanel.add(flecheDroite);

        productMappingFrame.getContentPane().add(mainPanel);

        //------------title label-------------
        JLabel titleLabel = new JLabel();
        titleLabel.setText("Voici l'emplacement de votre produit :");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Avenir", Font.BOLD, textSize+3));
        titleLabel.setBounds(50, 70, 400, 50);

        //------------aisle panel-----------
        RoundedPanel aislePanel = new RoundedPanel(60, 60);
        aislePanel.setBounds(100, 170, 300, 70);
        aislePanel.setBackground(Color.WHITE);

        //------------shelf panel----------
        RoundedPanel shelfPanel = new RoundedPanel(60, 60);
        shelfPanel.setBounds(100, 310, 300, 70);
        shelfPanel.setBackground(Color.WHITE);

        //------------floor panel----------
        RoundedPanel floorPanel = new RoundedPanel(60, 60);
        floorPanel.setBounds(100, 200, 300, 70);
        floorPanel.setBackground(Color.WHITE);

        //------------aisle label-----------
        JLabel aisleLabel = new JLabel();
        aisleLabel.setText("Allée " + emplacement.getAllee());
        aisleLabel.setFont(new Font("Avenir", Font.BOLD, textSize));
        aisleLabel.setBorder(new EmptyBorder(borderTop, 0, 0, 0));
        aislePanel.add(aisleLabel);

        //------------shelf label-----------
        JLabel shelfLabel = new JLabel();
        shelfLabel.setText("Rayon n° " + emplacement.getIdRayon());
        shelfLabel.setFont(new Font("Avenir", Font.BOLD, textSize));
        shelfLabel.setBorder(new EmptyBorder(borderTop, 0, 0, 0));
        shelfPanel.add(shelfLabel);

        //------------floor label-----------
        JLabel floorLabel = new JLabel();
        floorLabel.setText("Étage n° " + etage.getNumeroEtage());
        floorLabel.setFont(new Font("Avenir", Font.BOLD, textSize));
        floorLabel.setBorder(new EmptyBorder(borderTop, 0, 0, 0));
        floorPanel.add(floorLabel);

        //==============================================
        ImageIcon map = new ImageIcon(Objects.requireNonNull(MethodesFront.class.getResource("/mapV1.png")));

        JLabel mapLabel = new JLabel(map);
        mainPanel.add(mapLabel);

        if (path.getPath() != null) {
            mapPanel = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);

                    Graphics2D g2d = (Graphics2D) g;
                    // Épaisseur de la ligne (chemin)
                    g2d.setStroke(new BasicStroke(5));

                    // Dessiner l'image de fond
                    if (backgroundImage != null) {
                        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                    }
                    // Dessiner le chemin

                    g.setColor(Color.RED);
                    for (int i = 0; i < path.getPath().size() - 1; i++) {
                        PointChemin p1 = path.getPath().get(i);
                        PointChemin p2 = path.getPath().get(i + 1);
                        g.drawLine(p1.getCoordX(), p1.getCoordY(), p2.getCoordX(), p2.getCoordY());
                    }
                }
            };
        } else {
            mapPanel = new JPanel();
            mapPanel.add(mapLabel);
        }

        mapPanel.setLayout(null);
        mapPanel.setBounds(60, 60,770, 580);
        mainPanel.add(mapPanel);

        panelPlan = new JPanel();
        panelPlan.setLayout(null);
        panelPlan.setBounds(840, 60,500,580);
        panelPlan.setBackground(Color.decode(Template.COULEUR_SECONDAIRE));
        mainPanel.add(panelPlan);

        //==============================================
            //Bouton config :
            setPath = new JButton();
            setPath.setText("Configurer les chemins");
            setPath.addActionListener(this);
            setPath.setBounds(1145, 650, 200, 40);
            mainPanel.add(setPath);

        //Titre : numero d'étage du plan
        etageTitre = new JLabel();
        afficherEtageTitre();

        panelPlan.add(titleLabel);
        //panelTestPlan.add(aislePanel);
        panelPlan.add(shelfPanel);
        panelPlan.add(floorPanel);
        productMappingFrame.setVisible(true);

        //=================================================
        //TODO : si l'étage actuel (qui s'affiche sur la map) < étage du produit (rayon.getNumeroRayon), afficher le path jusqu'aux escalators.
        if(etageActuel < etage.getNumeroEtage()){
            //TODO : Afficher path jusqu'aux escalators.
            //Ajouter path dans la BD dans le rayon 0.
        } else {
            //Sinon, si etageActuel == etage du produit : afficher le path jusquau produit (SELECT sur les points associés au rayon)
        }
    }

    public void afficherEtageTitre(){
        if(etageActuel == 1){
            etageTitre.setText("Plan du 1er étage :");
        } else  etageTitre.setText("Plan du " + etageActuel + "ème étage :");
        etageTitre.setFont(new Font("Avenir", Font.BOLD, textSize+2));
        etageTitre.setBounds(600, 10, 300, 50);
        mainPanel.add(etageTitre);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
       if(e.getSource() == setPath){
            PathManagement pathManagement = new PathManagement();
            productMappingFrame.dispose();
       } else if(e.getSource() == flecheGauche){
           //Pas possible d'avoir un étage négatif
           if(etageActuel > 1){
               etageActuel --;
               afficherEtageTitre();
               mainPanel.repaint();
               mainPanel.revalidate();
           }
       } else if(e.getSource() == flecheDroite){
           //Pas possible que l'etage actuel soit supérieur à l'étage effectif
           if(etageActuel < etage.getNumeroEtage()){
               etageActuel++;
               afficherEtageTitre();
               mainPanel.repaint();
               mainPanel.revalidate();
           }
       }
    }
    
}
