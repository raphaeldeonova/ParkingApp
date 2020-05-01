package model;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileReader;
import java.io.Reader;

import static org.junit.jupiter.api.Assertions.*;

public class AccountTest {
    public Account test;

    @BeforeEach
    public void setUp() {
        test = new Account("test", "test@gmail.com");
    }

    @Test
    public void testConstructor() {
        assertEquals("test", test.getName());
        assertEquals("test@gmail.com", test.getEmail());
        assertEquals(0, test.getBalance());
        assertEquals(0, test.getReservations().size());
    }

    @Test
    public void testDeposit() {
        test.deposit(3);
        assertEquals(3, test.getBalance());
    }

    @Test
    public void testWithdraw() {
        test.deposit(3);
        test.withdraw(2);
        assertEquals(1, test.getBalance());
    }

    @Test
    public void testToStringEmpty() {
        String expected = "test's account:\nbalance: 0\nReservations:\n";
        assertEquals(expected, test.toString());
    }

    @Test
    public void testToStringSingleReservation() {
        ParkingSpot ps = new ParkingSpot("testm0", "medium", 5);
        Reservation res = new Reservation(ps, test, 5, 3);
        test.addReservation(res);
        String expected = "test's account:\nbalance: 0\nReservations:\n";
        expected += "1)\n";
        expected += res.toString();
        assertEquals(expected, test.toString());
    }

    @Test
    public void testRemoveReservation() {
        ParkingSpot ps = new ParkingSpot("testm0", "medium", 5);
        Reservation res = new Reservation(ps, test, 5, 3);
        test.addReservation(res);
        test.removeReservation(res);
        assertFalse(test.getReservations().contains(res));
    }

    @Test
    public void testToJSONObject() {
        Account savetest = new Account("testname", "testemail");
        savetest.deposit(57);
        ParkingSpot pc0 = new ParkingSpot("PC0", "medium", 5);
        ParkingSpot pc2 = new ParkingSpot("PC2", "medium", 5);
        Reservation res1 = new Reservation(pc0, savetest, 3,2);
        Reservation res2 = new Reservation(pc2, savetest, 5,7);
        savetest.addReservation(res2);
        savetest.addReservation(res1);
        JSONObject result = savetest.toJsonObject();

        JSONParser parser = new JSONParser();
        try {
            Reader reader = new FileReader("./data/testaccount2.json");
            Object obj = parser.parse(reader);
            JSONObject expected = (JSONObject) obj;
            assertEquals(expected.toJSONString(), result.toJSONString());
        } catch (Exception e) {
            fail("unexpected exception caught");
        }
    }
}
