package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ParkingSpotTest {

    ParkingSpot test;
    Account testAccount;

    @BeforeEach
    public void setUp(){
        test = new ParkingSpot("T1F", "medium", 4);
    }

    @Test
    public void testPrintStats() {
        assertEquals("T1F, medium, rating: 4, availability: [ 0 - 23 ]", test.printStats());
    }

    @Test
    public void testConstructor() {
        assertEquals("T1F", test.getCodename());
        assertEquals("medium", test.getSize());
        assertEquals(4, test.getRating());
        assertEquals(0, test.reservationSize());
    }

    @Test
    public void testSetReservationEmpty(){
        Reservation testreservation = new Reservation(test, testAccount, 5, 3);
        assertTrue(test.setReservation(testreservation));
        assertTrue(test.isReserved(5));
        assertTrue(test.isReserved(6));
        assertTrue(test.isReserved(7));
        assertEquals(3, test.reservationSize());
    }

    @Test
    public void testSetReservationFull(){
        Reservation testreservation = new Reservation(test, testAccount, 5, 3);
        test.setReservation(testreservation);
        Reservation anotherreservation = new Reservation(test, testAccount, 5, 3);
        assertFalse(test.setReservation(anotherreservation));
    }

    @Test
    public void testSetReservationNotAllConflict(){
        Reservation testreservation = new Reservation(test, testAccount, 5, 3);
        test.setReservation(testreservation);
        Reservation anotherreservation = new Reservation(test, testAccount, 4, 2);
        assertFalse(test.setReservation(anotherreservation));
    }

    @Test
    public void testIsAvailableReservation() {
        Reservation testreservation = new Reservation(test, testAccount, 5, 3);
        test.setReservation(testreservation);
        assertFalse(test.isAvailable(testreservation));
        Reservation different = new Reservation(test,testAccount, 1, 2);
        assertTrue(test.isAvailable(different));
        Reservation union = new Reservation(test,testAccount,4,3);
        assertFalse(test.isAvailable(union));
    }

    @Test
    public void testIsReserved() {
        Reservation testreservation = new Reservation(test, testAccount, 5, 3);
        test.setReservation(testreservation);
        assertFalse(test.isReserved(4));
        assertTrue(test.isReserved(5));
        assertTrue(test.isReserved(6));
        assertTrue(test.isReserved(7));
        assertFalse(test.isReserved(8));
    }

    @Test
    public void testWhenAvailableBase() {
        assertEquals("[ 0 - 23 ]", test.whenAvailable());
    }
    @Test
    public void testWhenAvailableSimple() {
        Reservation testreservation = new Reservation(test, testAccount, 5, 3);
        test.setReservation(testreservation);
        assertEquals("[ 0 - 4 ], [ 8 - 23 ]", test.whenAvailable());
    }

    @Test
    public void testWhenAvailableComplex() {
        Reservation testreservation1 = new Reservation(test, testAccount, 5, 3);
        Reservation testreservation2 = new Reservation(test, testAccount, 12, 2);
        Reservation testreservation3 = new Reservation(test, testAccount, 18, 3);
        test.setReservation(testreservation1);
        test.setReservation(testreservation2);
        test.setReservation(testreservation3);
        assertEquals("[ 0 - 4 ], [ 8 - 11 ], [ 14 - 17 ], [ 21 - 23 ]", test.whenAvailable());
    }

    @Test
    public void testWhenAvailableAnHourDuration() {
        Reservation testreservation1 = new Reservation(test, testAccount, 5, 3);
        Reservation testreservation2 = new Reservation(test, testAccount, 9, 1);
        Reservation testreservation3 = new Reservation(test, testAccount, 18, 3);
        test.setReservation(testreservation1);
        test.setReservation(testreservation2);
        test.setReservation(testreservation3);
        assertEquals("[ 0 - 4 ], [ 8 ], [ 10 - 17 ], [ 21 - 23 ]", test.whenAvailable());
    }

    @Test
    public void testGetReservations() {
        ArrayList<Reservation> reservations = new ArrayList<>();
        for (int i = 0; i < 24; i++) {
            reservations.add(null);
        }
        assertEquals(reservations, test.getReservations());
    }

}