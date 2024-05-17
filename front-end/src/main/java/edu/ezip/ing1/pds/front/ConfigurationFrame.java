package edu.ezip.ing1.pds.front;

import edu.ezip.ing1.pds.front.UC1.RecalculFrame;
import edu.ezip.ing1.pds.front.UC2.PathManagement;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class ConfigurationFrame implements ActionListener {
    JFrame configurationFrame;
    String titreHeader = "Options de configuration";
    JButton boutonCalcul;
    JButton boutonConfigurationChemins;

    public ConfigurationFrame(){
        configurationFrame = new JFrame();
        configurationFrame.setTitle("Options de configuration");
        configurationFrame.setSize(Template.LONGUEUR,Template.LARGEUR);
        configurationFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        configurationFrame.setLocationRelativeTo(null);
        configurationFrame.setResizable(false);

        //======== header =========
        MethodesFront.header(configurationFrame, titreHeader,550);
        //=========================

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(Color.decode(Template.COULEUR_PRINCIPALE));
        mainPanel.setLayout(null);
        configurationFrame.getContentPane().add(mainPanel);

        ImageIcon backIcon= new ImageIcon(Objects.requireNonNull(MethodesFront.class.getResource("/back.png")));
        JButton backButton=new JButton(backIcon);
        //new JButton(backIcon);
        backButton.setBounds(1330,640,60,60);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AdminMenu adminMenu= new AdminMenu();
                configurationFrame.dispose();

            }
        });
        mainPanel.add(backButton);

        boutonCalcul =new JButton("<html><center><br><img src='" +
                getClass().getResource("/carbon2.png") +
                "'><br><font size='5' face='Avenir'>Configurer les scores et empreintes</font></center></html>");
        boutonCalcul.addActionListener(this);
        boutonCalcul.setBounds(300, 230, 300, 230);
        mainPanel.add(boutonCalcul);

        boutonConfigurationChemins = new JButton("<html><center><br><img src='" +
                getClass().getResource("/path.png") +
                "'><br><br><font size='5' face='Avenir'>Configurez les chemins de votre magasin</font></center></html>");

        boutonConfigurationChemins.addActionListener(this);
        boutonConfigurationChemins.setBounds(800, 230, 300, 230);
        mainPanel.add(boutonConfigurationChemins);

        configurationFrame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e){
        if(e.getSource() == boutonConfigurationChemins){
            PathManagement pathManagement = new PathManagement();
            configurationFrame.dispose();
        } else if(e.getSource() == boutonCalcul){
            RecalculFrame recalculFrame=new RecalculFrame();
            configurationFrame.dispose();
        }
    }
}
