package utils

import spock.lang.Specification

import java.awt.Point

class EuclideanDistanceTest extends Specification {

    def "Should return 0 distance"() {
        given:
            def p1 = new Point(10, 10)
            def p2 = new Point(10, 10)
        when:
            def distance = EuclideanDistance.distance(p1, p2)
        then:
            assert distance == 0
    }

    def "Should calculate distance"() {
        given:
            def p1 = new Point(0, 0)
            def p2 = new Point(10, 0)
            def p3 = new Point(0, 10)
        when:
            def distance = EuclideanDistance.distance(p1, p2)
            def distance2 = EuclideanDistance.distance(p1, p3)
        then:
            assert distance == 10
            assert distance2 == 10
    }
}
