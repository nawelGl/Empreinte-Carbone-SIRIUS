package edu.ezip.ing1.pds.business.dto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.lang.reflect.Field;
import java.sql.PreparedStatement;


public class Categorie {
    private int idCategorie;
    private String nomCategorie;



    public Categorie(){


    }

    public final Categorie build(final ResultSet resultSet)
            throws SQLException, NoSuchFieldException, IllegalAccessException {
        setFieldsFromResultSet(resultSet,  "idCategorie","nomCategorie");
        return this;
    }

    public  final PreparedStatement build(PreparedStatement preparedStatement)
            throws  SQLException, NoSuchFieldException, IllegalAccessException{
        return buildPreparedStatement(preparedStatement,idCategorie,nomCategorie);
    }


    public int getIdCategorie() {
        return idCategorie;
    }

    public void setIdCategorie(int idCategorie) {
        this.idCategorie = idCategorie;
    }

    public String getNomCategorie() {
        return nomCategorie;
    }

    public void setNomCategorie(String nomCategorie) {
        this.nomCategorie = nomCategorie;
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
                "idCategorie='" + idCategorie + '\'' +','+
                "nomCategorie='" + nomCategorie+ '\'' +','+


                '}';
    }


}
