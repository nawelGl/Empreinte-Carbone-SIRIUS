package edu.ezip.ing1.pds.business.dto;


import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;


public class BestSellers {


    private List<BestSeller> bestSellers = new ArrayList<>();

    public List<BestSeller> getBestSellers() {
        return bestSellers;
    }

    public void setBestSellers(List<BestSeller> bestSellers) {
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
