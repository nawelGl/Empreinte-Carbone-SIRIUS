package edu.ezip.ing1.pds.business.dto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.lang.reflect.Field;
import java.sql.PreparedStatement;


public class SousCategorieB {
    private int idSousCategorieB;
    private String nom;
    private int codeGenre;


    public SousCategorieB(){


    }

    public final SousCategorieB build(final ResultSet resultSet)
            throws SQLException, NoSuchFieldException, IllegalAccessException {
        setFieldsFromResultSet(resultSet,  "idSousCategorieB","nom","codeGenre");
        return this;
    }

    public  final PreparedStatement build(PreparedStatement preparedStatement)
            throws  SQLException, NoSuchFieldException, IllegalAccessException{
        return buildPreparedStatement(preparedStatement,idSousCategorieB,nom,codeGenre);
    }

    public int getIdSousCategorieB() {
        return idSousCategorieB;
    }

    public void setIdSousCategorieB(int idSouscatB) {
        this.idSousCategorieB = idSouscatB;
    }

    public String getNomSouscatB() {
        return nom;
    }

    public void setNomSouscatB(String nomSouscatB) {
        this.nom = nomSouscatB;
    }

    public int getcodeGenre() {
        return codeGenre;
    }

    public void setcodeGenre(int codeGenre) {
        this.codeGenre = codeGenre;
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
        return "SousCategorieB{" +
                "idSouscatA='" + idSousCategorieB + '\'' +','+
                "nomSouscatB='" + nom+ '\'' +','+
                "codeGenre='" + codeGenre + '\'' +','+
                '}';
    }


}
