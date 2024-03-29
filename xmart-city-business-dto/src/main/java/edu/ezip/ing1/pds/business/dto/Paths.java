package edu.ezip.ing1.pds.business.dto;

import java.util.HashMap;

public class Paths {
    //Collection de tous les chemins reliés à leur ID.
    public static HashMap<Integer, Path> paths = new HashMap<Integer, Path>();
    
    //Supprimer un chemin :
    public static void removePath(Integer numeroRayon){
        paths.remove(numeroRayon);
    }

    //Modifier un chemin : revient à le redessner donc el supprimer et le rajouter
    public static void modifyPath(Integer numeroRayon, Path path){
        removePath(numeroRayon);
        paths.put(numeroRayon, path);
    }

    public static HashMap<Integer, Path> getPaths(){
        return paths;
    }
}
