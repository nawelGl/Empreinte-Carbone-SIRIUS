package edu.ezip.ing1.pds.business.dto;

import java.util.LinkedHashSet;
import java.util.Set;

public class SousCategoriesB {

    private Set<SousCategorieB> sousCategoriesB = new LinkedHashSet<>();

    public Set<SousCategorieB> getSousCategoriesB() {
        return sousCategoriesB;
    }

    public void setSousCategoriesB(Set<SousCategorieB> sousCategoriesB) {
        this.sousCategoriesB =sousCategoriesB;
    }

    public final SousCategoriesB add(final SousCategorieB sousCategorieB) {
        sousCategoriesB.add(sousCategorieB);
        return this;
    }

    @Override
    public String toString() {
        return "SousCategoriesB{" +
                "SousCategoriesB=" + sousCategoriesB +
                '}';
    }
}