package utils;

import java.awt.*;
import java.util.Collections;
import java.util.List;

public class PathUtils {

    public static void switchPoints(List<Point> points, int p1, int p2) {
        Point pointOne = points.get(p1);
        points.set(p1, points.get(p2));
        points.set(p2, pointOne);
    }

    public static List<Point> reverse(List<Point> points, int p1, int p2) {
        if(p1 > p2) {
            int tmp = p2;
            p1 = p2;
            p2 = tmp;
        }
        List<Point> arc = points.subList(p1+1, p2);
        Collections.reverse(arc);
        int idx = 0;
        for(int i=p1+1 ; i<p2 ; i++) {
            points.set(i, arc.get(idx));
            idx++;
        }
        return points;
    }

    public static List<Point> switchArcs(List<Point> points, int p1, int p2) {
        if(p1 + 1 < p2) {
            switchPoints(points, p1 + 1, p2);
            reverse(points, p1+1, p2);
        }
        return points;
    }
}
