package persistence;

import org.json.simple.JSONObject;

public interface Saveable {
    //EFFECTS: saves current account into .json
    JSONObject toJsonObject();
}
