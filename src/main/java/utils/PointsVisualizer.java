package utils;

import javax.swing.JComponent;
import javax.swing.JFrame;
import java.awt.Point;
import java.awt.Graphics;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class PointsVisualizer {
    private static int FRAME_WIDTH = 900;
    private static int FRAME_HEIGHT = 600;
    private static int FRAME_X_POS = 260;
    private static int FRAME_Y_POS = 150;
    private static int FRAME_X_BOUND = 70;
    private static int FRAME_Y_BOUND = 70;
    private static int POINT_WIDTH = 8;
    private static int POINT_HEIGHT = 8;

    private List<Point> firstPointsList;
    private List<Point> secondPointsList;

    public PointsVisualizer(List<Point> firstPointsSet, List<Point> secondPointsSet) {
        this.firstPointsList = firstPointsSet;
        this.secondPointsList = secondPointsSet;
    }

    public void draw() {
        JFrame f = new JFrame();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        f.setLocation(FRAME_X_POS, FRAME_Y_POS);
        f.getContentPane().add(new ConnectedPoints());
        f.setVisible(true);
    }

    private class ConnectedPoints extends JComponent {

        @Override
        public void paint(Graphics g) {
            printPointAndLine(g, firstPointsList, Color.RED);
            printPathLength(g, firstPointsList, Color.RED, 10, 20);
            printPointAndLine(g, secondPointsList, Color.BLUE);
            printPathLength(g, secondPointsList, Color.BLUE, 100, 20);
        }
    }

    private void printPathLength(Graphics g, List<Point> points, Color color, int x, int y) {
        g.setColor(color);
        String text = String.format("%.2f", PathLength.getTotalPathLength(points));
        g.drawString(text, x, y);
    }

    private void printPointAndLine(Graphics g, List<Point> points, Color color) {
        Point prevPoint = points.get(0);
        int fixPos = POINT_WIDTH / 2;
        int cnt = 0;
        for(Point point: points) {
            g.setColor(Color.magenta);
            if(cnt > 0)
                g.setColor(Color.BLACK);
            Point scaledPoint = scalePoint(point);
            if(scaledPoint.x >= FRAME_WIDTH || scaledPoint.y >= FRAME_HEIGHT) {
                System.out.println(scaledPoint);
            }
            g.fillOval(scaledPoint.x, scaledPoint.y, POINT_WIDTH, POINT_HEIGHT);
            if(cnt > 0) {
                g.setColor(color);
                g.drawLine(prevPoint.x + fixPos, prevPoint.y + fixPos,
                        scaledPoint.x + fixPos, scaledPoint.y + fixPos);
            }
            prevPoint = scaledPoint;
            cnt++;
        }
    }

    private Point findBound(List<Point> points) {
        Optional<Integer> xMax = points.stream().map(point -> point.x).max(Comparator.naturalOrder());
        Optional<Integer> yMax = points.stream().map(point -> point.y).max(Comparator.naturalOrder());
        return new Point(xMax.orElse(0), yMax.orElse(0));
    }

    private Point scalePoint(Point point) {
        List<Point> list = new ArrayList<>();
        list.addAll(firstPointsList);
        list.addAll(secondPointsList);
        Point bound = findBound(list);
        int scaleX = (int)((double)point.x / bound.x * (FRAME_WIDTH - FRAME_X_BOUND)) + FRAME_X_BOUND / 2;
        int scaleY = (int)((double)point.y / bound.y * (FRAME_HEIGHT - FRAME_Y_BOUND)) + FRAME_Y_BOUND / 2;
        return new Point(scaleX, scaleY);
    }
}
