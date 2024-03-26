package edu.ezip.ing1.pds.front;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

public class PathManagement implements ActionListener{

    JFrame pathManagementFrame = new JFrame();

    public PathManagement(){

        pathManagementFrame  = new JFrame();
        pathManagementFrame.setSize(Template.LONGUEUR, Template.LARGEUR);
        pathManagementFrame.setTitle("Configurez vos chemins.");
        pathManagementFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pathManagementFrame.setLocationRelativeTo(null);
        pathManagementFrame.setResizable(false);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
    }
    
}
