package Finders;

import utils.EuclideanDistance;

import java.awt.Point;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class NearestNeighbor implements PathFinder {
    @Override
    public List<Point> resolvePath(List<Point> points, int start1, int start2) {
        LinkedList<Point> resultOne = new LinkedList<>();
        LinkedList<Point> resultTwo = new LinkedList<>();

        resultOne.add(points.get(start1));
        resultTwo.add(points.get(start2));

        while(!isFull(resultOne, points) || !isFull(resultTwo, points)) {
            double min = Double.MAX_VALUE;
            Point focusedPointOne = resultOne.getLast();
            Point focusedPointTwo = resultTwo.getLast();
            Point minPoint = null;
            boolean oneMin = true;
            for(Point seekPoint: points) {
                double distOne = EuclideanDistance.distance(focusedPointOne, seekPoint);
                double distTwo = EuclideanDistance.distance(focusedPointTwo, seekPoint);
                if (isMinimum(distOne, distTwo, min)) {
                    if(containPoint(resultOne, resultTwo, seekPoint)) {
                        if (!isFull(resultOne, points) && !isFull(resultTwo, points)) {
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
                            if (isFull(resultOne, points)) {
                                minPoint = seekPoint;
                                min = distTwo;
                                oneMin = false;
                            } else if (isFull(resultTwo, points)) {
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
        return resultOne;
    }

    private int randomIndex(List<Point> points) {
        Random random = new Random();
        return random.nextInt(points.size());
    }

    private boolean isMinimum(double p1, double p2, double min) {
        return p1 < min && p1 > 0 || p2 < min && p2 > 0;
    }

    private boolean isFull(List<Point> result, List<Point> data) {
        return result.size() >= (data.size() / 2);
    }

    private boolean containPoint(List<Point> firstList, List<Point> secondList, Point point) {
        return !firstList.contains(point) && !secondList.contains(point);
    }
}
