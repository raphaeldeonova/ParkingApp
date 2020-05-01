package persistence;

import model.Account;
import model.ParkingLot;
import model.ParkingSpot;
import model.Reservation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonWriterTest {
    private JsonWriter test;
    private JsonReader checker;

    @BeforeEach
    public void setUp() {
        test = new JsonWriter("writertest");
        ArrayList<ParkingLot> testparkinglots = new ArrayList<>();
        ParkingLot pacificcenter = new ParkingLot("PacificCenter", 5);
        ParkingLot metrotown = new ParkingLot("metrotown", 4);

        for (int i = 0; i < 3; i++) {
            pacificcenter.addParkingSpot(new ParkingSpot("PC" + i, "large", 4));
            metrotown.addParkingSpot(new ParkingSpot("M" + i, "medium", 5));
        }
        testparkinglots.add(pacificcenter);
        testparkinglots.add(metrotown);
        try {
            checker = new JsonReader(testparkinglots, "writertest");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testSaveAccounts() {
        ArrayList<Account> testarray = new ArrayList<>();
        Account testaccount = new Account("deo", "deonova@gmail.com");
        testarray.add(testaccount);
        testarray.add(new Account("test", "test@gmail.com"));
        ParkingSpot pc0 = new ParkingSpot("PC0", "medium", 5);
        ParkingSpot pc2 = new ParkingSpot("PC2", "medium", 5);
        Reservation res1 = new Reservation(pc0, testaccount, 3,2);
        Reservation res2 = new Reservation(pc2, testaccount, 5,7);
        testaccount.addReservation(res1);
        testaccount.addReservation(res2);
        test.saveAccounts(testarray);
        try {
            assertEquals(testarray.toString(), checker.findall().toString());
        } catch (Exception e) {
            fail();
        }
    }
}
