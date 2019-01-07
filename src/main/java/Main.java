import Finders.BruteForceSearch;
import Finders.NearestNeighbor;
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

        Instant t1 = Instant.now();
        NearestNeighbor nearestNeighbor = new NearestNeighbor();
        Paths nearestNeighborPaths = nearestNeighbor.resolvePath(list);
        Instant t2 = Instant.now();
        nearestNeighbor.printStatistics();
        System.out.println("Execution time: " + Duration.between(t1, t2));

        Instant t1Brute = Instant.now();
        BruteForceSearch bruteForceSearch = new BruteForceSearch();
        Paths bruteForcePaths = bruteForceSearch.resolvePath(list);
        Instant t2Brute = Instant.now();
        bruteForceSearch.printStatistics();
        System.out.println("Execution time: " + Duration.between(t1Brute, t2Brute));

        PointsVisualizer nnVis = new PointsVisualizer(nearestNeighborPaths);
        nnVis.draw();

        PointsVisualizer bruteVis = new PointsVisualizer(bruteForcePaths);
        bruteVis.draw();
    }
}
