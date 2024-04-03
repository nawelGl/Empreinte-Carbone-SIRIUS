package edu.ezip.ing1.pds.front.UC3;

import edu.ezip.ing1.pds.front.Template;

import javax.swing.*;

public class LoginFrame {
    JFrame loginFrame;

    public LoginFrame(){

        //-----setting de base--------

        loginFrame = new JFrame("Veuillez se connecter");
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setResizable(false);
        loginFrame.setSize(Template.LONGUEUR,Template.LARGEUR);
        loginFrame.setLocationRelativeTo(null);


        //-----------panel de login

        JPanel loginPanel = new JPanel();

        //------------panel de ID------------
        JLabel idLabel = new JLabel("ID:");
        JTextField idTxt= new JTextField(10);

        JPanel idPanel = new JPanel();
        idPanel.add(idLabel);
        idPanel.add(idTxt);

        //------------panel de mot de passe------------
        JLabel pwLabel = new JLabel("Password:");
        JTextField pwTxt= new JTextField(10);

        JPanel pwPanel = new JPanel();
        pwPanel.add(pwLabel);
        idPanel.add(pwTxt);

        //bouton login ou annuler
        JButton loginBtt = new JButton("Login");
        JButton cancleBtt = new JButton("Annuler");
        JPanel subPanel = new JPanel();
        subPanel.add(loginBtt);
        subPanel.add(cancleBtt);

        //--------ajout des panels
        loginPanel.add(idPanel);
        loginPanel.add(pwPanel);
        loginPanel.add(subPanel);

        //----ajout au frame

        loginFrame.add(loginPanel);






    }


}
