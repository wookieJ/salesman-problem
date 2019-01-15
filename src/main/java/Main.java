import Finders.*;
import Similarities.SimilarEdges;
import model.Paths;
import org.slf4j.spi.LocationAwareLogger;
import utils.DataLoader;
import utils.PathLength;
import utils.PointsVisualizer;

import javax.swing.*;
import java.awt.Point;
import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

    private static final String FILENAME = "kroa100.tsp";
    private static final long BRUTE_FORCE_EPOCH_NUMBER = 1_000_000;

    public static void main(String[] args) {
        List<Point> list = DataLoader.loadPoints(FILENAME);

        NearestNeighbor nearestNeighbor = new NearestNeighbor(list, "Nearest Neighbor");
        BruteForceSearch bruteForceSearch = new BruteForceSearch(list, "Brute force", BRUTE_FORCE_EPOCH_NUMBER);
        BruteForceSearch randomPath = new BruteForceSearch(list, "Random paths", 1);

        LocalSearch localSearchRP = new LocalSearch(randomPath);
        LocalSearch localSearchBF = new LocalSearch(bruteForceSearch);
        LocalSearch localSearchNN = new LocalSearch(nearestNeighbor);
        IteratedLocalSearch iteratedLocalSearchNN = new IteratedLocalSearch(localSearchNN);

        runAlgorithm(bruteForceSearch);
        runAlgorithm(randomPath);
        runAlgorithm(nearestNeighbor);
        runAlgorithm(localSearchRP);
        runAlgorithm(localSearchBF);
        runAlgorithm(localSearchNN);
        runAlgorithm(iteratedLocalSearchNN);
        SimilarEdges similarEdges = new SimilarEdges(list);
        similarEdges.execute();
        Map<Double, Double> allCostsEdges = similarEdges.getAllCostsEdges();
        for(Double key : allCostsEdges.keySet()) {
            System.out.println(key + "," + allCostsEdges.get(key));
        }
//        drawSimilarEdgesVsCosts(allCostsEdges);
//        double bestLengthNN = nearestNeighbor.getMinDistance();
//        LocalSearch bestNN = null;
//        Instant t1 = Instant.now();
//        for (int i = 0; i < 100; i++) {
//            nearestNeighbor = new NearestNeighbor(list, "Nearest Neighbor");
//            localSearchNN = new LocalSearch(nearestNeighbor);
//            Paths pathsNN = localSearchNN.resolvePath();
//            double pathLengthNN = PathLength.getTotalPathLength(pathsNN);
//            if(pathLengthNN < bestLengthNN) {
//                bestLengthNN = pathLengthNN;
//                bestNN = localSearchNN;
//            }
//        }
//        Instant t2 = Instant.now();
//        System.out.println("=========================================================");
//        String title = "100 uruchomień NN";
//        System.out.println(title);
//        bestNN.printStatistics();
//        PointsVisualizer pointsVisualizer = new PointsVisualizer(bestNN.getBasePath());
//        pointsVisualizer.draw(title);
//        System.out.println("Execution time: " + Duration.between(t1, t2));
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

    private static void drawSimilarEdgesVsCosts(Map<Double,Double> allCostsEdges){
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900,600);
    }
}
