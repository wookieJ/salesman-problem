package utils;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class Points {
    private static int frameWidth = 900;
    private static int frameHeight = 600;
    private static int pointWidth = 5;
    private static int pointHeight = 5;

    private List<Point> points = new ArrayList<>();

    public void visualizePoints() {
        JFrame f = new JFrame();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(frameWidth,frameHeight);
        f.setLocation(260,150);
        f.getContentPane().add(new Dots(this.points));
        f.setVisible(true);
    }

    private class Dots extends JComponent {
        List<Point> points;

        Dots(List<Point> points) {
            this.points = points;
        }

        @Override
        public void paint(Graphics g) {
            Point bound = findBound(this.points);
            points.forEach(point -> {
                g.setColor(Color.RED);
                int scaleX = (int)((double)point.x / bound.x * frameWidth);
                int scaleY = (int)((double)point.y / bound.y * frameHeight);
                g.fillOval(scaleX, scaleY, pointWidth, pointHeight);
            });
        }
    }

    private Point findBound(List<Point> points) {
        Optional<Integer> xMax = points.stream().map(point -> point.x).max(Comparator.naturalOrder());
        Optional<Integer> yMax = points.stream().map(point -> point.y).max(Comparator.naturalOrder());
        return new Point(xMax.orElse(0), yMax.orElse(0));
    }
}
