package ui;

import model.Account;
import model.ParkingLot;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class DepositPage extends MainPage {
    private JSlider slider;
    private JLabel value;
    private JButton pick;

    //EFFECTS: creates a page for the user to deposit money into his/her account
    public DepositPage(Account account, ArrayList<Account> allaccounts, ArrayList<ParkingLot> parkinglots) {
        super(account, allaccounts, parkinglots);
        loadButton();
        loadLabel();
        loadTitle();
        loadSlider();
    }

    //EFFECTS: loads title into screen
    protected void loadTitle() {
        JLabel title = new JLabel("How much do you want to deposit?");
        Font font1 = new Font("Sans Serif", Font.BOLD, 30);
        title.setFont(font1);
        title.setBounds(130,60,600,60);
        add(title);
    }

    //MODIFIES: this
    //EFFECTS: loads buttons into screen
    private void loadButton() {
        pick = new JButton("Pick");
        pick.setBounds(270,300,200,80);
        pick.addActionListener(new ButtonHandler());
        add(pick);
    }

    //EFFECTS: loads labels into screen
    private void loadLabel() {
        value = new JLabel("Slide to get value");
        Font font1 = new Font("Sans Serif", Font.BOLD, 30);
        value.setFont(font1);
        value.setBounds(250,210,300,50);
        add(value);
    }

    //MODIFIES: this
    //EFFECTS: loads sliders into screen
    private void loadSlider() {
        slider = new JSlider(JSlider.HORIZONTAL,0, 100, 0);
        slider.setBounds(230,130,300,40);
        slider.addChangeListener(new ChangeHandler());
        add(slider);
    }

    //A class used to handle any change in slider
    private class ChangeHandler implements ChangeListener {

        @Override
        //EFFECTS: if it's called from the slider, change the value label bounds and change it text into curernt value
        public void stateChanged(ChangeEvent e) {
            if (e.getSource() == slider) {
                value.setBounds(350,220,300,50);
                value.setText(slider.getValue() + "$");
            }
        }
    }

    //A class used to handle button press in Deposit Page
    private class ButtonHandler implements ActionListener {

        @Override
        //EFFECTS: handle every button action in this page
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == pick) {
                account.deposit(slider.getValue());
                playSound("cashregister");
                setVisible(false);
                new MainMenu(account, allaccount, parkinglots);
            }
        }
    }
}
