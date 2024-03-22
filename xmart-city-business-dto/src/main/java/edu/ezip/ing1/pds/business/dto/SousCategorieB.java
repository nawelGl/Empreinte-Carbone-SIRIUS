package edu.ezip.ing1.pds.business.dto;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SousCategorieB {
    private int idSousCategorieB;
    private String nom;
    private int idSousCategorieA;

    public SousCategorieB(){

    }

    public SousCategorieB(int idSousCategorieB, String nom, int idSousCategorieA){
        this.idSousCategorieB = idSousCategorieB;
        this.nom = nom;
        this.idSousCategorieA = idSousCategorieA;
    }

    public int getIdSousCategorieB() {
        return idSousCategorieB;
    }

    public void setIdSousCategorieB(int idSousCategorieB) {
        this.idSousCategorieB = idSousCategorieB;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getIdSousCategorieA() {
        return idSousCategorieA;
    }

    public void setIdSousCategorieA(int idSousCategorieA) {
        this.idSousCategorieA = idSousCategorieA;
    }

    public final SousCategorieB build(final ResultSet resultSet)
            throws SQLException, NoSuchFieldException, IllegalAccessException {
        setFieldsFromResultSet(resultSet, "idSousCategorieB", "nom", "idSousCategorieA");
        return this;
    }

    public final PreparedStatement build(PreparedStatement preparedStatement)
            throws SQLException, NoSuchFieldException, IllegalAccessException {
        return buildPreparedStatement(preparedStatement, idSousCategorieB, nom, idSousCategorieA);
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
                "idSousCategorieB = " + idSousCategorieB +
                ", nom = " + nom +
                ", idSousCategorieA='" + idSousCategorieA + '\'' +
                '}';
    }
}
