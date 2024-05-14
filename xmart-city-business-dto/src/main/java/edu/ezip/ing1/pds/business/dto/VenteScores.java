package edu.ezip.ing1.pds.business.dto;


import java.util.LinkedHashSet;
import java.util.Set;


public class VenteScores {


    private Set<VenteScore> venteScores = new LinkedHashSet<>();

    public Set<VenteScore> getVenteScores() {
        return venteScores;
    }

    public void setVenteScores(Set<VenteScore> venteScores) {
        this.venteScores = venteScores;
    }

    public final VenteScores add(final VenteScore venteScore) {
        venteScores.add(venteScore);
        return this;
    }

    @Override
    public String toString() {
        return "VenteScores{" +
                "venteScores=" + venteScores +
                '}';
    }
}
