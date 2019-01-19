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
import java.util.*;

public class Main {

    private static final String FILENAME = "kroa150.tsp";
    private static final long BRUTE_FORCE_EPOCH_NUMBER = 1_000_000;

    public static void main(String[] args) {
        List<Point> list = DataLoader.loadPoints(FILENAME);

        NearestNeighbor nearestNeighbor = new NearestNeighbor(list, "Nearest Neighbor");
        BruteForceSearch bruteForceSearch = new BruteForceSearch(list, "Brute force", BRUTE_FORCE_EPOCH_NUMBER);
        BruteForceSearch randomPath = new BruteForceSearch(list, "Random paths", 1);

        LocalSearch localSearchRP = new LocalSearch(randomPath);
        LocalSearch localSearchBF = new LocalSearch(bruteForceSearch);
        LocalSearch localSearchNN = new LocalSearch(nearestNeighbor);

//        runAlgorithm(bruteForceSearch);
//        runAlgorithm(randomPath);
//        runAlgorithm(nearestNeighbor);
//        runLocalSearchNN(nearestNeighbor, localSearchNN, list);
//        runLocalSearchBrute(bruteForceSearch, localSearchBF, list);
//        runAlgorithm(localSearchRP);
//        runAlgorithm(localSearchBF);
//        runAlgorithm(localSearchNN);
//        SimilarEdges similarEdges = new SimilarEdges(list);
//        similarEdges.execute();
//        Map<Double, Double> allCostsEdges = similarEdges.getAllCostsEdges();
//        for(Double key : allCostsEdges.keySet()) {
//            System.out.println(key + "," + allCostsEdges.get(key));
//        }
        IteratedLocalSearch iteratedLocalSearchNN = new IteratedLocalSearch(localSearchNN);
        runAlgorithm(iteratedLocalSearchNN);
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

    private static long runMSLS(NearestNeighbor nearestNeighbor, LocalSearch localSearchNN, List<Point> list) {
        double bestLengthNN = nearestNeighbor.getMinDistance();
        LocalSearch bestNN = null;
        Instant t1 = Instant.now();
        for (int i = 0; i < 100; i++) {
            nearestNeighbor = new NearestNeighbor(list, "Nearest Neighbor");
            localSearchNN = new LocalSearch(nearestNeighbor);
            Paths pathsNN = localSearchNN.resolvePath();
            double pathLengthNN = PathLength.getTotalPathLength(pathsNN);
            if(pathLengthNN < bestLengthNN) {
                bestLengthNN = pathLengthNN;
                bestNN = localSearchNN;
            }
        }
        Instant t2 = Instant.now();
        System.out.println("=========================================================");
        String title = "100 uruchomień NN";
        System.out.println(title);
        bestNN.printStatistics();
        PointsVisualizer pointsVisualizer = new PointsVisualizer(bestNN.getBasePath());
        pointsVisualizer.draw(title);
        System.out.println("Execution time: " + Duration.between(t1, t2));
        return Duration.between(t1,t2).getSeconds();
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
    private static void runLocalSearchNN(NearestNeighbor nearestNeighbor, LocalSearch localSearchNN, List<Point> list) {
        double bestLengthNN = nearestNeighbor.getMinDistance();
        double maxLengthNN = 0.0;
        List<Integer> allTimes = new ArrayList<>();
        List<Double> allDistance = new ArrayList<>();
        LocalSearch bestNN = null;
        for (int i = 0; i < 10; i++) {
            nearestNeighbor = new NearestNeighbor(list, "Nearest Neighbor");
            localSearchNN = new LocalSearch(nearestNeighbor);
            Instant t1 = Instant.now();
            Paths pathsNN = localSearchNN.resolvePath();
            Instant t2 = Instant.now();
            allTimes.add(Duration.between(t1,t2).getNano()/1000000);
            double pathLengthNN = PathLength.getTotalPathLength(pathsNN);
            allDistance.add(pathLengthNN);
            if(pathLengthNN < bestLengthNN) {
                bestLengthNN = pathLengthNN;
                bestNN = localSearchNN;
            } else if (pathLengthNN > maxLengthNN) {
                maxLengthNN = pathLengthNN;
            }
        }
        Integer minTime = allTimes.stream().mapToInt(v -> v).min().orElseThrow(NoSuchElementException::new);
        Integer maxTime = allTimes.stream().mapToInt(v -> v).max().orElseThrow(NoSuchElementException::new);
        Double avgDistance = setAvgDistance(allDistance);
        System.out.println("=========================================================");
        String title = "Local Search (Nearest Neighbor)";
        System.out.println(title);
        PathFinder.stat(bestNN.getBasePath(), bestLengthNN,maxLengthNN,avgDistance);
        System.out.println("Max Time(ms): " + maxTime);
        System.out.println("Min Time(ms): " + minTime);
        PointsVisualizer pointsVisualizer = new PointsVisualizer(bestNN.getBasePath());
        pointsVisualizer.draw(title);
    }

    private static void runLocalSearchBrute(BruteForceSearch bruteForceSearch, LocalSearch localSearchBF, List<Point> list) {
        double bestLengthBF = bruteForceSearch.getMinDistance();
        double maxLengthBF = 0.0;
        List<Integer> allTimes = new ArrayList<>();
        List<Double> allDistance = new ArrayList<>();
        LocalSearch bestBF = null;
        for (int i = 0; i < 10; i++) {
            bruteForceSearch = new BruteForceSearch(list, "Brute force", BRUTE_FORCE_EPOCH_NUMBER);
            localSearchBF = new LocalSearch(bruteForceSearch);
            Instant t1 = Instant.now();
            Paths pathsBF = localSearchBF.resolvePath();
            Instant t2 = Instant.now();
            allTimes.add(Duration.between(t1,t2).getNano()/1000000);
            double pathLengthBF = PathLength.getTotalPathLength(pathsBF);
            allDistance.add(pathLengthBF);
            if(pathLengthBF < bestLengthBF) {
                bestLengthBF = pathLengthBF;
                bestBF = localSearchBF;
            } else if (pathLengthBF > maxLengthBF) {
                maxLengthBF = pathLengthBF;
            }
        }
        Integer minTime = allTimes.stream().mapToInt(v -> v).min().orElseThrow(NoSuchElementException::new);
        Integer maxTime = allTimes.stream().mapToInt(v -> v).max().orElseThrow(NoSuchElementException::new);
        Double avgDistance = setAvgDistance(allDistance);
        System.out.println("=========================================================");
        String title = "Local Search (Random)";
        System.out.println(title);
        PathFinder.stat(bestBF.getBasePath(), bestLengthBF,maxLengthBF,avgDistance);
        System.out.println("Max Time(ms): " + maxTime);
        System.out.println("Min Time(ms): " + minTime);
        PointsVisualizer pointsVisualizer = new PointsVisualizer(bestBF.getBasePath());
        pointsVisualizer.draw(title);
    }

    private static double setAvgDistance(List<Double> allDistance) {
        double sum = 0;
        if(!allDistance.isEmpty()) {
            for(Double distance : allDistance) {
                sum+= distance;
            }
            return sum / allDistance.size();
        }
        return sum;
    }
}
