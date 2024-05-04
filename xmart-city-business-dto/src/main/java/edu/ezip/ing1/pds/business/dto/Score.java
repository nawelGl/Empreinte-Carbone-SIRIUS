package edu.ezip.ing1.pds.business.dto;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Score {
    String lettreScore;
    double borneInf;
    double borneSup;

    public Score(){

    }

    public final Score build(final ResultSet resultSet)
            throws SQLException, NoSuchFieldException, IllegalAccessException {
        setFieldsFromResultSet(resultSet, "lettreScore", "borneInf", "borneSup");
        return this;
    }

    public final PreparedStatement build(PreparedStatement preparedStatement)
            throws SQLException, NoSuchFieldException, IllegalAccessException {
        return buildPreparedStatement(preparedStatement, lettreScore, borneInf, borneSup);
    }

    public Score(String lettreScore, double borneInf, double borneSup){
        this.lettreScore = lettreScore;
        this.borneInf = borneInf;
        this.borneSup = borneSup;
    }

    public Score(String json) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Score temp = objectMapper.readValue(json, Score.class);
        this.lettreScore= temp.getlettreScore();
        this.borneInf= temp.getborneInf();
        this.borneSup= temp.getborneSup();
    }

    // Getters and setters for each field

    public String getlettreScore() {
        return lettreScore;
    }

    public void setlettreScore(String lettreScore) {
        this.lettreScore = lettreScore;
    }

    public double getborneInf() {
        return borneInf;
    }

    public void setborneInf(double borneInf) {
        this.borneInf = borneInf;
    }

    public double getborneSup() {
        return borneSup;
    }

    public void setborneSup(double borneSup) {
        this.borneSup = borneSup;
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
                "lettreScore =" + lettreScore +
                ", borneInf ='" + borneInf + '\'' +
                ", borneSup ='" + borneSup + '\'' +
                '}';
    }

}
