package edu.ezip.ing1.pds.business.dto;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Ville {
    private int idVille;
    private String nomVille;
    private String nomPays;
    private double coordLatitude;
    private double coordLongitude;

    public Ville(){

    }

    public Ville(int idVille, String nomVille, String nomPays,double coordLatitude, double coordLongitude){
        this.idVille = idVille;
        this.nomVille= nomVille;
        this.nomPays= nomPays;
        this.coordLatitude= coordLatitude;
        this.coordLongitude= coordLongitude;

    }

    // Getters and setters for each field
    public int getIdVille() {
        return idVille;
    }

    public void setIdVille(int idVille) {
        this.idVille = idVille;
    }

    public double getCoordLatitude() {
        return coordLatitude;
    }

    public double getCoordLongitude() {
        return coordLongitude;
    }

    public String getNomPays() {
        return nomPays;
    }

    public final Ville build(final ResultSet resultSet)
            throws SQLException, NoSuchFieldException, IllegalAccessException {
        setFieldsFromResultSet(resultSet, "idVille", "nomVille", "nomPays", "coordLatitude","coordLongitude");
        return this;
    }

    public final PreparedStatement build(PreparedStatement preparedStatement)
            throws SQLException, NoSuchFieldException, IllegalAccessException {
        return buildPreparedStatement(preparedStatement, idVille, nomVille, nomPays, coordLatitude, coordLongitude);
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
    public String toString() {
        return "Ville{" +
                "idVille=" + idVille +
                ",nomVille ='" + nomVille + '\'' +
                ", nomPays ='" + nomPays + '\'' +
                ", coordLatitude ='" + coordLatitude + '\'' +
                ", coordLongitude ='" + coordLongitude + '\'' +
                '}';
    }

}
