package Similarities;

import Finders.BruteForceSearch;
import Finders.LocalSearch;
import Finders.NearestNeighbor;
import model.Paths;

import java.awt.Point;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SimilarEdges {

    private LocalSearch localSearch;
    private NearestNeighbor nearestNeighbor;
    private BruteForceSearch bruteForceSearch;
    private List<Point> listOfPoints;
    private List<Paths> allRoutes;
    private List<Double> allCosts;

    public Map<Double, Double> getAllCostsEdges() {
        return allCostsEdges;
    }

    private Map<Double,Double> allCostsEdges = new HashMap<>();


    public SimilarEdges(List<Point> listOfPoints) {
        this.listOfPoints = listOfPoints;
        allCosts = new LinkedList<>();
        allRoutes = new LinkedList<>();
    }

    public void execute() {
        for (int i = 0; i < 1000; i++) {
            bruteForceSearch = new BruteForceSearch(listOfPoints, "Random", 1);
            localSearch = new LocalSearch(bruteForceSearch);
            Paths paths = localSearch.resolvePath();
            allRoutes.add(paths);
            allCosts.add(localSearch.getMinDistance());
        }

        for (int i = 0; i < allRoutes.size(); i++) {
            Paths route = allRoutes.get(i);
            int similarEdges = 0;
            List<List<Point>> routePairs = createPairs(route.getPointsOne(), route.getPointsTwo());
            for(Paths otherRoutes : allRoutes.stream().filter(o -> o != route).collect(Collectors.toList())) {
                List<List<Point>> otherRoutePairs = createPairs(otherRoutes.getPointsOne(), otherRoutes.getPointsTwo());
                similarEdges += checkEdges(routePairs, otherRoutePairs);
            }
            allCostsEdges.put(allCosts.get(i),similarEdges/999.0);
        }
    }

    private int checkEdges(List<List<Point>> routePairs , List<List<Point>> otherRoutePairs) {
        return (int) routePairs.stream().filter(otherRoutePairs::contains).count();
    }

    private List<List<Point>> createPairs(List<Point> routeOne, List<Point> routeTwo){
        List<List<Point>> pairs = new LinkedList<>();
        for(int i = 0; i < routeOne.size() - 1; i++) {
            List<Point> pair = new LinkedList<>();
            pair.add(routeOne.get(i));
            pair.add(routeOne.get(i+1));
            pairs.add(pair);
        }
        for(int i = 0; i < routeTwo.size() - 1; i++) {
            List<Point> pair = new LinkedList<>();
            pair.add(routeTwo.get(i));
            pair.add(routeTwo.get(i+1));
            pairs.add(pair);
        }
        return pairs;
    }

}
