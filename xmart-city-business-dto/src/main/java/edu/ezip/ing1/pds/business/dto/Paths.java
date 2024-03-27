package edu.ezip.ing1.pds.business.dto;

import java.util.HashMap;

public class Paths {
    //Collection de tous les chemins reliés à leur ID.
    HashMap<Integer, Path> paths = new HashMap<Integer, Path>();

    //Ajouter un chemin :
    public void addPath(Integer numeroRayon, Path path){
        paths.put(numeroRayon, path);
    }
    
    //Supprimer un chemin :
    public void removePath(Integer numeroRayon){
        paths.remove(numeroRayon);
    }

    //Modifier un chemin : revient à le redessner donc el supprimer et le rajouter
    public void modifyPath(Integer numeroRayon, Path path){
        removePath(numeroRayon);
        addPath(numeroRayon, path);
    }
}
