package model;

import java.util.ArrayList;

public class ParkingSpot {

    private String codename;
    private int price;
    private ParkingLot parkingLot;

    //Size is one-of:
    //  -"small"
    //  -"medium"
    //  -"big"
    private String size;

    //Rating is in range [1 - 5]
    private int rating;

    private ArrayList<Reservation> reservations;

    //EFFECTS: make a new parking spot with the codename, size, rating, empty reservations
    public ParkingSpot(String codename, String size, int rating) {
        this.codename = codename;
        this.size = size;
        this.rating = rating;
        reservations = new ArrayList<>();
        for (int i = 0; i < 24; i++) {
            reservations.add(null);
        }
    }

    //MODIFIES: this
    //EFFECTS: sets the parkingLot and its price to this
    public void setParkingLot(ParkingLot parkingLot) {
        this.parkingLot = parkingLot;
        this.price = this.parkingLot.getPrice();
        parkingLot.addParkingSpot(this);
    }

    public int getPrice() {
        return price;
    }

    //MODIFIES: this
    //EFFECTS: if already reserved, at such time, return false, else, make such reservation, and return true
    public boolean setReservation(Reservation reservation) {
        for (int i = reservation.getTime(); i < reservation.getTime() + reservation.getDuration(); i++) {
            if (isReserved(i)) {
                return false;
            } else {
                reservations.set(i,reservation);
            }
        }
        return true;
    }

    //EFFECTS: return true if there is already a reservation at time t;
    public boolean isReserved(int t) {
        return reservations.get(t) != null;
    }

    //EFFECTS: prints out the codename, availability, size, and rating
    public String printStats() {
        String output = "";
        output += this.codename + ", ";
        output += this.size + ", ";
        output += "rating: " + this.rating + ", ";
        output += "availability: " + this.whenAvailable();

        return output;
    }

    //EFFECTS: returns the number of actual reservations (not null)
    public int reservationSize() {
        int count = 0;
        for (Reservation current: reservations) {
            if (current != null) {
                count += 1;
            }
        }
        return count;
    }

    //EFFECTS: returns the time available for reservation
    //         in the form : "[0 - 1], [9 - 17], [8]"
    public String whenAvailable() {
        boolean flag = false;
        int min = 0;
        int max;
        String output = "";
        for (int i = 0; i < 24; i++) {
            if (!flag && reservations.get(i) == null) {
                flag = true;
                min = i;
            }
            if (flag && (i == 23 || reservations.get(i + 1) != null)) {
                flag = false;
                max = i;
                if (min == max) {
                    output += "[ " + max + " ], ";
                } else {
                    output += "[ " + min + " - " + max + " ], ";
                }
            }
        }
        return output.substring(0, output.length() - 2);
    }

    //EFECTS: return true if that time and duration is available
    public boolean isAvailable(Reservation reservation) {
        for (int i = reservation.getTime(); i < reservation.getTime() + reservation.getDuration(); i++) {
            if (isReserved(i)) {
                return false;
            }
        }
        return true;
    }

    public int getRating() {
        return rating;
    }

    public String getCodename() {
        return codename;
    }

    public String getSize() {
        return size;
    }

    public ArrayList<Reservation> getReservations() {
        return reservations;
    }
}
