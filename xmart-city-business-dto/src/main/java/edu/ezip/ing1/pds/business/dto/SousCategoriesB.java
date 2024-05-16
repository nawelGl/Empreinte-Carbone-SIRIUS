package edu.ezip.ing1.pds.business.dto;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

public class SousCategoriesB {

    private ArrayList<SousCategorieB> sousCategoriesB = new ArrayList<>();

    public ArrayList<SousCategorieB> getSousCategoriesB() {
        return sousCategoriesB;
    }

    public void setSousCategoriesB(ArrayList<SousCategorieB> sousCategoriesB) {
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