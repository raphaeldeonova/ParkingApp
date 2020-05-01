package persistence;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;

import model.Account;
import model.ParkingLot;
import model.ParkingSpot;
import model.Reservation;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JsonReader {
    private String filepath;
    private ArrayList<ParkingLot> allparkinglot;
    private JSONParser parser;
    private Reader reader;

    //EFFECTS: create a JsonReader with "./data/" default filepath, all parkinglots, and a parser
    public JsonReader(ArrayList<ParkingLot> allparkinglot, String filename) throws FileNotFoundException {
        this.filepath = "./data/" + filename + ".json";
        this.allparkinglot = allparkinglot;
        parser = new JSONParser();
        reader = new FileReader(filepath);

    }

    //EFFECTS: returns an arraylist of all account
    public ArrayList<Account> findall() throws IOException, ParseException {
        Object obj = parser.parse(reader);
        JSONObject acc = (JSONObject) obj;
        JSONArray accs = (JSONArray) acc.get("accounts");

        ArrayList<Account> rsf = new ArrayList<>();

        for (JSONObject currentaccount : (Iterable<JSONObject>) accs) {
            rsf.add(toAccount(currentaccount));
        }
        return rsf;
    }

    //REQUIRES: object has to be in the right format
    //EFFECTS: returns the appropriate account of the object
    public Account toAccount(JSONObject object) {
        String name = (String) object.get("name");
        String email = (String) object.get("email");
        int balance = ((Long) object.get("balance")).intValue();
        Account output = new Account(name, email);
        output.deposit(balance);
        JSONArray reservations = (JSONArray) object.get("reservations");
        for (Object o: reservations) {
            int intendedtime = ((Long) ((JSONObject) o).get("time")).intValue();
            int intendedduration = ((Long) ((JSONObject) o).get("duration")).intValue();
            String intendedps = (String) ((JSONObject) o).get("parkingspot");
            ParkingSpot ps = findParkingSpot(intendedps);
            Reservation res = new Reservation(ps, output, intendedtime, intendedduration);
            output.addReservation(res);
            ps.setReservation(res);
        }
        return output;
    }

    //REQUIRES: codename is a valid codename of a parkingspot
    //EFFECTS: returns the parkingspot of that codename from all parkingspot
    public ParkingSpot findParkingSpot(String codename) {
        for (ParkingLot pl : allparkinglot) {
            for (ParkingSpot ps: pl.getParkingspots()) {
                if (codename.equals(ps.getCodename())) {
                    return ps;
                }
            }
        }
        return null;
    }
}
