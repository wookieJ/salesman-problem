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

    public static void switchArcs(List<Point> points, int p1, int p2) {
        if(p1 > p2) {
            int cpy = p1;
            p1 = p2;
            p2 = cpy;
        }
        List<Point> arc = null;
        try {
            arc = points.subList(p1, p2 + 1);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("HERE");
        }
        Collections.reverse(arc);
        int idx = 0;
        for(int i=p1 ; i<p2 ; i++) {
            points.set(i, arc.get(idx));
            idx++;
        }
    }
}
