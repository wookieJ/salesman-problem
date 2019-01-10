package Finders;

import model.Paths;
import utils.PathLength;
import utils.PathUtils;

import java.awt.Point;
import java.util.LinkedList;
import java.util.List;

public class LocalSearch implements PathFinder {
    private static final String NAME = "Local Search";

    private Paths basePath;
    PathFinder pathFinder;
    private double minDistance;
    private double maxDistance;

    public void setMinDistance(double minDistance) {
        this.minDistance = minDistance;
    }

    public double getMaxDistance() {
        return maxDistance;
    }

    public void setMaxDistance(double maxDistance) {
        this.maxDistance = maxDistance;
    }

    public PathFinder getPathFinder() {
        return pathFinder;
    }

    public LocalSearch(PathFinder pathFinder) {
        this.pathFinder = pathFinder;
        this.basePath = pathFinder.resolvePath();
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
        basePath.setPointsOne(new LinkedList<>(resolveOnePathPoints2(basePath.getPointsOne())));
        basePath.setPointsTwo(new LinkedList<>(resolveOnePathPoints2(basePath.getPointsTwo())));

        setMinDistance(PathLength.getTotalPathLength(basePath));
        return basePath;
    }

    private List<Point> resolveOnePathPoints2(List<Point> points) {
        int size = points.size();
        List<Point> newTour;
        int noImproveNumber = 0;

        while (noImproveNumber < 10) {
            double bestDistance = PathLength.getPathLength(points);
            for (int i=1; i<size - 1; i++) {
                for (int k=i + 1; k<size - 1; k++)  {
                    newTour = PathUtils.opt2(points, i, k);
                    double newDistance = PathLength.getPathLength(newTour);
                    if (newDistance < bestDistance) {
                        bestDistance = newDistance;
                        noImproveNumber = 0;
                        points = new LinkedList<>(newTour);
                    } else {
                        setMaxDistance(newDistance);
                    }
                }
            }
            noImproveNumber++;
        }
        setMinDistance(PathLength.getPathLength(points));
        return points;
    }

    public void printStatistics() {
        PathFinder.stat(basePath, getMinDistance(), getMaxDistance());
        System.out.println(String.format("Upgrade ratio = %.2f", (pathFinder.getMinDistance() / getMinDistance())));
    }
}