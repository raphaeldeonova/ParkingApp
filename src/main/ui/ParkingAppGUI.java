package ui;

import model.Account;
import model.ParkingLot;
import model.ParkingSpot;
import model.Reservation;
import org.json.simple.parser.ParseException;
import persistence.JsonReader;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class ParkingAppGUI {
    public static final int WIDTH = 800;
    public static final int HEIGHT = 500;
    private ArrayList<ParkingLot> parkinglots;
    private ArrayList<Account> allaccount;

    public ParkingAppGUI() {
        parkinglots = loadParkingLot();
        allaccount = loadAccounts();
        Account test = new Account("Deo", "deonova@gmail.com");
        ParkingSpot ps1 = new ParkingSpot("M0", "medium", 5);
        ParkingSpot ps2 = new ParkingSpot("M5", "medium", 5);
        test.addReservation(new Reservation(ps1, test, 3, 4));
        test.addReservation(new Reservation(ps2, test, 5, 7));

        ui.LoginPage loginPage = new ui.LoginPage(allaccount, parkinglots);
//        ViewPage viewpage = new ViewPage(test, allaccount, parkinglots);
//        ReservePage reservepage = new ReservePage(test, allaccount, parkinglots);
//        DepositPage depositPage = new DepositPage(test, allaccount, parkinglots);
//        MainMenu mainMenu = new MainMenu(test, allaccount, parkinglots);
    }

    private ArrayList<ParkingLot> loadParkingLot() {
        ArrayList<ParkingLot> rsf = new ArrayList<>();
        ParkingLot metrotown = new ParkingLot("Metrotown", 4);
        ParkingLot pacificcenter = new ParkingLot("PacificCenter", 5);

        for (int i = 0; i < 3; i++) {
            metrotown.addParkingSpot(new ParkingSpot("M" + i, "medium", 5));
            pacificcenter.addParkingSpot(new ParkingSpot("PC" + i, "large", 4));
        }

        rsf.add(metrotown);
        rsf.add(pacificcenter);
        return rsf;
    }

    private ArrayList<Account> loadAccounts() {
        JsonReader jsonReader = null;
        try {
            jsonReader = new JsonReader(parkinglots, "account");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ArrayList<Account> accounts = null;
        try {
            accounts = jsonReader.findall();
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return accounts;
    }
}
