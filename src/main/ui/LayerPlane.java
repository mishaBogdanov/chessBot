package ui;

import model.ChessGame;
import model.ListOfGames;
import persistance.JsonReader;
import persistance.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;

// this is the main plane that draws different options
public class LayerPlane extends JLayeredPane {
    int currentConfig;
    private ListOfGames list = new ListOfGames();

    private JsonReader jsonReader;
    private JsonWriter jsonWriter;
    MainScreenOptions options;

    private static final String JSON_STORE = "./data/games.json";

    public LayerPlane() {
        options = new MainScreenOptions(this);
        jsonReader = new JsonReader(JSON_STORE);
        jsonWriter = new JsonWriter(JSON_STORE);
        this.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        this.setSize(1000, 700);
        this.add(new MainScreenOptions(this));
    }

    //MODIFIES: this
    //EFFECTS: sets up the square for bot play
    public void setupBotPlay(int depth, char who) {
        this.removeAll();
        this.add(options);
        ChessPvPMenu curView = new ChessPvPMenu(list, depth, who);
        this.add(curView);
        this.add(new EndGame(curView));
        this.repaint();
        this.revalidate();
    }

    //MODIFIES: this
    //EFFECTS: sets the tab for given option
    @SuppressWarnings("methodlength")
    public void setOptions(String from) {
        if (from.equals("import games")) {
            try {
                list = jsonReader.read();
            } catch (IOException e) {
                options.getGetGamesError();
                this.removeAll();
                this.add(options);
                this.repaint();
                this.revalidate();
            }
        } else if (from.equals("view games")) {
            this.removeAll();
            this.add(options);
            ChessViewGame curView = new ChessViewGame(null);
            this.add(curView);
            this.add(new SelectGame(list, curView));
            this.repaint();
            this.revalidate();
        } else if (from.equals("play against friend")) {
            this.removeAll();
            this.add(options);
            ChessPvPMenu curView = new ChessPvPMenu(list, 0, 'N');
            this.add(curView);
            this.add(new EndGame(curView));
            this.repaint();
            this.revalidate();
        } else if (from.equals("save games")) {
            try {
                jsonWriter.open();
                jsonWriter.write(list);
                jsonWriter.close();
            } catch (FileNotFoundException e) {
                System.out.println("Unable to write to file: " + JSON_STORE);
            }
        } else if (from.equals("play against bot")) {
            this.removeAll();
            this.add(options);
            this.add(new BotSelection(this));
            this.repaint();
            this.revalidate();
        }
    }


}
