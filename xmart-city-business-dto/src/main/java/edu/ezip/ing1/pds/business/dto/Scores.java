package edu.ezip.ing1.pds.business.dto;

import java.util.LinkedHashSet;
import java.util.Set;

public class Scores {

    private Set<Score> Scores = new LinkedHashSet<>();

    public Set<Score> getScores() {
        return Scores;
    }

    public void setScores(Set<Score> Scores) {
        this.Scores = Scores;
    }

    public final Scores add(final Score Score) {
        Scores.add(Score);
        return this;
    }

    @Override
    public String toString() {
        return "Scores{" +
                "Scores=" + Scores +
                '}';
    }
}