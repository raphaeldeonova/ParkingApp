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

public class ViewPage extends MainPage {
    private JTable table;
    private JButton mainmenu;
    private JButton cancel;

    //EFFECTS: creates a page that lets the user view his/her reservations
    public ViewPage(Account account, ArrayList<Account> allaccounts, ArrayList<ParkingLot> parkinglots) {
        super(account, allaccounts, parkinglots);
        loadTitle();
        loadTable();
        loadButton();
        loadLabel();
    }

    @Override
    //EFFECTS: loads titles into screen
    protected void loadTitle() {
        JLabel title = new JLabel(account.getName() + "'s reservations");
        Font font1 = new Font("Sans Serif", Font.BOLD, 60);
        title.setFont(font1);
        title.setBounds(30,10,600,60);
        add(title);
    }

    //EFFECTS: loads labels into screen
    private void loadLabel() {
        JLabel cancelreservation = new JLabel("Cancel any reservation? (pick id)");
        cancelreservation.setBounds(550, 100, 400, 50);
        add(cancelreservation);
    }

    //MODIFIES: this
    //EFFECTS: loads labels into screen
    private void loadTable() {
        String[] column = {"ID", "CODENAME", "TIME", "DURATION"};
        Object[][] rows = parseReservation();
        table = new JTable(rows, column);
        table.setRowHeight(30);
        JScrollPane panel = new JScrollPane(table);
        panel.setBounds(50,100,400,200);
        add(panel);
    }

    //MODIFIES: this
    //EFFECTS: loads buttons into screen
    private void loadButton() {
        mainmenu = new JButton("Back");
        mainmenu.setBounds(50,350,200,50);
        mainmenu.addActionListener(new ButtonHandler());
        add(mainmenu);

        cancel = new JButton("Cancel");
        cancel.setBounds(550,220,100,50);
        cancel.addActionListener(new ButtonHandler());
        add(cancel);
    }

    //EFFECTS: parses the reservation into readable format to choose
    private String[][] parseReservation() {
        String[][] data = new String[account.getReservations().size()][5];
        int id = 1;
        for (Reservation current: account.getReservations()) {
            String[] curparse = {
                    Integer.toString(id),
                    current.getParkingSpot().getCodename(),
                    Integer.toString(current.getTime()),
                    Integer.toString(current.getDuration())
            };
            data[id - 1] = curparse;
            id++;
        }
        return data;
    }

    //A class for handling button press. (login button and create account button)
    private class ButtonHandler implements ActionListener {
        //EFFECTS: handle every button action in this page
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == mainmenu) {
                setVisible(false);
                new MainMenu(account, allaccount, parkinglots);
            } else if (e.getSource() == cancel) {
                if (table.getSelectedRow() != -1) {
                    int row = table.getSelectedRow();
                    Integer id = Integer.parseInt((String) table.getModel().getValueAt(row, 0));
                    Reservation selectedreservation = account.getReservations().get(id - 1);
                    account.removeReservation(selectedreservation);
                    loadTable();
                }
            }
        }
    }
}
