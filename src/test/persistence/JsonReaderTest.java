package persistence;


import model.Account;
import model.ParkingLot;
import model.ParkingSpot;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class JsonReaderTest {

    ArrayList<ParkingLot> testparkinglots;
    JsonReader test;
    JSONParser parser;

    @BeforeEach
    public void setUp() {
        testparkinglots = new ArrayList<>();
        ParkingLot pacificcenter = new ParkingLot("PacificCenter", 5);
        ParkingLot metrotown = new ParkingLot("metrotown", 4);

        for (int i = 0; i < 3; i++) {
            pacificcenter.addParkingSpot(new ParkingSpot("PC" + i, "large", 4));
            metrotown.addParkingSpot(new ParkingSpot("M" + i, "medium", 5));
        }
        testparkinglots.add(pacificcenter);
        testparkinglots.add(metrotown);

        try {
            test = new JsonReader(testparkinglots, "testaccount1");
        } catch (FileNotFoundException e) {
            fail();
        }
        parser = new JSONParser();
    }

    @Test
    public void testConstuructorInvalidFileName() {
        try {
            JsonReader nosuch = new JsonReader(testparkinglots, "nosuchfilename");
            fail();
        } catch (FileNotFoundException e) {
            //expected
        }
    }

    @Test
    public void testToAccount() {
        try {
            Reader reader = new FileReader("./data/testaccount2.json");
            Object obj = parser.parse(reader);
            JSONObject acc = (JSONObject) obj;
            Account testaccount = test.toAccount(acc);
            assertEquals("testname", testaccount.getName());
            assertEquals("testemail", testaccount.getEmail());
            assertEquals(57, testaccount.getBalance());
            assertEquals(2, testaccount.getReservations().size());
        } catch (FileNotFoundException e) {
            fail("filenoutfound exception caught");
        } catch (ParseException e) {
            fail("parseexception exception caught");
        } catch (IOException e) {
            fail("ioexception exception caught");
        }
    }

    @Test
    public void testFindall() {
        try {
            ArrayList<Account> expected = test.findall();
            Account acc1 = expected.get(0);
            Account acc2 = expected.get(1);
            assertEquals("testname", acc1.getName());
            assertEquals("testemail", acc1.getEmail());
            assertEquals(57, acc1.getBalance());
            assertEquals(1, acc1.getReservations().size());
            assertEquals("secondname", acc2.getName());
            assertEquals("secondemail", acc2.getEmail());
            assertEquals(60,acc2.getBalance());
            assertEquals(0, acc2.getReservations().size());
        } catch (IOException | ParseException e) {
            fail("unexpected exception");
        }
    }

    @Test
    public void testfindParkingSpotNull() {
        ParkingSpot testspot = test.findParkingSpot("nosuchaccount");
        assertNull(testspot);
    }
}
