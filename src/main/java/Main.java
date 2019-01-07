import Finders.BruteForceSearch;
import model.Paths;
import utils.DataLoader;
import utils.PointsVisualizer;

import java.awt.Point;
import java.time.Instant;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Point> list = DataLoader.loadPoints("kroa100.tsp");

        Instant t1 = Instant.now();
        BruteForceSearch bruteForceSearch = new BruteForceSearch();
        Paths bruteForcePath = bruteForceSearch.resolvePath(list, 0, 1);
        Instant t2 = Instant.now();

        PointsVisualizer bruteVis = new PointsVisualizer(bruteForcePath.getPointsOne(), bruteForcePath.getPointsTwo());
        bruteVis.draw();
    }
}
