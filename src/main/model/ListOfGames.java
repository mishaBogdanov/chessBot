package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistance.JsonWriter;

import java.util.ArrayList;

//contains a list of games to see previous games.
public class ListOfGames {
    private static final String JSON_STORE = "./data/workroom.json";
    private ArrayList<ChessGame> games;


    //EFFECTS: initializes the arraysList games.
    public ListOfGames() {
        games = new ArrayList<>();
    }

    public ArrayList<ChessGame> getGames() {
        return games;
    }

    //EFFECTS: returns true if the list is empty
    public Boolean isListEmpty() {
        if (games.size() == 0) {
            return true;
        } else {
            return false;
        }
    }


    // MODIFIES: this
    // EFFECTS: adds a game to the list.
    public void addGame(ChessGame given) {
        String event = "a game was just saved where " + (given.getWhoWon() == 'W' ? "White won" :
                given.getWhoWon() == 'B' ? "Black won " : "it was a draw");
        EventLog.getInstance().logEvent(new Event(event));
        games.add(given);
    }


    //EFFECTS: makes a Json of the list of these games.
    public JSONObject toJson() {
        JSONObject obj = new JSONObject();
        JSONArray array = new JSONArray();
        for (ChessGame game : games) {
            array.put(game.toJson());
        }
        obj.put("games", array);
        return obj;
    }


}
