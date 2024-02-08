package edu.ezip.ing1.pds.front;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class MainFrame extends JFrame implements ActionListener {

    private int longueur;
    private int largeur;
    private String titre;
    private JButton button;

    public static MainFrame instance = null;

    //Contructeur par défaut :
    private MainFrame(){
        longueur = 800;
        largeur = 600;
        titre = "Index";

        initialize();
    }

    //Constructeur paramétré avec titre uniquement :
    private MainFrame(String titre){
        this.titre = titre;
        longueur = 800;
        largeur = 600;

        initialize();
    }

    //Constructeur paramétré avec toutes les valeurs :
    private MainFrame(String titre, int longueur, int largeur){
        this.titre = titre;
        this.longueur = longueur;
        this.largeur = largeur;

        initialize();
    }

    //Méthode getInstance pour ne pouvoir faire qu'une instance au plus.
    public static MainFrame getInstance(){
        if(instance == null){
            instance = new MainFrame("Home");
        }
        return instance;
    }

    //Méthode pour initialiser la JFrame :
    public void initialize(){
        setTitle(titre);
        setSize(longueur, largeur);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        button = new JButton();
        button.addActionListener(this);
        button.setText("Select data on database");
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        getContentPane().add(panel);
        panel.add(button, BorderLayout.SOUTH);

        setVisible(true);
    }

    //TODO : eventuellement changer la taille du bouton car prend toute la longueur de la frame.

    public static void main(String[] args) {
        MainFrame frame =  MainFrame.getInstance();
        frame.initialize();

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == button){
            try {
                //Test avec un echo :
                ProcessBuilder pb = new ProcessBuilder("/bin/bash", "-c", "java -version");
                pb.redirectOutput(ProcessBuilder.Redirect.INHERIT);
                pb.redirectError(ProcessBuilder.Redirect.INHERIT);
                Process process = pb.start();
                int exitCode = process.waitFor();
                System.out.println("Code de sortie de la commande : " + exitCode);
            } catch (Exception ex) {
                //TODO : ajouter un logger pour les messages d'erreur ?
                throw new RuntimeException(ex);
            }
        }
    }
}