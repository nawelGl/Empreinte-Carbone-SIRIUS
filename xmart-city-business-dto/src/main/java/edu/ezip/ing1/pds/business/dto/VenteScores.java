package edu.ezip.ing1.pds.business.dto;


import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;


public class VenteScores {


    private List<VenteScore> venteScores = new ArrayList<>();

    public List<VenteScore> getVenteScores() {
        return venteScores;
    }

    public void setVenteScores(List<VenteScore> venteScores) {
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
