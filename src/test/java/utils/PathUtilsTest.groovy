package utils

import spock.lang.Specification

import java.awt.Point

class PathUtilsTest extends Specification {
    def p1 = new Point(0, 1)
    def p2 = new Point(0, 2)
    def p3 = new Point(0, 3)
    def p4 = new Point(0, 4)
    def p5 = new Point(0, 5)
    def p6 = new Point(0, 6)

    def"Should switch points"() {
        given:
            def list = [p1, p2, p3, p4] as List
        when:
            PathUtils.switchPoints(list, 0, 2)
        then:
            assert list.size() > 0
            assert list == [p3, p2, p1, p4]
    }

    def"Should switch arc"() {
        given:
            def list = [p1, p2, p3, p4, p5] as List
        when:
            PathUtils.switchArcs(list, 1, 3)
        then:
            assert list.size() > 0
            assert list == [p1, p4, p3, p2, p5]
    }

    def"Should switch arc with last point"() {
        given:
            def list = [p1, p2, p3, p4, p5] as List
        when:
            PathUtils.switchArcs(list, 2, 4)
        then:
            assert list.size() > 0
            assert list == [p1, p2, p5, p4, p3]
    }

    def"Should switch arc with reversed indexes"() {
        given:
            def list = [p1, p2, p3, p4, p5] as List
        when:
            PathUtils.switchArcs(list, 3, 1)
        then:
            assert list.size() > 0
            assert list == [p1, p4, p3, p2, p5]
    }
}
