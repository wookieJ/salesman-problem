package utils

import model.Paths
import spock.lang.Specification

import java.awt.Point

class PathLengthTest extends Specification {

    def "Should return 0 path length"() {
        given:
            def p1 = new Point(0, 0)
            def p2= new Point(0, 0)
            def p3 = new Point(0, 0)
            def points = [p1, p2, p3]
        when:
            def distance = PathLength.getPathLength(points)
        then:
            assert distance == 0
    }

    def "Should return correct path length"() {
        given:
            def p1 = new Point(0, 0)
            def p2= new Point(2, 0)
            def p3 = new Point(2, 3)
            def points = [p1, p2, p3]
        when:
            def distance = PathLength.getPathLength(points)
        then:
            assert distance == 5.0
    }

    def "Should return correct total path length"() {
        given:
            def p1 = new Point(0, 0)
            def p2= new Point(2, 0)
            def p3 = new Point(2, 3)
            def points = [p1, p2, p3] as LinkedList<Point>
            def points2 = [p1, p2, p3] as LinkedList<Point>
            def path = new Paths()
            path.setPointsOne(points)
            path.setPointsTwo(points2)
        when:
            def distance = PathLength.getTotalPathLength(path)
        then:
            assert distance == 10
    }
}
