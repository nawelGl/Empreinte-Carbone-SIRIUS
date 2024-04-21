package edu.ezip.ing1.pds.business.dto;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

public class PathPointChemin {

    private ArrayList<PointChemin> path = new ArrayList<>();

    public ArrayList<PointChemin> getPath() {
        return path;
    }

    public void setPoints(ArrayList<PointChemin> path) {
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