package ui;

import model.*;
import persistance.JsonReader;
import persistance.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;


//the main chess menus class.
public class ChessMenus {
    private Scanner input;
    private ListOfGames list = new ListOfGames();

//    BotPlayer botPlayer = new BotPlayer();

    private JsonReader jsonReader;

    private JsonWriter jsonWriter;
    private static final String JSON_STORE = "./data/games.json";

    public ChessMenus() {
        input = new Scanner(System.in);
        jsonReader = new JsonReader(JSON_STORE);
        jsonWriter = new JsonWriter(JSON_STORE);
        runChess();

    }

    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("E d H m");


    //EFFECTS: displays the main meny
    private void displayMenu() {
        System.out.println("select from:");
        System.out.println("    p -> play against friend");
        System.out.println("    b -> play against bot");
        System.out.println("    v -> view previous games");
        System.out.println("    l -> to load previous games");
        System.out.println("    s -> save current games");
        System.out.println("    q -> quit");

    }


    //EFFECTS: gives a string to be displayed as a tile representing each game played
    private String getGameTile(ChessGame givenGame, int n) {
        return n + ". Game played on: "
                + (givenGame.getDate() != null ? (DateTimeFormatter.ofPattern("EEEE d").format(givenGame.getDate()))
                + "th"
                + DateTimeFormatter.ofPattern("H:m").format(givenGame.getDate()) + ". "
                : givenGame.getEndTimeInString())
                + (givenGame.getWhoWon() == 'W' ? "White won."
                : givenGame.getWhoWon() == 'B' ? "Black won." : "It was a tie");
    }


    //EFFECTS: shows the menu where you can see the games you've played
    private void printGameOptions() {
        System.out.println("Enter the number of the game you want to inspect, or q to leave.");
        ArrayList<ChessGame> listOfChessGames = list.getGames();
        for (int i = 0; i < listOfChessGames.size(); i++) {
            System.out.println(getGameTile(listOfChessGames.get(i), i + 1));
        }
    }


    //EFFECTS: shows the screen where you can review games
    private void enterGameViewScreen(ChessGame game) {
        System.out.println("go to next move with n, previous with p and quit with q.");
        int currentMove = 0;
        String givenInput = "n";
        while (givenInput.equals("n") || givenInput.equals("p")) {
            model.GameState localState = new model.GameState();
            localState.setBoard(game.getMoves().get(currentMove));
            printBoard(localState);
            givenInput = input.next().toLowerCase();
            if (givenInput.equals("n") && currentMove + 1 < game.getMoves().size()) {
                currentMove++;
            } else if (givenInput.equals("n") && currentMove + 1 >= game.getMoves().size()) {
                System.out.println("this is the end of the game");
            } else if (givenInput.equals("p") && currentMove - 1 >= 0) {
                currentMove--;
            } else if (givenInput.equals("p") && currentMove - 1 < 0) {
                System.out.println("this is the first move");
            }
        }
        System.out.println("you chose to quit");
    }


    //EFFECTS: starts the game review process.
    private void inspectGames() {
        printGameOptions();
        String option = input.next();
        try {
            int chosenNumber = Integer.parseInt(option);
            if (chosenNumber - 1 >= list.getGames().size()) {
                int hardBreak = 2 / 0;
            } else {
                enterGameViewScreen(list.getGames().get(chosenNumber - 1));
            }
        } catch (Exception e) {
            System.out.println("you entered q or an invalid option.");
        }

    }


    //EFFECTS: prints the board with the given state
    private void printBoard(model.GameState state) {
        System.out.println("   A  B  C  D  E  F  G  H");
        System.out.print("8 ");

        for (int i = 0; i < 127; i += 2) {
            System.out.print("|" + state.getBoard().charAt(i) + state.getBoard().charAt(i + 1));

            if ((i + 2) % 16 == 0 && (i + 1) / 16 + 2 != 9) {
                System.out.println("|");
                System.out.print((9 - ((i + 1) / 16 + 2)) + " ");
            }
        }
        System.out.println("|");
    }


    //EFFECTS: prints the game instructions for PvP
    private void printGameInstructions() {
        System.out.println("play against a friend. make moves by typing out commands in the given style:");
        System.out.println("if you want to move a piece from E4 to E5 just type E4E5");
        System.out.println("if someone is in mate type the character of who won, or D for draw");
        System.out.println("so if white won type W or if black won type B or if its draw D or Q for quit.");

    }

    //EFFECTS: prints the game instructions for bot play
    private void printGameAgainstBotInstructions() {
        System.out.println("play against a bot. make moves by typing out commands in the given style:");
        System.out.println("if you want to move a piece from E4 to E5 just type E4E5");
        System.out.println("if someone is in mate type the character of who won, or D for draw");
        System.out.println("so if white won type W or if black won type B or if its draw D or Q for quit.");

    }


    //EFFECTS: tests who won the game and prints accordingly
    private void parseWinnngText(char winner) {
        if (winner == 'w') {
            System.out.println("white won. Press Enter to quit.");
        } else if (winner == 'b') {
            System.out.println("black won. Press Enter to quit.");
        } else if (winner == 'd') {
            System.out.println("it's a draw. Press Enter to quit.");
        } else {
            System.out.println("You chose to quit and end game. Press Enter to quit.");
        }
        input.next();
    }

    //MODIFIES: this
    //EFFECTS: plays against player
    private void playGame() {
        model.ChessGame game = new ChessGame();
        String move = null;
        printGameInstructions();
        printBoard(game.getGameState());
        while (game.getWhoWon() == 'N') {
            boolean goodMove = false;
            while (true) {
                System.out.println("next move:");
                move = input.next().toUpperCase();
                goodMove = game.parseNextMove(move) || move.equals("W") || move.equals("B")
                        || move.equals("D") || move.equals("Q");
                if (goodMove) {
                    break;
                }
                System.out.println("that wasn't a valid move. Try again.");

            }
            if (move.equals("W") || move.equals("B") || move.equals("D") || move.equals("Q")) {
                break;
            }
            printBoard(game.getGameState());

        }
        game.setWhoWon(move.charAt(0) != 'D' ? move.charAt(0) : 'D');
        list.addGame(game);
    }

    //EFFECTS: takes the input from the main menus
    private void processCommand(String command) {
        switch (command) {
            case "p":
                playGame();
                break;
            case "b":
//                startBot();
                break;
            case "v":
                inspectGames();
                break;
            case "s":
                saveWorkRoom();
                break;
            case "l":
                loadWorkRoom();
                break;
            default:
                System.out.println("Selection not valid...");
                break;

        }
    }


    //EFFECTS: prints the bot depth instructions.
    private void printBotDepthInstructions() {
        System.out.println("enter the search depth for the bot. recommended 5 and below.");
        System.out.println("the higher the search depth, the longer the bot will take.");
        System.out.println("enter the search depth:");
    }



    private String getGoodMove(ChessGame game) {
        String move = null;
        boolean goodMove = false;
        while (true) {
            System.out.println("next move:");
            move = input.next().toUpperCase();
            goodMove = game.parseNextMove(move) || move.equals("W") || move.equals("B")
                    || move.equals("D") || move.equals("Q");
            if (goodMove) {
                break;
            }
            System.out.println("that wasn't a valid move. Try again.");

        }
        return move;
    }


    private void loadWorkRoom() {
        try {
            list = jsonReader.read();
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    private void saveWorkRoom() {
        try {
            jsonWriter.open();
            jsonWriter.write(list);
            jsonWriter.close();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }


    public void runChess() {

        boolean keepGoing = true;
        String command = null;

        while (keepGoing) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();
            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }

        }


        System.out.println("\nGoodbye!");

    }


}
