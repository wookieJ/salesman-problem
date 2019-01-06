package utils

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
            def distance = PathLength.getTotalPathLength(points)
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
            def distance = PathLength.getTotalPathLength(points)
        then:
            assert distance == 5.0
    }
}
