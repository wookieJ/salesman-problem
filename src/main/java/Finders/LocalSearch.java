package Finders;

import model.Paths;
import utils.PathLength;
import utils.PathUtils;

import java.awt.Point;

public class LocalSearch {
    private static final String NAME = "Local Search";

    private Paths basePath;

    public String getName() {
        return NAME;
    }

    public LocalSearch(Paths basePath) {
        this.basePath = basePath;
    }

    public Paths resolvePath() {
        double delta = 0;
        double lastMoveDelta = 0;
        double minDelta;
        do {
            minDelta = Double.MAX_VALUE;

            double switchedPointsPathLength;
            double switchedArcsPathLengthOne;
            double switchedArcsPathLengthTwo;

            Paths minDeltaBestMove = new Paths(basePath);

            for(Point pointA: basePath.getPointsOne()) {
                for(Point pointB: basePath.getPointsTwo()) {
                    Paths switchedPointsPath = new Paths(basePath);
                    Paths switchedArcsPath = new Paths(basePath);
                    if(isStartOrEndPoint(pointA, pointB)) {
                        PathUtils.switchPoints(switchedPointsPath.getPointsOne(), basePath.getPointsOne().indexOf(pointA), basePath.getPointsTwo().indexOf(pointB));
                        switchedPointsPathLength = PathLength.getTotalPathLength(switchedPointsPath);
                        PathUtils.switchArcs(switchedArcsPath.getPointsOne(), basePath.getPointsOne().indexOf(pointA), basePath.getPointsTwo().indexOf(pointB));
                        switchedArcsPathLengthOne = PathLength.getTotalPathLength(switchedPointsPath);
                        PathUtils.switchArcs(switchedArcsPath.getPointsTwo(), basePath.getPointsOne().indexOf(pointA), basePath.getPointsTwo().indexOf(pointB));
                        switchedArcsPathLengthTwo = PathLength.getTotalPathLength(switchedPointsPath);

                        if(switchedPointsPathLength < switchedArcsPathLengthOne && switchedPointsPathLength < switchedArcsPathLengthTwo) {
                            delta = switchedPointsPathLength - PathLength.getTotalPathLength(basePath);
                            if(delta < minDelta) {
                                minDelta = delta;
                                minDeltaBestMove = switchedPointsPath;
                            }
                        } else if (switchedArcsPathLengthOne < switchedPointsPathLength && switchedArcsPathLengthOne < switchedArcsPathLengthTwo) {
                            delta = switchedArcsPathLengthOne - PathLength.getTotalPathLength(basePath);
                            if(delta < minDelta) {
                                minDelta = delta;
                                minDeltaBestMove = switchedArcsPath;
                            }
                        } else {
                            delta = switchedArcsPathLengthTwo - PathLength.getTotalPathLength(basePath);
                            if(delta < minDelta) {
                                minDelta = delta;
                                minDeltaBestMove = switchedArcsPath;
                            }
                        }

                        if(minDelta < 0) {
                            basePath = minDeltaBestMove;
                            lastMoveDelta = delta;
                        }
                    }
                }
            }
            System.out.println(minDelta);
        } while (lastMoveDelta < 0);

        return basePath;
    }

    private boolean isStartOrEndPoint(Point pointA, Point pointB) {
        return basePath.getPointsOne().indexOf(pointA) != 0 &&
                basePath.getPointsTwo().indexOf(pointB) != 0 &&
                basePath.getPointsOne().indexOf(pointA) != basePath.getPointsOne().size() - 1 &&
                basePath.getPointsTwo().indexOf(pointB) != basePath.getPointsTwo().size() - 1;
    }

    public void printStatistics() {

    }
}
