package edu.ezip.ing1.pds.front;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class MenPants extends CategoriesTemplate implements ActionListener{

    JButton pantalons;
    JButton jeans;
    JButton shorts;
    JButton pantalonsSurvetement;

    public MenPants(){
        super();

        pantalons = new JButton("Pantalons");
        setButtonStyle(pantalons, width, height); 
        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(pantalons, gbc);

        jeans = new JButton("Jeans");
        setButtonStyle(jeans, width, height); 
        gbc.gridx = 1;
        gbc.gridy = 0;
        mainPanel.add(jeans, gbc);

        shorts = new JButton("Shorts");
        setButtonStyle(shorts, width, height); 
        gbc.gridx = 0;
        gbc.gridy = 1;
        mainPanel.add(shorts, gbc);

        pantalonsSurvetement = new JButton("Pantalons de survêtement");
        setButtonStyle(pantalonsSurvetement, width, height); 
        gbc.gridx = 1;
        gbc.gridy = 1;
        mainPanel.add(pantalonsSurvetement, gbc);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
    }
    
}
