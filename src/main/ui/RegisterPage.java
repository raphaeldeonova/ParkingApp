package ui;

import exception.DuplicateAccountException;
import model.Account;
import model.ParkingLot;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class RegisterPage extends JFrame {

    private JButton registerbutton;
    private JTextField name;
    private JTextField email;
    private ArrayList<Account> allaccount;
    private ArrayList<ParkingLot> parkinglots;

    //EFFECTS: makes a register page and shows it to the user.
    public RegisterPage(ArrayList<Account> allaccount, ArrayList<ParkingLot> parkinglots) {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.allaccount = allaccount;
        this.parkinglots = parkinglots;
        loadTitle();
        loadLables();
        loadTextFields();
        loadButtons();

        setLayout(null);
        setSize(ParkingAppGUI.WIDTH,ParkingAppGUI.HEIGHT);
        setLocationRelativeTo(null); //sets to center of screen
        setVisible(true);
    }

    //EFFECTS: loads title into screen
    public void loadTitle() {
        JLabel title = new JLabel();
        Font font1 = new Font("Sans Serif", Font.BOLD, 40);
        title.setFont(font1);
        title.setBounds(200,40,600,100);
        title.setText("Create an Account");
        add(title);
    }

    //EFFECTS: loads labels into screen
    public void loadLables() {
        JLabel namelabel = new JLabel();
        namelabel.setText("Name: ");
        namelabel.setBounds(150, 120, 100,100);
        add(namelabel);
        JLabel emaillabel = new JLabel();
        emaillabel.setText("Email: ");
        emaillabel.setBounds(150, 190, 100,100);
        add(emaillabel);
    }

    //MODIFIES: this
    //EFFECTS: loads text input fields into screen
    public void loadTextFields() {
        name = new JTextField();
        name.setBounds(200, 150, 300,50);
        add(name);

        email = new JTextField();
        email.setBounds(200,210, 300, 50);
        add(email);
    }

    //MODIFIES: this
    //EFFECTS: loads all button necesary for the page.
    public void loadButtons() {
        registerbutton = new JButton("Register");
        registerbutton.setBounds(200, 280, 150, 70);
        registerbutton.addActionListener(new ButtonHandler());
        add(registerbutton);
    }

    //A class used to handle button press in Deposit Page
    private class ButtonHandler implements ActionListener {

        @Override
        //EFFECTS: handle every button action in this page
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == registerbutton) {
                Account picked;
                try {
                    picked = verifyAccount(name.getText(), email.getText());
                    setVisible(false);
                    allaccount.add(picked);
                    new MainMenu(picked, allaccount, parkinglots);
                } catch (DuplicateAccountException ex) {
                    JLabel duplicateaccount = new JLabel("There is already an account with that name and email!");
                    duplicateaccount.setBounds(400, 280, 400,60);
                    add(duplicateaccount);
                    setVisible(false);
                    setVisible(true);
                }
            }
        }

        //EFFECTS: verify that no such account exists yet and return a new account with name and email
        //         if there is already an account with that spec, throw DuplicateAccountException
        private Account verifyAccount(String name, String email) throws DuplicateAccountException {
            for (Account current: allaccount) {
                if (current.getName().equals(name) && current.getEmail().equals(email)) {
                    throw new DuplicateAccountException();
                }
            }
            return new Account(name, email);
        }
    }
}
