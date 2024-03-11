package edu.ezip.ing1.pds.business.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import java.lang.reflect.Field;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@JsonRootName(value = "vente")
public class Vente {
    private int idMagasin;
    private int idProduit;
    private long dateEnMs;
    private int quantite;
    private Date date;


    public Vente() {
    }

    public final Vente build(final ResultSet resultSet)
            throws SQLException, NoSuchFieldException, IllegalAccessException {
        setFieldsFromResultSet(resultSet, "idMagasin", "idProduit", "date", "quantite");
        return this;
    }


    public final PreparedStatement build(PreparedStatement preparedStatement)
            throws SQLException, NoSuchFieldException, IllegalAccessException {
        return buildPreparedStatement(preparedStatement, idMagasin, idProduit, date, quantite);
    }

    public Vente(int idMagasin, int idProduit, long date, int quantite) {
        this.idMagasin = idMagasin;
        this.idProduit = idProduit;
        this.dateEnMs = date;
        this.date = new Date(dateEnMs);
        this.quantite = quantite;
    }

    // Getters and setters for each field
    @JsonProperty("vente_idMagasin")
    public int getIdMagasin(){
        return idMagasin;
    }

    public void setIdMagasin(int id){
        idMagasin = id;
    }

    @JsonProperty("vente_idProduit")
    public int getIdProduit(){
        return idProduit;
    }

    public void setIdProduit(int id){
        idProduit = id;
    }


    @JsonProperty("vente_date")
    public long getDateEnMs() {
        return dateEnMs;
    }

    public void setDateEnMs(long dateEnMs) {
        this.dateEnMs = dateEnMs;
    }
    @JsonProperty("vente_quantite")
    public int getQuantite(){
        return quantite;
    }

    public void setQuantite(int qtt){
        quantite = qtt;
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
                "idMagasin =" + idMagasin +
                ", idProduit=" + idProduit +
                ", date=" + date + '\'' +
                ", quantite='" + quantite + '\'' +
                '}';
    }

}