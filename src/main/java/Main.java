import Finders.BruteForceSearch;
import Finders.LocalSearch;
import Finders.NearestNeighbor;
import Finders.PathFinder;
import model.Paths;
import utils.DataLoader;
import utils.PointsVisualizer;

import java.awt.Point;
import java.time.Duration;
import java.time.Instant;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Point> list = DataLoader.loadPoints("kroa100.tsp");

        NearestNeighbor nearestNeighbor = new NearestNeighbor(list);
        BruteForceSearch bruteForceSearch = new BruteForceSearch(list);
        LocalSearch localSearchBF = new LocalSearch(bruteForceSearch);

        runAlgorithm(nearestNeighbor);
        runAlgorithm(bruteForceSearch);
        runAlgorithm(localSearchBF);
    }

    private static void runAlgorithm(PathFinder pathFinder) {
        Instant t1 = Instant.now();
        Paths optimalPath = pathFinder.resolvePath();
        Instant t2 = Instant.now();
        pathFinder.printStatistics();
        System.out.println("Execution time: " + Duration.between(t1, t2));

        PointsVisualizer visualizer = new PointsVisualizer(optimalPath);
        visualizer.draw(pathFinder.getName());
    }
}
