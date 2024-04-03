package edu.ezip.ing1.pds.front.UC3;

import edu.ezip.ing1.pds.front.Template;

import javax.swing.*;

public class LoginFrame {
    JFrame loginFrame;
    public LoginFrame(){
        loginFrame = new JFrame("Veuillez se connecter");
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setResizable(false);
        loginFrame.setSize(Template.LONGUEUR,Template.LARGEUR);
        loginFrame.setLocationRelativeTo(null);
    }


}
