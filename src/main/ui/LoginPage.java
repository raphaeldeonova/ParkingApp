package ui;

import exception.AccountNotFoundException;
import model.Account;
import model.ParkingLot;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class LoginPage extends JFrame {
    private JTextField name;
    private JTextField email;
    private ArrayList<Account> allaccount;
    private ArrayList<ParkingLot> parkinglots;
    private JButton loginbutton;
    private JButton registerbutton;

    //EFECTS: creates a Frame for the login page
    public LoginPage(ArrayList<Account> allaccount, ArrayList<ParkingLot> parkinglots) {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.allaccount = allaccount;
        this.parkinglots = parkinglots;

        loadTitle();
        loadLabel();
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
        Font font1 = new Font("Sans Serif", Font.BOLD, 60);
        title.setFont(font1);
        title.setBounds(200,40,600,100);
        title.setText("Login");
        add(title);
    }

    //EFFECTS: loads labels into screen
    public void loadLabel() {
        JLabel namelabel = new JLabel();
        namelabel.setText("Name: ");
        namelabel.setBounds(150, 120, 100,100);
        add(namelabel);
        JLabel emaillabel = new JLabel();
        emaillabel.setText("Email: ");
        emaillabel.setBounds(150, 190, 100,100);
        add(emaillabel);
        JLabel createaccount = new JLabel();
        createaccount.setText("Don't have an account?");
        createaccount.setBounds(550, 0, 200, 100);
        add(createaccount);
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
    //EFFECTS: loads buttons into screen
    public void loadButtons() {
        loginbutton = new JButton("Login");
        loginbutton.setBounds(200, 280, 150, 70);
        loginbutton.addActionListener(new ButtonHandler());
        add(loginbutton);
        registerbutton = new JButton("Create an Account");
        registerbutton.setBounds(550, 70, 200,80);
        registerbutton.addActionListener(new ButtonHandler());
        add(registerbutton);
    }

    //A class for handling button press. (login button and create account button)
    private class ButtonHandler implements ActionListener {

        //EFFECTS: handle every button action in this page
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == registerbutton) {
                setVisible(false);
                new RegisterPage(allaccount, parkinglots);
            } else if (e.getSource() == loginbutton) {
                try {
                    Account current = findAccount(name.getText(), email.getText());
                    setVisible(false);
                    new MainMenu(current, allaccount, parkinglots);
                } catch (AccountNotFoundException ex) {
                    JLabel noaccount = new JLabel("No such account found.");
                    noaccount.setBounds(400, 280, 200,60);
                    add(noaccount);
                    setVisible(false);
                    setVisible(true);
                }
            }
        }

        //EFFECTS: returns an account with name and email from allaccounts.
        //         if theres no such account, throw an AccountNotFoundException
        private Account findAccount(String name, String email) throws AccountNotFoundException {
            for (Account current: allaccount) {
                if (current.getName().equals(name) && current.getEmail().equals(email)) {
                    return current;
                }
            }
            throw new AccountNotFoundException();
        }
    }
}
