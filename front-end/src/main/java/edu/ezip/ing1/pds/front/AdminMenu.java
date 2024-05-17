package edu.ezip.ing1.pds.front;

import edu.ezip.ing1.pds.front.UC3.AccueilUC3;
import edu.ezip.ing1.pds.front.UC3.LoginFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class AdminMenu implements ActionListener {

    private final static String LoggingLabel = "F r o n t - A d m i n - M e n u";
    private final Logger logger = LoggerFactory.getLogger(LoggingLabel);
    private JFrame frame;
    private JButton configButton;
    private JButton UC3button;
    private String titreHeader= "Menu Administrateur";
    private int x= 580;
    private JPanel panelCentre;
   private JButton boutonArriere1;



    public AdminMenu(){
        frame= new JFrame();
        frame.setTitle("Menu administrateur");
        frame.setSize(Template.LONGUEUR,Template.LARGEUR);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        //-----panel header
        MethodesFront.header(frame,titreHeader,x);
        panelCentre = new JPanel(null);
        panelCentre.setBackground(Color.decode(Template.COULEUR_PRINCIPALE));

        ImageIcon backIcon= new ImageIcon(Objects.requireNonNull(MethodesFront.class.getResource("/back.png")));
        JButton backButton=new JButton(backIcon);
        //new JButton(backIcon);
        backButton.setBounds(1330,640,60,60);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LoginFrame loginFrame= new LoginFrame();
                frame.dispose();

            }
        });
        panelCentre.add(backButton);





        configButton = new JButton("<html><center><br><img src='" +
                getClass().getResource("/configGRANDE.png") +
                "'><br><br><font size='5' face='Avenir'>Configuration</font></center></html>");
        configButton.setBounds(300, 230, 300, 230);
        UC3button= new JButton("<html><center><br><img src='" +
                getClass().getResource("/statBisGrande.png") +
                "'><br><br><font size='5' face='Avenir'>Statistique du magasin</font></center></html>");

        UC3button.setBounds(800, 230, 300, 230);
        configButton.addActionListener(this);
        UC3button.addActionListener(this);
        panelCentre.add(configButton);
        panelCentre.add(UC3button);
        frame.add(panelCentre);




        frame.setVisible(true);

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==UC3button){
            AccueilUC3 accueilUC3=new AccueilUC3();
            frame.dispose();
        } else if(e.getSource() == configButton){
            ConfigurationFrame configurationFrame = new ConfigurationFrame();
            frame.dispose();
        }
    }
}
