package ui;

import model.Account;
import model.ParkingLot;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ReservePage extends MainPage {

    private JList<String> list;
    private JButton pick;
    private JButton mainmenu;

    //EFFECTS: creates a page for the user to pick parking lot for his/her reservation
    public ReservePage(Account account, ArrayList<Account> allaccount, ArrayList<ParkingLot> parkinglots) {
        super(account, allaccount, parkinglots);
        loadTitle();
        loadList();
        loadButton();
    }

    //EFFECTS: loads title into screen
    protected void loadTitle() {
        JLabel title = new JLabel("Pick a Destination!");
        Font font1 = new Font("Sans Serif", Font.BOLD, 60);
        title.setFont(font1);
        title.setBounds(0,30,600,60);
        add(title);
    }

    //MODIFIES: this
    //EFFECTS: loads list into screen
    private void loadList() {
        DefaultListModel<String> l1 = new DefaultListModel<>();
        for (ParkingLot pl: parkinglots) {
            l1.addElement(pl.getName() + ", " + pl.getPrice() + "$/hour");
        }
        list = new JList<>(l1);
        list.setBounds(100,130,400,200);
        list.setFixedCellHeight(100);
        list.setFont(new Font("Sans Serif",Font.BOLD,32));
        add(list);
    }

    //MODIFIES: this
    //EFFECTS: loads buttons into screen
    private void loadButton() {
        pick = new JButton("Pick");
        pick.setBounds(520, 170, 200,70);
        pick.addActionListener(new ButtonHandler());
        add(pick);
        mainmenu = new JButton("Back");
        mainmenu.setBounds(100, 350, 200, 70);
        mainmenu.addActionListener(new ButtonHandler());
        add(mainmenu);
    }

    //EFFECTS: finds a parkinglot from given name
    private ParkingLot findParkingLot(String name) {
        for (ParkingLot pl: parkinglots) {
            if (pl.getName().equals(name)) {
                return pl;
            }
        }
        return null;
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
                    String selectedname = list.getSelectedValue().split(",")[0];
                    ParkingLot parkinglot = findParkingLot(selectedname);
                    setVisible(false);
                    new PickParkingSpotPage(account, allaccount, parkinglots, parkinglot);
                }
            }
        }
    }
}
