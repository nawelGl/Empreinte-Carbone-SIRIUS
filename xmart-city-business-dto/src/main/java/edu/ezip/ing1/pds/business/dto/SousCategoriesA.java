package edu.ezip.ing1.pds.business.dto;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

public class SousCategoriesA {

    private ArrayList<SousCategorieA> sousCategoriesA = new ArrayList<>();

    public ArrayList<SousCategorieA> getSousCategoriesA() {
        return sousCategoriesA;
    }

    public void setSousCategoriesA(ArrayList<SousCategorieA> sousCategoriesA) {
        this.sousCategoriesA =sousCategoriesA;
    }

    public final SousCategoriesA add(final SousCategorieA sousCategorieA) {
        sousCategoriesA.add(sousCategorieA);
        return this;
    }


    @Override
    public String toString() {
        return "SousCategoriesA{" +
                "SousCategoriesA=" + sousCategoriesA +
                '}';
    }
}