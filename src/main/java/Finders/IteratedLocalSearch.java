package Finders;

import model.Paths;
import utils.PathUtils;

import java.awt.Point;
import java.util.List;
import java.util.LinkedList;
import java.util.Random;


public class IteratedLocalSearch implements PathFinder {
    private static final String NAME = "Iterated Local Search";

    private Paths basePath;
    PathFinder pathFinder;
    private double minDistance;
    private double maxDistance;

    public IteratedLocalSearch(PathFinder pathFinder) {
        this.pathFinder = pathFinder;
        this.basePath = pathFinder.resolvePath();
    }

    @Override
    public Paths resolvePath() {
        basePath.setPointsOne(new LinkedList<>(resolveOnePathIterate()));
        return basePath;
    }

    private List<Point> resolveOnePathIterate(List<Point> points ) {
        for (int i = 0; i < 10; i++) {
            double bestCost, currentCost;
            //Initial cost is the best
            bestCost = pathFinder.getMinDistance();
            int noImproveNumber = 0;
            List<Point> newTour;
            while(noImproveNumber < 10) {
                currentCost = bestCost;
                newTour = perturbate(points);
                pathFinder.
                //Local search
                //Cost
                //Compare
            }

        }
        return points;
    }

    private List<Point> perturbate(List<Point> points) {
        Random random = new Random();
        List<Point> newTour;
        List<Point> newPoints = new LinkedList<>(points);
        for (int currPerturbation = 0; currPerturbation < 4; currPerturbation++) {
            int randPointIndex = random.nextInt(points.size() -1);
            int randPointIndexNext = (randPointIndex + 1) % 51;
            newTour = PathUtils.opt2(newPoints, randPointIndex, randPointIndexNext);
            newPoints = new LinkedList<>(newTour);
        }
        return newPoints;
    }

    @Override
    public void printStatistics() {
        PathFinder.stat(basePath, getMinDistance(), getMaxDistance());
        System.out.println(String.format("Upgrade ratio = %.2f", (pathFinder.getMinDistance() / getMinDistance())));
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public double getMinDistance() {
        return minDistance;
    }

    public void setMinDistance(double minDistance) {
        this.minDistance = minDistance;
    }

    public double getMaxDistance() {
        return maxDistance;
    }

    public void setMaxDistance(double maxDistance) {
        this.maxDistance = maxDistance;
    }
}
