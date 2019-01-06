package Finders;

import utils.EuclideanDistance;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;

public class NearestNeighbor implements PathFinder {
    @Override
    public List<Point> resolvePath(List<Point> points) {
        LinkedList<Point> result = new LinkedList<>();
        result.add(points.get(0));

        while(result.size() != points.size()) {
            double min = Double.MAX_VALUE;
            Point focusedPoint = result.getLast();
            Point minPoint = null;
            for(Point seekPoint: points) {
                double dist = EuclideanDistance.distance(focusedPoint, seekPoint);
                if(dist < min && dist > 0 && !result.contains(seekPoint)) {
                    min = dist;
                    minPoint = seekPoint;
                }
            }
            if(minPoint != null) {
                result.addLast(minPoint);
            }
        }
        result.addLast(points.get(0));
        return result;
    }
}
