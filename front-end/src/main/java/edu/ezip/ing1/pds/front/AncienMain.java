
package edu.ezip.ing1.pds.front;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class AncienMain extends JFrame implements ActionListener {

    //Frame demandée
    private int longueur;
    private int largeur;
    private String titre;
    private JButton button;
    private JLabel label;

    public static AncienMain instance = null;
    private JTextArea textArea;

    //Contructeur par défaut :
    private AncienMain(){
        longueur = 800;
        largeur = 600;
        titre = "Index";

        initialize();
    }

    //Constructeur paramétré avec titre uniquement :
    private AncienMain(String titre){
        this.titre = titre;
        longueur = 900;
        largeur = 800;

        initialize();
    }

    //Constructeur paramétré avec toutes les valeurs :
    private AncienMain(String titre, int longueur, int largeur){
        this.titre = titre;
        this.longueur = longueur;
        this.largeur = largeur;

        initialize();
    }

    //Méthode getInstance pour ne pouvoir faire qu'une instance au plus.
    //Si deja une insatnce la recupere sinon en crée une
    public static AncienMain getInstance(){
        if(instance == null){
            instance = new AncienMain("Home");
        }
        return instance;
    }

    //Méthode pour initialiser la JFrame :
    public void initialize(){
        setTitle(titre);
        setSize(longueur, largeur);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        label=new JLabel("Liste des élèves :");
        label.setFont(new Font("Arial", Font.BOLD, 20));
        label.setBorder(new EmptyBorder(15, 20, 0, 0));
        
        button = new JButton();
        button.addActionListener(this);
        button.setText("Afficher les élèves");
        JPanel panel = new JPanel();
        panel.setBackground(Color.white);
        panel.setLayout(new BorderLayout());
        getContentPane().add(panel);
        panel.add(label, BorderLayout.NORTH);
        panel.add(button, BorderLayout.SOUTH);

        textArea = new JTextArea();
        textArea.setBounds(250, 70, 360, 600);
        JScrollPane scrollPane = new JScrollPane(textArea);
        JPanel panelMid = new JPanel();
        panelMid.add(scrollPane);
        panelMid.setLayout(null);
        panelMid.setBackground(Color.white);
        panelMid.add(textArea);
        panelMid.add(scrollPane);
        panel.add(panelMid, BorderLayout.CENTER);


        setVisible(true);
    }

    //TODO : eventuellement changer la taille du bouton car prend toute la longueur de la frame.

    public static void main(String[] args) {
        AncienMain frame =  AncienMain.getInstance();

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
                        if (compteur >= 11) {
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