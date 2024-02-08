package edu.ezip.ing1.pds.frontEnd;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame{

    private int longueur;
    private int largeur;
    private String titre;

    public static MainFrame instance = null;

    //Contructeur par défaut :
    private MainFrame(){
        longueur = 500;
        largeur = 700;
        titre = "Index";
    }

    //Constructeur paramétré avec titre uniquement :
    private MainFrame(String titre){
        this.titre = titre;
        longueur = 500;
        largeur = 700;
    }

    //Constructeur paramétré avec toutes les valeurs :
    private MainFrame(String titre, int longueur, int largeur){
        this.titre = titre;
        this.longueur = longueur;
        this.largeur = largeur;
    }

//Méthode getInstance pour ne pouvoir faire qu'une instance au plus.
    public Frame getInstance(){
        if(instance == null){
            instance = new MainFrame();
        }
        return instance;
    }

    //Méthode pour initialiser la JFrame :
    public void initialize(){
        MainFrame frame = new MainFrame();
        frame.setTitle(titre);
        frame.setSize(longueur, largeur);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setVisible(true);
    }


    //Main :
    public static void main(String[] args) {
        MainFrame mainFrame = new MainFrame("Home");
    }

}