package edu.ezip.ing1.pds.business.dto;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class Produit {
    private int idProduit;
    private int idEmplacement;
    private int idVilleDepart;
    private int idVilleArrive;
    private String couleur;
    private String taille;
    private int reference;
    private String score;
    private String genre;
    private double empreinte;
    private int idMagasin;
    private int idMarque;
    private String nomProduit;
    private Integer idsouscatB;
    private int idTransportMode;
    private double poids;
    private double prix;
    private Integer idSousCatA;
    private Integer idCategorie;


    
public Produit(){}

    public final Produit build(final ResultSet resultSet)
            throws SQLException, NoSuchFieldException, IllegalAccessException {
        setFieldsFromResultSet(resultSet, "idEmplacement", "couleur", "taille", "reference", "score", "genre", "empreinte", "idMagasin", "idMarque", "nomProduit", "idsouscatB", "idVilleDepart", "idVilleArrive","idTransportMode", "poids","prix","idSousCatA","idCategorie");
        return this;
    }


    public final PreparedStatement build(PreparedStatement preparedStatement)
            throws SQLException, NoSuchFieldException, IllegalAccessException {
        return buildPreparedStatement(preparedStatement, idEmplacement, couleur, taille, reference, score, genre, empreinte, idMagasin, idMarque, nomProduit, idsouscatB,idVilleDepart, idVilleArrive,idTransportMode, poids,prix, idSousCatA,idCategorie);
    }

    public Produit(int idProduit, int idEmplacement, String couleur, String taille, int reference, String score, String genre, double empreinte, int idMagasin, int idMarque, String nomProduit, Integer idsouscatB, int idVilleDepart,int idVilleArrive, int idTransportMode, double poids,double prix, int idSousCatA,int idCategorie) {
        this.idProduit = idProduit;
        this.idEmplacement = idEmplacement;
        this.idVilleDepart =idVilleDepart;
        this.idVilleArrive =idVilleArrive;
        this.couleur = couleur;
        this.taille = taille;
        this.reference = reference;
        this.score = score;
        this.genre = genre;
        this.empreinte = empreinte;
        this.idMagasin = idMagasin;
        this.idMarque = idMarque;
        this.nomProduit = nomProduit;
        this.idsouscatB = idsouscatB;
        this.idTransportMode=idTransportMode;
        this.poids=poids;
        this.prix=prix;
        this.idSousCatA=idSousCatA;
        this.idCategorie=idCategorie;
    }

        public Produit(String json) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Produit temp = objectMapper.readValue(json, Produit.class);
            this.idProduit = temp.getIdProduit();
            this.idEmplacement = temp.getIdEmplacement();
            this.idVilleDepart =temp.getIdVilleDepart();
            this.idVilleArrive =temp.getIdVilleArrive();
            this.couleur = temp.getCouleur();
            this.taille = temp.getTaille();
            this.reference = temp.getReference();
            this.score = temp.getScore();
            this.genre = temp.getGenre();
            this.empreinte = temp.getEmpreinte();
            this.idMagasin = temp.getIdMagasin();
            this.idMarque = temp.getIdMarque();
            this.nomProduit = temp.getNomProduit();
            this.idsouscatB = temp.getIdsouscatB();
            this.idTransportMode=temp.getIdTransportMode();
            this.poids=temp.getPoids();
            this.prix=temp.getPrix();
            this.idSousCatA=temp.getIdSousCatA();
            this.idCategorie= temp.getIdCategorie();
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

    public double getEmpreinte() {
        return empreinte;
    }

    public void setEmpreinte(double empreinte){this.empreinte = empreinte;}

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

    public Integer getIdsouscatB() {
        return idsouscatB;
    }

    public void setIdsouscatB(Integer idsouscatB) {
        this.idsouscatB = idsouscatB;
    }

    public int getIdVilleArrive() {
        return idVilleArrive;
    }

    public void setIdVilleArrive(int idVilleArrive) {this.idVilleArrive = idVilleArrive;}

    public int getIdVilleDepart() {return idVilleDepart;}

    public void setIdVilleDepart(int idVilleDepart) {
        this.idVilleDepart = idVilleDepart;
    }

    public int getIdTransportMode() {
        return idTransportMode;
    }

    public void setIdTransportMode(int idTransportMode) {
        this.idTransportMode = idTransportMode;
    }

    public double getPoids() {
        return poids;
    }

    public void setPoids(double poids) {
        this.poids = poids;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public Integer getIdCategorie() {
        return idCategorie;
    }

    public void setIdCategorie(Integer idCategorie) {
        this.idCategorie = idCategorie;
    }

    public Integer getIdSousCatA() {
        return idSousCatA;
    }

    public void setIdSousCatA(Integer idSousCatA) {
        this.idSousCatA = idSousCatA;
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
                ",idVilleDepart='" +idVilleDepart + '\'' +
                ", idVilleArrive='" + idVilleArrive + '\'' +
                ", couleur='" + couleur + '\'' +
                ", taille='" + taille + '\'' +
                ", reference=" + reference +
                ", score=" + score +
                ", genre='" + genre + '\'' +
                ", empreinte=" + empreinte +
                ", id_magasin =" + idMagasin +
                ", nom_produit =" + nomProduit +
                ", idsouscatb =" + idsouscatB +
                ", idTransportMode =" + idTransportMode +
                ", poids =" + poids +
                ", prix =" + prix +
                ", idSousCatA =" + idSousCatA +
                ", idCategorie =" + idCategorie+
                '}';
    }

}