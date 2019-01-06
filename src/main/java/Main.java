import Finders.NearestNeighbor;
import utils.DataLoader;
import utils.PathLength;
import utils.PointsVisualizer;

import java.awt.Point;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        List<Point> firstList = DataLoader.loadPoints("kroa100.tsp")
                .stream()
                .limit(50)
                .collect(Collectors.toList());

        List<Point> secondList = DataLoader.loadPoints("kroa100.tsp")
                .stream()
                .skip(50)
                .collect(Collectors.toList());

        NearestNeighbor nearestNeighbor = new NearestNeighbor();
        List<Point> KNNFirst = nearestNeighbor.resolvePath(firstList);
        List<Point> KNNSecond = nearestNeighbor.resolvePath(secondList);

        PointsVisualizer points = new PointsVisualizer(firstList, secondList);
        points.draw();

        PointsVisualizer points2 = new PointsVisualizer(KNNFirst, KNNSecond);
        points2.draw();

        double firstLength = PathLength.getTotalPathLength(KNNFirst);
        double secondLength = PathLength.getTotalPathLength(KNNSecond);
        System.out.println(KNNFirst.size() + " red points + " + KNNSecond.size() + " blue points");
        System.out.println(String.format("Red path length: %.2f", firstLength));
        System.out.println(String.format("Blue path length: %.2f", secondLength));
        System.out.println(String.format("Total path length: %.2f", (secondLength + firstLength)));
    }
}
