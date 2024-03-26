package edu.ezip.ing1.pds.business.dto;

import java.util.ArrayList;
import java.awt.*;

//path class to register a path for each product place.
public class Path {
    //unknow number of points for each path
    ArrayList<Point> points = new ArrayList<Point>();

    //set a point :
    public void setPoint(Point point){
        points.add(point);
    }
    
}
