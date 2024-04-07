package edu.ezip.ing1.pds.front;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class MenCategories extends CategoriesTemplate implements ActionListener{

    JButton hauts;
    JButton bas;
    JButton costumes;
    JButton sousVetements;
    JButton sport;



    public MenCategories(){
        super();

        hauts = new JButton("Hauts");
        hauts.addActionListener(this);
        setButtonStyle(hauts, width, height); 
        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(hauts, gbc);

        bas = new JButton("Bas");
        bas.addActionListener(this);
        setButtonStyle(bas, width, height); 
        gbc.gridx = 1;
        gbc.gridy = 0;
        mainPanel.add(bas, gbc);

        costumes = new JButton("Costumes");
        costumes.addActionListener(this);
        setButtonStyle(costumes, width, height); 
        gbc.gridx = 2;
        gbc.gridy = 0;
        mainPanel.add(costumes, gbc);

        sousVetements = new JButton("Sous vêtements");
        sousVetements.addActionListener(this);
        setButtonStyle(sousVetements, width, height); 
        gbc.gridx = 0;
        gbc.gridy = 1;
        mainPanel.add(sousVetements, gbc);

        sport = new JButton("Vêtements de sport");
        sport.addActionListener(this);
        setButtonStyle(sport, width, height); 
        gbc.gridx = 1;
        gbc.gridy = 1;
        mainPanel.add(sport, gbc);
    }

    @Override
    public void actionPerformed(ActionEvent e){
        super.actionPerformed(e);
        if(e.getSource() == hauts){
            MenTops menTops = new MenTops();
            categorieFrame.dispose();
        } else if (e.getSource() == bas){
            MenPants menPants = new MenPants();
            categorieFrame.dispose();
        } else if(e.getSource() == costumes){
            MenSuits menSuits = new MenSuits();
            categorieFrame.dispose();
        } else if(e.getSource() == sousVetements){
            MenUnderwears menUnderwears = new MenUnderwears();
            categorieFrame.dispose();
        } else if(e.getSource() == sport){
            MenSportswear menSportswear = new MenSportswear();
            categorieFrame.dispose();
        }
    }
}
