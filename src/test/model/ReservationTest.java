package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ReservationTest {
    public Reservation test;
    public ParkingSpot testparkingspot;

    @BeforeEach
    public void setUp() {
        ParkingLot testparkinglot = new ParkingLot("test", 5);
        testparkingspot = new ParkingSpot("T1F", "medium", 4);
        testparkingspot.setParkingLot(testparkinglot);
        Account testaccount = new Account("test", "test@gmail.com");
        test = new Reservation(testparkingspot, testaccount, 5, 3);
        testparkingspot.setReservation(test);
    }

    @Test
    public void testGetPrice() {
        assertEquals(15, test.getPrice());
    }

    @Test
    public void testConstructor() {
        assertTrue(test.getParkingSpot().equals(testparkingspot));
        assertEquals("test", test.getUsername());
        assertEquals("test@gmail.com", test.getUserEmail());
        assertEquals(5, test.getTime());
        assertEquals(3, test.getDuration());
    }

    @Test
    public void testPrint(){
        assertEquals("Name: test\nEmail: test@gmail.com\nParking Spot: T1F\nTime: 5\nDuration: 3\n\n", test.toString());
    }

    @Test
    public void testCancel() {
        test.cancel();
        for (Reservation res: testparkingspot.getReservations()) {
            assertNull(res);
        }
    }
}
