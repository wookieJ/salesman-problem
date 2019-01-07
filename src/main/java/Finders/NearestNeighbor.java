package Finders;

import model.Paths;
import utils.EuclideanDistance;

import java.awt.Point;
import java.util.LinkedList;
import java.util.List;

public class NearestNeighbor implements PathFinder {
    @Override
    public Paths resolvePath(List<Point> points, int start1, int start2) {
        LinkedList<Point> resultOne = new LinkedList<>();
        LinkedList<Point> resultTwo = new LinkedList<>();

        resultOne.add(points.get(start1));
        resultTwo.add(points.get(start2));

        while(!PathFinder.isFull(resultOne, points) || !PathFinder.isFull(resultTwo, points)) {
            double min = Double.MAX_VALUE;
            Point focusedPointOne = resultOne.getLast();
            Point focusedPointTwo = resultTwo.getLast();
            Point minPoint = null;
            boolean oneMin = true;
            for(Point seekPoint: points) {
                double distOne = EuclideanDistance.distance(focusedPointOne, seekPoint);
                double distTwo = EuclideanDistance.distance(focusedPointTwo, seekPoint);
                if (PathFinder.isMinimum(distOne, distTwo, min)) {
                    if(PathFinder.notContainPoint(resultOne, resultTwo, seekPoint)) {
                        if (!PathFinder.isFull(resultOne, points) && !PathFinder.isFull(resultTwo, points)) {
                            if (distOne < distTwo) {
                                minPoint = seekPoint;
                                min = distOne;
                                oneMin = true;
                            } else if (distOne >= distTwo) {
                                minPoint = seekPoint;
                                min = distTwo;
                                oneMin = false;
                            }
                        } else {
                            if (PathFinder.isFull(resultOne, points)) {
                                minPoint = seekPoint;
                                min = distTwo;
                                oneMin = false;
                            } else if (PathFinder.isFull(resultTwo, points)) {
                                minPoint = seekPoint;
                                min = distOne;
                                oneMin = true;
                            }
                        }
                    }
                }

            }
            if(minPoint != null) {
                if(oneMin) {
                    resultOne.addLast(minPoint);
                } else {
                    resultTwo.addLast(minPoint);
                }
            }
        }
        resultOne.addLast(resultOne.get(0));
        resultTwo.addLast(resultTwo.get(0));
        resultOne.addAll(resultTwo);
//        return resultOne;
        return null;
    }
}
