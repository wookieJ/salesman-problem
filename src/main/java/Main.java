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
        BruteForceSearch bruteForceSearch = new BruteForceSearch(list, 1_000_000);
        BruteForceSearch randomPath = new BruteForceSearch(list, 1);
        LocalSearch localSearchRP = new LocalSearch(randomPath);
        LocalSearch localSearchBF = new LocalSearch(bruteForceSearch);
        LocalSearch localSearchNN = new LocalSearch(nearestNeighbor);

        runAlgorithm(nearestNeighbor);
        runAlgorithm(bruteForceSearch);
        runAlgorithm(randomPath);
        runAlgorithm(localSearchRP);
        runAlgorithm(localSearchBF);
        runAlgorithm(localSearchNN);
    }

    private static void runAlgorithm(PathFinder pathFinder) {
        System.out.println("=========================================================");
        String title = pathFinder.getName();
        if(pathFinder instanceof LocalSearch) {
            title += " (" + ((LocalSearch) pathFinder).getPathFinder().getName() + ")";
        }
        System.out.println(title);
        Instant t1 = Instant.now();
        Paths optimalPath = pathFinder.resolvePath();
        Instant t2 = Instant.now();
        pathFinder.printStatistics();
        System.out.println("Execution time: " + Duration.between(t1, t2));

        PointsVisualizer visualizer = new PointsVisualizer(optimalPath);
        visualizer.draw(title);
    }
}
