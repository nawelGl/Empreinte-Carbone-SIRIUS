package edu.ezip.ing1.pds.front;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class WomenPants extends CategoriesTemplate implements ActionListener {

    JButton jupes;
    JButton pantalons;
    JButton jeans;
    JButton leggings;
    JButton shorts;

    public WomenPants(){
        super();

        jupes = new JButton("Jupes");
        setButtonStyle(jupes, width, height); 
        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(jupes, gbc);

        pantalons = new JButton("Pantalons");
        setButtonStyle(pantalons, width, height); 
        gbc.gridx = 1;
        gbc.gridy = 0;
        mainPanel.add(pantalons, gbc);

        jeans = new JButton("Jeans");
        setButtonStyle(jeans, width, height); 
        gbc.gridx = 2;
        gbc.gridy = 0;
        mainPanel.add(jeans, gbc);

        leggings = new JButton("Leggings");
        setButtonStyle(leggings, width, height); 
        gbc.gridx = 0;
        gbc.gridy = 1;
        mainPanel.add(leggings, gbc);

        shorts = new JButton("Shorts");
        setButtonStyle(shorts, width, height); 
        gbc.gridx = 1;
        gbc.gridy = 1;
        mainPanel.add(shorts, gbc);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
    }

    
}
