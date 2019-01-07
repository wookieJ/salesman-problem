package Finders;

import model.Paths;

import java.awt.Point;
import java.util.List;
import java.util.Random;

public interface PathFinder {
    Paths resolvePath(List<Point> points, int start1, int start2);

    static int randomIndex(List<Point> points) {
        Random random = new Random();
        return random.nextInt(points.size());
    }

    static boolean isMinimum(double p1, double p2, double min) {
        return p1 < min && p1 > 0 || p2 < min && p2 > 0;
    }

    static boolean isFull(List<Point> result, List<Point> data) {
        return result.size() >= (data.size() / 2);
    }

    static boolean notContainPoint(List<Point> firstList, List<Point> secondList, Point point) {
        return !firstList.stream().anyMatch(point1 -> point1.x == point.x && point1.y == point.y) &&
                !secondList.stream().anyMatch(point1 -> point1.x == point.x && point1.y == point.y);
    }
}
