package Finders;

import model.Paths;

import java.awt.Point;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public interface PathFinder {

    List<Point> data = new LinkedList<>();

    Paths resolvePath();

    void printStatistics();

    String getName();

    static int randomIndex(List<Point> points) {
        Random random = new Random();
        return random.nextInt(points.size());
    }

    static boolean isMinimum(double p1, double p2, double min) {
        return p1 < min && p1 > 0 || p2 < min && p2 > 0;
    }

    static boolean isMaximum(double p1, double p2, double max) {
        return p1 > max && p1 > 0 || p2 > max && p2 > 0;
    }

    static boolean isFull(List<Point> result, List<Point> data) {
        return result.size() >= (data.size() / 2);
    }

    static boolean notContainPoint(Paths paths, Point point) {
        return !paths.getPointsOne().stream().anyMatch(point1 -> point1.x == point.x && point1.y == point.y) &&
                !paths.getPointsTwo().stream().anyMatch(point1 -> point1.x == point.x && point1.y == point.y);
    }

    static void stat(Paths optimalPaths, double minDistance, double maxDistance) {
        System.out.println("=========================================================");
        System.out.println(new Date());
        System.out.println(optimalPaths.getPointsOne().size() + " red points + " +
                optimalPaths.getPointsTwo().size() + " blue points (Last point is also first)");
        System.out.println("Optimal path length: " + minDistance);
        System.out.println("Worse path length: " + maxDistance);
    }

}
