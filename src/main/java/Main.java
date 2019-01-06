import utils.DataLoader;
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

        PointsVisualizer points = new PointsVisualizer(firstList, secondList);
        points.draw();
    }
}
