package model;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class ParkingLotTest {
    public ParkingLot test;

    @BeforeEach
    public void setUp() {
        test = new ParkingLot("testname", 3);
    }

    @Test
    public void testConstructor() {
        for (ParkingSpot ps: test.getParkingspots()) {
            assertNull(ps);
        }
        assertEquals(3, test.getPrice());
        assertEquals("testname", test.getName());
    }

    @Test
    public void testAddParkingSpot() {
        ParkingSpot testparkingspot = new ParkingSpot("test", "medium", 5);
        test.addParkingSpot(testparkingspot);
        assertTrue(test.getParkingspots().contains(testparkingspot));
    }

    @Test
    public void testAddParkingSpotMany() {
        ParkingSpot testparkingspot1 = new ParkingSpot("test", "medium", 5);
        ParkingSpot testparkingspot2 = new ParkingSpot("test2", "small", 3);
        ParkingSpot testparkingspot3 = new ParkingSpot("test3", "medium", 5);
        test.addParkingSpot(testparkingspot1);
        test.addParkingSpot(testparkingspot2);
        test.addParkingSpot(testparkingspot3);
        assertEquals(3, test.getParkingspots().size());
        assertTrue(test.getParkingspots().contains(testparkingspot1));
        assertTrue(test.getParkingspots().contains(testparkingspot2));
        assertTrue(test.getParkingspots().contains(testparkingspot3));
    }

    @Test
    public void testListParkingSpotBase() {
        assertEquals("", test.listParkingSpots());
    }

    @Test
    public void testListParkingSpotSimple() {
        ParkingSpot testparkingspot = new ParkingSpot("T1F", "medium", 4);
        test.addParkingSpot(testparkingspot);
        assertEquals("T1F, medium, rating: 4, availability: [ 0 - 23 ]\n", test.listParkingSpots());
    }

    @Test
    public void testListParkingSpotComplex() {
        ParkingSpot testparkingspot1 = new ParkingSpot("T1F", "medium", 4);
        ParkingSpot testparkingspot2 = new ParkingSpot("T2F", "small", 5);
        test.addParkingSpot(testparkingspot1);
        test.addParkingSpot(testparkingspot2);
        String expected = "";
        expected += "T1F, medium, rating: 4, availability: [ 0 - 23 ]\n";
        expected += "T2F, small, rating: 5, availability: [ 0 - 23 ]\n";
        assertEquals(expected, test.listParkingSpots());
    }
}
