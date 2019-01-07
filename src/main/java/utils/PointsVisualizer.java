package utils;

import model.Paths;

import javax.swing.JComponent;
import javax.swing.JFrame;
import java.awt.Point;
import java.awt.Graphics;
import java.awt.Color;
import java.util.*;

public class PointsVisualizer {
    private static final int FRAME_WIDTH = 900;
    private static final int FRAME_HEIGHT = 600;
    private static final int FRAME_X_POS = 260;
    private static final int FRAME_Y_POS = 150;
    private static final int FRAME_X_BOUND = 70;
    private static final int FRAME_Y_BOUND = 70;
    private static final int POINT_WIDTH = 8;
    private static final int POINT_HEIGHT = 8;

    private Paths pointList;

    public PointsVisualizer(Paths pointList) {
        this.pointList = new Paths(pointList);
    }

    public void draw(String name) {
        JFrame f = new JFrame();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        f.setLocation(FRAME_X_POS, FRAME_Y_POS);
        f.getContentPane().add(new ConnectedPoints(name));
        f.setVisible(true);
    }

    private class ConnectedPoints extends JComponent {

        private String name;

        public ConnectedPoints(String name) {
            this.name = name;
        }

        @Override
        public void paint(Graphics g) {
            printPointAndLine(g, pointList.getPointsOne(), Color.RED);
            double firstPathLength = PathLength.getPathLength(pointList.getPointsOne());
            printText(g, String.format("%.2f", firstPathLength), Color.RED, 10, 20);
            printPointAndLine(g, pointList.getPointsTwo(), Color.BLUE);
            double secondPathLength = PathLength.getPathLength(pointList.getPointsTwo());
            printText(g, String.format("%.2f", secondPathLength), Color.BLUE, 90, 20);
            printText(g, String.format("total=%.2f", (firstPathLength + secondPathLength)), Color.BLACK, 170, 20);
            printText(g, this.name, Color.BLACK, 450, 20);
        }
    }

    private void printText(Graphics g, String text, Color color, int x, int y) {
        g.setColor(color);
        g.drawString(text, x, y);
    }

    private void printPointAndLine(Graphics g, List<Point> points, Color color) {
        Point prevPoint = points.get(0);
        int fixPos = POINT_WIDTH / 2;
        int cnt = 0;
        for(Point point: points) {
            g.setColor(Color.magenta);
            if(cnt > 0 && cnt < points.size() - 1)
                g.setColor(Color.BLACK);
            Point scaledPoint = scalePoint(point);
            if(scaledPoint.x >= FRAME_WIDTH || scaledPoint.y >= FRAME_HEIGHT) {
                System.out.println(scaledPoint);
            }
            g.fillOval(scaledPoint.x, scaledPoint.y, POINT_WIDTH, POINT_HEIGHT);
            printText(g, String.valueOf(cnt), Color.BLACK, scaledPoint.x, scaledPoint.y - 5);
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
        list.addAll(pointList.getPointsOne());
        list.addAll(pointList.getPointsTwo());
        Point bound = findBound(list);
        int scaleX = (int)((double)point.x / bound.x * (FRAME_WIDTH - FRAME_X_BOUND)) + FRAME_X_BOUND / 2;
        int scaleY = (int)((double)point.y / bound.y * (FRAME_HEIGHT - FRAME_Y_BOUND)) + FRAME_Y_BOUND / 2;
        return new Point(scaleX, scaleY);
    }
}
