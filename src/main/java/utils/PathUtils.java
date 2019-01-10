package utils;

import java.awt.*;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class PathUtils {

    public static List<Point> switchPoints(List<Point> points, int p1, int p2) {
        List<Point> newPoints = new LinkedList<>(points);
        Point pointOne = newPoints.get(p1);
        newPoints.set(p1, points.get(p2));
        newPoints.set(p2, pointOne);

        return newPoints;
    }

    public static List<Point> opt2(List<Point> points, int p1, int p2) {
        List<Point> newPoints = new LinkedList<>(points.subList(0, p1));

        List<Point> arc = new LinkedList<>(points.subList(p1, p2 + 1));
        Collections.reverse(arc);
        newPoints.addAll(arc);

        arc = new LinkedList<>(points.subList(p2 + 1, points.size()));
        newPoints.addAll(arc);

        return newPoints;
    }
}
