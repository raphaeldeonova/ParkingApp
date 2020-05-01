package model;

public class Reservation {
    private Account account;
    private ParkingSpot parkingSpot;
    private int time;
    private int duration; //hours
    private int price;

    // REQUIRES: 0 <= time <= 23, and duration >0 and duration + time >= 24
    // EFFECTS: makes a reservation with a parkingspot, username, email, time of reservation, and duration
    public Reservation(ParkingSpot parkingSpot, Account account, int time, int duration) {
        this.parkingSpot = parkingSpot;
        this.account = account;
        this.time = time;
        this.duration = duration;
        this.price = parkingSpot.getPrice() * this.duration;
    }

    @Override
    //EFFECTS: returns reservation stats
    public String toString() {
        String output = "";
        output += "Name: " + getUsername() + "\n";
        output += "Email: " + getUserEmail() + "\n";
        output += "Parking Spot: " + this.parkingSpot.getCodename() + "\n";
        output += "Time: " + getTime() + "\n";
        output += "Duration: " + getDuration() + "\n";
        output += "\n";
        return output;
    }

    @Override
    public int hashCode() {
        int output = 39;
        output += 17 * parkingSpot.hashCode();
        output += 17 * account.hashCode();
        output += 17 * time + 19 * duration;
        return output;
    }

    //MODIFIES: this
    //EFFECTS: cancel the reservation from the parking spot.
    public void  cancel() {
        for (int i = time; i < time + duration; i++) {
            this.parkingSpot.getReservations().set(i, null);
        }
    }

    public int getTime() {
        return time;
    }

    public int getDuration() {
        return duration;
    }

    public ParkingSpot getParkingSpot() {
        return parkingSpot;
    }

    public String getUserEmail() {
        return this.account.getEmail();
    }

    public String getUsername() {
        return this.account.getName();
    }

    public int getPrice() {
        return price;
    }
}
