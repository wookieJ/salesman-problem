package Finders;

import model.Paths;
import utils.EuclideanDistance;
import utils.PathLength;

import java.awt.Point;
import java.util.List;

public class NearestNeighbor implements PathFinder {
    private static final String NAME = "Nearest Neighbor";

    private List<Point> data;

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

    public double getMaxDistance() {
        return maxDistance;
    }

    public void setMinDistance(double minDistance) {
        this.minDistance = minDistance;
    }

    public void setMaxDistance(double maxDistance) {
        this.maxDistance = maxDistance;
    }

    public NearestNeighbor(List<Point> data) {
        this.data = data;
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public double getMinDistance() {
        return minDistance;
    }

    @Override
    public Paths resolvePath() {
        for(int i=0; i<data.size(); i++) {
            for (int j = 0; j < data.size(); j++) {
                if(i != j) {
                    results = new Paths();
                    results.addToOne(data.get(i));
                    results.addToTwo(data.get(j));

                    while (!PathFinder.isFull(results.getPointsOne(), data) || !PathFinder.isFull(results.getPointsTwo(), data)) {
                        double distance = Double.MAX_VALUE;
                        Point focusedPointOne = results.getLastFromOne();
                        Point focusedPointTwo = results.getLastFromTwo();
                        Point minPoint = null;
                        boolean oneMin = true;
                        for (Point seekPoint : data) {
                            double distOne = EuclideanDistance.distance(focusedPointOne, seekPoint);
                            double distTwo = EuclideanDistance.distance(focusedPointTwo, seekPoint);
                            if (PathFinder.isMinimum(distOne, distTwo, distance)) {
                                if (PathFinder.notContainPoint(results, seekPoint)) {
                                    if (!PathFinder.isFull(results.getPointsOne(), data) && !PathFinder.isFull(results.getPointsTwo(), data)) {
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
                                        if (PathFinder.isFull(results.getPointsOne(), data)) {
                                            minPoint = seekPoint;
                                            distance = distTwo;
                                            oneMin = false;
                                        } else if (PathFinder.isFull(results.getPointsTwo(), data)) {
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
                        setMaxDistance(pathDistance);
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
