package edu.ezip.ing1.pds.business.dto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.lang.reflect.Field;
import java.sql.PreparedStatement;


public class SousCategorieA {
    private int idSouscatA;
    private String nomSouscatA;
    private int idCategorie;


    public SousCategorieA(){


    }

    public final SousCategorieA build(final ResultSet resultSet)
            throws SQLException, NoSuchFieldException, IllegalAccessException {
        setFieldsFromResultSet(resultSet,  "idsouscata","nomSousCatA","idcategorie");
        return this;
    }

    public  final PreparedStatement build(PreparedStatement preparedStatement)
            throws  SQLException, NoSuchFieldException, IllegalAccessException{
        return buildPreparedStatement(preparedStatement,idSouscatA,nomSouscatA,idCategorie);
    }

    public int getIdSouscatA() {
        return idSouscatA;
    }

    public void setIdSouscatA(int idSouscatA) {
        this.idSouscatA = idSouscatA;
    }

    public String getNomSouscatA() {
        return nomSouscatA;
    }

    public void setNomSouscatA(String nomSouscatA) {
        this.nomSouscatA = nomSouscatA;
    }

    public int getIdCategorie() {
        return idCategorie;
    }

    public void setIdCategorie(int idCategorie) {
        this.idCategorie = idCategorie;
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
        return "SousCategorieA{" +
                "idSouscatA='" + idSouscatA + '\'' +','+
                "nomSouscatA='" + nomSouscatA+ '\'' +','+
                "idCategorie='" + idCategorie + '\'' +','+


                '}';
    }


}
