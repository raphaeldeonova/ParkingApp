package ui;

import exception.AccountNotFoundException;
import model.Account;
import model.ParkingLot;
import model.ParkingSpot;
import model.Reservation;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import persistence.JsonReader;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class ParkingApp {
    private Scanner input;
    private ArrayList<Account> accounts;
    private ArrayList<ParkingLot> parkinglots;

    //temporary fields
    private Account pickedAccount;
    private ParkingLot pickedparkinglot;
    private ParkingSpot pickedparkingspot;
    private int pickedtime;
    private int pickedduration;
    private Reservation currentReservation;

    //EFFECT: runs the ParkingApp
    public ParkingApp() {
        runApp();
    }

    //EFFECTS: runs the main menu of the Parking App
    private void runApp() {
        parkinglots = new ArrayList<>();
        accounts = new ArrayList<>();
        input = new Scanner(System.in);
        loadParkingLots();
        loadAccounts();

        processLoginCommand();
        processMainMenuCommand();

        saveAccounts();
        System.out.println("\ngoodbye!");
    }

    private void processLoginCommand() {
        System.out.println("Welcome to the Parking App!");

        System.out.println("Enter your name: ");
        String name = input.next();
        System.out.println("Enter your email: ");
        String email = input.next();
        try {
            pickedAccount = findAccount(name, email);
        } catch (AccountNotFoundException e) {
            pickedAccount = new Account(name, email);
            accounts.add(pickedAccount);
            System.out.println("New account made");
        }
    }

    //EFFECTS: prompts the user to pick a task
    private void processMainMenuCommand() {
        String command;
        boolean keepgoing = true;
        while (keepgoing) {

            System.out.println("Hello " + pickedAccount.getName());
            System.out.println("Your balance: " + pickedAccount.getBalance());
            System.out.println("What would you like to do? (quit/reserve/view/deposit)");

            command = input.next();
            command = command.toLowerCase();

            if (command.equals("quit")) {
                keepgoing = false;
            } else if (command.equals("reserve") || command.equals("view") || command.equals("deposit")) {
                processCommand(command);
            } else {
                System.out.println("Invalid command, try again");
            }
        }
    }

    //EFFECTS: runs the appropriate task corresponding to given command.
    private void processCommand(String command) {
        switch (command) {
            case "reserve":
                doReserve();
                break;
            case "view":
                doView();
                break;
            case "deposit":
                doDeposit();
                break;
        }
    }

    //EFFECTS: makes the app prompts the user to deposit some amount to pickedaccount
    private void doDeposit() {
        System.out.println("How much do you want to deposit?");
        boolean keepgoing = true;
        int amount = -1;
        while (keepgoing) {
            amount = input.nextInt();
            if (amount > 0) {
                keepgoing = false;
                pickedAccount.deposit(amount);
            }
        }
        System.out.println("deposited " + amount + " $");
    }

    //EFFECTS: makes the app display current reservations, and prompts the user to cancel a reservation.
    private void doView() {
        System.out.println(pickedAccount.toString());
        System.out.println("Cancel any reservation? (y/n)");

        String command;
        command = input.next();
        if (command.toLowerCase().equals("y")) {
            processCancelReservation();
        }
    }

    //EFFECTS: prompts the user to pick a reservation to cancel
    private void processCancelReservation() {
        System.out.println("Which reservation would you want to cancel? (the number associated to the reservation)");

        boolean keepgoing = true;
        int command = 0;
        while (keepgoing) {
            command = input.nextInt();
            if (command > 0 && command < pickedAccount.getReservations().size() + 1) {
                keepgoing = false;
            } else {
                System.out.println("Invalid number, try again.");
            }
        }
        pickedAccount.getReservations().get(command - 1).cancel();
        pickedAccount.getReservations().remove(command - 1);
        System.out.println("Reservation Cancelled");
    }

    //EFFECTS: makes the app do a reservation for the user
    private void doReserve() {
        pickParkingLot();
        pickParkingSpot();
        pickTime();
        pickDuration();
        int amount = pickedparkinglot.getPrice() * pickedduration;
        if (pickedAccount.getBalance() >= amount) {
            currentReservation = new Reservation(pickedparkingspot, pickedAccount, pickedtime, pickedduration);
            validateReservation(currentReservation);
        } else {
            System.out.println("Insufficient balance");
        }
    }

    //EFFECTS: prompts the user to pick a duration
    private void pickDuration() {
        boolean keepgoing;
        System.out.println("for how long?");
        keepgoing = true;
        while (keepgoing) {
            int temp = input.nextInt();
            if (temp <= 0 || temp + pickedtime >= 24) {
                System.out.println("Invalid duration");
            } else {
                pickedduration = temp;
                keepgoing = false;
            }
        }
    }

    //EFFECTS: prompts the user to pick a time
    private void pickTime() {
        System.out.println("what time would you like to reserve?");
        boolean keepgoing = true;
        int time;
        while (keepgoing) {
            time = input.nextInt();
            if (time < 0 || time > 23) {
                System.out.println("Invalid time, try again.");
            } else {
                pickedtime = time;
                keepgoing = false;
            }
        }
    }

    //EFFECTS: prompts the user to pick a parking spot
    private void pickParkingSpot() {
        System.out.println("These are the available parking spots to choose in " + pickedparkinglot.getName() + ":");
        System.out.println(pickedparkinglot.listParkingSpots());
        boolean keepgoing = true;
        String command;
        while (keepgoing) {
            System.out.println("Choose the codename for your intended parking spot: ");
            command = input.next();
            for (ParkingSpot ps : pickedparkinglot.getParkingspots()) {
                if (command.toLowerCase().equals(ps.getCodename().toLowerCase())) {
                    pickedparkingspot = ps;
                    keepgoing = false;
                }
            }
            if (keepgoing) {
                System.out.println("Invalid codename, try again.");
            }
        }
    }

    //EFFECTS: prompts the user to pick a parking lot
    private void pickParkingLot() {
        System.out.println("Pick your destination: ");
        listParkingLots();

        boolean keepgoing = true;
        while (keepgoing) {
            String command;
            command = input.next();

            for (ParkingLot pl: parkinglots) {
                if (command.toLowerCase().equals(pl.getName().toLowerCase())) {
                    pickedparkinglot = pl;
                    keepgoing = false;
                }
            }
            if (keepgoing) {
                System.out.println("Invalid destination, pick again.");
            }
        }
    }

    //EFFECTS: prompts the user to confirm his/her reservation
    private void validateReservation(Reservation reservation) {
        System.out.println("Your reservation:");
        System.out.println(reservation.toString());
        System.out.println("confirm reservation? (y/n)");
        String command = input.next();
        if (command.toLowerCase().equals("y")) {
            pickedAccount.addReservation(reservation);
            if (pickedparkingspot.isAvailable(currentReservation)) {
                pickedparkingspot.setReservation(currentReservation);
                pickedAccount.withdraw(pickedparkinglot.getPrice() * reservation.getDuration());
            } else {
                System.out.println("There is already a reservation at that time!");
            }
        }
    }

    //EFFECTS: lists all name of available parking lots
    private void listParkingLots() {
        for (ParkingLot pl: parkinglots) {
            System.out.println(pl.getName() + " | price: " + pl.getPrice() + " /hour");
        }
    }

    //EFFECTS: loads all parkinglots into the app (beta: metrotown and pacific center)
    private void loadParkingLots() {
        ParkingLot metrotown = new ParkingLot("Metrotown", 4);
        ParkingLot pacificcenter = new ParkingLot("PacificCenter", 5);

        for (int i = 0; i < 3; i++) {
            metrotown.addParkingSpot(new ParkingSpot("M" + i, "medium", 5));
            pacificcenter.addParkingSpot(new ParkingSpot("PC" + i, "large", 4));
        }

        parkinglots.add(metrotown);
        parkinglots.add(pacificcenter);
    }

    //EFFECTS: saves all accounts in the app including any changes in current attempt
    @SuppressWarnings("unchecked")
    private void saveAccounts() {
        JSONObject output = new JSONObject();
        JSONArray allaccounts = new JSONArray();
        for (Account account: accounts) {
            if (account.getName().equals(pickedAccount.getName())) {
                allaccounts.add(pickedAccount.toJsonObject());
            } else {
                allaccounts.add(account.toJsonObject());
            }
        }
        output.put("accounts", allaccounts);

        try {
            FileWriter file = new FileWriter("./data/account.json");
            file.write(output.toJSONString());
            file.flush();
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //EFFECTS: loads all accounts from account.json
    private void loadAccounts() {
        JsonReader jsonReader = null;
        try {
            jsonReader = new JsonReader(parkinglots, "account");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            accounts = jsonReader.findall();
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    //EFFECTS: finds an account with given name and email in all accounts.
    //         if no such account is found, throw an AccountNotFoundException.
    private Account findAccount(String name, String email) throws AccountNotFoundException {
        for (Account current: accounts) {
            if (name.equals(current.getName()) && email.equals(current.getEmail())) {
                return current;
            }
        }
        throw new AccountNotFoundException();
    }
}
