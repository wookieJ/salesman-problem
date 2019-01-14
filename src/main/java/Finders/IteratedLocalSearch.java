package Finders;

import model.Paths;
import org.slf4j.spi.LocationAwareLogger;
import utils.PathLength;
import utils.PathUtils;

import java.awt.Point;
import java.nio.file.Path;
import java.util.List;
import java.util.LinkedList;
import java.util.Random;


public class IteratedLocalSearch implements PathFinder {
    private static final String NAME = "Iterated Local Search";

    private Paths basePath;
    PathFinder pathFinder;
    private LocalSearch localSearch;
    private double minDistance;
    private double maxDistance;

    public IteratedLocalSearch(LocalSearch localSearch) {
        this.localSearch = localSearch;
        this.basePath = localSearch.getBasePath();
    }

    @Override
    public Paths resolvePath() {
        resolveOnePathIterate(basePath.getPointsOne());
        resolveSecondPathIterate(basePath.getPointsTwo());
        return basePath;
    }

    private List<Point> resolveOnePathIterate(List<Point> points) {

       double bestCost, currentCost;
       bestCost = localSearch.getMinDistance();
       int noImproveNumber = 0;
       LinkedList<Point> newTourOne;

       while(noImproveNumber < 3000) {
           Paths newPath = new Paths(basePath);
           newTourOne =(LinkedList<Point>) perturbate(this.basePath.getPointsOne());
           newPath.setPointsOne(newTourOne);
           localSearch.setBasePath(newPath);
           newPath = localSearch.resolvePath();
           currentCost = PathLength.getTotalPathLength(newPath);
           if(currentCost < bestCost) {
               bestCost = currentCost;
               setMinDistance(bestCost);
               noImproveNumber = 0;
               points = new LinkedList<>(newPath.getPointsOne());
               this.basePath.setPointsOne((LinkedList<Point>)  points);
           } else if(getMaxDistance() < currentCost){
               setMaxDistance(currentCost);
           }
           noImproveNumber++;
       }
       return points;
    }

    private List<Point> resolveSecondPathIterate(List<Point> points) {

        double bestCost, currentCost;
        bestCost = localSearch.getMinDistance();
        int noImproveNumber = 0;
        LinkedList<Point> newTourOne;

        while(noImproveNumber < 3000) {
            Paths newPath = new Paths(basePath);
            newTourOne =(LinkedList<Point>) perturbate(this.basePath.getPointsTwo());
            newPath.setPointsTwo(newTourOne);
            localSearch.setBasePath(newPath);
            newPath = localSearch.resolvePath();
            currentCost = PathLength.getTotalPathLength(newPath);
            if(currentCost < bestCost) {
                bestCost = currentCost;
                setMinDistance(bestCost);
                noImproveNumber = 0;
                points = new LinkedList<>(newPath.getPointsTwo());
                this.basePath.setPointsTwo((LinkedList<Point>)  points);
            } else if(getMaxDistance() < currentCost){
                setMaxDistance(currentCost);
            }
            noImproveNumber++;
        }
        return points;
    }

    private List<Point> perturbate(List<Point> points) {
        Random random = new Random();
        List<Point> newTour;
        List<Point> newPoints = new LinkedList<>(points);
        for (int currPerturbation = 0; currPerturbation < 4; currPerturbation++) {
            int randPointIndex = random.nextInt(points.size() -1);
            int randPointIndexNext = random.nextInt(points.size() -1);
            while(randPointIndex == 0 || randPointIndex == points.size()) {
                randPointIndex = random.nextInt(points.size() -1);
            }
            while(randPointIndexNext == 0 || randPointIndexNext == points.size()) {
                randPointIndexNext = random.nextInt(points.size() -1);
            }
            newTour = PathUtils.switchPoints(newPoints, randPointIndex, randPointIndexNext);
            newPoints = new LinkedList<>(newTour);
        }
        return newPoints;
    }

    @Override
    public void printStatistics() {
        PathFinder.stat(basePath, getMinDistance(), getMaxDistance());
        System.out.println(String.format("Upgrade ratio = %.2f", (localSearch.getMinDistance() / getMinDistance())));
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
