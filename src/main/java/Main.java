import utils.DataLoader;
import utils.Points;

public class Main {
    public static void main(String[] args) {
        Points.visualizePoints(DataLoader.loadPoints("kroa100.tsp"));
    }
}
