package ui;

import model.Account;
import model.ParkingLot;
import persistence.JsonWriter;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public abstract class MainPage extends JFrame {
    protected Account account;
    protected ArrayList<Account> allaccount;
    protected ArrayList<ParkingLot> parkinglots;
    protected JsonWriter jsonWriter;

    public MainPage(Account account, ArrayList<Account> allaccounts, ArrayList<ParkingLot> parkinglots) {
        this.account = account;
        this.allaccount = allaccounts;
        this.parkinglots = parkinglots;
        this.jsonWriter = new JsonWriter("account");

        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(ParkingAppGUI.WIDTH,ParkingAppGUI.HEIGHT);
        setLocationRelativeTo(null); //sets to center of screen
        setVisible(true);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                saveAccounts();
            }
        });
    }

    //EFFECTS: saves all accounts in the app including any changes in current attempt
    protected void saveAccounts() {
        jsonWriter.saveAccounts(allaccount);
    }

    //REQUIRES: the file has to be in .wav format
    //EFFECTS: plays the sound associated with such filename
    protected void playSound(String filename) {
        String soundname = "./data/" + filename + ".wav";
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundname).getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
            e.printStackTrace();
        }
    }

    //EFFECTS: loads title into screen
    protected abstract void loadTitle();
}
