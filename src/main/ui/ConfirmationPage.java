package ui;

import model.Account;
import model.ParkingLot;
import model.Reservation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ConfirmationPage extends MainPage {
    private Reservation reservation;
    private JButton yes;
    private JButton no;

    //EFFECTS: creates a page for the user to confirm his/her reservation
    public ConfirmationPage(Account account,
                            ArrayList<Account> allaccount,
                            ArrayList<ParkingLot> parkinglots,
                            Reservation reservation) {
        super(account, allaccount, parkinglots);
        this.reservation = reservation;

        loadTitle();
        loadButton();
        loadLabel();
    }

    //EFFECTS: loads title into screen
    protected void loadTitle() {
        JLabel title = new JLabel("Confirm Reservation?");
        Font font1 = new Font("Sans Serif", Font.BOLD, 30);
        title.setFont(font1);
        title.setBounds(30,10,600,60);
        add(title);
    }

    //EFFECTS: loads labels into screen
    private void loadLabel() {
        Font font1 = new Font("Sans Serif", Font.BOLD, 20);
        JLabel parkingspot = new JLabel("Parking Spot: " + reservation.getParkingSpot().getCodename());
        JLabel accountname = new JLabel("Account name: " + account.getName());
        JLabel accountemail = new JLabel("Account email: " + account.getEmail());
        JLabel time = new JLabel("Starting time: " + reservation.getTime() + ":00");
        JLabel duration = new JLabel("Duration: " + reservation.getDuration());
        parkingspot.setFont(font1);
        accountname.setFont(font1);
        accountemail.setFont(font1);
        time.setFont(font1);
        duration.setFont(font1);
        parkingspot.setBounds(50,100,500,50);
        accountname.setBounds(50,150, 500, 50);
        accountemail.setBounds(50,200,500,50);
        time.setBounds(50,250,500,50);
        duration.setBounds(50,300,500,50);
        add(parkingspot);
        add(accountname);
        add(accountemail);
        add(time);
        add(duration);
    }

    //MODIFIES: this
    //EFFECTS: loads buttons into screen
    private void loadButton() {
        yes = new JButton("Yes");
        yes.setBounds(500,100,200,80);
        yes.addActionListener(new ButtonHandler());
        add(yes);
        no = new JButton("No");
        no.setBounds(500,200,200,80);
        no.addActionListener(new ButtonHandler());
        add(no);
    }

    //A class for handling button press. (login button and create account button)
    private class ButtonHandler implements ActionListener {
        //EFFECTS: handle every button action in this page
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == no) {
                setVisible(false);
                new MainMenu(account, allaccount, parkinglots);
            } else if (e.getSource() == yes) {
                if (account.getBalance() >= reservation.getPrice()) {
                    account.addReservation(reservation);
                    reservation.getParkingSpot().setReservation(reservation);
                    account.withdraw(reservation.getPrice());
                    setVisible(false);
                    new MainMenu(account, allaccount, parkinglots);
                } else {
                    JLabel invalid = new JLabel("Insufficient Balance");
                    invalid.setBounds(400,300,200,100);
                    add(invalid);
                    refresh();
                }
            }
        }

        //EFFECTS: updates the page from any changes
        private void refresh() {
            setVisible(false);
            setVisible(true);
        }
    }
}
