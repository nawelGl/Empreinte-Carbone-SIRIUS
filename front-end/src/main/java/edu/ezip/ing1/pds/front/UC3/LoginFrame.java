package edu.ezip.ing1.pds.front.UC3;

import edu.ezip.ing1.pds.front.MethodesFront;
import edu.ezip.ing1.pds.front.RoundedPanel;
import edu.ezip.ing1.pds.front.Template;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginFrame implements ActionListener {
    JFrame loginFrame;
    JButton loginBtt;
    JTextField idTxt;
    JTextField pwTxt;

    final String id ="admin"; // à connecter avec la bd
    final String pwd ="1234";

    //Todo creer le table user?
    public LoginFrame(){

        //-----setting de base--------

        loginFrame = new JFrame("Veuillez se connecter");
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setResizable(false);
        loginFrame.setSize(Template.LONGUEUR,Template.LARGEUR);
        loginFrame.setLocationRelativeTo(null);

        //------header-----------
        MethodesFront.header(loginFrame,"Connectez pour accéder à l'interface administrateur",410);

        //----------panel principal--------------
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBackground(Color.decode(Template.COULEUR_PRINCIPALE));
        loginFrame.getContentPane().add(BorderLayout.CENTER, mainPanel);

        //----------panel secondaire--------------
        //JPanel secondPanel = new JPanel();
        RoundedPanel secondPanel = new RoundedPanel(60,60);
        secondPanel.setLayout(null);
        secondPanel.setBackground(Color.decode(Template.COULEUR_SECONDAIRE));
        //secondPanel.setSize(new Dimension(30, 100));
        secondPanel.setBounds(300, 200, 800, 300);
        //Ajout du JLabel :
        JLabel titrePanelSecondaire = new JLabel("Veuillez saisir votre identifiant et mot de passe :");
        titrePanelSecondaire.setForeground(Color.WHITE);
        titrePanelSecondaire.setFont(new Font("Avenir", Font.BOLD, 21));
        titrePanelSecondaire.setBounds(40, 25, 760, 80);
        secondPanel.add(titrePanelSecondaire);

        //------------panel de ID------------
        JLabel idLabel = new JLabel("Identifiant :");
        idLabel.setForeground(Color.WHITE);
        idLabel.setFont(new Font("Avenir", Font.BOLD, 16));

        idTxt= new JTextField(20);
        idLabel.setBounds(250, 130, 120, 20); // 위치와 크기 설정
        idTxt.setBounds(360, 130, 120, 20);

        //------------panel de mot de passe------------
        JLabel pwLabel = new JLabel("Mot de passe :");
        pwLabel.setForeground(Color.WHITE);
        pwLabel.setFont(new Font("Avenir", Font.BOLD, 16));

        pwTxt= new JPasswordField(20);
        pwLabel.setBounds(250, 160, 120, 20);
        pwTxt.setBounds(360, 160, 120, 20);

        //bouton login
        loginBtt = new JButton("Connexion");
        loginBtt.addActionListener(this);
        loginBtt.setBounds(360, 197, 100, 30);


        //--------ajout des panels
        secondPanel.add(idLabel);
        secondPanel.add(idTxt);
        secondPanel.add(pwLabel);
        secondPanel.add(pwTxt);
        secondPanel.add(loginBtt);

        mainPanel.add(secondPanel);



        //----ajout au frame


        loginFrame.setVisible(true);






    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==loginBtt) {
            if (id.equals(idTxt.getText()) && pwd.equals(pwTxt.getText())){
                AccueilUC3 accueilUC3 = new AccueilUC3();
                loginFrame.dispose();
        }else {
            JOptionPane.showMessageDialog(null,"Identifiant ou mot de passe inconnue");
            }
        }

    }


}
