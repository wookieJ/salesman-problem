package Finders;

import model.Paths;
import utils.EuclideanDistance;
import utils.PathLength;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NearestNeighbor implements PathFinder {
    private static String NAME;

    private List<Point> data;
    private List<Double> allDistance;

    private Paths results = new Paths();
    private Paths optimalPath = new Paths();
    private Paths worsePath = new Paths();

    private double minDistance = Double.MAX_VALUE;
    private double maxDistance = 0;
    private double avgDistance = 0.0;

    public double getAvgDistance() {
        return avgDistance;
    }

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

    public NearestNeighbor(List<Point> data, String title) {
        this.data = data;
        NAME = title;
        allDistance = new ArrayList<>();
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
        Random random = new Random();
        for(int i = 0; i < 10; i++) {
            results = new Paths();
            results.addToOne(data.get(random.nextInt(data.size())));
            results.addToTwo(data.get(random.nextInt(data.size())));

            while (results.getFirstFromTwo() == results.getFirstFromOne()) {
                results.getPointsTwo().set(0, data.get(random.nextInt(data.size())));
            }

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
            allDistance.add(pathDistance);
            if (pathDistance < minDistance) {
                minDistance = pathDistance;
                optimalPath = new Paths(results);
            } else if (pathDistance > maxDistance) {
                setMaxDistance(pathDistance);
                worsePath = new Paths(results);
            }
        }
        avgDistance = setAvgDistance();
        return optimalPath;
    }

    @Override
    public void printStatistics() {
        PathFinder.stat(optimalPath, getMinDistance(), getMaxDistance(), getAvgDistance());
    }

    private double setAvgDistance() {
        double sum = 0;
        if(!allDistance.isEmpty()) {
            for(Double distance : allDistance) {
                sum+= distance;
            }
            return sum / allDistance.size();
        }
        return sum;
    }
}
