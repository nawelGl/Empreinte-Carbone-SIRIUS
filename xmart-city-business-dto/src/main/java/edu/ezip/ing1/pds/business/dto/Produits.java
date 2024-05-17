package edu.ezip.ing1.pds.business.dto;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

public class Produits {

    private ArrayList<Produit> produits = new ArrayList<>();

    public ArrayList<Produit> getProduits() {
        return produits;
    }

    public void setProduits(ArrayList<Produit> produits) {
        this.produits = produits;
    }

    public final Produits add(final Produit produit) {
        produits.add(produit);
        return this;
    }

    @Override
    public String toString() {
        return "Produits{" +
                "produits=" + produits +
                '}';
    }
}