package edu.ezip.ing1.pds.front;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProductMapping implements ActionListener{

    JFrame productMappingFrame;
    private String productName = RechercheReference.product.getNomProduit();
    private String productColor = RechercheReference.product.getCouleur();

    public ProductMapping(){
        productMappingFrame  = new JFrame();
        productMappingFrame.setSize(Template.LONGUEUR, Template.LARGEUR);
        productMappingFrame.setTitle("Trouvez votre produit.");
        productMappingFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        productMappingFrame.setLocationRelativeTo(null);
        productMappingFrame.setResizable(false);

        //----------panel header--------------
        String titreHeader = "Se rendre au produit \"" + productName + " " + productColor + "\"";
        Methodes.header(productMappingFrame, titreHeader, 250);

        productMappingFrame.setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
    }
    
}
