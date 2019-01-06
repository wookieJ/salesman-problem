import Finders.NearestNeighbor;
import utils.DataLoader;
import utils.PathLength;
import utils.PointsVisualizer;

import java.awt.Point;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        List<Point> list = DataLoader.loadPoints("kroa100.tsp");

        Instant t1 = Instant.now();
        NearestNeighbor nearestNeighbor = new NearestNeighbor();
        List<Point> optimizePath = nearestNeighbor.resolvePath(list);
        Instant t2 = Instant.now();

        List<Point> firstPoints = optimizePath.stream().limit(list.size()/2+1).collect(Collectors.toList());
        List<Point> secondPoints = optimizePath.stream().skip(list.size()/2+1).collect(Collectors.toList());

        PointsVisualizer points = new PointsVisualizer(firstPoints, secondPoints);
        points.draw();

        double firstLength = PathLength.getTotalPathLength(firstPoints);
        double secondLength = PathLength.getTotalPathLength(secondPoints);
        System.out.println("===================================================");
        System.out.println(new Date());
        System.out.println(firstPoints.size() + " red points + " + secondPoints.size() + " blue points (Last is also first)");
        System.out.println(String.format("Red path length: %.2f", firstLength));
        System.out.println(String.format("Blue path length: %.2f", secondLength));
        System.out.println(String.format("Total path length: %.2f", (secondLength + firstLength)));
        System.out.println("Execution time: " + Duration.between(t1, t2));
    }
}
