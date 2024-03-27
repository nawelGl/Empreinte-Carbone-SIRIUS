package edu.ezip.ing1.pds.business.dto;

import java.util.ArrayList;
import java.awt.*;
import java.lang.reflect.Array;

//path class to register a path for each product place.
public class Path {
    //unknow number of points for each path
    ArrayList<Point> points = new ArrayList<Point>();

    //set a point :
    public void setPoint(Point point){
        points.add(point);
    }
    
    public ArrayList<Point> getPoint(){
        return points;
    }
}
