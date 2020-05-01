package model;

import java.util.ArrayList;

public class AccountReservationManager {

    private ArrayList<Reservation> reservations;

    public AccountReservationManager() {
        this.reservations = new ArrayList<>();
    }

    //MODIFIES: this
    //EFFECTS: add a reservation to the reservations list
    public void addReservation(Reservation reservation) {
        this.reservations.add(reservation);
    }

    //MODIFIES: this
    //EFFECTS: removes the reservation from all reservations
    public void removeReservation(Reservation reservation) {
        this.reservations.remove(reservation);
        reservation.cancel();
    }

    public ArrayList<Reservation> getReservations() {
        return this.reservations;
    }
}
