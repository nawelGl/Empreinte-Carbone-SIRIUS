package edu.ezip.ing1.pds.front.Categories;

import edu.ezip.ing1.pds.front.Categories.CategoriesTemplate;

import static java.lang.String.valueOf;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class WomenTop extends CategoriesTemplate implements ActionListener{
    //buttons for each sub category :
    JButton chemisiers;
    JButton tshirts;
    JButton blouses;
    JButton pulls;
    JButton vestes;
    JButton gilets;

    public WomenTop(){
        super();

        chemisiers = new JButton("Chemisiers");
        setButtonStyle(chemisiers, width, height); 
        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(chemisiers, gbc);

        tshirts = new JButton("T-shirts");
        setButtonStyle(tshirts, width, height); 
        gbc.gridx = 1;
        gbc.gridy = 0;
        mainPanel.add(tshirts, gbc);

        blouses = new JButton("Blouses");
        setButtonStyle(blouses, width, height); 
        gbc.gridx = 2;
        gbc.gridy = 0;
        mainPanel.add(blouses, gbc);

        pulls = new JButton("Pulls");
        setButtonStyle(pulls, width, height); 
        gbc.gridx = 0;
        gbc.gridy = 1;
        mainPanel.add(pulls, gbc);

        vestes = new JButton("Vestes");
        setButtonStyle(vestes, width, height); 
        gbc.gridx = 1;
        gbc.gridy = 1;
        mainPanel.add((vestes), gbc);

        gilets = new JButton("Gilets");
        setButtonStyle(gilets, width, height); 
        gbc.gridx = 2;
        gbc.gridy = 1;
        mainPanel.add(gilets, gbc);
    }


    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
