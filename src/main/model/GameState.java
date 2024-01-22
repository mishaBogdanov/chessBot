package model;


//contains the current state of the game
public class GameState {
    String board;
    int whiteKingPos;
    int blackKingPos;
    Boolean whiteKingMoved;
    Boolean blackKingMoved;
    Boolean topRightRookMoved;
    Boolean topLeftRookMoved;
    Boolean bottomRightRookMoved;
    Boolean bottomLeftRookMoved;
    Boolean[] whitePawnsMoved = new Boolean[8]; //true means just skipped, false otherwise
    Boolean[] blackPawnsMoved = new Boolean[8];
    int movesRemaining;
    double returnValue;
    int movesMade;

    //EFFECTS: creates a new gamestate and sets all the pawns moved to false. should be used for player games.
    public GameState() {
        for (int i = 0; i < 8; i++) {
            whitePawnsMoved[i] = false;
            blackPawnsMoved[i] = false;
        }
        whiteKingMoved = false;
        blackKingMoved = false;
        bottomLeftRookMoved = false;
        bottomRightRookMoved = false;
        topLeftRookMoved = false;
        topRightRookMoved = false;
        whiteKingPos = 120;
        blackKingPos = 8;
        board = "BRBHBBBQBKBBBHBRBPBPBPBPBPBPBPBP                          "
                + "                                      WPWPWPWPWPWPWPWPWRWHWBWQWKWBWHWR";
    }

    //REQUIRES: depth > 0
    //EFFECTS: creates a GameState ready to go for a bot match. sets up the board, depth, white and black king
    //         positions, sets up the pawns.

    public GameState(int depth) {
        for (int i = 0; i < 8; i++) {
            whitePawnsMoved[i] = false;
            blackPawnsMoved[i] = false;
        }
        whiteKingMoved = false;
        blackKingMoved = false;
        bottomLeftRookMoved = false;
        bottomRightRookMoved = false;
        topLeftRookMoved = false;
        topRightRookMoved = false;
        movesRemaining = depth;
        whiteKingPos = 120;
        blackKingPos = 8;
        board = "BRBHBBBQBKBBBHBRBPBPBPBPBPBPBPBP                          "
                + "                                      WPWPWPWPWPWPWPWPWRWHWBWQWKWBWHWR";
    }

    //EFFECTS: creates a new GameState object thats the exact same as given.
    public static GameState coppyState(GameState given) {
        GameState returnValue = new GameState();
        returnValue.board = given.board;
        returnValue.blackKingMoved = given.blackKingMoved;
        returnValue.whiteKingPos = given.whiteKingPos;
        returnValue.whiteKingMoved = given.whiteKingMoved;
        returnValue.bottomLeftRookMoved = given.bottomLeftRookMoved;
        returnValue.bottomRightRookMoved = given.bottomRightRookMoved;
        returnValue.movesMade = given.movesMade;
        returnValue.topRightRookMoved = given.topRightRookMoved;
        returnValue.topLeftRookMoved = given.topLeftRookMoved;
        returnValue.blackKingPos = given.blackKingPos;
        returnValue.movesRemaining = given.movesRemaining;
        returnValue.returnValue = given.returnValue;
        for (int i = 0; i < 8; i++) {
            returnValue.blackPawnsMoved[i] = given.blackPawnsMoved[i];
            returnValue.whitePawnsMoved[i] = given.whitePawnsMoved[i];
        }
        return returnValue;
    }


    // EFFECTS: resets the pawn moves to all be false.
    static void resetPawnMoves(Boolean[] givenList) {
        for (int i = 0; i < 8; i++) {
            if (givenList[i]) {
                givenList[i] = false;
            }
        }
    }

    public String getBoard() {
        return board;
    }

    public void setBoard(String givenBoard) {
        board = givenBoard;
    }

    public int getMovesRemaining() {
        return movesRemaining;
    }

    public int getMovesMade() {
        return movesMade;
    }

    public void resetMovesMade() {
        movesMade = 0;
    }

    public void setWhiteKingMoved(Boolean whiteKingMoved) {
        this.whiteKingMoved = whiteKingMoved;
    }

    public void setWhiteKingPos(int whiteKingPos) {
        this.whiteKingPos = whiteKingPos;
    }

    public int getWhiteKingPos() {
        return whiteKingPos;
    }

    public void setBlackKingPos(int blackKingPos) {
        this.blackKingPos = blackKingPos;
    }

    public int getBlackKingPos() {
        return blackKingPos;
    }

    public void setBlackKingMoved(boolean blackKingPos) {
        this.blackKingMoved = blackKingPos;
    }

    public boolean getWhiteKingMoved() {
        return whiteKingMoved;
    }

    public boolean getBlackKingMoved() {
        return blackKingMoved;
    }

    public boolean getBottomRightRookMoved() {
        return bottomRightRookMoved;
    }

    public boolean getBottomLeftRookMoved() {
        return bottomLeftRookMoved;
    }

    public boolean getTopRightRookMoved() {
        return topRightRookMoved;
    }

    public boolean getTopLeftRookMoved() {
        return topLeftRookMoved;
    }

    public Boolean[] getWhitePawnsMoved() {
        return whitePawnsMoved;
    }

    public Boolean[] getBlackPawnsMoved() {
        return blackPawnsMoved;
    }

    public void setMovesRemaining(int g) {
        movesRemaining = g;
    }
}
