package model;

import java.util.ArrayList;

public class ParkingLot {
    private String name;
    private int price;
    private ArrayList<ParkingSpot> parkingspots;

    //MODIFIES: this
    //EFFECTS: initialize a parking lot with empty list of parking spots ,pr price (dollars/hour), and name
    public ParkingLot(String name, int pr) {
        parkingspots = new ArrayList<>();
        this.price = pr;
        this.name = name;
    }

    //MODIFIES: this
    //EFFECTS: add a Parking Spot to this
    public void addParkingSpot(ParkingSpot parkingSpot) {
        if (!this.parkingspots.contains(parkingSpot)) {
            parkingspots.add(parkingSpot);
            parkingSpot.setParkingLot(this);
        }
    }

    //EFFECTS: list out parking spots
    public String listParkingSpots() {
        String output = "";
        for (ParkingSpot parking: parkingspots) {
            output += parking.printStats();
            output += "\n";
        }
        return output;
    }

    public String getName() {
        return name;
    }

    public ArrayList<ParkingSpot> getParkingspots() {
        return parkingspots;
    }

    public int getPrice() {
        return price;
    }
}
