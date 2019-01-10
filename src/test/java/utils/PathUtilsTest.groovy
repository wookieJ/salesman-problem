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
    def p7 = new Point(0, 7)
    def p8 = new Point(0, 8)

    def"Should switch points"() {
        given:
        def list = [p1, p2, p3, p4] as List

        when:
        list = PathUtils.switchPoints(list, 0, 2)

        then:
        assert list.size() > 0
        assert list == [p3, p2, p1, p4]
    }


    def "should swap list"() {
        given:
        def list = [p1, p2, p3, p4, p5, p6, p7, p8, p1] as List

        when:
        list = PathUtils.opt2(list, 3, 6)

        then:
        assert list.size() > 0
        assert list == [p1, p2, p3, p7, p6, p5, p4, p8, p1]
    }
}
