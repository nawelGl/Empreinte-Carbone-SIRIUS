package edu.ezip.ing1.pds.business.dto;


import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BestSeller {

    private int reference;

    private String score;

    private String taille;
    private String genre;
    private double empreinte;
    private double prix;

    private int sum;



    public BestSeller() {
    }

    public final BestSeller build(final ResultSet resultSet)
            throws SQLException, NoSuchFieldException, IllegalAccessException {
        setFieldsFromResultSet(resultSet,  "reference","score","taille","genre","empreinte","prix","sum");
        return this;
    }


    public final PreparedStatement build(PreparedStatement preparedStatement)
            throws SQLException, NoSuchFieldException, IllegalAccessException {
        return buildPreparedStatement(preparedStatement, reference,score,taille,genre,empreinte,prix,sum);
    }

    public int getReference() {
        return reference;
    }

    public void setReference(int reference) {
        this.reference = reference;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getTaille() {
        return taille;
    }

    public void setTaille(String taille) {
        this.taille = taille;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public double getEmpreinte() {
        return empreinte;
    }

    public void setEmpreinte(double empreinte) {
        this.empreinte = empreinte;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum){this.sum=sum;}



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
        return "BestSeller{" +
                "reference='" + reference + '\'' +','+
                "score='" + score + '\'' +','+
                "taille='" + taille + '\'' +','+
                "genre='" + genre + '\'' +','+
                "empreinte='" + empreinte + '\'' +','+
                "prix='" + prix + '\'' +','+
                "sum1='" + sum+ '\''+

                '}';
    }

}