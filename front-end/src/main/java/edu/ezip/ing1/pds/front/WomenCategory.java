package edu.ezip.ing1.pds.front;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class WomenCategory extends CategoriesTemplate implements ActionListener {

    //buttons for each sub category :
    JButton hauts;
    JButton bas;
    JButton robes;
    JButton vetementsMaternite;
    JButton lingerie;

    public WomenCategory(){
        super();

        hauts = new JButton("Hauts");
        setButtonStyle(hauts, width, height); 
        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(hauts, gbc);

        bas = new JButton("Bas");
        setButtonStyle(bas, width, height); 
        gbc.gridx = 1;
        gbc.gridy = 0;
        mainPanel.add(bas, gbc);

        robes = new JButton("Robes");
        setButtonStyle(robes, width, height); 
        gbc.gridx = 2;
        gbc.gridy = 0;
        mainPanel.add(robes, gbc);

        vetementsMaternite = new JButton("Vêtements de maternité");
        setButtonStyle(vetementsMaternite, width, height); 
        gbc.gridx = 0;
        gbc.gridy = 1;
        mainPanel.add(vetementsMaternite, gbc);

        lingerie = new JButton("Lingerie");
        setButtonStyle(lingerie, width, height); 
        gbc.gridx = 1;
        gbc.gridy = 1;
        mainPanel.add(lingerie, gbc);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
    }
    
}
