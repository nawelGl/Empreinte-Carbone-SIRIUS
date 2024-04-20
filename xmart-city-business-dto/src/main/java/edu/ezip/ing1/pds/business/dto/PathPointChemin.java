package edu.ezip.ing1.pds.business.dto;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

//    public final PathPointChemin build(final ResultSet resultSet)
//            throws SQLException, NoSuchFieldException, IllegalAccessException {
//        setFieldsFromResultSet(resultSet, "idPoint", "coordX", "coordY", "idRayon");
//        return this;
//    }
//
//    public final PreparedStatement build(PreparedStatement preparedStatement)
//            throws SQLException, NoSuchFieldException, IllegalAccessException {
//        return buildPreparedStatement(preparedStatement, idPoint, coordX, coordY, idRayon);
//    }

    public String toString(){
        StringBuilder string = new StringBuilder();
        string.append("Path de coordonnées :\n");
        for (PointChemin point : pathPoints) {
            string.append("(" + point.getCoordX() + ", " + point.getCoordY() + ")\n");
        }
        return String.valueOf(string);
    }
}
