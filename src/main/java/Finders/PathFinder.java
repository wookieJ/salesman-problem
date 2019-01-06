package Finders;

import java.awt.Point;
import java.util.List;

public interface PathFinder {
    List<Point> resolvePath(List<Point> points, int start1, int start2);
}
