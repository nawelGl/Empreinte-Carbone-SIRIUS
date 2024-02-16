package edu.ezip.ing1.pds.business.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@JsonRootName(value = "produit")
public class Produit {
    private int idProduit = 0;
    private int idEmplacement;
    private String paysDepart;
    private String paysArrive;
    private String couleur;
    private String taille;
    private int reference;
    private char score;
    private String genre;
    private double empreinte;
    private int idMagasin;
    private int idMarque;
    private String nomProduit;

    public Produit() {
        this.couleur = "vert";
        this. taille = "S";
        this.paysArrive= "maroc";
    }

    public Produit(String paysArrive, String couleur, String taille) {
        this.paysArrive = paysArrive;
        this.couleur = couleur;
        this.taille = taille;
    }
    public final Produit build(final ResultSet resultSet)
            throws SQLException, NoSuchFieldException, IllegalAccessException {
        setFieldsFromResultSet(resultSet, "id_emplacement", "pays_depart", "pays_arrive", "couleur", "taille", "reference", "score", "genre", "empreinte", "id_magasin", "id_marque", "nom_produit");
        return this;
    }


    public final PreparedStatement build(PreparedStatement preparedStatement)
            throws SQLException, NoSuchFieldException, IllegalAccessException {
        return buildPreparedStatement(preparedStatement, idProduit, idEmplacement, paysDepart, paysArrive, couleur, taille, reference, score, genre, empreinte, idMagasin, idMarque, nomProduit);
    }

    public Produit(int idProduit, int idEmplacement, String paysDepart, String paysArrive, String couleur, String taille, int reference, char score, String genre, double empreinte, int idMagasin, int idMarque, String nomProduit) {
        this.idProduit = idProduit;
        this.idEmplacement = idEmplacement;
        this.paysDepart = paysDepart;
        this.paysArrive = paysArrive;
        this.couleur = couleur;
        this.taille = taille;
        this.reference = reference;
        this.score = score;
        this.genre = genre;
        this.empreinte = empreinte;
        this.idMagasin = idMagasin;
        this.idMarque = idMarque;
        this.nomProduit = nomProduit;
    }

    // Getters and setters for each field
    public int getIdProduit() {
        return idProduit;
    }

    public int getIdEmplacement() {
        return idEmplacement;
    }

    public String getPaysDepart() {
        return paysDepart;
    }

    public String getPaysArrive() {
        return paysArrive;
    }

    public String getCouleur() {
        return couleur;
    }

    public String getTaille() {
        return taille;
    }

    public int getReference() {
        return reference;
    }

    public char getScore() {
        return score;
    }

    public String getGenre() {
        return genre;
    }

    public double getEmpreinte() {
        return empreinte;
    }

    public int getIdMagasin() {
        return idMagasin;
    }

 //   public int getIdMarque() {
//        return idMarque;
//    }


    public void setIdProduit(int idProduit) {
        this.idProduit = idProduit;
    }

    public String getNomProduit() {
        return nomProduit;
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
        return "Produit{" +
                "idProduit=" + idProduit +
                ", idEmplacement=" + idEmplacement +
                ", paysDepart='" + paysDepart + '\'' +
                ", paysArrive='" + paysArrive + '\'' +
                ", couleur='" + couleur + '\'' +
                ", taille='" + taille + '\'' +
                ", reference=" + reference +
                ", score=" + score +
                ", genre='" + genre + '\'' +
                ", empreinte=" + empreinte +
                ", id_magasin =" + idMagasin +
                ", nom_produit =" + nomProduit +
                '}';
    }
}