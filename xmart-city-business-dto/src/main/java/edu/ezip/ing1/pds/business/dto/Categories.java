package edu.ezip.ing1.pds.business.dto;

import java.util.LinkedHashSet;
import java.util.Set;

public class Categories {

    private Set<Categorie> categories = new LinkedHashSet<>();

    public Set<Categorie> getCategories() {
        return categories;
    }

    public void setCategories(Set<Categorie> categories) {
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