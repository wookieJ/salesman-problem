import Finders.NearestNeighbor;
import utils.DataLoader;
import utils.PathLength;
import utils.PointsVisualizer;

import java.awt.Point;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        List<Point> list = DataLoader.loadPoints("kroa150.tsp");

        List<Point> optimalPath = new LinkedList<>();
        double minDistance = Double.MAX_VALUE;
        Instant t1 = Instant.now();
        for(int i=0 ; i<list.size() ; i++) {
            for(int j=0 ; j<list.size() ; j++) {
                NearestNeighbor nearestNeighbor = new NearestNeighbor();
                List<Point> nearestNeighborPath = nearestNeighbor.resolvePath(list, i, j);
                List<Point> firstPoints = nearestNeighborPath.stream().limit(list.size() / 2 + 1).collect(Collectors.toList());
                List<Point> secondPoints = nearestNeighborPath.stream().skip(list.size() / 2 + 1).collect(Collectors.toList());
                double firstLength = PathLength.getTotalPathLength(firstPoints);
                double secondLength = PathLength.getTotalPathLength(secondPoints);
                if ((firstLength + secondLength) < minDistance) {
                    minDistance = firstLength + secondLength;
                    optimalPath = nearestNeighborPath;
                }
            }
        }
        Instant t2 = Instant.now();

        List<Point> firstPoints = optimalPath.stream().limit(list.size()/2+1).collect(Collectors.toList());
        List<Point> secondPoints = optimalPath.stream().skip(list.size()/2+1).collect(Collectors.toList());

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
