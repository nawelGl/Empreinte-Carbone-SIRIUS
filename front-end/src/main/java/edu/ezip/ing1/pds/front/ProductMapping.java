package edu.ezip.ing1.pds.front;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import edu.ezip.ing1.pds.business.dto.Emplacement;
import edu.ezip.ing1.pds.client.SelectEmplacementById;
import edu.ezip.ing1.pds.commons.Request;

import static java.lang.String.valueOf;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class ProductMapping implements ActionListener{

    JFrame productMappingFrame;
    private String productName = RechercheReference.product.getNomProduit();
    private String productColor = RechercheReference.product.getCouleur();
    JButton backHomeButton;
    JButton setPath;

    private Emplacement emplacement;
    private int idEmplacement = RechercheReference.product.getIdEmplacement();
    //private String productAisle = RechercheReference.product.getIdEmplacement();
    private int textSize = 20;
    private int borderTop = 20;

    public ProductMapping(){
        //Recherche de l'emplacement via l'idEmplacement du produit :
        try {
            Request request = new Request();
            request.setRequestContent(valueOf(idEmplacement));
            emplacement = SelectEmplacementById.launchSelectEmplacementById(request);
        } catch(Exception e){
            System.out.println("Erreur sur l'idEmplacement : " + e.getMessage());
        }

        productMappingFrame  = new JFrame();
        productMappingFrame.setSize(Template.LONGUEUR, Template.LARGEUR);
        productMappingFrame.setTitle("Trouvez votre produit.");
        productMappingFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        productMappingFrame.setLocationRelativeTo(null);
        productMappingFrame.setResizable(false);

        //----------panel header--------------
        String titreHeader = "Se rendre au produit \"" + productName + " " + productColor + "\"";
        Methodes.header(productMappingFrame, titreHeader, 470);

        //---------panel principal-----------
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(Color.decode(Template.COULEUR_PRINCIPALE));
        mainPanel.setLayout(null);
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
        shelfLabel.setText("Rayon n° " + emplacement.getRayon());
        shelfLabel.setFont(new Font("Avenir", Font.BOLD, textSize));
        shelfLabel.setBorder(new EmptyBorder(borderTop, 0, 0, 0));
        shelfPanel.add(shelfLabel);

        //------------floor label-----------
        JLabel floorLabel = new JLabel();
        floorLabel.setText("Étage : " + emplacement.getEtage());
        floorLabel.setFont(new Font("Avenir", Font.BOLD, textSize));
        floorLabel.setBorder(new EmptyBorder(borderTop, 0, 0, 0));
        floorPanel.add(floorLabel);

        //==============================================
        JPanel panelMap = new JPanel();
        panelMap.setLayout(null);
        panelMap.setBounds(60, 50,770,580);
        panelMap.setBackground(Color.WHITE);

        ImageIcon map = new ImageIcon(Objects.requireNonNull(Methodes.class.getResource("/mapV1.png")));

        JLabel mapLabel = new JLabel(map);
        mapLabel.setBounds(60, 50, 770, 580);
        mainPanel.add(mapLabel);

       // mainPanel.add(panelTestMap);

        JPanel panelPlan = new JPanel();
        panelPlan.setLayout(null);
        panelPlan.setBounds(840, 50,500,580);
        panelPlan.setBackground(Color.decode(Template.COUELUR_SECONDAIRE));
        mainPanel.add(panelPlan);

        //==============================================

            //Bouton retour :
            backHomeButton = new JButton("Retour à l'accueil");
            backHomeButton.addActionListener(this);
            backHomeButton.setBounds(610, 650, 180, 40);
            mainPanel.add(backHomeButton);
        
            //Bouton config :
            setPath = new JButton();
            setPath.setText("Configurer les chemins");
            setPath.addActionListener(this);
            setPath.setBounds(1150, 650, 200, 40);
            mainPanel.add(setPath);

        panelPlan.add(titleLabel);
        //panelTestPlan.add(aislePanel);
        panelPlan.add(shelfPanel);
        panelPlan.add(floorPanel);
        productMappingFrame.setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
       if(e.getSource() == backHomeButton){
            productMappingFrame.dispose();
            EcranAcceuil ecranAcceuil = new EcranAcceuil();
       } else if(e.getSource() == setPath){
        productMappingFrame.dispose();
        PathManagement pathManagement = new PathManagement();
       }
    }
    
}
