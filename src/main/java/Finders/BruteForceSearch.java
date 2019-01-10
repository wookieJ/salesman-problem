package Finders;

import model.Paths;
import utils.PathLength;

import java.awt.Point;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class BruteForceSearch implements PathFinder {
    public String NAME;
    private long EPOCH_NUMBER;

    private List<Point> data;

    private static Random random = new Random();

    private Paths optimalPaths = new Paths();
    private Paths worsePaths = new Paths();

    private static double minDistance = Double.MAX_VALUE;
    private static double maxDistance = 0.0;
    private double distance;
    private boolean whichSet;

    public double getMaxDistance() {
        return maxDistance;
    }

    public static void setMinDistance(double minDistance) {
        BruteForceSearch.minDistance = minDistance;
    }

    public static void setMaxDistance(double maxDistance) {
        BruteForceSearch.maxDistance = maxDistance;
    }

    public Paths getOptimalPaths() {
        return optimalPaths;
    }

    public Paths getWorsePaths() {
        return worsePaths;
    }

    public BruteForceSearch(List<Point> data, String title, long epochNumber) {
        this.data = data;
        this.EPOCH_NUMBER = epochNumber;
        this.NAME = title;
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
        minDistance = Double.MAX_VALUE;
        for(int i=0; i<this.EPOCH_NUMBER; i++) {
            Paths results = new Paths();

            int startIndOne = random.nextInt(data.size());
            int startIndTwo = random.nextInt(data.size());
            while(startIndOne == startIndTwo) {
                startIndTwo = random.nextInt(data.size());
            }

            Point startPointOne = data.get(startIndOne);
            Point startPointTwo = data.get(startIndTwo);

            results.addToOne(startPointOne);
            results.addToTwo(startPointTwo);

            List<Integer> remainingIndexes = IntStream.rangeClosed(0, data.size() - 1).boxed().collect(Collectors.toList());
            remainingIndexes.remove(remainingIndexes.indexOf(startIndOne));
            remainingIndexes.remove(remainingIndexes.indexOf(startIndTwo));

            while (!PathFinder.isFull(results.getPointsOne(), data) || !PathFinder.isFull(results.getPointsTwo(), data)) {
                int randomIndex = random.nextInt(remainingIndexes.size());
                Point randomPoint = data.get(remainingIndexes.get(randomIndex));
                if (PathFinder.notContainPoint(results, randomPoint)) {
                    whichSet = random.nextBoolean();
                    if (whichSet && !PathFinder.isFull(results.getPointsOne(), data)) {
                        results.addLastToOne(randomPoint);
                        remainingIndexes.remove(randomIndex);
                    } else if(!whichSet && !PathFinder.isFull(results.getPointsTwo(), data)){
                        results.addLastToTwo(randomPoint);
                        remainingIndexes.remove(randomIndex);
                    }
                }
            }
            results.addFirstPointToLastOne();
            results.addFirstPointToLastTwo();
            distance = PathLength.getTotalPathLength(results);
            if(distance < getMinDistance()) {
                setMinDistance(distance);
                optimalPaths.setPointsOne(new LinkedList<>(results.getPointsOne()));
                optimalPaths.setPointsTwo(new LinkedList<>(results.getPointsTwo()));
            } else if (distance > getMaxDistance()) {
                setMaxDistance(distance);
                worsePaths.setPointsOne(new LinkedList<>(results.getPointsOne()));
                worsePaths.setPointsTwo(new LinkedList<>(results.getPointsTwo()));
            }
        }
        return optimalPaths;
    }

    @Override
    public void printStatistics() {
        PathFinder.stat(optimalPaths, getMinDistance(), getMaxDistance());
        System.out.println("Tries number: " + EPOCH_NUMBER);
    }
}
