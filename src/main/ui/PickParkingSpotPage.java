package ui;

import model.Account;
import model.ParkingLot;
import model.ParkingSpot;
import model.Reservation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class PickParkingSpotPage extends MainPage {
    private static String[] availabletime = {
            "0", "1", "2", "3", "4", "5", "6",
            "7", "8", "9", "10", "11", "12",
            "13", "14", "15", "16", "17", "18",
            "19", "20", "21", "22", "23"
    };
    private static String[] availableduration = {
            "1", "2", "3", "4", "5", "6"
    };
    private ParkingLot pickedparkinglot;
    private JList<String> list;
    private JButton mainmenu;
    private JButton pick;
    private JComboBox time;
    private JComboBox duration;

    //EFFECTS: creates a page for the user to pick a parking spot of their choice
    public PickParkingSpotPage(Account account,
                               ArrayList<Account> allaccount,
                               ArrayList<ParkingLot> parkinglots,
                               ParkingLot pickedparkinglot) {
        super(account, allaccount, parkinglots);
        this.pickedparkinglot = pickedparkinglot;

        loadTitle();
        loadList();
        loadButton();
        loadComboBox();
        loadLabel();
    }

    //EFFECTS: loads title into screen
    protected void loadTitle() {
        JLabel title = new JLabel("Pick a Parking Spot in " + pickedparkinglot.getName());
        Font font1 = new Font("Sans Serif", Font.BOLD, 30);
        title.setFont(font1);
        title.setBounds(30,10,600,60);
        add(title);
    }

    //EFFECTS: loads label into screen
    private void loadLabel() {
        JLabel time = new JLabel("Time:");
        time.setBounds(520,60,100,50);
        add(time);
        JLabel duration = new JLabel("Duration:");
        duration.setBounds(600,60,100,50);
        add(duration);
    }

    //MODIFIES: this
    //EFFECTS: loads list into screen
    private void loadList() {
        DefaultListModel<String> l1 = new DefaultListModel<>();
        for (ParkingSpot ps: pickedparkinglot.getParkingspots()) {
            l1.addElement(ps.printStats());
        }
        list = new JList<>(l1);
        list.setBounds(100,100,400,200);
        list.setFixedCellHeight(50);
        list.setFont(new Font("Sans Serif",Font.BOLD,16));
        add(list);
    }

    //MODIFIES: this
    //EFFECTS: loads buttons into screen
    private void loadButton() {
        mainmenu = new JButton("Back");
        mainmenu.setBounds(100,320, 200, 80);
        mainmenu.addActionListener(new ButtonHandler());
        add(mainmenu);
        pick = new JButton("Pick");
        pick.setBounds(520,200,200,80);
        pick.addActionListener(new ButtonHandler());
        add(pick);
    }

    //MODIFIES: this
    //EFFECTS: loads combo box into screen
    private void loadComboBox() {
        time = new JComboBox(availabletime);
        time.setBounds(520, 100, 50, 50);
        add(time);
        duration = new JComboBox(availableduration);
        duration.setBounds(600,100,50,50);
        add(duration);
    }

    //A class used to handle button press in Deposit Page
    private class ButtonHandler implements ActionListener {

        @Override
        //EFFECTS: handle every button action in this page
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == mainmenu) {
                setVisible(false);
                new MainMenu(account, allaccount, parkinglots);
            } else if (e.getSource() == pick) {
                if (list.getSelectedIndex() != -1) {
                    int selectedtime = Integer.parseInt((String) time.getSelectedItem());
                    int selectedduration = Integer.parseInt((String) duration.getSelectedItem());
                    ParkingSpot ps = findParkingSpot(list.getSelectedValue());
                    assert (ps != null);
                    Reservation reservation = new Reservation(ps, account, selectedtime, selectedduration);
                    if (ps.isAvailable(reservation)) {
                        setVisible(false);
                        new ConfirmationPage(account, allaccount, parkinglots, reservation);
                    } else {
                        JLabel invalid = new JLabel("Time and duration is invalid");
                        invalid.setBounds(450,300,200,100);
                        add(invalid);
                        refresh();
                    }
                }
            }
        }

        //EFFECTS: updates the page regarding any changes made
        private void refresh() {
            setVisible(false);
            setVisible(true);
        }

        //EFFECTS: finds the parking spot associated with string
        private ParkingSpot findParkingSpot(String parsedString) {
            String name = parsedString.split(",")[0];
            for (ParkingSpot ps: pickedparkinglot.getParkingspots()) {
                if (name.equals(ps.getCodename())) {
                    return ps;
                }
            }
            return null;
        }
    }
}