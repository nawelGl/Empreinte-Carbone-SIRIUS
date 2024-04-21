package edu.ezip.ing1.pds.business.dto;

import java.util.LinkedHashSet;
import java.util.Set;

public class PathPointChemin {

    private Set<PointChemin> path = new LinkedHashSet<>();

    public Set<PointChemin> getPath() {
        return path;
    }

    public void setPoints(Set<PointChemin> path) {
        this.path = path;
    }

    public final PathPointChemin add(final PointChemin point) {
        path.add(point);
        return this;
    }

    @Override
    public String toString() {
        return "Path{" +
                "path=" + path +
                '}';
    }
}