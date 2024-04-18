package edu.ezip.ing1.pds.business.dto;

import java.util.ArrayList;

public class PathPointChemin {
    //Collection de points (PointChemin) pour le SELECT

    private ArrayList<PointChemin> pathPoints;

    public PathPointChemin(){
        pathPoints = new ArrayList<PointChemin>();
    }

    public ArrayList<PointChemin> getPoints(){
        return pathPoints;
    }



    public String toString(){
        StringBuilder string = new StringBuilder();
        string.append("Path de coordonnées :\n");
        for (PointChemin point : pathPoints) {
            string.append("(" + point.getCoordX() + ", " + point.getCoordY() + ")\n");
        }
        return String.valueOf(string);
    }
}
