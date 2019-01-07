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
    private static final long EPOCH_NUMBER = 1_000;

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
    public Paths resolvePath(List<Point> points, int start1, int start2) {
        minDistance = Double.MAX_VALUE;
        for(int i=0; i<EPOCH_NUMBER; i++) {
            Paths result = new Paths();

            Point startPointOne = points.get(start1);
            Point startPointTwo = points.get(start2);

            result.addToOne(startPointOne);
            result.addToTwo(startPointTwo);

            List<Integer> remainingIndexes = IntStream.rangeClosed(0, points.size() - 1).boxed().collect(Collectors.toList());
            remainingIndexes.remove(remainingIndexes.indexOf(start1));
            remainingIndexes.remove(remainingIndexes.indexOf(start2));

            while (!PathFinder.isFull(result.getPointsOne(), points) || !PathFinder.isFull(result.getPointsTwo(), points)) {
                int randomIndex = random.nextInt(remainingIndexes.size());
                Point randomPoint = points.get(remainingIndexes.get(randomIndex));
                if (PathFinder.notContainPoint(result.getPointsOne(), result.getPointsTwo(), randomPoint)) {
                    whichSet = random.nextBoolean();
                    if (whichSet && !PathFinder.isFull(result.getPointsOne(), points)) {
                        result.addLastToOne(randomPoint);
                        remainingIndexes.remove(randomIndex);
                    } else if(!whichSet && !PathFinder.isFull(result.getPointsTwo(), points)){
                        result.addLastToTwo(randomPoint);
                        remainingIndexes.remove(randomIndex);
                    }
                }
            }
            result.addLastToOne(result.getFirstFromOne());
            result.addLastToTwo(result.getFirstFromTwo());
            distance = PathLength.getTotalPathLength(result.getPointsOne()) +
                    PathLength.getTotalPathLength(result.getPointsTwo());
            if(distance < minDistance) {
                minDistance = distance;
                optimalPaths.setPointsOne(new LinkedList<>(result.getPointsOne()));
                optimalPaths.setPointsTwo(new LinkedList<>(result.getPointsTwo()));
            } else if (distance > maxDistance) {
                maxDistance = distance;
                worsePaths.setPointsOne(new LinkedList<>(result.getPointsOne()));
                worsePaths.setPointsTwo(new LinkedList<>(result.getPointsTwo()));
            }
        }
        return optimalPaths;
    }
}
