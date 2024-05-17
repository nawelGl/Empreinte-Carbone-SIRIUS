package edu.ezip.ing1.pds.front.UC3;


import edu.ezip.ing1.pds.business.dto.User;
import edu.ezip.ing1.pds.business.dto.Users;
import edu.ezip.ing1.pds.client.UC3.SelectAllUser;
import edu.ezip.ing1.pds.commons.Request;
import edu.ezip.ing1.pds.front.AdminMenu;
import edu.ezip.ing1.pds.front.MethodesFront;
import edu.ezip.ing1.pds.front.RoundedPanel;
import edu.ezip.ing1.pds.front.Template;

import edu.ezip.ing1.pds.front.*;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.IOException;
import java.util.Objects;


public class LoginFrame implements ActionListener {
    JFrame loginFrame;
    JButton loginBtt;
    JTextField idTxt;
    JTextField pwTxt;
    static Users users;
    static User user;



    public LoginFrame(){

        Request request = new Request();
        try {
            users= SelectAllUser.launchSelectAllUsers(request);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        //-----setting de base--------

        loginFrame = new JFrame("Veuillez vous connecter");
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setResizable(false);
        loginFrame.setSize(Template.LONGUEUR,Template.LARGEUR);
        loginFrame.setLocationRelativeTo(null);

        //------header-----------
        MethodesFront.header(loginFrame,"Connectez pour accéder à votre statistisque",500);

        //----------panel principal--------------
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBackground(Color.decode(Template.COULEUR_PRINCIPALE));
        loginFrame.getContentPane().add(BorderLayout.CENTER, mainPanel);

        ImageIcon backIcon= new ImageIcon(Objects.requireNonNull(MethodesFront.class.getResource("/back.png")));
        JButton backButton=new JButton(backIcon);
        //new JButton(backIcon);
        backButton.setBounds(1330,640,60,60);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EcranAcceuil ecranAcceuil= new EcranAcceuil();
                loginFrame.dispose();

            }
        });
        mainPanel.add(backButton);


        //----------panel secondaire--------------
        //JPanel secondPanel = new JPanel();
        RoundedPanel secondPanel = new RoundedPanel(60,60);
        secondPanel.setLayout(null);
        secondPanel.setBackground(Color.decode(Template.COULEUR_SECONDAIRE));
        //secondPanel.setSize(new Dimension(30, 100));
        secondPanel.setBounds(300, 200, 800, 350);
        //Ajout du JLabel :
        JLabel titrePanelSecondaire = new JLabel("Veuillez saisir votre identifiant et mot de passe :");
        titrePanelSecondaire.setForeground(Color.WHITE);
        titrePanelSecondaire.setFont(Template.FONT_TITRES);
        titrePanelSecondaire.setBounds(38, 23, 760, 80);
        secondPanel.add(titrePanelSecondaire);

        //------------panel de ID------------
        JLabel idLabel = new JLabel("Identifiant:");
        idLabel.setForeground(Color.WHITE);

        idLabel.setFont(new Font("Avenir", Font.BOLD, 17));


        idTxt= new JTextField(20);
        idLabel.setBounds(250, 165, 120, 20); // 위치와 크기 설정
        idTxt.setBounds(360, 165, 120, 20);

        //------------panel de mot de passe------------
        JLabel pwLabel = new JLabel("Mot de passe:");
        pwLabel.setForeground(Color.WHITE);

        pwLabel.setFont(new Font("Avenir", Font.BOLD, 17));


        pwTxt= new JPasswordField(20);
        pwLabel.setBounds(250, 190, 120, 20);
        pwTxt.setBounds(360, 190, 120, 20);



        //bouton login
        loginBtt = new JButton("Connexion");
        loginBtt.addActionListener(this);
        loginBtt.setBounds(360, 290, 100, 30);



        //--------ajout des panels
        secondPanel.add(idLabel);
        secondPanel.add(idTxt);
        secondPanel.add(pwLabel);
        secondPanel.add(pwTxt);
        secondPanel.add(loginBtt);

        mainPanel.add(secondPanel);


        loginFrame.setVisible(true);






    }
    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource()==loginBtt) {
            if (authenticateUser()){
            loginFrame.dispose();
            AdminMenu adminMenu=new AdminMenu();
        }else {
            JOptionPane.showMessageDialog(null,"Identifiant ou mot de passe inconnu");
            }
        }

    }

    public boolean authenticateUser(){
        boolean loginSuccess=false;
        String enteredId = idTxt.getText();
        String enteredPassword = pwTxt.getText();

        for(User user : users.getUsers()){
            if(user.getIdentifiant().equals(enteredId) && String.valueOf(user.getPassword()).equals(enteredPassword)){
                loginSuccess = true;
                break;
            }
        }

        return loginSuccess;
    }


}
