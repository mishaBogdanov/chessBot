package ui;

import model.ChessGame;
import model.ListOfGames;

import java.time.format.DateTimeFormatter;

//this is what gives the list to previous games
public class ListsClass {

    //EFFECTS: gives a list of played games
    public static String[] giveList(ListOfGames g, int size) {
        String[] listGames = new String[size];
        int n = 1;
        for (ChessGame p : g.getGames()) {
            listGames[n - 1] = getGameTile(p, n);
            n++;
        }
        return listGames;

    }

    //EFFECTS: gives the string for the game
    private static String getGameTile(ChessGame givenGame, int n) {
        return n + ". Game played on: "
                + (givenGame.getDate() != null ? (DateTimeFormatter.ofPattern("EEEE d").format(givenGame.getDate()))
                + "th"
                + DateTimeFormatter.ofPattern("H:m").format(givenGame.getDate()) + ". "
                : givenGame.getEndTimeInString())
                + (givenGame.getWhoWon() == 'W' ? "White won."
                : givenGame.getWhoWon() == 'B' ? "Black won." : "It was a tie");
    }
}
