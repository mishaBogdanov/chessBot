package ui;

import model.BotPlayer;
import model.ChessGame;
import model.GameState;
import model.ListOfGames;

import javax.swing.*;
import javax.swing.border.AbstractBorder;
import java.awt.*;
import java.util.ArrayList;

// this is the panel that creates the PvP board
public class ChessPvPMenu extends JPanel {
    char whoseMove = 'W';
    ChessGame thisGame = new ChessGame();

    private int currentSelected;

    ListOfGames list;

    int botMoves;
    char playerColor;

    //EFFECTS: initializes the board
    public ChessPvPMenu(ListOfGames list, int botMoves, char color) {
        this.botMoves = botMoves;
        playerColor = color;
        this.list = list;
        whoseMove = 'W';
        this.setSize(new Dimension(700, 900));
        this.setLayout(new FlowLayout());
        this.add(drawChessBoard(thisGame.getGameState().getBoard()));
        this.setBackground(new Color(45, 45, 45));
        AbstractBorder brdrLeft = new TextBubbleBorder(Color.BLACK, 2, 30, 0);
        this.setBorder(brdrLeft);
    }

    public void setCurrentSelected(int g) {
        currentSelected = g;
    }

    public int getCurrentSelected() {
        return currentSelected;
    }

    //MODIFIES: this
    //EFFECTS: returns the panel with just the board
    @SuppressWarnings("methodlength")
    private JPanel drawChessBoard(String givenBoard) {
        JPanel board = new JPanel();
        board.setLayout(new GridLayout(9, 9, 1, 1));
        board.setLocation(0, 0);
        board.setBackground(new Color(45, 45, 45));
        board.setForeground(Color.white);
        this.setPreferredSize(new Dimension(500, 500));
        JPanel greyPannel = new JPanel();
        greyPannel.setBackground(new Color(45, 45, 45));
        board.add(greyPannel);
        board.add(new Label("A"));
        board.add(new Label("B"));
        board.add(new Label("C"));
        board.add(new Label("D"));
        board.add(new Label("E"));
        board.add(new Label("F"));
        board.add(new Label("G"));
        board.add(new Label("H"));
        int currentNum = 1;
        ArrayList<ChessTileButton> listButton = generateBoard(givenBoard, thisGame.getGameState());

        for (int i = 0; i < 72; i++) {
            if (i % 9 == 0) {
                board.add(new Label(Integer.toString(9 - currentNum)));
                currentNum++;
            } else {
                board.add(listButton.get(i - i / 9 - 1));
            }
        }
        return board;
    }


    //MODIFIES: this
    //EFFECTS: edits the chessGame field to make the move.
    @SuppressWarnings("methodlength")
    public void makeMove(int to) {
        if (to == 200) {
            BotPlayer botPlayer = new BotPlayer();
            thisGame.getGameState().setMovesRemaining(botMoves);
            thisGame.makeBotMove(botPlayer.makeMove(thisGame.getGameState(), playerColor == 'W' ? 'B' : 'W'));
            JPanel newPannel = drawChessBoard(thisGame.getGameState().getBoard());
            this.removeAll();
            this.add(newPannel);
            this.repaint();
            this.revalidate();
        }
        thisGame.makeNumericMove(currentSelected * 2, to * 2);
        JPanel newPannel = drawChessBoard(thisGame.getGameState().getBoard());
        this.removeAll();
        this.add(newPannel);
        this.repaint();
        this.revalidate();

        if (botMoves == 0) {
            whoseMove = whoseMove == 'W' ? 'B' : 'W';
        } else {
            BotPlayer botPlayer = new BotPlayer();
            thisGame.getGameState().setMovesRemaining(botMoves);
            thisGame.makeBotMove(botPlayer.makeMove(thisGame.getGameState(), playerColor == 'W' ? 'B' : 'W'));
            newPannel = drawChessBoard(thisGame.getGameState().getBoard());
            this.removeAll();
            this.add(newPannel);
            this.repaint();
            this.revalidate();
        }


        newPannel = drawChessBoard(thisGame.getGameState().getBoard());
        this.removeAll();
        this.add(newPannel);
        this.repaint();
        this.revalidate();
    }

    //MODIFIES: this
    //EFFECTS: resets the board, and adds the game to list of games played.
    public void whiteWinGame() {
        thisGame.setWhoWon('W');
        list.addGame(thisGame);
        thisGame = new ChessGame();
        JPanel newPannel = drawChessBoard(thisGame.getGameState().getBoard());
        this.removeAll();
        this.add(newPannel);
        this.repaint();
        this.revalidate();

    }

    //MODIFIES: this
    //EFFECTS: resets the board, and adds the game to list of games played.
    public void blackWinGame() {
        thisGame.setWhoWon('B');
        list.addGame(thisGame);
        thisGame = new ChessGame();
        JPanel newPannel = drawChessBoard(thisGame.getGameState().getBoard());
        this.removeAll();
        this.add(newPannel);
        this.repaint();
        this.revalidate();

    }

    //MODIFIES: this
    //EFFECTS: resets the board, and adds the game to list of games played.
    public void draw() {
        thisGame.setWhoWon('D');
        list.addGame(thisGame);
        thisGame = new ChessGame();
        JPanel newPannel = drawChessBoard(thisGame.getGameState().getBoard());
        this.removeAll();
        this.add(newPannel);
        this.repaint();
        this.revalidate();

    }


    //EFFECTS: returns a list of chess tiles that can then be layed out to represent the board
    private ArrayList<ChessTileButton> generateBoard(String board, GameState game) {
        ArrayList<ChessTileButton> returnValue = new ArrayList<>();
        for (int i = 0; i < 127; i += 2) {
            returnValue.add(new ChessTileButton((i - (i / 16) * 2) % 4 == 0,
                    String.valueOf(board.charAt(i))
                            + board.charAt(i + 1), false, returnValue,
                    i / 2, game, whoseMove, this));
        }
        return returnValue;
    }


}
