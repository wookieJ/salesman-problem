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
}
