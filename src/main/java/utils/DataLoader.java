package utils;

import java.awt.Point;
import java.util.List;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class DataLoader {
    public static List<Point> loadPoints(String filename) {
        List<Point> points = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader("src/main/resources/" + filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                if(line.matches("[0-9]{1,4} [0-9]{1,4} [0-9]{1,4}")) {
                    String[] splited = line.split(" ");
                    points.add(new Point(Integer.parseInt(splited[1]), Integer.parseInt(splited[2])));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return points;
    }
}
