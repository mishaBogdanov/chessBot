package persistance;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.stream.Stream;

import model.ChessGame;
import model.Event;
import model.EventLog;
import model.ListOfGames;
import org.json.*;

// Represents a reader that reads workroom from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads workroom from file and returns it;
    // throws IOException if an error occurs reading data from file
    public ListOfGames read() throws IOException {
        EventLog.getInstance().logEvent(new Event("just pulled games from storage"));
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return addThingies(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {

            stream.forEach(contentBuilder::append);
        }

        return contentBuilder.toString();
    }


    // MODIFIES: wr
    // EFFECTS: parses thingies from JSON object and adds them to workroom
    private ListOfGames addThingies(JSONObject jsonObject) {
        ListOfGames lg = new ListOfGames();
        JSONArray jsonArray = jsonObject.getJSONArray("games");
        for (Object json : jsonArray) {
            JSONObject nextGame = (JSONObject) json;
            ArrayList<String> myArray = new ArrayList<>();
            for (int i = 0; i < nextGame.getJSONArray("moves").length(); i++) {
                myArray.add(nextGame.getJSONArray("moves").getJSONObject(i).getString("move"));
            }
            ChessGame game = new ChessGame((char) nextGame.getInt("who won"), myArray, nextGame.getString("date"));
            lg.addGame(game);
        }
        return lg;
    }
}
