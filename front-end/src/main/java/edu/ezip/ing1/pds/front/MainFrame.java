package edu.ezip.ing1.pds.front;

import ch.qos.logback.classic.net.JMSQueueAppender;
import edu.ezip.ing1.pds.backend.CoreBackendServer;
import edu.ezip.ing1.pds.backend.RequestHandler;
import edu.ezip.ing1.pds.commons.Request;
import edu.ezip.ing1.pds.commons.Response;
import edu.ezip.ing1.pds.business.server.XMartCityService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;

public class MainFrame extends JFrame implements ActionListener {

    private int longueur;
    private int largeur;
    private String titre;
    private JButton button;
    private JLabel label;

    public static MainFrame instance = null;
    private JTextArea textArea;

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
    //Si deja une insatnce la recupere sinon en crée une
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

        label=new JLabel("Liste de tous les élèves");
        label.setFont(new Font("Arial", Font.BOLD, 20));



        button = new JButton();
        button.addActionListener(this);
        button.setText("Select data on database");
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        getContentPane().add(panel);
        panel.add(label, BorderLayout.NORTH);
        panel.add(button, BorderLayout.SOUTH);

        textArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(textArea);
        panel.add(scrollPane, BorderLayout.CENTER);


        setVisible(true);
    }

    //TODO : eventuellement changer la taille du bouton car prend toute la longueur de la frame.

    public static void main(String[] args) {
        MainFrame frame =  MainFrame.getInstance();

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == button) {
            try {
                // Définir la commande pour exécuter le fichier JAR
                String command = "java -jar /Users/nawreshajabouda/Documents/GitHub/Empreinte-Carbone-SIRIUS/xmart-select-client/target/xmart-select-client-1.0-SNAPSHOT-jar-with-dependencies.jar";

                // Lancer la commande
                Process process = Runtime.getRuntime().exec(command);

                // Lire la sortie de la commande
                InputStream inputStream = process.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                int compteur=0;
                while ((line = reader.readLine()) != null ) {

                        compteur++; // On veut afficher a partir de la 9ieme lignes
                        if (compteur >= 10) {
                            // Ajouter chaque ligne à partir de la 9ème ligne au JTextArea
                            textArea.append(line + "\n");
                        }
                }


                // Lire la sortie d'erreur de la commande (si nécessaire)
                InputStream errorStream = process.getErrorStream();
                BufferedReader errorReader = new BufferedReader(new InputStreamReader(errorStream));

                // Afficher la sortie d'erreur
                while ((line = errorReader.readLine()) != null) {
                    System.err.println(line);
                }

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }



}