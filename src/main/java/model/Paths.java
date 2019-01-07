package model;

import java.awt.Point;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Paths {
    private LinkedList<Point> pointsOne = new LinkedList<>();
    private LinkedList<Point> pointsTwo = new LinkedList<>();

    public Paths() {
    }

    public Paths(Paths paths) {
        this.pointsOne = new LinkedList<>(paths.getPointsOne());
        this.pointsTwo = new LinkedList<>(paths.getPointsTwo());
    }

    public List<Point> getPointsOne() {
        return pointsOne;
    }

    public void setPointsOne(LinkedList<Point> pointsOne) {
        this.pointsOne = pointsOne;
    }

    public List<Point> getPointsTwo() {
        return pointsTwo;
    }

    public void setPointsTwo(LinkedList<Point> pointsTwo) {
        this.pointsTwo = pointsTwo;
    }

    public boolean addToOne(Point point) {
        return pointsOne.add(point);
    }

    public boolean addToTwo(Point point) {
        return pointsTwo.add(point);
    }

    public void addLastToOne(Point point) {
        pointsOne.addLast(point);
    }

    public void addLastToTwo(Point point) {
        pointsTwo.addLast(point);
    }

    public Point getFirstFromOne() {
        return pointsOne.getFirst();
    }

    public Point getFirstFromTwo() {
        return pointsTwo.getFirst();
    }

    public void addAllToOne(List<Point> points) {
        pointsOne.addAll(points);
    }

    public void addAllToTwo(List<Point> points) {
        pointsTwo.addAll(points);
    }

    public Point getLastFromOne() {
        return pointsOne.getLast();
    }

    public Point getLastFromTwo() {
        return pointsTwo.getLast();
    }

    public void addFirstPointToLastOne() {
        pointsOne.addLast(pointsOne.getFirst());
    }

    public void addFirstPointToLastTwo() {
        pointsTwo.addLast(pointsTwo.getFirst());
    }

    public void switchPoints(int p1, int p2) {
        Point pointOne = pointsOne.get(p1);
        pointsOne.set(p1, pointsTwo.get(p2));
        pointsTwo.set(p2, pointOne);
    }

    public void switchArcsOne(int p1, int p2) {
        if(p1 > p2) {
            int cpy = p1;
            p1 = p2;
            p2 = cpy;
        }
        List<Point> arc = pointsOne.subList(p1, p2 + 1);
        Collections.reverse(arc);
        int idx = 0;
        for(int i=p1 ; i<p2 ; i++) {
            pointsOne.set(i, arc.get(idx));
            idx++;
        }
    }

    public void switchArcsTwo(int p1, int p2) {
        int cpy;
        if(p1 > p2) {
            cpy = p1;
            p1 = p2;
            p2 = cpy;
        }
        List<Point> arc = pointsTwo.subList(p1, p2 + 1);
        Collections.reverse(arc);
        int idx = 0;
        for(int i=p1 ; i<p2 ; i++) {
            pointsTwo.set(i, arc.get(idx));
            idx++;
        }
    }
}
