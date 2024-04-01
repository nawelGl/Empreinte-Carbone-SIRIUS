package edu.ezip.ing1.pds.business.dto;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Emplacement {
    private int idEmplacement;
    private String allee;
    private int idRayon;
    private String etage;

    public Emplacement(){

    }

    public Emplacement(int idEmplacement, String allee, int rayon, String etage){
        this.idEmplacement = idEmplacement;
        this.allee = allee;
        this.idRayon = rayon;
        this.etage = etage;
    }

    // Getters and setters for each field
    public int getIdEmplacement() {
        return idEmplacement;
    }

    public void setIdEmplacement(int idEmplacement) {
        this.idEmplacement = idEmplacement;
    }

    public String getAllee() {
        return allee;
    }

    public void setAllee(String allee) {
        this.allee = allee;
    }

    public int getIdRayon() {
        return idRayon;
    }

    public void setIdRayon(int rayon) {
        this.idRayon = rayon;
    }

    public String getEtage() {
        return etage;
    }

    public void setEtage(String etage) {
        this.etage = etage;
    }

    public final Emplacement build(final ResultSet resultSet)
            throws SQLException, NoSuchFieldException, IllegalAccessException {
        setFieldsFromResultSet(resultSet, "idEmplacement", "allee", "idRayon", "etage");
        return this;
    }

        public final PreparedStatement build(PreparedStatement preparedStatement)
            throws SQLException, NoSuchFieldException, IllegalAccessException {
        return buildPreparedStatement(preparedStatement, idEmplacement, allee, idRayon, etage);
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
        return "Emplacement{" +
                "idEmplacement=" + idEmplacement +
                ", allée ='" + allee + '\'' +
                ", rayon ='" + idRayon + '\'' +
                ", étage ='" + etage + '\'' +
                '}';
    }
    
}
