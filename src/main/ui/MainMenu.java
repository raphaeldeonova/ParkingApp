package ui;

import model.Account;
import model.ParkingLot;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class MainMenu extends MainPage {
    private JButton reserve;
    private JButton view;
    private JButton deposit;
    private JButton save;

    //EFFECTS: creates a main page for the user to either reserve, view reservation, or deposit
    public MainMenu(Account account, ArrayList<Account> allaccounts, ArrayList<ParkingLot> parkinglots) {
        super(account, allaccounts, parkinglots);
        loadTitle();
        loadButtons();
        loadImage();
    }

    @Override
    //EFFECTS: loads title into screen
    protected void loadTitle() {
        JLabel title = new JLabel("Hello " + account.getName() + "!");
        Font font1 = new Font("Sans Serif", Font.BOLD, 60);
        title.setFont(font1);
        title.setBounds(30,30,600,60);
        add(title);

        JLabel balance = new JLabel("Your balance is: " + account.getBalance());
        Font font3 = new Font("Sans Serif", Font.BOLD, 15);
        balance.setFont(font3);;
        balance.setBounds(30,100, 700, 50);
        add(balance);
    }

    //MODIFIES: this
    //EFFECTS: loads buttons into screen
    private void loadButtons() {
        reserve = new JButton("Reserve");
        reserve.setBounds(70, 190, 200, 120);
        reserve.addActionListener(new ButtonHandler());
        add(reserve);
        view = new JButton("View");
        view.setBounds(300, 190, 200, 120);
        view.addActionListener(new ButtonHandler());
        add(view);
        deposit = new JButton("Deposit");
        deposit.setBounds(530,190,200,120);
        deposit.addActionListener(new ButtonHandler());
        add(deposit);
        save = new JButton("save");
        save.setBounds(70,350,100,60);
        save.addActionListener(new ButtonHandler());
        add(save);
    }

    //MODIFIES: this
    //EFFECTS: loads images into screen
    private void loadImage() {
        ImageIcon im = new ImageIcon("./data/quikpark.png");
        JLabel image = new JLabel();
        image.setIcon(im);
        image.setBounds(400,0,600,200);
        add(image);
        repaint();
    }

    //A class used to handle button press in Deposit Page
    private class ButtonHandler implements ActionListener {
        //EFFECTS: handle every button action in this page
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == view) {
                setVisible(false);
                new ViewPage(account, allaccount, parkinglots);
            } else if (e.getSource() == reserve) {
                setVisible(false);
                new ReservePage(account, allaccount, parkinglots);
            } else if (e.getSource() == deposit) {
                setVisible(false);
                new DepositPage(account, allaccount, parkinglots);
            } else if (e.getSource() == save) {
                saveAccounts();
            }
        }
    }
}
