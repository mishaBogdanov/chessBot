package ui;

import model.ChessGame;
import model.GameState;

import javax.swing.*;
import javax.swing.border.AbstractBorder;
import java.awt.*;
import java.util.ArrayList;

//this is the plane for showing game viewings
public class ChessViewGame extends JPanel {
    int currentMove = 0;
    ChessGame currentGame;

    public ChessViewGame(ChessGame game) {
        currentGame = game;
        this.setSize(new Dimension(700, 900));
        this.setLayout(new FlowLayout());
        if (game != null) {
            this.add(drawChessBoard(game.getMoves().get(currentMove)));
        }
        this.add(new JButton("previous"));
        this.add(new JButton("next"));
        this.setBackground(new Color(45, 45, 45));
        AbstractBorder brdrLeft = new TextBubbleBorder(Color.BLACK, 2, 30, 0);
        this.setBorder(brdrLeft);
    }

    //MODIFIES: this
    //EFFECTS: sets a game to be viewed
    public void setGame(ChessGame game) {
        this.removeAll();
        currentGame = game;
        currentMove = 0;
        this.add(drawChessBoard(game.getMoves().get(currentMove)));
        this.repaint();
        this.revalidate();
    }

    //MODIFIES: this
    //EFFECTS: draws the next move of the game
    public void nextMove() {
        if (currentMove < currentGame.getMoves().size() - 1) {
            currentMove++;
            this.removeAll();
            this.add(drawChessBoard(currentGame.getMoves().get(currentMove)));
            this.repaint();
            this.revalidate();
        }
    }

    //MODIFIES: this
    //EFFECTS: gets and paints the previous move
    public void prevMove() {
        if (currentMove > 0) {
            currentMove--;
            this.removeAll();
            this.add(drawChessBoard(currentGame.getMoves().get(currentMove)));
            this.repaint();
            this.revalidate();
        }
    }

    //EFFECTS: returns a pannel with board ready to be drawn
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
        ArrayList<ChessTileButton> listButton = generateBoard(givenBoard);

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

    //EFFECTS: generates a list of squares of a chess board
    private ArrayList<ChessTileButton> generateBoard(String board) {
        ArrayList<ChessTileButton> returnValue = new ArrayList<>();
        for (int i = 0; i < 127; i += 2) {
            returnValue.add(new ChessTileButton((i - (i / 16) * 2) % 4 == 0, String.valueOf(board.charAt(i))
                    + board.charAt(i + 1), false, returnValue, i / 2, new GameState(), 'N',
                    null));
        }
        return returnValue;
    }


}
