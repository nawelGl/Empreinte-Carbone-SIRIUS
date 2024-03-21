package edu.ezip.ing1.pds.business.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import java.util.LinkedHashSet;
import java.util.Set;


public class Ventes {


    private Set<Vente> ventes = new LinkedHashSet<>();

    public Set<Vente> getVentes() {
        return ventes;
    }

    public void setVentes(Set<Vente> ventes) {
        this.ventes = ventes;
    }

    public final Ventes add(final Vente vente) {
        ventes.add(vente);
        return this;
    }

    @Override
    public String toString() {
        return "Ventes{" +
                "ventes=" + ventes +
                '}';
    }
}