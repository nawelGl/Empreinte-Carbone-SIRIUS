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
import edu.ezip.ing1.pds.front.*;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import static java.lang.String.valueOf;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class ProductMapping implements ActionListener{
    protected static Logger loggerProductMapping;
    JFrame productMappingFrame;
    private String productName = RechercheReference.getProduct().getNomProduit();
    private String productColor = RechercheReference.getProduct().getCouleur();
    private JButton leftArrow;
    private JButton rightArrow;
    private JLabel floorTitle;
    private Emplacement place;
    private Etage floor;
    private PathPointChemin path;
    private PathPointChemin pathEscalators;
    private JPanel mainPanel;
    private JPanel mapPanel;
    private JPanel panelPlan;
    private BufferedImage backgroundImage;
    private int actualFloor = 1;
    private int idPlace = RechercheReference.getProduct().getIdEmplacement();
    //private String productAisle = RechercheReference.product.getIdplace();
    private int textSize = 20;
    private int borderTop = 20;
    private boolean noPathFound = false;
    private int highestFloor = 0;
    private boolean changementfloor = true;
    private boolean requestHasFailed = false;

    public ProductMapping(){

        loggerProductMapping = LogManager.getLogger(ProductMapping.class);

        productMappingFrame = new JFrame();
        productMappingFrame.setSize(Template.LONGUEUR, Template.LARGEUR);
        productMappingFrame.setTitle("Trouvez votre produit.");
        productMappingFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        productMappingFrame.setLocationRelativeTo(null);
        productMappingFrame.setResizable(false);

        //=============================== Select place by ID ===================================
        try {
            place = SelectEmplacementById.launchSelectEmplacementById(idPlace);
        } catch(Exception e){
            requestHasFailed = true;
            EcranAcceuil ecranAcceuil = new EcranAcceuil();
            productMappingFrame.dispose();
        }
        //======================================================================================

        if(!requestHasFailed){
            //================================ Select floor by ID ================================
            try {
                floor = SelectEtageById.lauchSelectEtageById(valueOf(place.getIdEtage()));
            } catch (Exception e) {
                requestHasFailed = true;
                EcranAcceuil ecranAcceuil = new EcranAcceuil();
                JOptionPane.showMessageDialog(productMappingFrame, "[ERREUR 404] Attention, la connection avec le serveur n'a pas pu être établie.", "[ERROR 404] - Connection refusée !", JOptionPane.ERROR_MESSAGE);
                productMappingFrame.dispose();
            }
            //===================================================================================
        }

        if(!requestHasFailed){
            //=========================== Select path by ID =============================
            try {
                path = SelectPointsByIdRayon.launchSelectPointsByIdRayon(valueOf(place.getIdRayon()));
            } catch(Exception e){
                requestHasFailed = true;
                loggerProductMapping.warn("Erreur sur la récupération des points : " + e.getMessage());
                EcranAcceuil ecranAcceuil = new EcranAcceuil();
                JOptionPane.showMessageDialog(productMappingFrame, "[ERREUR 404] Attention, la connection avec le serveur n'a pas pu être établie.", "[ERROR 404] - Connection refusée !", JOptionPane.ERROR_MESSAGE);
                productMappingFrame.dispose();
            }
            //===========================================================================
        }

        if(!requestHasFailed){
            //====================== Select path to elevator by ID (id == 15) =====================
            try {
                pathEscalators = SelectPointsByIdRayon.launchSelectPointsByIdRayon(valueOf(15));
            } catch(Exception e){
                requestHasFailed = true;
                loggerProductMapping.warn("Erreur sur la récupération des points vers l'escalator : " + e.getMessage());
                EcranAcceuil ecranAcceuil = new EcranAcceuil();
                JOptionPane.showMessageDialog(productMappingFrame, "[ERREUR 404] Attention, la connection avec le serveur n'a pas pu être établie.", "[ERROR 404] - Connection refusée !", JOptionPane.ERROR_MESSAGE);
                productMappingFrame.dispose();
            }

            //======================================================================================
        }

        //---------- header panel --------------
        String titreHeader = "Se rendre au produit \"" + productName + " " + productColor + "\"";
        MethodesFront.header(productMappingFrame, titreHeader, 480);
        //------------------------------------

        //------- upload the map ------
        try {
            backgroundImage = ImageIO.read(Objects.requireNonNull(MethodesFront.class.getResource("/mapV1.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //-----------------------------------

        //--------- main panel -----------
        mainPanel = new JPanel();
        mainPanel.setBackground(Color.decode(Template.COULEUR_PRINCIPALE));
        mainPanel.setLayout(null);

        ImageIcon leftArrowImage= new ImageIcon(Objects.requireNonNull(MethodesFront.class.getResource("/flecheGauche.png")));
        leftArrow = new JButton(leftArrowImage);
        leftArrow.addActionListener(this);
        leftArrow.setBounds(355, 645, 50, 50);
        mainPanel.add(leftArrow);

        ImageIcon rightArrowImage= new ImageIcon(Objects.requireNonNull(MethodesFront.class.getResource("/flecheDroite.png")));
        rightArrow = new JButton(rightArrowImage);
        rightArrow.addActionListener(this);
        rightArrow.setBounds(455, 645, 50, 50);
        mainPanel.add(rightArrow);

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
        aisleLabel.setText("Allée " + place.getAllee());
        aisleLabel.setFont(new Font("Avenir", Font.BOLD, textSize));
        aisleLabel.setBorder(new EmptyBorder(borderTop, 0, 0, 0));
        aislePanel.add(aisleLabel);

        //------------shelf label-----------
        JLabel shelfLabel = new JLabel();
        shelfLabel.setText("Rayon n° " + place.getIdRayon());
        shelfLabel.setFont(new Font("Avenir", Font.BOLD, textSize));
        shelfLabel.setBorder(new EmptyBorder(borderTop, 0, 0, 0));
        shelfPanel.add(shelfLabel);

        //------------floor label-----------
        JLabel floorLabel = new JLabel();
        floorLabel.setText("Étage n° " + floor.getNumeroEtage());
        floorLabel.setFont(new Font("Avenir", Font.BOLD, textSize));
        floorLabel.setBorder(new EmptyBorder(borderTop, 0, 0, 0));
        floorPanel.add(floorLabel);

        //==============================================
        ImageIcon map = new ImageIcon(Objects.requireNonNull(MethodesFront.class.getResource("/mapV1.png")));

        JLabel mapLabel = new JLabel(map);
        mainPanel.add(mapLabel);

        checkPath();

        panelPlan = new JPanel();
        panelPlan.setLayout(null);
        panelPlan.setBounds(840, 60,500,580);
        panelPlan.setBackground(Color.decode(Template.COULEUR_SECONDAIRE));
        if(noPathFound){
            JLabel errorNoPathFound = new JLabel();
            errorNoPathFound.setText("<html>Oups, problème d'affichage sur ce chemin, l'administrateur a été mis au courant et le problème sera réglé bientôt ! Vous pouvez vous aider des indications ci dessus pour trouver votre produit.</html>");
            errorNoPathFound.setFont(Template.FONT_ECRITURE);
            errorNoPathFound.setForeground(Color.WHITE);
            errorNoPathFound.setBounds(40, 450, 400, 100);
            panelPlan.add(errorNoPathFound);
        }
        mainPanel.add(panelPlan);

        floorTitle = new JLabel();
        afficherfloorTitle();

        panelPlan.add(titleLabel);
        panelPlan.add(shelfPanel);
        panelPlan.add(floorPanel);
        productMappingFrame.setVisible(true);

        //=================================================
        //TODO : si l'étage actuel (qui s'affiche sur la map) < étage du produit (rayon.getNumeroRayon), afficher le path jusqu'aux escalators.
        if(actualFloor < floor.getNumeroEtage()){
            //TODO : Afficher path jusqu'aux escalators.
            //Ajouter path dans la BD dans le rayon 0.
        } else {
            //Sinon, si actualFloor == floor du produit : afficher le path jusquau produit (SELECT sur les points associés au rayon)
        }
    }

    public void afficherfloorTitle(){
        if(actualFloor == 1){
            floorTitle.setText("Plan du 1er étage :");
        } else  floorTitle.setText("Plan du " + actualFloor + "ème étage :");
        floorTitle.setFont(new Font("Avenir", Font.BOLD, textSize+2));
        floorTitle.setBounds(600, 10, 300, 50);
        mainPanel.add(floorTitle);
    }

    public void checkPath(){
        // Supprime le mapPanel s'il existe déjà
        if (mapPanel != null) {
            mainPanel.remove(mapPanel);
        }
        if(changementfloor){
            if (path != null) {
                //Si on est au bon étage par rapport au produit :
                if(actualFloor == floor.getNumeroEtage()){
                    mapPanel = new JPanel() {
                        @Override
                        protected void paintComponent(Graphics g) {
                            super.paintComponent(g);
                            Graphics2D g2d = (Graphics2D) g;
                            g2d.setStroke(new BasicStroke(5));

                            if (backgroundImage != null) {
                                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                            }

                            g.setColor(Color.RED);
                            for (int i = 0; i < path.getPath().size() - 1; i++) {
                                PointChemin p1 = path.getPath().get(i);
                                PointChemin p2 = path.getPath().get(i + 1);
                                g.drawLine(p1.getCoordX(), p1.getCoordY(), p2.getCoordX(), p2.getCoordY());
                            }
                        }
                    };
                    changementfloor = false;
                    mapPanel.setLayout(null);
                    mapPanel.setBounds(60, 60,770, 580);
                    mainPanel.add(mapPanel);
                } else {
                    mapPanel = new JPanel() {
                        @Override
                        protected void paintComponent(Graphics g) {
                            super.paintComponent(g);

                            Graphics2D g2d = (Graphics2D) g;
                            g2d.setStroke(new BasicStroke(5));

                            if (backgroundImage != null) {
                                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                            }

                            g.setColor(Color.RED);
                            for (int i = 0; i < pathEscalators.getPath().size() - 1; i++) {
                                PointChemin p1 = pathEscalators.getPath().get(i);
                                PointChemin p2 = pathEscalators.getPath().get(i + 1);
                                g.drawLine(p1.getCoordX(), p1.getCoordY(), p2.getCoordX(), p2.getCoordY());
                            }
                        }
                    };
                    changementfloor = false;
                    mapPanel.setLayout(null);
                    mapPanel.setBounds(60, 60,770, 580);
                    mainPanel.add(mapPanel);
                }
            } else {
                noPathFound = true;
                mapPanel = new JPanel();
                JLabel mapSiPasDeChemin = new JLabel();
                mapSiPasDeChemin.setIcon(new ImageIcon(Objects.requireNonNull(ProductMapping.class.getResource("/mapV1.png"))));
                mapSiPasDeChemin.setBounds(0, 0,770, 580);
                mapPanel.add(mapSiPasDeChemin);
                mapPanel.setLayout(null);
                mapPanel.setBounds(60, 60,770, 580);
                mainPanel.add(mapPanel);
                changementfloor = false;
            }
            changementfloor = false;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
      if(e.getSource() == leftArrow){
           if(actualFloor > 1){
               changementfloor = true;
               actualFloor --;
               afficherfloorTitle();
               if (mapPanel != null) {
                   mainPanel.remove(mapPanel);
                   mainPanel.repaint();
                   mainPanel.revalidate();
               }
               checkPath();
           }
       } else if(e.getSource() == rightArrow){
           if(actualFloor < floor.getNumeroEtage()){
               changementfloor = true;
               actualFloor++;
               afficherfloorTitle();
               if (mapPanel != null) {
                   mainPanel.remove(mapPanel);
                   mainPanel.repaint();
                   mainPanel.revalidate();
               }
               checkPath();
           }
       }
    }
}
