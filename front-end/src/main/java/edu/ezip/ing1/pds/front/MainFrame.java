package edu.ezip.ing1.pds.front;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame{

    private int longueur;
    private int largeur;
    private String titre;

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
        setVisible(true);
    }


    //Main :
    public static void main(String[] args) {
        MainFrame.getInstance().initialize();
    }

}