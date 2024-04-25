package edu.ezip.ing1.pds.business.dto;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Marque {
    int idMarque;
    String nomMarque;
    double co2ParAn;
    String rse;

    public Marque(){

    }

    public final Marque build(final ResultSet resultSet)
            throws SQLException, NoSuchFieldException, IllegalAccessException {
        setFieldsFromResultSet(resultSet, "idMarque", "nomMarque", "co2ParAn","rse");
        return this;
    }

    public final PreparedStatement build(PreparedStatement preparedStatement)
            throws SQLException, NoSuchFieldException, IllegalAccessException {
        return buildPreparedStatement(preparedStatement, idMarque, nomMarque, co2ParAn,rse);
    }

    public Marque(int idMarque,String nomMarque, double co2ParAn, String rse){
        this.idMarque = idMarque;
        this.nomMarque = nomMarque;
        this.co2ParAn = co2ParAn;
        this.rse=rse;
    }

    // Getters and setters for each field


    public double getCo2parAn() {
        return co2ParAn;
    }

    public int getIdMarque() {
        return idMarque;
    }

    public String getNomMarque() {
        return nomMarque;
    }

    public String getRse() {
        return rse;
    }

    public void setCo2parAn(double co2ParAn) {
        this.co2ParAn = co2ParAn;
    }

    public void setIdMarque(int idMarque) {
        this.idMarque = idMarque;
    }

    public void setNomMarque(String nomMarque) {
        this.nomMarque = nomMarque;
    }

    public void setRse(String rse) {
        this.rse = rse;
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
        return "Étage{" +
                "idMarque =" + idMarque +
                ", nomMarque ='" + nomMarque + '\'' +
                ", co2ParAn ='" + co2ParAn + '\'' +
                ", rse ='" + rse + '\'' +
                '}';
    }

}
