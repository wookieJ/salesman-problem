package utils;

import java.awt.Point;
import java.util.List;

public class PathLength {
    public static double getTotalPathLength(List<Point> points) {
        double distance = 0;
        for(int i=0; i<points.size()-1; i++) {
            distance += EuclideanDistance.distance(points.get(i), points.get(i+1));
        }
        return distance;
    }
}
