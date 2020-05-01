package persistence;

import model.Account;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class JsonWriter {
    private FileWriter file;

    //MODIFIES: this
    //EFFECTS: creates a jsonWriter for filename
    public JsonWriter(String filename) {
        try {
            file = new FileWriter("./data/" + filename + ".json");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    //EFFECTS: saves all accounts to the file
    public void saveAccounts(ArrayList<Account> allaccount) {
        JSONObject output = new JSONObject();
        JSONArray allaccounts = new JSONArray();
        for (Account account: allaccount) {
            allaccounts.add(account.toJsonObject());
        }
        output.put("accounts", allaccounts);

        try {
            file.write(output.toJSONString());
            file.flush();
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
