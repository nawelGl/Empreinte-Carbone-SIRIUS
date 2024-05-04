package edu.ezip.ing1.pds.business.dto;


import java.util.LinkedHashSet;
import java.util.Set;


public class BestSellers {


    private Set<BestSeller> bestSellers = new LinkedHashSet<>();

    public Set<BestSeller> getBestSellers() {
        return bestSellers;
    }

    public void setBestSellers(Set<BestSeller> bestSellers) {
        this.bestSellers = bestSellers;
    }

    public final BestSellers add(final BestSeller bestSeller) {
        bestSellers.add(bestSeller);
        return this;
    }

    @Override
    public String toString() {
        return "BestSellers{" +
                "bestSellers=" + bestSellers +
                '}';
    }
}
