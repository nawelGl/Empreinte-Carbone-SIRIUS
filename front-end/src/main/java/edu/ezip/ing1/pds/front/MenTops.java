package edu.ezip.ing1.pds.front;

import java.awt.event.ActionListener;

import javax.swing.JButton;

public class MenTops extends CategoriesTemplate implements ActionListener{

    JButton chemises;
    JButton tshirts;
    JButton polos;
    JButton sweatsCapuche;
    JButton vestes;
    JButton pulls;

    public MenTops(){
        super();

        chemises = new JButton("Chemises");
        setButtonStyle(chemises, width, height); 
        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(chemises, gbc);

        tshirts = new JButton("T-shirts");
        setButtonStyle(tshirts, width, height); 
        gbc.gridx = 1;
        gbc.gridy = 0;
        mainPanel.add(tshirts, gbc);

        polos = new JButton("Polos");
        setButtonStyle(polos, width, height); 
        gbc.gridx = 2;
        gbc.gridy = 0;
        mainPanel.add(polos, gbc);

        sweatsCapuche = new JButton("Sweats à capuche");
        setButtonStyle(sweatsCapuche, width, height); 
        gbc.gridx = 0;
        gbc.gridy = 1;
        mainPanel.add(sweatsCapuche, gbc);

        vestes = new JButton("Vestes");
        setButtonStyle(vestes, width, height); 
        gbc.gridx = 1;
        gbc.gridy = 1;
        mainPanel.add(vestes, gbc);

        pulls = new JButton("Pulls");
        setButtonStyle(pulls, width, height); 
        gbc.gridx = 2;
        gbc.gridy = 1;
        mainPanel.add(pulls, gbc);
    }
    
}
