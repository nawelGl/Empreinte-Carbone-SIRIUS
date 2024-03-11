package edu.ezip.ing1.pds.business.dto;

import java.util.LinkedHashSet;
import java.util.Set;

public class Produits {

    private Set<Produit> produits = new LinkedHashSet<>();

    public Set<Produit> getProduits() {
        return produits;
    }

    public void setProduits(Set<Produit> produits) {
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