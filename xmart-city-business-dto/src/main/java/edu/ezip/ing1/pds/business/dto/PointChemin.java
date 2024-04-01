package edu.ezip.ing1.pds.business.dto;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//Nom = point chemin pour eviter la confusion avec java.awt.Point.java

public class PointChemin {

    private int idPoint;
    private int coordX;
    private int coordY;
    private int idRayon;

    public PointChemin(){

    }

    public final PointChemin build(final ResultSet resultSet)
            throws SQLException, NoSuchFieldException, IllegalAccessException {
        setFieldsFromResultSet(resultSet, "idPoint", "coordX", "coordY", "idRayon");
        return this;
    }


    public final PreparedStatement build(PreparedStatement preparedStatement)
            throws SQLException, NoSuchFieldException, IllegalAccessException {
        return buildPreparedStatement(preparedStatement, idPoint, coordX, coordY, idRayon);
    }

    public PointChemin(int idPoint, int coordX, int coordY, int idRayon){
        this.idPoint = idPoint;
        this.coordX = coordX;
        this.coordY = coordY;
        this.idRayon = idRayon;
    }

    //Getters ans setters :
    public int getIdPoint() {
        return idPoint;
    }
    
    public void setIdPoint(int idPoint) {
        this.idPoint = idPoint;
    }

    public int getCoordX() {
        return coordX;
    }

    public void setCoordX(int coordX) {
        this.coordX = coordX;
    }

    public int getCoordY() {
        return coordY;
    }

    public void setCoordY(int coordY) {
        this.coordY = coordY;
    }

    public int getIdRayon() {
        return idRayon;
    }

    public void setIdRayon(int idRayon) {
        this.idRayon = idRayon;
    }


    private void setFieldsFromResultSet(final ResultSet resultSet, final String... fieldNames)
            throws NoSuchFieldException, SQLException, IllegalAccessException {
        for (final String fieldName : fieldNames) {
            final Field field = this.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(this, resultSet.getObject(fieldName));
        }
    }

    private final PreparedStatement buildPreparedStatement(PreparedStatement preparedStatement, final Object... fieldValues)
            throws SQLException, NoSuchFieldException, IllegalAccessException {
        int ix = 0;
        for (final Object fieldValue : fieldValues) {
            preparedStatement.setObject(++ix, fieldValue);
        }
        return preparedStatement;
    }

    @Override
    public String toString(){
        return "Point{" +
                "idPoint = " + idPoint +
                "coordonnée X = " + coordX +
                "coordonnée Y = " + coordY +
                "idRayon = " + idRayon+
        "}";
    }
    
}
