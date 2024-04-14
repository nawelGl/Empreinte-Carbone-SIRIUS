package edu.ezip.ing1.pds.business.dto;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Etage {
    int idEtage;
    String description;
    int numeroEtage;

    public Etage(){

    }

    public final Etage build(final ResultSet resultSet)
            throws SQLException, NoSuchFieldException, IllegalAccessException {
        setFieldsFromResultSet(resultSet, "idEtage", "description", "numeroEtage");
        return this;
    }

    public final PreparedStatement build(PreparedStatement preparedStatement)
            throws SQLException, NoSuchFieldException, IllegalAccessException {
        return buildPreparedStatement(preparedStatement, idEtage, description, numeroEtage);
    }

    public Etage(int idEtage, String description, int numeroEtage){
        this.idEtage = idEtage;
        this.description = description;
        this.numeroEtage = numeroEtage;
    }

    // Getters and setters for each field

    public int getIdEtage() {
        return idEtage;
    }

    public void setIdEtage(int idEtage) {
        this.idEtage = idEtage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getNumeroEtage() {
        return numeroEtage;
    }

    public void setNumeroEtage(int numeroEtage) {
        this.numeroEtage = numeroEtage;
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
                "idEtage =" + idEtage +
                ", description ='" + description + '\'' +
                ", numeroEtage ='" + numeroEtage + '\'' +
                '}';
    }

}
