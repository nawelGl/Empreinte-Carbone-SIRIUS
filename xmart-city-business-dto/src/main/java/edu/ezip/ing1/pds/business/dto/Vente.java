package edu.ezip.ing1.pds.business.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import java.lang.reflect.Field;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class Vente {
    //TODO Ajouter IdVente
    private int reference;
    private int quantite;
    private String score;
    private float empreinte;


    public Vente() {
    }

    public final Vente build(final ResultSet resultSet)
            throws SQLException, NoSuchFieldException, IllegalAccessException {
        setFieldsFromResultSet(resultSet,  "reference","quantite","score","empreinte");
        return this;
    }


    public final PreparedStatement build(PreparedStatement preparedStatement)
            throws SQLException, NoSuchFieldException, IllegalAccessException {
        return buildPreparedStatement(preparedStatement, reference,quantite,score,empreinte);
    }

    public void setReference(int reference){
        this.reference=reference;
    }
    public int getReference(){
        return reference;
    }

    public void setQuantite(int quantite){
        this.quantite = quantite;
    }

    public int getQuantite(){
        return quantite;
    }

    public  void setScore(String score){
        this.score=score;
    }
    public String getScore(){
        return score;
    }
    public void setEmpreinte(float empreinte){
        this.empreinte=empreinte;
    }
    public float getEmpreinte(){
        return empreinte;
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
        return "Vente{" +
                "reference='" + reference + '\'' +','+
                "quantite='" + quantite + '\'' +','+
                "score='" + score + '\'' +','+
                "empreinte='" + empreinte + '\''+

                '}';
    }

}