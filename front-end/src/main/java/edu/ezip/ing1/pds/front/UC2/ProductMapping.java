package edu.ezip.ing1.pds.front.UC2;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import edu.ezip.ing1.pds.business.dto.Emplacement;
import edu.ezip.ing1.pds.client.SelectEmplacementById;
import edu.ezip.ing1.pds.commons.Request;
import edu.ezip.ing1.pds.front.*;
import edu.ezip.ing1.pds.front.UC2.PathManagement;

import static java.lang.String.valueOf;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class ProductMapping implements ActionListener{

    JFrame productMappingFrame;
    private String productName = RechercheReference.getProduct().getNomProduit();
    private String productColor = RechercheReference.getProduct().getCouleur();
    JButton setPath;

    private Emplacement emplacement;
    private int idEmplacement = RechercheReference.getProduct().getIdEmplacement();
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

        try{
            if(emplacement.getEtage() == null || emplacement.getAllee() == null || emplacement.getIdRayon() == 0){
                throw new Exception();
            }
        } catch(Exception exc){
            System.out.println(exc.getMessage());
            EcranAcceuil ecranAcceuil = new EcranAcceuil();
            JOptionPane.showMessageDialog(productMappingFrame, "[ERREUR 404] Attention, la connection avec le serveur n'a pas pu être établie.", "[ERROR 404] - Connection refusée !", JOptionPane.ERROR_MESSAGE);
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
        shelfLabel.setText("Rayon n° " + emplacement.getIdRayon());
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
        Border border = BorderFactory.createLineBorder(Color.BLACK);
        panelMap.setBorder(border);
        panelMap.setBackground(Color.WHITE);

        ImageIcon map = new ImageIcon(Objects.requireNonNull(Methodes.class.getResource("/mapV1.png")));

        JLabel mapLabel = new JLabel(map);
        mapLabel.setBounds(60, 50, 770, 580);
        mainPanel.add(mapLabel);

       // mainPanel.add(panelTestMap);

        JPanel panelPlan = new JPanel();
        panelPlan.setLayout(null);
        panelPlan.setBounds(840, 50,500,580);
        panelPlan.setBackground(Color.decode(Template.COULEUR_SECONDAIRE));
        mainPanel.add(panelPlan);

        //==============================================
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
       if(e.getSource() == setPath){
            PathManagement pathManagement = new PathManagement();
            productMappingFrame.dispose();
       }
    }
    
}
