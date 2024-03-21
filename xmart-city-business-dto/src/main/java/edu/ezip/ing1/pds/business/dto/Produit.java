package edu.ezip.ing1.pds.business.dto;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class Produit {
    private int idProduit;
    private int idEmplacement;
    private String paysDepart;
    private String paysArrivee;
    private String couleur;
    private String taille;
    private int reference;
    private String score;
    private String genre;
    private float empreinte;
    private int idMagasin;
    private int idMarque;
    private String nomProduit;

    public Produit() {
    }

    public final Produit build(final ResultSet resultSet)
            throws SQLException, NoSuchFieldException, IllegalAccessException {
        setFieldsFromResultSet(resultSet, "idEmplacement", "paysDepart", "paysArrivee", "couleur", "taille", "reference", "score", "genre", "empreinte", "idMagasin", "idMarque", "nomProduit");
        return this;
    }


    public final PreparedStatement build(PreparedStatement preparedStatement)
            throws SQLException, NoSuchFieldException, IllegalAccessException {
        return buildPreparedStatement(preparedStatement, idEmplacement, paysDepart, paysArrivee, couleur, taille, reference, score, genre, empreinte, idMagasin, idMarque, nomProduit);
    }

    public Produit(int idProduit, int idEmplacement, String paysDepart, String paysArrive, String couleur, String taille, int reference, String score, String genre, float empreinte, int idMagasin, int idMarque, String nomProduit) {
        this.idProduit = idProduit;
        this.idEmplacement = idEmplacement;
        this.paysDepart = paysDepart;
        this.paysArrivee = paysArrive;
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


    public void setIdProduit(int idProduit) {
        this.idProduit = idProduit;
    }

    public int getIdEmplacement() {
        return idEmplacement;
    }

    public void setIdEmplacement(int idEmplacement){this.idEmplacement = idEmplacement;}

    public String getPaysDepart() {
        return paysDepart;
    }


    public void setPaysDepart(String paysDepart){this.paysDepart = paysDepart;}

    public String getPaysArrivee() {
        return paysArrivee;
    }

    public void setPaysArrivee(String paysArrivee){this.paysArrivee = paysArrivee;}

    public String getCouleur() {
        return couleur;
    }
    
    public void setCouleur(String couleur){this.couleur = couleur;}

    public String getTaille() {
        return taille;
    }

    public void setTaille(String taille){this.taille = taille;}

    public int getReference() {
        return reference;
    }

    public void setReference(int reference){this.reference = reference;}

    public String getScore() {
        return score;
    }

    public void setScore(String score) {this.score = score;}

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre){this.genre = genre;}

    public float getEmpreinte() {
        return empreinte;
    }

    public void setEmpreinte(float empreinte){this.empreinte = empreinte;}

    public int getIdMagasin() {
        return idMagasin;
    }

    public void setIdMagasin(int idMagasin){this.idMagasin = idMagasin;}

    public int getIdMarque(){return idMarque;}

    public void setIdMarque(int idMarque){this.idMarque = idMarque;}

    public String getNomProduit() {
        return nomProduit;
    }

    public void setNomProduit(String nomProduit){this.nomProduit = nomProduit;}



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
                ", paysArrive='" + paysArrivee + '\'' +
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