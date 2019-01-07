package Finders;

import model.Paths;
import utils.EuclideanDistance;
import utils.PathLength;

import java.awt.Point;
import java.nio.file.Path;
import java.util.Date;
import java.util.List;

public class NearestNeighbor implements PathFinder {

    private Paths results = new Paths();
    private Paths optimalPath = new Paths();
    private Paths worsePath = new Paths();

    private double minDistance = Double.MAX_VALUE;
    private double maxDistance = 0;

    public Paths getResults() {
        return results;
    }

    public Paths getOptimalPath() {
        return optimalPath;
    }

    public Paths getWorsePath() {
        return worsePath;
    }

    public double getMinDistance() {
        return minDistance;
    }

    public double getMaxDistance() {
        return maxDistance;
    }

    @Override
    public Paths resolvePath(List<Point> points) {
        for(int i=0; i<points.size(); i++) {
            for (int j = 0; j < points.size(); j++) {
                if(i != j) {
                    results = new Paths();
                    results.addToOne(points.get(i));
                    results.addToTwo(points.get(j));

                    while (!PathFinder.isFull(results.getPointsOne(), points) || !PathFinder.isFull(results.getPointsTwo(), points)) {
                        double distance = Double.MAX_VALUE;
                        Point focusedPointOne = results.getLastFromOne();
                        Point focusedPointTwo = results.getLastFromTwo();
                        Point minPoint = null;
                        boolean oneMin = true;
                        for (Point seekPoint : points) {
                            double distOne = EuclideanDistance.distance(focusedPointOne, seekPoint);
                            double distTwo = EuclideanDistance.distance(focusedPointTwo, seekPoint);
                            if (PathFinder.isMinimum(distOne, distTwo, distance)) {
                                if (PathFinder.notContainPoint(results, seekPoint)) {
                                    if (!PathFinder.isFull(results.getPointsOne(), points) && !PathFinder.isFull(results.getPointsTwo(), points)) {
                                        if (distOne < distTwo) {
                                            minPoint = seekPoint;
                                            distance = distOne;
                                            oneMin = true;
                                        } else if (distOne >= distTwo) {
                                            minPoint = seekPoint;
                                            distance = distTwo;
                                            oneMin = false;
                                        }
                                    } else {
                                        if (PathFinder.isFull(results.getPointsOne(), points)) {
                                            minPoint = seekPoint;
                                            distance = distTwo;
                                            oneMin = false;
                                        } else if (PathFinder.isFull(results.getPointsTwo(), points)) {
                                            minPoint = seekPoint;
                                            distance = distOne;
                                            oneMin = true;
                                        }
                                    }
                                }
                            }
                        }
                        if (minPoint != null) {
                            if (oneMin) {
                                results.addLastToOne(minPoint);
                            } else {
                                results.addLastToTwo(minPoint);
                            }
                        }
                    }
                    results.addFirstPointToLastOne();
                    results.addFirstPointToLastTwo();
                    double pathDistance = PathLength.getTotalPathLength(results);
                    if(pathDistance < minDistance) {
                        minDistance = pathDistance;
                        optimalPath = new Paths(results);
                    } else if (pathDistance > maxDistance) {
                        maxDistance = pathDistance;
                        worsePath = new Paths(results);
                    }
                }
            }
        }
        return optimalPath;
    }

    @Override
    public void printStatistics() {
        PathFinder.stat(optimalPath, getMinDistance(), getMaxDistance());
    }
}
