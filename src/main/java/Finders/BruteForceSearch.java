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
    private static final long EPOCH_NUMBER = 1_000_000;

    private static Random random = new Random();

    private static Paths optimalPaths = new Paths();
    private static Paths worsePaths = new Paths();

    private static double minDistance = Double.MAX_VALUE;
    private static double maxDistance = 0.0;
    private static double distance;
    private static boolean whichSet;

    public static double getMinDistance() {
        return minDistance;
    }

    public static double getMaxDistance() {
        return maxDistance;
    }

    public static Paths getOptimalPaths() {
        return optimalPaths;
    }

    public static Paths getWorsePaths() {
        return worsePaths;
    }

    @Override
    public Paths resolvePath(List<Point> points) {
        minDistance = Double.MAX_VALUE;
        for(int i=0; i<EPOCH_NUMBER; i++) {
            Paths results = new Paths();

            int startIndOne = random.nextInt(points.size());
            int startIndTwo = random.nextInt(points.size());
            while(startIndOne == startIndTwo) {
                startIndTwo = random.nextInt(points.size());
            }

            Point startPointOne = points.get(startIndOne);
            Point startPointTwo = points.get(startIndTwo);

            results.addToOne(startPointOne);
            results.addToTwo(startPointTwo);

            List<Integer> remainingIndexes = IntStream.rangeClosed(0, points.size() - 1).boxed().collect(Collectors.toList());
            remainingIndexes.remove(remainingIndexes.indexOf(startIndOne));
            remainingIndexes.remove(remainingIndexes.indexOf(startIndTwo));

            while (!PathFinder.isFull(results.getPointsOne(), points) || !PathFinder.isFull(results.getPointsTwo(), points)) {
                int randomIndex = random.nextInt(remainingIndexes.size());
                Point randomPoint = points.get(remainingIndexes.get(randomIndex));
                if (PathFinder.notContainPoint(results, randomPoint)) {
                    whichSet = random.nextBoolean();
                    if (whichSet && !PathFinder.isFull(results.getPointsOne(), points)) {
                        results.addLastToOne(randomPoint);
                        remainingIndexes.remove(randomIndex);
                    } else if(!whichSet && !PathFinder.isFull(results.getPointsTwo(), points)){
                        results.addLastToTwo(randomPoint);
                        remainingIndexes.remove(randomIndex);
                    }
                }
            }
            results.addFirstPointToLastOne();
            results.addFirstPointToLastTwo();
            distance = PathLength.getTotalPathLength(results);
            if(distance < minDistance) {
                minDistance = distance;
                optimalPaths.setPointsOne(new LinkedList<>(results.getPointsOne()));
                optimalPaths.setPointsTwo(new LinkedList<>(results.getPointsTwo()));
            } else if (distance > maxDistance) {
                maxDistance = distance;
                worsePaths.setPointsOne(new LinkedList<>(results.getPointsOne()));
                worsePaths.setPointsTwo(new LinkedList<>(results.getPointsTwo()));
            }
        }
        return optimalPaths;
    }

    @Override
    public void printStatistics() {
        PathFinder.stat(optimalPaths, getMinDistance(), getMaxDistance());
    }
}
