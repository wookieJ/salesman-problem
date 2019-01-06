package utils

import spock.lang.Specification
import java.awt.Point

class DataLoaderTest extends Specification {

    def dataLoader = new DataLoader()

    def "Should load all points"() {
        when:
            def points100 = dataLoader.loadPoints("kroa100.tsp")
            def points150 = dataLoader.loadPoints("kroa150.tsp")
        then:
            assert points100 instanceof List<Point>
            assert points150 instanceof List<Point>
            assert points100.size() == 100
            assert points150.size() == 150
    }
}
