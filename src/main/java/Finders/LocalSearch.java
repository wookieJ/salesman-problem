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
        basePath.setPointsOne((LinkedList<Point>) resolveOnePathPoints(basePath.getPointsOne()));
        basePath.setPointsTwo((LinkedList<Point>) resolveOnePathPoints(basePath.getPointsTwo()));

        basePath.setPointsOne((LinkedList<Point>) resolveOnePathArcs(basePath.getPointsOne()));
        basePath.setPointsTwo((LinkedList<Point>) resolveOnePathArcs(basePath.getPointsTwo()));

        setMinDistance(PathLength.getTotalPathLength(basePath));
//        PathUtils.switchPoints(basePath.getPointsOne(), 32, 34);
        return basePath;
    }

    private List<Point> resolveOnePathPoints(List<Point> points) {
        boolean isImproved = false;
        double bestDistance;
        do {
            startAlgorithm:
            {
                List<Point> swappedList = new LinkedList<>(points);
                bestDistance = PathLength.getPathLength(points);
                for (int i = 1; i < swappedList.size(); i++) {
                    for (int j = i + 1; j < swappedList.size() - 1; j++) {
                        PathUtils.switchPoints(swappedList, i, j);
                        double newDistance = PathLength.getPathLength(swappedList);
                        isImproved = false;
                        if (newDistance < bestDistance) {
                            isImproved = true;
                            points = swappedList;
                            break startAlgorithm;
                        } else if (newDistance > getMaxDistance()) {
                            setMaxDistance(newDistance);
                        }
                    }
                }
            }
        } while (isImproved);

        return points;
    }

    private List<Point> resolveOnePathArcs(List<Point> points) {
        boolean isImproved = false;
        double bestDistance;
        do {
            startAlgorithm:
            {
                List<Point> swappedListArc = new LinkedList<>(points);
                bestDistance = PathLength.getPathLength(points);
                for (int i = 1; i < swappedListArc.size(); i++) {
                    for (int j = i + 1; j < swappedListArc.size() - 1; j++) {
                        swappedListArc = PathUtils.switchArcs(swappedListArc, i, j);
                        double newDistanceArc = PathLength.getPathLength(swappedListArc);
                        isImproved = false;
                        if (newDistanceArc < bestDistance) {
                            isImproved = true;
                            points = swappedListArc;
                            break startAlgorithm;
                        } else if (newDistanceArc > getMaxDistance()) {
                            setMaxDistance(newDistanceArc);
                        }
                    }
                }
            }
        } while (isImproved);

        return points;
    }

    public void printStatistics() {
        PathFinder.stat(basePath, getMinDistance(), getMaxDistance());
        System.out.println(String.format("Upgrade ratio = %.2f", (pathFinder.getMinDistance() / getMinDistance())));
    }
}