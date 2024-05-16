package edu.ezip.ing1.pds.business.dto;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

public class Categories {

    private ArrayList<Categorie> categories = new ArrayList<>();

    public ArrayList<Categorie> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<Categorie> categories) {
        this.categories =categories;
    }

    public final Categories add(final Categorie categorie) {
       categories.add(categorie);
        return this;
    }

    @Override
    public String toString() {
        return "Categories{" +
                "categories=" + categories +
                '}';
    }
}