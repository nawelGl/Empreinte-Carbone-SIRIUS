package edu.ezip.ing1.pds.front.UC3;

import edu.ezip.ing1.pds.front.Methodes;
import edu.ezip.ing1.pds.front.Template;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginFrame implements ActionListener {
    JFrame loginFrame;
    JButton loginBtt;


    public LoginFrame(){

        //-----setting de base--------

        loginFrame = new JFrame("Veuillez se connecter");
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setResizable(false);
        loginFrame.setSize(Template.LONGUEUR,Template.LARGEUR);
        loginFrame.setLocationRelativeTo(null);

        //------header-----------
        Methodes.header(loginFrame,"Vos statistiques par produit",525);


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

        //bouton login
        JButton loginBtt = new JButton("Login");

        loginBtt.addActionListener(this);

        JPanel subPanel = new JPanel();
        subPanel.add(loginBtt);


        //--------ajout des panels
        loginPanel.add(idPanel);
        loginPanel.add(pwPanel);
        loginPanel.add(subPanel);

        loginPanel.setBounds(300,200,600,450);

        //----ajout au frame

        loginFrame.add(loginPanel);
        loginFrame.setVisible(true);






    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==loginBtt) {
            loginFrame.dispose();
            AccueilUC3 accueilUC3 = new AccueilUC3();
        }

    }


}
