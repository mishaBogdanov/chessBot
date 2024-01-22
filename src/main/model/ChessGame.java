package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistance.JsonWriter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;


//contains the info for current and past chess games
public class ChessGame {
    private GameState gameState;

    private String endTimeInString;


    private ArrayList<String> moves;

    private char whoWon;

    private char whoseMove;

    LocalDateTime gameFinishTime;

    //EFFECTS: creates a chess game from scratch. should be used for PvP
    public ChessGame() {
        gameState = new GameState();
        gameState.board = "BRBHBBBQBKBBBHBRBPBPBPBPBPBPBPBP                             "
                + "                                   WPWPWPWPWPWPWPWPWRWHWBWQWKWBWHWR";
        moves = new ArrayList<>();
        whoWon = 'N';
    }

    //EFFECTS: creates a chess game with gameState state.
    public ChessGame(GameState state) {
        moves = new ArrayList<>();
        gameState = state;
        whoWon = 'N';
    }

    public ChessGame(char whoWon, ArrayList<String> moves, String time) {
        this.whoWon = whoWon;
        this.moves = moves;

        this.endTimeInString = time;
    }

    public ChessGame(char whoWon, ArrayList<String> moves, LocalDateTime time) {
        this.whoWon = whoWon;
        this.moves = moves;
        this.gameFinishTime = time;
    }

    //REQUIRES: the from and to be formatted like a chess move
    //MODIFIES: this
    //EFFECTS: makes the given move, saves it in the gameState, and adds a move
    public GameState makePhysMove(String from, String to) {


        int fromIndex = -16 * from.charAt(1) + 896 + (from.charAt(0) - 65) * 2;
        int toIndex = -16 * to.charAt(1) + 896 + (to.charAt(0) - 65) * 2;

        if (gameState.board.charAt(fromIndex) == 'B' && gameState.board.charAt(fromIndex + 1) == 'K') {
            gameState.blackKingPos = toIndex;
            gameState.blackKingMoved = true;
        }
        if (gameState.board.charAt(fromIndex) == 'W' && gameState.board.charAt(fromIndex + 1) == 'K') {
            gameState.whiteKingPos = toIndex;
            gameState.whiteKingMoved = true;
        }
        gameState.board = gameState.board.substring(0, toIndex) + gameState.board.substring(fromIndex, fromIndex + 2)
                + gameState.board.substring(toIndex + 2);
        gameState.board = gameState.board.substring(0, fromIndex) + "  "
                + gameState.board.substring(fromIndex + 2);
        gameState.movesMade++;
        moves.add(gameState.board);
//        whoseMove = whoseMove == 'W' ? 'B' : 'W';
        return gameState;
    }

    //MODIFIES: this
    //EFFECTS: makes a move based just of numbers.
    @SuppressWarnings("methodlength")
    public GameState makeNumericMove(int fromIndex, int toIndex) {
        String event = (gameState.board.charAt(fromIndex) == 'W' ? "White " : "Black ")
                + "made a move, and the board now looks like this: ";
        boolean castled = false;
        if (gameState.board.charAt(fromIndex) == 'W' && gameState.board.charAt(fromIndex + 1) == 'K'
                && toIndex == 124 && fromIndex == 120) {
            gameState.board = gameState.board.substring(0, 120) + "  WRWK  ";
            gameState.whiteKingPos = toIndex;
            castled = true;
        }
        if (gameState.board.charAt(fromIndex) == 'W' && gameState.board.charAt(fromIndex + 1) == 'K'
                && toIndex == 116 && fromIndex == 120) {
            gameState.board = gameState.board.substring(0, 112) + "    WKWR  "
                    + gameState.board.substring(122);
            gameState.whiteKingPos = toIndex;
            castled = true;
        }
        if (gameState.board.charAt(fromIndex) == 'B' && gameState.board.charAt(fromIndex + 1) == 'K'
                && toIndex == 4 && fromIndex == 8) {
            gameState.board = "    BKBR  " + gameState.board.substring(10);
            gameState.blackKingPos = toIndex;
            castled = true;
        }
        if (gameState.board.charAt(fromIndex) == 'B' && gameState.board.charAt(fromIndex + 1) == 'K'
                && toIndex == 12 && fromIndex == 8) {
            gameState.board = gameState.board.substring(0, 8) + "  BRBK  " + gameState.board.substring(16);
            gameState.blackKingPos = toIndex;
            castled = true;
        }
        if (!castled) {
            if (gameState.board.charAt(fromIndex) == 'W' && gameState.board.charAt(fromIndex + 1) == 'K') {
                gameState.whiteKingMoved = true;
                gameState.whiteKingPos = toIndex;
            } else if (gameState.board.charAt(fromIndex) == 'B' && gameState.board.charAt(fromIndex + 1) == 'K') {
                gameState.blackKingMoved = true;
                gameState.blackKingPos = toIndex;
            } else if (fromIndex == 126 && gameState.board.charAt(fromIndex + 1) == 'R') {
                gameState.bottomRightRookMoved = true;
            } else if (fromIndex == 112 && gameState.board.charAt(fromIndex + 1) == 'R') {
                gameState.bottomLeftRookMoved = true;
            } else if (fromIndex == 0 && gameState.board.charAt(fromIndex + 1) == 'R') {
                gameState.topLeftRookMoved = true;
            } else if (fromIndex == 14 && gameState.board.charAt(fromIndex + 1) == 'R') {
                gameState.topRightRookMoved = true;
            }
            gameState.board = gameState.board.substring(0, toIndex)
                    + gameState.board.substring(fromIndex, fromIndex + 2)
                    + gameState.board.substring(toIndex + 2);
            gameState.board = gameState.board.substring(0, fromIndex) + "  "
                    + gameState.board.substring(fromIndex + 2);

            moves.add(gameState.board);
        }
        event += gameState.board;
        EventLog.getInstance().logEvent(new Event(event));

        return gameState;
    }

    //MODIFIES: this
    //EFFECTS: checks if string entered is in correct notation, and makes the move.
    public boolean parseNextMove(String move) {
        if (
                move.length() == 4
                        && move.charAt(0) <= 72
                        && move.charAt(2) <= 72
                        && move.charAt(0) >= 65
                        && move.charAt(2) >= 65
                        && move.charAt(1) >= 49
                        && move.charAt(1) <= 56
                        && move.charAt(3) >= 49
                        && move.charAt(3) <= 56) {
            makePhysMove(move.substring(0, 2),
                    move.substring(2));
            return true;
        }
        return false;
    }

    //REQUIRES: given must be W or B
    //MODIFIES: this
    //EFFECTS: sets the win to either be white or black
    public void setWhoWon(char given) {
        whoWon = given;
        gameFinishTime = LocalDateTime.now();
    }

    public GameState getGameState() {
        return gameState;
    }

    public char getWhoWon() {
        return whoWon;
    }

    public LocalDateTime getDate() {
        return gameFinishTime;
    }

    public ArrayList<String> getMoves() {
        return moves;
    }

    //adds a move to the list of moves
    public void makeBotMove(GameState givenState) {
        String event = "Bot made a move and now the board looks like: " + givenState.board;
        EventLog.getInstance().logEvent(new Event(event));
        moves.add(givenState.getBoard());
        gameState = givenState;
    }

    //EFFECTS: returns a Json object of this game
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        JSONArray madeMoves = new JSONArray();
        for (String i : moves) {
            JSONObject obj = new JSONObject();
            obj.put("move", i);
            madeMoves.put(obj);
        }
        json.put("who won", whoWon);
        json.put("moves", madeMoves);

        json.put("date",
                gameFinishTime != null
                        ? (DateTimeFormatter.ofPattern("EEEE d").format(gameFinishTime)) + "th"
                        + DateTimeFormatter.ofPattern("H:m").format(gameFinishTime) + ". " : endTimeInString)
        ;
        return json;
    }

    public String getEndTimeInString() {
        return endTimeInString;
    }


}
