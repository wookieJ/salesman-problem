package model

import spock.lang.Specification
import java.awt.Point

class PathsTest extends Specification {

    def p1 = new Point(0, 1)
    def p2 = new Point(0, 2)
    def p3 = new Point(0, 3)
    def p4 = new Point(0, 4)
    def p5 = new Point(0, 5)
    def p6 = new Point(0, 6)

    def"Should switch points"() {
        given:
            def list = [p1, p2, p3] as List
            def list2 = [p4, p5, p6] as List
            def paths = new Paths()
            paths.pointsOne = list
            paths.pointsTwo = list2
        when:
            paths.switchPoints(0, 2)
        then:
            assert paths.getPointsOne() instanceof List
            assert paths.getPointsOne().size() > 0
            assert paths.getPointsOne() == [p6, p2, p3]

            assert paths.getPointsTwo() instanceof List
            assert paths.getPointsTwo().size() > 0
            assert paths.getPointsTwo() == [p4, p5, p1]
    }

    def"Should switch arc"() {
        given:
            def list = [p1, p2, p3, p4, p5] as List
            def paths = new Paths()
            paths.pointsOne = list
            paths.pointsTwo = list
        when:
            paths.switchArcsOne(1, 3)
            paths.switchArcsTwo(1, 3)
        then:
            assert paths.getPointsOne() instanceof List
            assert paths.getPointsOne().size() > 0
            assert paths.getPointsOne() == [p1, p4, p3, p2, p5]

            assert paths.getPointsTwo() instanceof List
            assert paths.getPointsTwo().size() > 0
            assert paths.getPointsTwo() == [p1, p4, p3, p2, p5]
    }

    def"Should switch arc with last point"() {
        given:
            def list = [p1, p2, p3, p4, p5] as List
            def paths = new Paths()
            paths.pointsOne = list
            paths.pointsTwo = list
        when:
            paths.switchArcsOne(2, 4)
            paths.switchArcsTwo(2, 4)
        then:
            assert paths.getPointsOne() instanceof List
            assert paths.getPointsOne().size() > 0
            assert paths.getPointsOne() == [p1, p2, p5, p4, p3]

            assert paths.getPointsTwo() instanceof List
            assert paths.getPointsTwo().size() > 0
            assert paths.getPointsTwo() == [p1, p2, p5, p4, p3]
    }

    def"Should switch arc with reversed indexes"() {
        given:
            def list = [p1, p2, p3, p4, p5] as List
            def paths = new Paths()
            paths.pointsOne = list
            paths.pointsTwo = list
        when:
            paths.switchArcsOne(3, 1)
            paths.switchArcsTwo(3, 1)
        then:
            assert paths.getPointsOne() instanceof List
            assert paths.getPointsOne().size() > 0
            assert paths.getPointsOne() == [p1, p4, p3, p2, p5]

            assert paths.getPointsTwo() instanceof List
            assert paths.getPointsTwo().size() > 0
            assert paths.getPointsTwo() == [p1, p4, p3, p2, p5]
    }
}
