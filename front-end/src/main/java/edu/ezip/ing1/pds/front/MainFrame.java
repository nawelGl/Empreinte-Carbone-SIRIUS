package edu.ezip.ing1.pds.front;

import edu.ezip.ing1.pds.backend.CoreBackendServer;
import edu.ezip.ing1.pds.backend.RequestHandler;
import edu.ezip.ing1.pds.commons.Request;
import edu.ezip.ing1.pds.commons.Response;
import edu.ezip.ing1.pds.business.server.XMartCityService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;

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

        System.out.println("Ok frame lancée !");

        setVisible(true);
    }

    //TODO : eventuellement changer la taille du bouton car prend toute la longueur de la frame.

    public static void main(String[] args) {
        MainFrame frame =  MainFrame.getInstance();

    }

//    @Override
//    public void actionPerformed(ActionEvent e) {
//        if(e.getSource() == button){
//            try {
//                //Test avec un echo :
//                ProcessBuilder pb = new ProcessBuilder("/bin/bash", "-c", "java -version");
//                pb.redirectOutput(ProcessBuilder.Redirect.INHERIT);
//                pb.redirectError(ProcessBuilder.Redirect.INHERIT);
//                Process process = pb.start();
//                int exitCode = process.waitFor();
//                System.out.println("Code de sortie de la commande : " + exitCode);
//            } catch (Exception ex) {
//                //TODO : ajouter un logger pour les messages d'erreur ?
//                throw new RuntimeException(ex);
//            }
//        }
//    }

//    @Override
//    public void actionPerformed(ActionEvent e) {
//        if(e.getSource() == button){
//            Runtime runtime = Runtime.getRuntime();
//            try {
//                runtime.exec("cd ..");
//                runtime.exec("cd xmart-city-backend/target");
//                runtime.exec("java -jar xmart-city-backend-1.0-SNAPSHOT-jar-with-dependencies.jar");
//            } catch (IOException ex) {
//                ex.printStackTrace();
//                System.out.println("Fail");
//            }
//            System.out.println("yoooo");
//        }
//    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == button){
            //appeler dispatch ?
            CoreBackendServer server = null;
            try {
                server = new CoreBackendServer();
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
            server.run();
//            final Request request = new Request();
//            final Connection connection;
//            final RequestHandler rh = server.requestHandlers;
//            connection = rh.getConnection();
//            request.setRequestOrder("SELECT_ALL_STUDENTS");
//            XMartCityService xmartCityService = XMartCityService.getInstance();
//            try {
//                final Response response = xmartCityService.dispatch(request, connection);
//            } catch (Exception ex) {
//                System.out.println(ex.getMessage())
//            }
        }
    }


}