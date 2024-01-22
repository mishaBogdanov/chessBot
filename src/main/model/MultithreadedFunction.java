package model;

//This class contains the code to initiate the multithread function recursively.
public class MultithreadedFunction implements Runnable {
    private final int threadExpNum = 4;
    private int whichPart;
    private GameState toComplete;

    private GameState output;
    private char whoseMove;

    // EFFECTS: sets up the required fields like givenState, whoseMove, whichPart.
    public MultithreadedFunction(GameState givenState, char move, int part) {

        toComplete = givenState;
        whoseMove = move;
        whichPart = part;

    }

    //REQUIRES: whoseMove, enemyMove 'W' or 'B'
    //MODIFIES: returnState
    //EFFECTS: makes the future moves, and checks if we should change returnState according to minmax.
    //         also decides wether or not to multithread the next steps.
    private void splitOrNot(GameState state, GameState returnState, GameState localState, char enemyMove) {
        char myMove = enemyMove == 'W' ? 'B' : 'W';
        GameState result;
        if (state.movesMade >= threadExpNum) {
            result = BotPlayer.whiteMakeMove(localState, enemyMove);
        } else {
            result = MultithreadingStarter.split(localState, enemyMove);
        }
        if ((result.returnValue >= returnState.returnValue && myMove == 'W')
                || (result.returnValue <= returnState.returnValue && myMove == 'B')) {
            returnState.returnValue = result.returnValue;
            returnState.board = localState.board;

        }
    }


    //REQUIRES: whoseMove == 'W' || whoseMove == 'B'
    //EFFECTS: this function returns the GameState with the next good move for whoseMove
    @SuppressWarnings("methodlength")
    GameState whiteMakeMove(GameState state, char whoseMove) { //might make return int to make things faster
        if (state.movesRemaining == 0) {
            GameState returningValue = GameState.coppyState(state);
            returningValue.returnValue = BotPlayer.getCurrentScore(returningValue);
            return returningValue;
        } else {                                //multithread this later to speed things up

            state.movesMade++;
            GameState returnState = GameState.coppyState(state);


            returnState.returnValue = (whoseMove == 'W') ? -999999 : 999999;
            if (whoseMove == 'W') {
                GameState.resetPawnMoves(state.whitePawnsMoved);
            } else {
                GameState.resetPawnMoves(state.blackPawnsMoved);
            }
            char enemyMove = (whoseMove == 'W') ? 'B' : 'W';
            state.movesRemaining -= 1;
            for (int i = whichPart == 1 ? 0 : whichPart == 2 ? 2 : whichPart == 3 ? 4 : 6; i < 127; i += 8) {


                if (state.board.charAt(i) == whoseMove && state.board.charAt(i + 1) == 'P') { //pawn


                    int distFromleft = i % 16 / 2;
                    int distFromTop = i / 16;

                    if (whoseMove == 'W') {
                        if (distFromTop == 3) {  //en pesant right
                            if (distFromleft < 7 && state.blackPawnsMoved[distFromleft + 1]) {
                                GameState localState = GameState.coppyState(state);
                                localState.board = localState.board.substring(0, i - 14) + "WP"
                                        + localState.board.substring(i - 12, i) + "  "
                                        + localState.board.substring(i + 2);
//                                localState.board[i - 14] = 'W';
//                                localState.board[i - 13] = 'P';
//                                localState.board[i] = ' ';
//                                localState.board[i + 1] = ' ';
                                if (!BotPlayer.isSquareInCheck(localState.board, localState.whiteKingPos, 'B')) {
                                    splitOrNot(state, returnState, localState, enemyMove);
                                }
                            }
                        }
                        if (distFromTop == 3) {  //en pesant left
                            if (distFromleft > 0 && state.blackPawnsMoved[distFromleft - 1]) {
                                GameState localState = GameState.coppyState(state);
                                localState.board = localState.board.substring(0, i - 18) + "WP"
                                        + localState.board.substring(i - 16, i) + "  "
                                        + localState.board.substring(i + 2);
//                                localState.board[i - 18] = 'W';
//                                localState.board[i - 17] = 'P';
//                                localState.board[i] = ' ';
//                                localState.board[i + 1] = ' ';
                                if (!BotPlayer.isSquareInCheck(localState.board, localState.whiteKingPos, 'B')) {
                                    splitOrNot(state, returnState, localState, enemyMove);
                                }

                            }
                        }

                        if (distFromleft > 0 && state.board.charAt(i - 18) == 'B') { //capturing left
                            GameState localState = GameState.coppyState(state);
                            if (distFromTop == 1) {
                                localState.board = localState.board.substring(0, i - 18) + "WQ"
                                        + localState.board.substring(i - 16, i) + "  "
                                        + localState.board.substring(i + 2);
                            } else {
                                localState.board = localState.board.substring(0, i - 18) + "WP"
                                        + localState.board.substring(i - 16, i) + "  "
                                        + localState.board.substring(i + 2);
                            }

//                            localState.board[i - 18] = 'W';
//                            localState.board[i - 17] = 'P';
//                            localState.board[i] = ' ';
//                            localState.board[i + 1] = ' ';
                            if (!BotPlayer.isSquareInCheck(localState.board, localState.whiteKingPos, 'B')) {
                                splitOrNot(state, returnState, localState, enemyMove);
                            }
                        }
                        if (distFromleft < 6 && state.board.charAt(i - 14) == 'B') { //capturing right

                            GameState localState = GameState.coppyState(state);
                            if (distFromTop == 1) {
                                localState.board = localState.board.substring(0, i - 14) + "WQ"
                                        + localState.board.substring(i - 12, i) + "  "
                                        + localState.board.substring(i + 2);
                            } else {
                                localState.board = localState.board.substring(0, i - 14) + "WP"
                                        + localState.board.substring(i - 12, i) + "  "
                                        + localState.board.substring(i + 2);
                            }

//                            localState.board[i - 14] = 'W';
//                            localState.board[i - 13] = 'P';
//                            localState.board[i] = ' ';
//                            localState.board[i + 1] = ' ';
                            if (!BotPlayer.isSquareInCheck(localState.board, localState.whiteKingPos, 'B')) {
                                splitOrNot(state, returnState, localState, enemyMove);
                            }
                        }
                        if (distFromTop == 1 && state.board.charAt(i - 16) == ' ') {
                            GameState localState = GameState.coppyState(state);
                            localState.board = localState.board.substring(0, i - 16) + "WQ"
                                    + localState.board.substring(i - 14, i) + "  "
                                    + localState.board.substring(i + 2);
//                            localState.board[i - 16] = 'W';
//                            localState.board[i - 15] = 'Q';
//                            localState.board[i] = ' ';
//                            localState.board[i + 1] = ' ';
                            if (!BotPlayer.isSquareInCheck(localState.board, localState.whiteKingPos, 'B')) {
                                splitOrNot(state, returnState, localState, enemyMove);
                            }
                        } else if (state.board.charAt(i - 16) == ' ') { //moving forward
                            GameState localState = GameState.coppyState(state);
                            localState.board = localState.board.substring(0, i - 16) + "WP"
                                    + localState.board.substring(i - 14, i) + "  "
                                    + localState.board.substring(i + 2);
//                            localState.board[i - 16] = 'W';
//                            localState.board[i - 15] = 'P';
//                            localState.board[i] = ' ';
//                            localState.board[i + 1] = ' ';
                            if (!BotPlayer.isSquareInCheck(localState.board, localState.whiteKingPos, 'B')) {
                                splitOrNot(state, returnState, localState, enemyMove);
                            }
                            if (distFromTop == 6 && state.board.charAt(i - 32) == ' ') { //checking for double jump
                                localState = GameState.coppyState(state);
                                localState.whitePawnsMoved[distFromleft] = true;
                                localState.board = localState.board.substring(0, i - 32) + "WP"
                                        + localState.board.substring(i - 30, i) + "  "
                                        + localState.board.substring(i + 2);
//                                localState.board[i - 32] = 'W';
//                                localState.board[i - 31] = 'P';
//                                localState.board[i] = ' ';
//                                localState.board[i + 1] = ' ';
                                if (!BotPlayer.isSquareInCheck(localState.board, localState.whiteKingPos, 'B')) {
                                    splitOrNot(state, returnState, localState, enemyMove);
                                }
                            }
                        }
                    } else {
                        if (distFromTop == 4) {  //en pesant right
                            if (distFromleft < 7 && state.whitePawnsMoved[distFromleft + 1]) {
                                GameState localState = GameState.coppyState(state);
                                localState.board = localState.board.substring(0, i) + "  "
                                        + localState.board.substring(i + 2, i + 18) + "BP"
                                        + localState.board.substring(i + 20);
//                                localState.board[i + 18] = 'B';
//                                localState.board[i + 19] = 'P';
//                                localState.board[i] = ' ';
//                                localState.board[i + 1] = ' ';
                                if (!BotPlayer.isSquareInCheck(localState.board, localState.blackKingPos, 'W')) {
                                    splitOrNot(state, returnState, localState, enemyMove);
                                }
                            }
                        }
                        if (distFromTop == 4) {  //en pesant left
                            if (distFromleft > 0 && state.whitePawnsMoved[distFromleft - 1]) {
                                GameState localState = GameState.coppyState(state);
                                localState.board = localState.board.substring(0, i) + "  "
                                        + localState.board.substring(i + 2, i + 14) + "BP"
                                        + localState.board.substring(i + 16);
//                                localState.board[i + 14] = 'B';
//                                localState.board[i + 15] = 'P';
//                                localState.board[i] = ' ';
//                                localState.board[i + 1] = ' ';
                                if (!BotPlayer.isSquareInCheck(localState.board, localState.blackKingPos, 'W')) {
                                    splitOrNot(state, returnState, localState, enemyMove);
                                }

                            }
                        }

                        if (distFromleft > 0 && state.board.charAt(i + 14) == 'W') { //capturing left
                            GameState localState = GameState.coppyState(state);
                            if (distFromTop == 6) {
                                localState.board = localState.board.substring(0, i) + "  "
                                        + localState.board.substring(i + 2, i + 14) + "BQ"
                                        + localState.board.substring(i + 16);
                            } else {
                                localState.board = localState.board.substring(0, i) + "  "
                                        + localState.board.substring(i + 2, i + 14) + "BP"
                                        + localState.board.substring(i + 16);
                            }


//                            localState.board[i + 14] = 'B';
//                            localState.board[i + 15] = 'P';
//                            localState.board[i] = ' ';
//                            localState.board[i + 1] = ' ';
                            if (!BotPlayer.isSquareInCheck(localState.board, localState.blackKingPos, 'W')) {
                                splitOrNot(state, returnState, localState, enemyMove);
                            }
                        }
                        if (distFromleft < 7 && state.board.charAt(i + 18) == 'W') { //capturing right
                            GameState localState = GameState.coppyState(state);

                            if (distFromTop == 6) {
                                localState.board = localState.board.substring(0, i) + "  "
                                        + localState.board.substring(i + 2, i + 18) + "BQ"
                                        + localState.board.substring(i + 20);
                            } else {
                                localState.board = localState.board.substring(0, i) + "  "
                                        + localState.board.substring(i + 2, i + 18) + "BP"
                                        + localState.board.substring(i + 20);
                            }

//                            localState.board[i + 18] = 'B';
//                            localState.board[i + 19] = 'P';
//                            localState.board[i] = ' ';
//                            localState.board[i + 1] = ' ';
                            if (!BotPlayer.isSquareInCheck(localState.board, localState.blackKingPos, 'W')) {
                                splitOrNot(state, returnState, localState, enemyMove);
                            }
                        }
                        if (distFromTop == 6 && state.board.charAt(i + 16) == ' ') {
                            GameState localState = GameState.coppyState(state);
                            localState.board = localState.board.substring(0, i) + "  "
                                    + localState.board.substring(i + 2, i + 16) + "BQ"
                                    + localState.board.substring(i + 18);
//                            localState.board[i + 16] = 'B';
//                            localState.board[i + 16] = 'Q';
//                            localState.board[i] = ' ';
//                            localState.board[i + 1] = ' ';
                            if (!BotPlayer.isSquareInCheck(localState.board, localState.blackKingPos, 'W')) {
                                splitOrNot(state, returnState, localState, enemyMove);
                            }
                        } else if (state.board.charAt(i + 16) == ' ' && distFromTop != 6) { //moving forward
                            GameState localState = GameState.coppyState(state);
                            localState.board = localState.board.substring(0, i) + "  "
                                    + localState.board.substring(i + 2, i + 16) + "BP"
                                    + localState.board.substring(i + 18);
//                            localState.board[i + 16] = 'B';
//                            localState.board[i + 17] = 'P';
//                            localState.board[i] = ' ';
//                            localState.board[i + 1] = ' ';
                            if (!BotPlayer.isSquareInCheck(localState.board, localState.blackKingPos, 'W')) {
                                splitOrNot(state, returnState, localState, enemyMove);
                            }
                            if (state.board.charAt(i + 32) == ' ' && distFromTop == 1) { //checking for double jump
                                localState = GameState.coppyState(state);
                                localState.board = localState.board.substring(0, i) + "  "
                                        + localState.board.substring(i + 2, i + 32) + "BP"
                                        + localState.board.substring(i + 34);
                                localState.blackPawnsMoved[distFromleft] = true;
//                                localState.board[i + 32] = 'B';
//                                localState.board[i + 33] = 'P';
//                                localState.board[i] = ' ';
//                                localState.board[i + 1] = ' ';
                                if (!BotPlayer.isSquareInCheck(localState.board, localState.blackKingPos, 'W')) {
                                    GameState result;
                                    if (state.movesMade >= threadExpNum) {
                                        result = BotPlayer.whiteMakeMove(localState, 'W');
                                    } else {
                                        result = MultithreadingStarter.split(localState, 'W');
                                    }
                                    if (result.returnValue <= returnState.returnValue) {
                                        returnState.returnValue = result.returnValue;
                                        returnState.board = localState.board;

                                    }
                                }
                            }
                        }
                    }
                } else if (state.board.charAt(i) == whoseMove && state.board.charAt(i + 1) == 'R') { //rook moves
                    GameState localState = GameState.coppyState(state);
                    int distFromleft = i % 16 / 2;
                    int distFromTop = i / 16;
                    int distFromLeftCoppy = distFromleft;
                    int distFromTopCoppy = distFromTop;
                    int localPos = i - 2;
                    distFromleft--;
                    while (distFromleft >= 0 && state.board.charAt(localPos) == ' ') { //left
                        localState = GameState.coppyState(state);
                        if (i == 126) {
                            localState.bottomRightRookMoved = true;
                        }  else if (i == 14) {
                            localState.topRightRookMoved = true;
                        }
                        localState.board = localState.board.substring(0, localPos) + whoseMove + "R"
                                + localState.board.substring(localPos + 2, i) + "  "
                                + localState.board.substring(i + 2);
//                        localState.board[i] = ' ';
//                        localState.board[i + 1] = ' ';
//                        localState.board[localPos] = whoseMove;
//                        localState.board[localPos + 1] = 'R';
                        localPos -= 2;
                        distFromleft--;
                        if (!BotPlayer.isSquareInCheck(localState.board, (whoseMove == 'W')
                                ? localState.whiteKingPos : localState.blackKingPos, enemyMove)) {
                            splitOrNot(state, returnState, localState, enemyMove);
                        }

                    }
                    if (distFromleft >= 0 && state.board.charAt(localPos) == enemyMove) {
                        localState = GameState.coppyState(state);
                        if (i == 126) {
                            localState.bottomRightRookMoved = true;
                        }  else if (i == 14) {
                            localState.topRightRookMoved = true;
                        }
                        localState.board = localState.board.substring(0, localPos) + whoseMove + "R"
                                + localState.board.substring(localPos + 2, i) + "  "
                                + localState.board.substring(i + 2);
//                        localState.board[i] = ' ';
//                        localState.board[i + 1] = ' ';
//                        localState.board[localPos] = whoseMove;
//                        localState.board[localPos + 1] = 'R';
                        if (!BotPlayer.isSquareInCheck(localState.board, (whoseMove == 'W')
                                ? localState.whiteKingPos : localState.blackKingPos, enemyMove)) {
                            splitOrNot(state, returnState, localState, enemyMove);
                        }
                    }
                    localPos = i - 16;
                    distFromTop--;
                    while (distFromTop >= 0 && state.board.charAt(localPos) == ' ') { //up
                        localState = GameState.coppyState(state);
                        if (i == 112) {
                            localState.bottomLeftRookMoved = true;
                        } else if (i == 126) {
                            localState.bottomRightRookMoved = true;
                        }
                        localState.board = localState.board.substring(0, localPos) + whoseMove + "R"
                                + localState.board.substring(localPos + 2, i) + "  "
                                + localState.board.substring(i + 2);
//                        localState.board[i] = ' ';
//                        localState.board[i + 1] = ' ';
//                        localState.board[localPos] = whoseMove;
//                        localState.board[localPos + 1] = 'R';
                        localPos -= 16;
                        distFromTop--;
                        if (!BotPlayer.isSquareInCheck(localState.board, (whoseMove == 'W')
                                ? localState.whiteKingPos : localState.blackKingPos, enemyMove)) {
                            GameState result;
                            if (state.movesMade >= threadExpNum) {
                                result = BotPlayer.whiteMakeMove(localState, enemyMove);
                            } else {
                                result = MultithreadingStarter.split(localState, enemyMove);
                            }
                            if ((whoseMove == 'W' && result.returnValue >= returnState.returnValue)
                                    || (whoseMove == 'B' && result.returnValue <= returnState.returnValue)) {
                                returnState.returnValue = result.returnValue;
                                returnState.board = localState.board;

                            }
                        }
                    }
                    if (distFromTop >= 0 && state.board.charAt(localPos) == enemyMove) {
                        localState = GameState.coppyState(state);
                        if (i == 112) {
                            localState.bottomLeftRookMoved = true;
                        } else if (i == 126) {
                            localState.bottomRightRookMoved = true;
                        }
                        localState.board = localState.board.substring(0, localPos) + whoseMove + "R"
                                + localState.board.substring(localPos + 2, i) + "  "
                                + localState.board.substring(i + 2);
//                        localState.board[i] = ' ';
//                        localState.board[i + 1] = ' ';
//                        localState.board[localPos] = whoseMove;
//                        localState.board[localPos + 1] = 'R';
                        if (!BotPlayer.isSquareInCheck(localState.board, (whoseMove == 'W')
                                ? localState.whiteKingPos : localState.blackKingPos, enemyMove)) {
                            splitOrNot(state, returnState, localState, enemyMove);
                        }
                    }
                    localPos = i + 2;
                    distFromLeftCoppy++;
                    while (distFromLeftCoppy <= 7 && state.board.charAt(localPos) == ' ') { //right

                        localState = GameState.coppyState(state);
                        if (i == 112) {
                            localState.bottomLeftRookMoved = true;
                        } else if (i == 0) {
                            localState.topLeftRookMoved = true;
                        }
                        localState.board = localState.board.substring(0, i) + "  "
                                + localState.board.substring(i + 2, localPos) + whoseMove + "R"
                                + localState.board.substring(localPos + 2);
//                        localState.board[i] = ' ';
//                        localState.board[i + 1] = ' ';
//                        localState.board[localPos] = whoseMove;
//                        localState.board[localPos + 1] = 'R';
                        localPos += 2;
                        distFromLeftCoppy++;
                        if (!BotPlayer.isSquareInCheck(localState.board, (whoseMove == 'W')
                                ? localState.whiteKingPos : localState.blackKingPos, enemyMove)) {
                            GameState result;
                            if (state.movesMade >= threadExpNum) {
                                result = BotPlayer.whiteMakeMove(localState, enemyMove);
                            } else {
                                result = MultithreadingStarter.split(localState, enemyMove);
                            }
                            if ((whoseMove == 'W' && result.returnValue >= returnState.returnValue)
                                    || (whoseMove == 'B' && result.returnValue <= returnState.returnValue)) {
                                returnState.returnValue = result.returnValue;
                                returnState.board = localState.board;

                            }
                        }
                    }
                    if (distFromLeftCoppy <= 7 && state.board.charAt(localPos) == enemyMove) {
                        localState = GameState.coppyState(state);
                        if (i == 112) {
                            localState.bottomLeftRookMoved = true;
                        } else if (i == 0) {
                            localState.topLeftRookMoved = true;
                        }
                        localState.board = localState.board.substring(0, i) + "  "
                                + localState.board.substring(i + 2, localPos) + whoseMove + "R"
                                + localState.board.substring(localPos + 2);
//                        localState.board[i] = ' ';
//                        localState.board[i + 1] = ' ';
//                        localState.board[localPos] = whoseMove;
//                        localState.board[localPos + 1] = 'R';

                        if (!BotPlayer.isSquareInCheck(localState.board, (whoseMove == 'W') ? localState.whiteKingPos
                                : localState.blackKingPos, enemyMove)) {
                            splitOrNot(state, returnState, localState, enemyMove);
                        }
                    }
                    localPos = i + 16;
                    distFromTopCoppy++;
                    while (distFromTopCoppy < 8 && state.board.charAt(localPos) == ' ') { //down
                        localState = GameState.coppyState(state);
                        if (i == 0) {
                            localState.topLeftRookMoved = true;
                        } else if (i == 14) {
                            localState.topRightRookMoved = true;
                        }
                        localState.board = localState.board.substring(0, i) + "  "
                                + localState.board.substring(i + 2, localPos) + whoseMove + "R"
                                + localState.board.substring(localPos + 2);
//                        localState.board[i] = ' ';
//                        localState.board[i + 1] = ' ';
//                        localState.board[localPos] = whoseMove;
//                        localState.board[localPos + 1] = 'R';
                        localPos += 16;
                        distFromTopCoppy++;
                        if (!BotPlayer.isSquareInCheck(localState.board, (whoseMove == 'W')
                                ? localState.whiteKingPos : localState.blackKingPos, enemyMove)) {
                            GameState result;
                            if (state.movesMade >= threadExpNum) {
                                result = BotPlayer.whiteMakeMove(localState, enemyMove);
                            } else {
                                result = MultithreadingStarter.split(localState, enemyMove);
                            }
                            if ((whoseMove == 'W' && result.returnValue >= returnState.returnValue)
                                    || (whoseMove == 'B' && result.returnValue <= returnState.returnValue)) {
                                returnState.returnValue = result.returnValue;
                                returnState.board = localState.board;

                            }
                        }
                    }
                    if (distFromTopCoppy < 8 && state.board.charAt(localPos) == enemyMove) {
                        localState = GameState.coppyState(state);
                        if (i == 0) {
                            localState.topLeftRookMoved = true;
                        } else if (i == 14) {
                            localState.topRightRookMoved = true;
                        }
                        localState.board = localState.board.substring(0, i) + "  "
                                + localState.board.substring(i + 2, localPos) + whoseMove + "R"
                                + localState.board.substring(localPos + 2);
//                        localState.board[i] = ' ';
//                        localState.board[i + 1] = ' ';
//                        localState.board[localPos] = whoseMove;
//                        localState.board[localPos + 1] = 'R';
                        if (!BotPlayer.isSquareInCheck(localState.board, (whoseMove == 'W') ? localState.whiteKingPos
                                : localState.blackKingPos, enemyMove)) {
                            splitOrNot(state, returnState, localState, enemyMove);
                        }
                    }
                } else if (state.board.charAt(i) == whoseMove && state.board.charAt(i + 1) == 'B') { // bishop
                    //cout << i << endl;
                    GameState localState = GameState.coppyState(state);
                    int distFromleft = i % 16 / 2;
                    int distFromTop = i / 16;
                    int distFromLeftCoppy = distFromleft;
                    int distFromTopCoppy = distFromTop;

                    int localPos = i - 18;
                    distFromLeftCoppy--;
                    distFromTopCoppy--;
                    while (distFromLeftCoppy >= 0 && distFromTopCoppy >= 0
                            && state.board.charAt(localPos) == ' ') { //left up
                        localState = GameState.coppyState(state);
                        localState.board = localState.board.substring(0, localPos) + whoseMove + "B"
                                + localState.board.substring(localPos + 2, i) + "  "
                                + localState.board.substring(i + 2);
//                        localState.board[i] = ' ';
//                        localState.board[i + 1] = ' ';
//                        localState.board[localPos] = whoseMove;
//                        localState.board[localPos + 1] = 'B';
                        localPos -= 18;
                        distFromLeftCoppy--;
                        distFromTopCoppy--;
                        if (!BotPlayer.isSquareInCheck(localState.board, (whoseMove == 'W')
                                ? localState.whiteKingPos : localState.blackKingPos, enemyMove)) {
                            GameState result;
                            if (state.movesMade >= threadExpNum) {
                                result = BotPlayer.whiteMakeMove(localState, enemyMove);
                            } else {
                                result = MultithreadingStarter.split(localState, enemyMove);
                            }
                            if ((whoseMove == 'W' && result.returnValue >= returnState.returnValue)
                                    || (whoseMove == 'B' && result.returnValue <= returnState.returnValue)) {
                                returnState.returnValue = result.returnValue;
                                returnState.board = localState.board;

                            }
                        }
                    }
                    if (distFromLeftCoppy >= 0 && distFromTopCoppy >= 0 && state.board.charAt(localPos) == enemyMove) {

                        localState = GameState.coppyState(state);
                        localState.board = localState.board.substring(0, localPos) + whoseMove + "B"
                                + localState.board.substring(localPos + 2, i) + "  "
                                + localState.board.substring(i + 2);
//                        localState.board[i] = ' ';
//                        localState.board[i + 1] = ' ';
//                        localState.board[localPos] = whoseMove;
//                        localState.board[localPos + 1] = 'B';
                        if (!BotPlayer.isSquareInCheck(localState.board, (whoseMove == 'W')
                                ? localState.whiteKingPos : localState.blackKingPos, enemyMove)) {
                            splitOrNot(state, returnState, localState, enemyMove);
                        }
                    }

                    localPos = i - 14;
                    distFromLeftCoppy = distFromleft;
                    distFromTopCoppy = distFromTop;
                    distFromLeftCoppy++;
                    distFromTopCoppy--;
                    while (distFromTopCoppy >= 0 && distFromLeftCoppy < 8
                            && state.board.charAt(localPos) == ' ') { //up right


                        localState = GameState.coppyState(state);
                        localState.board = localState.board.substring(0, localPos) + whoseMove + "B"
                                + localState.board.substring(localPos + 2, i) + "  "
                                + localState.board.substring(i + 2);
//                        localState.board[i] = ' ';
//                        localState.board[i + 1] = ' ';
//                        localState.board[localPos] = whoseMove;
//                        localState.board[localPos + 1] = 'B';
                        localPos -= 14;
                        distFromTopCoppy--;
                        distFromLeftCoppy++;
                        if (!BotPlayer.isSquareInCheck(localState.board, (whoseMove == 'W')
                                ? localState.whiteKingPos : localState.blackKingPos, enemyMove)) {
                            GameState result;
                            if (state.movesMade >= threadExpNum) {
                                result = BotPlayer.whiteMakeMove(localState, enemyMove);
                            } else {
                                result = MultithreadingStarter.split(localState, enemyMove);
                            }
                            if ((whoseMove == 'W' && result.returnValue >= returnState.returnValue)
                                    || (whoseMove == 'B' && result.returnValue <= returnState.returnValue)) {
                                returnState.returnValue = result.returnValue;
                                returnState.board = localState.board;

                            }
                        }
                    }
                    if (distFromLeftCoppy < 8 && distFromTopCoppy >= 0 && state.board.charAt(localPos) == enemyMove) {
                        localState = GameState.coppyState(state);
                        localState.board = localState.board.substring(0, localPos) + whoseMove + "B"
                                + localState.board.substring(localPos + 2, i) + "  "
                                + localState.board.substring(i + 2);
//                        localState.board[i] = ' ';
//                        localState.board[i + 1] = ' ';
//                        localState.board[localPos] = whoseMove;
//                        localState.board[localPos + 1] = 'B';
                        if (!BotPlayer.isSquareInCheck(localState.board, (whoseMove == 'W')
                                ? localState.whiteKingPos : localState.blackKingPos, enemyMove)) {
                            splitOrNot(state, returnState, localState, enemyMove);
                        }
                    }
                    localPos = i + 14;
                    distFromLeftCoppy = distFromleft;
                    distFromTopCoppy = distFromTop;
                    distFromLeftCoppy--;
                    distFromTopCoppy++;
                    while (distFromTopCoppy < 8 && distFromLeftCoppy >= 0
                            && state.board.charAt(localPos) == ' ') { //downLeft
                        localState = GameState.coppyState(state);
                        localState.board = localState.board.substring(0, i) + "  "
                                + localState.board.substring(i + 2, localPos) + whoseMove + "B"
                                + localState.board.substring(localPos + 2);
//                        localState.board[i] = ' ';
//                        localState.board[i + 1] = ' ';
//                        localState.board[localPos] = whoseMove;
//                        localState.board[localPos + 1] = 'B';
                        localPos += 14;
                        distFromTopCoppy++;
                        distFromLeftCoppy--;
                        if (!BotPlayer.isSquareInCheck(localState.board, (whoseMove == 'W')
                                ? localState.whiteKingPos : localState.blackKingPos, enemyMove)) {
                            GameState result;
                            if (state.movesMade >= threadExpNum) {
                                result = BotPlayer.whiteMakeMove(localState, enemyMove);
                            } else {
                                result = MultithreadingStarter.split(localState, enemyMove);
                            }
                            if ((whoseMove == 'W' && result.returnValue >= returnState.returnValue)
                                    || (whoseMove == 'B' && result.returnValue <= returnState.returnValue)) {
                                returnState.returnValue = result.returnValue;
                                returnState.board = localState.board;

                            }
                        }
                    }
                    if (distFromTopCoppy < 8 && distFromLeftCoppy >= 0 && state.board.charAt(localPos) == enemyMove) {
                        localState = GameState.coppyState(state);
                        localState.board = localState.board.substring(0, i) + "  "
                                + localState.board.substring(i + 2, localPos) + whoseMove + "B"
                                + localState.board.substring(localPos + 2);
//                        localState.board[i] = ' ';
//                        localState.board[i + 1] = ' ';
//                        localState.board[localPos] = whoseMove;
//                        localState.board[localPos + 1] = 'B';
                        if (!BotPlayer.isSquareInCheck(localState.board, (whoseMove == 'W') ? localState.whiteKingPos
                                : localState.blackKingPos, enemyMove)) {
                            splitOrNot(state, returnState, localState, enemyMove);
                        }
                    }

                    localPos = i + 18;
                    distFromLeftCoppy = distFromleft;
                    distFromTopCoppy = distFromTop;
                    distFromLeftCoppy++;
                    distFromTopCoppy++;
                    while (distFromTopCoppy < 8 && distFromLeftCoppy < 8
                            && state.board.charAt(localPos) == ' ') { //downRight
                        localState = GameState.coppyState(state);
                        localState.board = localState.board.substring(0, i) + "  "
                                + localState.board.substring(i + 2, localPos) + whoseMove
                                + "B" + localState.board.substring(localPos + 2);
//                        localState.board[i] = ' ';
//                        localState.board[i + 1] = ' ';
//                        localState.board[localPos] = whoseMove;
//                        localState.board[localPos + 1] = 'B';
                        localPos += 18;
                        distFromTopCoppy++;
                        distFromLeftCoppy++;
                        if (!BotPlayer.isSquareInCheck(localState.board, (whoseMove == 'W')
                                ? localState.whiteKingPos : localState.blackKingPos, enemyMove)) {
                            GameState result;
                            if (state.movesMade >= threadExpNum) {
                                result = BotPlayer.whiteMakeMove(localState, enemyMove);
                            } else {
                                result = MultithreadingStarter.split(localState, enemyMove);
                            }
                            if ((whoseMove == 'W' && result.returnValue >= returnState.returnValue)
                                    || (whoseMove == 'B' && result.returnValue <= returnState.returnValue)) {
                                returnState.returnValue = result.returnValue;
                                returnState.board = localState.board;

                            }
                        }
                    }
                    if (distFromTopCoppy < 8 && distFromLeftCoppy < 8 && state.board.charAt(localPos) == enemyMove) {
                        localState = GameState.coppyState(state);
                        localState.board = localState.board.substring(0, i) + "  "
                                + localState.board.substring(i + 2, localPos) + whoseMove + "B"
                                + localState.board.substring(localPos + 2);
//                        localState.board[i] = ' ';
//                        localState.board[i + 1] = ' ';
//                        localState.board[localPos] = whoseMove;
//                        localState.board[localPos + 1] = 'B';
                        if (!BotPlayer.isSquareInCheck(localState.board, (whoseMove == 'W') ? localState.whiteKingPos
                                : localState.blackKingPos, enemyMove)) {
                            splitOrNot(state, returnState, localState, enemyMove);
                        }
                    }
                } else if (state.board.charAt(i) == whoseMove && state.board.charAt(i + 1) == 'Q') { //Queen
                    GameState localState = GameState.coppyState(state);
                    int distFromleft = i % 16 / 2;
                    int distFromTop = i / 16;
                    int distFromLeftCoppy = distFromleft;
                    int distFromTopCoppy = distFromTop;
                    int localPos = i - 2;
                    distFromleft--;
                    while (distFromleft >= 0 && state.board.charAt(localPos) == ' ') { //left
                        localState = GameState.coppyState(state);
                        localState.board = localState.board.substring(0, localPos) + whoseMove + "Q"
                                + localState.board.substring(localPos + 2, i) + "  "
                                + localState.board.substring(i + 2);
//                        localState.board[i] = ' ';
//                        localState.board[i + 1] = ' ';
//                        localState.board[localPos] = whoseMove;
//                        localState.board[localPos + 1] = 'R';
                        localPos -= 2;
                        distFromleft--;
                        if (!BotPlayer.isSquareInCheck(localState.board, (whoseMove == 'W')
                                ? localState.whiteKingPos : localState.blackKingPos, enemyMove)) {
                            splitOrNot(state, returnState, localState, enemyMove);
                        }

                    }
                    if (distFromleft >= 0 && state.board.charAt(localPos) == enemyMove) {
                        localState = GameState.coppyState(state);
                        localState.board = localState.board.substring(0, localPos) + whoseMove + "Q"
                                + localState.board.substring(localPos + 2, i) + "  "
                                + localState.board.substring(i + 2);
//                        localState.board[i] = ' ';
//                        localState.board[i + 1] = ' ';
//                        localState.board[localPos] = whoseMove;
//                        localState.board[localPos + 1] = 'R';
                        if (!BotPlayer.isSquareInCheck(localState.board, (whoseMove == 'W')
                                ? localState.whiteKingPos : localState.blackKingPos, enemyMove)) {
                            splitOrNot(state, returnState, localState, enemyMove);
                        }
                    }
                    localPos = i - 16;
                    distFromTop--;
                    while (distFromTop >= 0 && state.board.charAt(localPos) == ' ') { //up
                        localState = GameState.coppyState(state);

                        localState.board = localState.board.substring(0, localPos) + whoseMove + "Q"
                                + localState.board.substring(localPos + 2, i) + "  "
                                + localState.board.substring(i + 2);
//                        localState.board[i] = ' ';
//                        localState.board[i + 1] = ' ';
//                        localState.board[localPos] = whoseMove;
//                        localState.board[localPos + 1] = 'R';
                        localPos -= 16;
                        distFromTop--;
                        if (!BotPlayer.isSquareInCheck(localState.board, (whoseMove == 'W')
                                ? localState.whiteKingPos : localState.blackKingPos, enemyMove)) {
                            GameState result;
                            if (state.movesMade >= threadExpNum) {
                                result = BotPlayer.whiteMakeMove(localState, enemyMove);
                            } else {
                                result = MultithreadingStarter.split(localState, enemyMove);
                            }
                            if ((whoseMove == 'W' && result.returnValue >= returnState.returnValue)
                                    || (whoseMove == 'B' && result.returnValue <= returnState.returnValue)) {
                                returnState.returnValue = result.returnValue;
                                returnState.board = localState.board;

                            }
                        }
                    }
                    if (distFromTop >= 0 && state.board.charAt(localPos) == enemyMove) {
                        localState = GameState.coppyState(state);
                        localState.board = localState.board.substring(0, localPos) + whoseMove + "Q"
                                + localState.board.substring(localPos + 2, i) + "  "
                                + localState.board.substring(i + 2);
//                        localState.board[i] = ' ';
//                        localState.board[i + 1] = ' ';
//                        localState.board[localPos] = whoseMove;
//                        localState.board[localPos + 1] = 'R';
                        if (!BotPlayer.isSquareInCheck(localState.board, (whoseMove == 'W')
                                ? localState.whiteKingPos : localState.blackKingPos, enemyMove)) {
                            splitOrNot(state, returnState, localState, enemyMove);
                        }
                    }
                    localPos = i + 2;
                    distFromLeftCoppy++;
                    while (distFromLeftCoppy <= 7 && state.board.charAt(localPos) == ' ') { //right

                        localState = GameState.coppyState(state);
                        localState.board = localState.board.substring(0, i) + "  "
                                + localState.board.substring(i + 2, localPos) + whoseMove + "Q"
                                + localState.board.substring(localPos + 2);
//                        localState.board[i] = ' ';
//                        localState.board[i + 1] = ' ';
//                        localState.board[localPos] = whoseMove;
//                        localState.board[localPos + 1] = 'R';
                        localPos += 2;
                        distFromLeftCoppy++;
                        if (!BotPlayer.isSquareInCheck(localState.board, (whoseMove == 'W')
                                ? localState.whiteKingPos : localState.blackKingPos, enemyMove)) {
                            GameState result;
                            if (state.movesMade >= threadExpNum) {
                                result = BotPlayer.whiteMakeMove(localState, enemyMove);
                            } else {
                                result = MultithreadingStarter.split(localState, enemyMove);
                            }
                            if ((whoseMove == 'W' && result.returnValue >= returnState.returnValue)
                                    || (whoseMove == 'B' && result.returnValue <= returnState.returnValue)) {
                                returnState.returnValue = result.returnValue;
                                returnState.board = localState.board;

                            }
                        }
                    }
                    if (distFromLeftCoppy <= 7 && state.board.charAt(localPos) == enemyMove) {
                        localState = GameState.coppyState(state);
                        localState.board = localState.board.substring(0, i) + "  "
                                + localState.board.substring(i + 2, localPos) + whoseMove + "Q"
                                + localState.board.substring(localPos + 2);
//                        localState.board[i] = ' ';
//                        localState.board[i + 1] = ' ';
//                        localState.board[localPos] = whoseMove;
//                        localState.board[localPos + 1] = 'R';

                        if (!BotPlayer.isSquareInCheck(localState.board, (whoseMove == 'W')
                                ? localState.whiteKingPos : localState.blackKingPos, enemyMove)) {
                            splitOrNot(state, returnState, localState, enemyMove);
                        }
                    }
                    localPos = i + 16;
                    distFromTopCoppy++;
                    while (distFromTopCoppy < 8 && state.board.charAt(localPos) == ' ') { //down
                        localState = GameState.coppyState(state);
                        localState.board = localState.board.substring(0, i) + "  "
                                + localState.board.substring(i + 2, localPos) + whoseMove + "Q"
                                + localState.board.substring(localPos + 2);
//                        localState.board[i] = ' ';
//                        localState.board[i + 1] = ' ';
//                        localState.board[localPos] = whoseMove;
//                        localState.board[localPos + 1] = 'R';
                        localPos += 16;
                        distFromTopCoppy++;
                        if (!BotPlayer.isSquareInCheck(localState.board, (whoseMove == 'W')
                                ? localState.whiteKingPos : localState.blackKingPos, enemyMove)) {
                            splitOrNot(state, returnState, localState, enemyMove);
                        }
                    }
                    if (distFromTopCoppy < 8 && state.board.charAt(localPos) == enemyMove) {
                        localState = GameState.coppyState(state);
                        localState.board = localState.board.substring(0, i) + "  "
                                + localState.board.substring(i + 2, localPos) + whoseMove + "Q"
                                + localState.board.substring(localPos + 2);
//                        localState.board[i] = ' ';
//                        localState.board[i + 1] = ' ';
//                        localState.board[localPos] = whoseMove;
//                        localState.board[localPos + 1] = 'R';
                        if (!BotPlayer.isSquareInCheck(localState.board, (whoseMove == 'W')
                                ? localState.whiteKingPos : localState.blackKingPos, enemyMove)) {
                            splitOrNot(state, returnState, localState, enemyMove);
                        }
                    }
                    distFromleft = i % 16 / 2;
                    distFromTop = i / 16;
                    distFromLeftCoppy = distFromleft;
                    distFromTopCoppy = distFromTop;

                    localPos = i - 18;
                    distFromLeftCoppy--;
                    distFromTopCoppy--;
                    while (distFromLeftCoppy >= 0 && distFromTopCoppy >= 0
                            && state.board.charAt(localPos) == ' ') { //left up
                        localState = GameState.coppyState(state);
                        localState.board = localState.board.substring(0, localPos) + whoseMove + "Q"
                                + localState.board.substring(localPos + 2, i) + "  "
                                + localState.board.substring(i + 2);
//                        localState.board[i] = ' ';
//                        localState.board[i + 1] = ' ';
//                        localState.board[localPos] = whoseMove;
//                        localState.board[localPos + 1] = 'B';
                        localPos -= 18;
                        distFromLeftCoppy--;
                        distFromTopCoppy--;
                        if (!BotPlayer.isSquareInCheck(localState.board, (whoseMove == 'W')
                                ? localState.whiteKingPos : localState.blackKingPos, enemyMove)) {
                            splitOrNot(state, returnState, localState, enemyMove);
                        }
                    }
                    if (distFromLeftCoppy >= 0 && distFromTopCoppy >= 0 && state.board.charAt(localPos) == enemyMove) {

                        localState = GameState.coppyState(state);
                        localState.board = localState.board.substring(0, localPos) + whoseMove + "Q"
                                + localState.board.substring(localPos + 2, i) + "  "
                                + localState.board.substring(i + 2);
//                        localState.board[i] = ' ';
//                        localState.board[i + 1] = ' ';
//                        localState.board[localPos] = whoseMove;
//                        localState.board[localPos + 1] = 'B';
                        if (!BotPlayer.isSquareInCheck(localState.board, (whoseMove == 'W')
                                ? localState.whiteKingPos : localState.blackKingPos, enemyMove)) {
                            splitOrNot(state, returnState, localState, enemyMove);
                        }
                    }

                    localPos = i - 14;
                    distFromLeftCoppy = distFromleft;
                    distFromTopCoppy = distFromTop;
                    distFromLeftCoppy++;
                    distFromTopCoppy--;
                    while (distFromTopCoppy >= 0 && distFromLeftCoppy < 8
                            && state.board.charAt(localPos) == ' ') { //up right


                        localState = GameState.coppyState(state);
                        localState.board = localState.board.substring(0, localPos) + whoseMove + "Q"
                                + localState.board.substring(localPos + 2, i) + "  "
                                + localState.board.substring(i + 2);
//                        localState.board[i] = ' ';
//                        localState.board[i + 1] = ' ';
//                        localState.board[localPos] = whoseMove;
//                        localState.board[localPos + 1] = 'B';
                        localPos -= 14;
                        distFromTopCoppy--;
                        distFromLeftCoppy++;
                        if (!BotPlayer.isSquareInCheck(localState.board, (whoseMove == 'W')
                                ? localState.whiteKingPos : localState.blackKingPos, enemyMove)) {
                            splitOrNot(state, returnState, localState, enemyMove);
                        }
                    }
                    if (distFromLeftCoppy < 8 && distFromTopCoppy >= 0 && state.board.charAt(localPos) == enemyMove) {
                        localState = GameState.coppyState(state);
                        localState.board = localState.board.substring(0, localPos) + whoseMove + "Q"
                                + localState.board.substring(localPos + 2, i) + "  "
                                + localState.board.substring(i + 2);
//                        localState.board[i] = ' ';
//                        localState.board[i + 1] = ' ';
//                        localState.board[localPos] = whoseMove;
//                        localState.board[localPos + 1] = 'B';
                        if (!BotPlayer.isSquareInCheck(localState.board, (whoseMove == 'W')
                                ? localState.whiteKingPos : localState.blackKingPos, enemyMove)) {

                            GameState result;
                            splitOrNot(state, returnState, localState, enemyMove);
                        }
                    }
                    localPos = i + 14;
                    distFromLeftCoppy = distFromleft;
                    distFromTopCoppy = distFromTop;
                    distFromLeftCoppy--;
                    distFromTopCoppy++;
                    while (distFromTopCoppy < 8 && distFromLeftCoppy >= 0
                            && state.board.charAt(localPos) == ' ') { //downLeft
                        localState = GameState.coppyState(state);
                        localState.board = localState.board.substring(0, i) + "  "
                                + localState.board.substring(i + 2, localPos) + whoseMove + "Q"
                                + localState.board.substring(localPos + 2);
//                        localState.board[i] = ' ';
//                        localState.board[i + 1] = ' ';
//                        localState.board[localPos] = whoseMove;
//                        localState.board[localPos + 1] = 'B';
                        localPos += 14;
                        distFromTopCoppy++;
                        distFromLeftCoppy--;
                        if (!BotPlayer.isSquareInCheck(localState.board, (whoseMove == 'W')
                                ? localState.whiteKingPos : localState.blackKingPos, enemyMove)) {
                            splitOrNot(state, returnState, localState, enemyMove);
                        }
                    }
                    if (distFromTopCoppy < 8 && distFromLeftCoppy >= 0 && state.board.charAt(localPos) == enemyMove) {
                        localState = GameState.coppyState(state);
                        localState.board = localState.board.substring(0, i) + "  "
                                + localState.board.substring(i + 2, localPos) + whoseMove + "Q"
                                + localState.board.substring(localPos + 2);
//                        localState.board[i] = ' ';
//                        localState.board[i + 1] = ' ';
//                        localState.board[localPos] = whoseMove;
//                        localState.board[localPos + 1] = 'B';
                        if (!BotPlayer.isSquareInCheck(localState.board, (whoseMove == 'W')
                                ? localState.whiteKingPos : localState.blackKingPos, enemyMove)) {
                            splitOrNot(state, returnState, localState, enemyMove);
                        }
                    }

                    localPos = i + 18;
                    distFromLeftCoppy = distFromleft;
                    distFromTopCoppy = distFromTop;
                    distFromLeftCoppy++;
                    distFromTopCoppy++;
                    while (distFromTopCoppy < 8 && distFromLeftCoppy < 8
                            && state.board.charAt(localPos) == ' ') { //downRight
                        localState = GameState.coppyState(state);
                        localState.board = localState.board.substring(0, i) + "  "
                                + localState.board.substring(i + 2, localPos) + whoseMove + "Q"
                                + localState.board.substring(localPos + 2);
//                        localState.board[i] = ' ';
//                        localState.board[i + 1] = ' ';
//                        localState.board[localPos] = whoseMove;
//                        localState.board[localPos + 1] = 'B';
                        localPos += 18;
                        distFromTopCoppy++;
                        distFromLeftCoppy++;
                        if (!BotPlayer.isSquareInCheck(localState.board, (whoseMove == 'W')
                                ? localState.whiteKingPos : localState.blackKingPos, enemyMove)) {
                            splitOrNot(state, returnState, localState, enemyMove);
                        }
                    }
                    if (distFromTopCoppy < 8 && distFromLeftCoppy < 8 && state.board.charAt(localPos) == enemyMove) {
                        localState = GameState.coppyState(state);
                        localState.board = localState.board.substring(0, i) + "  "
                                + localState.board.substring(i + 2, localPos) + whoseMove + "Q"
                                + localState.board.substring(localPos + 2);
//                        localState.board[i] = ' ';
//                        localState.board[i + 1] = ' ';
//                        localState.board[localPos] = whoseMove;
//                        localState.board[localPos + 1] = 'B';
                        if (!BotPlayer.isSquareInCheck(localState.board, (whoseMove == 'W')
                                ? localState.whiteKingPos : localState.blackKingPos, enemyMove)) {
                            splitOrNot(state, returnState, localState, enemyMove);
                        }
                    }
                } else if (state.board.charAt(i) == whoseMove && state.board.charAt(i + 1) == 'K') { //king
                    int distFromleft = i % 16 / 2;
                    int distFromTop = i / 16;
                    GameState localState = GameState.coppyState(state);

//                    localState.board[i] = ' ';
//                    localState.board[i + 1] = ' ';

                    if (distFromleft < 7 && (localState.board.charAt(i + 2) == ' '
                            || localState.board.charAt(i + 2) == enemyMove)
                            && !BotPlayer.isSquareInCheck(localState.board, i + 2, enemyMove)) {
                        localState.board = localState.board.substring(0, i) + "  " + whoseMove + "K"
                                + localState.board.substring(i + 4);
//                        localState.board[i + 2] = whoseMove;
//                        localState.board[i + 3] = 'K';
                        if (whoseMove == 'W') {
                            localState.whiteKingPos = i + 2;
                            localState.whiteKingMoved = true;
                        } else {
                            localState.blackKingPos = i + 2;
                            localState.blackKingMoved = true;
                        }
                        splitOrNot(state, returnState, localState, enemyMove);
                    }
                    localState = GameState.coppyState(state);

//                    localState.board[i] = ' ';
//                    localState.board[i + 1] = ' ';

                    if (distFromleft > 0 && (localState.board.charAt(i - 2) == ' '
                            || localState.board.charAt(i - 2) == enemyMove)
                            && !BotPlayer.isSquareInCheck(localState.board, i - 2, enemyMove)) {
                        localState.board = localState.board.substring(0, i - 2) + whoseMove + "K" + "  "
                                + localState.board.substring(i + 2);
//                        localState.board[i - 2] = whoseMove;
//                        localState.board[i - 1] = 'K';
                        if (whoseMove == 'W') {
                            localState.whiteKingPos = i - 2;
                            localState.whiteKingMoved = true;
                        } else {
                            localState.blackKingPos = i - 2;
                            localState.blackKingMoved = true;
                        }
                        splitOrNot(state, returnState, localState, enemyMove);
                    }
                    localState = GameState.coppyState(state);

//                    localState.board[i] = ' ';
//                    localState.board[i + 1] = ' ';
                    if (distFromTop < 7 && (localState.board.charAt(i + 16) == ' '
                            || localState.board.charAt(i + 16) == enemyMove)
                            && !BotPlayer.isSquareInCheck(localState.board, i + 16, enemyMove)) {
                        localState.board = localState.board.substring(0, i) + "  "
                                + localState.board.substring(i + 2, i + 16) + whoseMove + "K"
                                + localState.board.substring(i + 18);
//                        localState.board[i + 16] = whoseMove;
//                        localState.board[i + 17] = 'K';
                        if (whoseMove == 'W') {
                            localState.whiteKingPos = i + 16;
                            localState.whiteKingMoved = true;
                        } else {
                            localState.blackKingPos = i + 16;
                            localState.blackKingMoved = true;
                        }
                        splitOrNot(state, returnState, localState, enemyMove);
                    }
                    localState = GameState.coppyState(state);

//                    localState.board[i] = ' ';
//                    localState.board[i + 1] = ' ';
                    if (distFromTop > 0 && (localState.board.charAt(i - 16) == ' '
                            || localState.board.charAt(i - 16) == enemyMove)
                            && !BotPlayer.isSquareInCheck(localState.board, i - 16, enemyMove)) {
                        localState.board = localState.board.substring(0, i - 16) + whoseMove + "K"
                                + localState.board.substring(i - 14, i) + "  "
                                + localState.board.substring(i + 2);
//                        localState.board[i - 16] = whoseMove;
//                        localState.board[i - 15] = 'K';
                        if (whoseMove == 'W') {
                            localState.whiteKingPos = i - 16;
                            localState.whiteKingMoved = true;
                        } else {
                            localState.blackKingPos = i - 16;
                            localState.blackKingMoved = true;
                        }
                        splitOrNot(state, returnState, localState, enemyMove);
                    }
                    localState = GameState.coppyState(state);

//                    localState.board[i] = ' ';
//                    localState.board[i + 1] = ' ';
                    if (distFromTop > 0 && distFromleft > 0 && (localState.board.charAt(i - 18) == ' '
                            || localState.board.charAt(i - 18) == enemyMove)
                            && !BotPlayer.isSquareInCheck(localState.board, i - 18, enemyMove)) {
                        localState.board = localState.board.substring(0, i - 18) + whoseMove + "K"
                                + localState.board.substring(i - 16, i) + "  "
                                + localState.board.substring(i + 2);
//                        localState.board[i - 18] = whoseMove;
//                        localState.board[i - 17] = 'K';
                        if (whoseMove == 'W') {
                            localState.whiteKingPos = i - 18;
                            localState.whiteKingMoved = true;
                        } else {
                            localState.blackKingPos = i - 18;
                            localState.blackKingMoved = true;
                        }
                        splitOrNot(state, returnState, localState, enemyMove);
                    }
                    localState = GameState.coppyState(state);

//                    localState.board[i] = ' ';
//                    localState.board[i + 1] = ' ';

                    if (distFromTop > 0 && distFromleft < 7 && (localState.board.charAt(i - 14) == ' '
                            || localState.board.charAt(i - 14) == enemyMove)
                            && !BotPlayer.isSquareInCheck(localState.board, i - 14, enemyMove)) {
                        localState.board = localState.board.substring(0, i - 14) + whoseMove + "K"
                                + localState.board.substring(i - 12, i) + "  "
                                + localState.board.substring(i + 2);
//                        localState.board[i - 14] = whoseMove;
//                        localState.board[i - 13] = 'K';
                        if (whoseMove == 'W') {
                            localState.whiteKingPos = i - 14;
                            localState.whiteKingMoved = true;
                        } else {
                            localState.blackKingPos = i - 14;
                            localState.blackKingMoved = true;
                        }
                        splitOrNot(state, returnState, localState, enemyMove);
                    }
                    localState = GameState.coppyState(state);

//                    localState.board[i] = ' ';
//                    localState.board[i + 1] = ' ';
                    if (distFromTop < 7 && distFromleft > 0 && (localState.board.charAt(i + 14) == ' '
                            || localState.board.charAt(i + 14) == enemyMove)
                            && !BotPlayer.isSquareInCheck(localState.board, i + 14, enemyMove)) {
                        localState.board = localState.board.substring(0, i) + "  "
                                + localState.board.substring(i + 2, i + 14) + whoseMove + 'K'
                                + localState.board.substring(i + 16);
//                        localState.board[i + 14] = whoseMove;
//                        localState.board[i + 15] = 'K';
                        if (whoseMove == 'W') {
                            localState.whiteKingPos = i + 14;
                            localState.whiteKingMoved = true;
                        } else {
                            localState.blackKingPos = i + 14;
                            localState.blackKingMoved = true;
                        }
                        splitOrNot(state, returnState, localState, enemyMove);
                    }
                    localState = GameState.coppyState(state);

//                    localState.board[i] = ' ';
//                    localState.board[i + 1] = ' ';
                    if (distFromleft < 7 && distFromTop < 7 && (localState.board.charAt(i + 18) == ' '
                            || localState.board.charAt(i + 18) == enemyMove)
                            && !BotPlayer.isSquareInCheck(localState.board, i + 18, enemyMove)) {
                        localState.board = localState.board.substring(0, i) + "  "
                                + localState.board.substring(i + 2, i + 18) + whoseMove + 'K'
                                + localState.board.substring(i + 20);
//                        localState.board[i + 18] = whoseMove;
//                        localState.board[i + 19] = 'K';
                        if (whoseMove == 'W') {
                            localState.whiteKingPos = i + 18;
                            localState.whiteKingMoved = true;
                        } else {
                            localState.blackKingPos = i + 18;
                            localState.blackKingMoved = true;
                        }
                        splitOrNot(state, returnState, localState, enemyMove);
                    }
                } else if (state.board.charAt(i) == whoseMove && state.board.charAt(i + 1) == 'H') {

                    int distFromleft = i % 16 / 2;
                    int distFromTop = i / 16;
                    GameState localState = GameState.coppyState(state);
                    if (distFromleft >= 1 && distFromTop >= 2 && (state.board.charAt(i - 34) == enemyMove
                            || state.board.charAt(i - 34) == ' ')) {
                        localState.board = localState.board.substring(0, i - 34) + whoseMove + "H"
                                + localState.board.substring(i - 32, i) + "  "
                                + localState.board.substring(i + 2);
//                        localState.board[i] = ' ';
//                        localState.board[i + 1] = ' ';
//                        localState.board[i - 34] = whoseMove;
//                        localState.board[i - 33] = 'H';
                        if (!BotPlayer.isSquareInCheck(localState.board, (whoseMove == 'W')
                                ? localState.whiteKingPos : localState.blackKingPos, enemyMove)) {
                            splitOrNot(state, returnState, localState, enemyMove);
                        }
                    }
                    localState = GameState.coppyState(state);
                    if (distFromleft <= 6 && distFromTop >= 2 && (state.board.charAt(i - 30) == enemyMove
                            || state.board.charAt(i - 30) == ' ')) {
                        localState.board = localState.board.substring(0, i - 30) + whoseMove + "H"
                                + localState.board.substring(i - 28, i) + "  "
                                + localState.board.substring(i + 2);
//                        localState.board[i] = ' ';
//                        localState.board[i + 1] = ' ';
//                        localState.board[i - 30] = whoseMove;
//                        localState.board[i - 29] = 'H';
                        if (!BotPlayer.isSquareInCheck(localState.board, (whoseMove == 'W')
                                ? localState.whiteKingPos : localState.blackKingPos, enemyMove)) {
                            splitOrNot(state, returnState, localState, enemyMove);
                        }
                    }

                    localState = GameState.coppyState(state);
                    if (distFromleft <= 5 && distFromTop >= 1 && (state.board.charAt(i - 12) == enemyMove
                            || state.board.charAt(i - 12) == ' ')) {
                        localState.board = localState.board.substring(0, i - 12) + whoseMove + "H"
                                + localState.board.substring(i - 10, i) + "  "
                                + localState.board.substring(i + 2);
//                        localState.board[i] = ' ';
//                        localState.board[i + 1] = ' ';
//                        localState.board[i - 12] = whoseMove;
//                        localState.board[i - 11] = 'H';
                        if (!BotPlayer.isSquareInCheck(localState.board, (whoseMove == 'W')
                                ? localState.whiteKingPos : localState.blackKingPos, enemyMove)) {
                            splitOrNot(state, returnState, localState, enemyMove);
                        }
                    }
                    localState = GameState.coppyState(state);
                    if (distFromleft <= 5 && distFromTop <= 6 && (state.board.charAt(i + 20) == enemyMove
                            || state.board.charAt(i + 20) == ' ')) {
                        localState.board = localState.board.substring(0, i) + "  "
                                + localState.board.substring(i + 2, i + 20) + whoseMove + "H"
                                + localState.board.substring(i + 22);
//                        localState.board[i] = ' ';
//                        localState.board[i + 1] = ' ';
//                        localState.board[i + 20] = whoseMove;
//                        localState.board[i + 21] = 'H';
                        if (!BotPlayer.isSquareInCheck(localState.board, (whoseMove == 'W')
                                ? localState.whiteKingPos : localState.blackKingPos, enemyMove)) {
                            splitOrNot(state, returnState, localState, enemyMove);
                        }
                    }
                    localState = GameState.coppyState(state);
                    if (distFromleft <= 6 && distFromTop <= 5 && (state.board.charAt(i + 34) == enemyMove
                            || state.board.charAt(i + 34) == ' ')) {
                        localState.board = localState.board.substring(0, i) + "  "
                                + localState.board.substring(i + 2, i + 34) + whoseMove + "H"
                                + localState.board.substring(i + 36);
//                        localState.board[i] = ' ';
//                        localState.board[i + 1] = ' ';
//                        localState.board[i + 34] = whoseMove;
//                        localState.board[i + 35] = 'H';
                        if (!BotPlayer.isSquareInCheck(localState.board, (whoseMove == 'W')
                                ? localState.whiteKingPos : localState.blackKingPos, enemyMove)) {
                            splitOrNot(state, returnState, localState, enemyMove);
                        }
                    }

                    localState = GameState.coppyState(state);
                    if (distFromleft >= 1 && distFromTop <= 5 && (state.board.charAt(i + 30) == enemyMove
                            || state.board.charAt(i + 30) == ' ')) {
                        localState.board = localState.board.substring(0, i) + "  "
                                + localState.board.substring(i + 2, i + 30) + whoseMove + "H"
                                + localState.board.substring(i + 32);
//                        localState.board[i] = ' ';
//                        localState.board[i + 1] = ' ';
//                        localState.board[i + 30] = whoseMove;
//                        localState.board[i + 31] = 'H';
                        if (!BotPlayer.isSquareInCheck(localState.board, (whoseMove == 'W')
                                ? localState.whiteKingPos : localState.blackKingPos, enemyMove)) {
                            splitOrNot(state, returnState, localState, enemyMove);
                        }
                    }
                    localState = GameState.coppyState(state);
                    if (distFromleft >= 2 && distFromTop <= 6 && (state.board.charAt(i + 12) == enemyMove
                            || state.board.charAt(i + 12) == ' ')) {
                        localState.board = localState.board.substring(0, i) + "  "
                                + localState.board.substring(i + 2, i + 12) + whoseMove + "H"
                                + localState.board.substring(i + 14);
//                        localState.board[i] = ' ';
//                        localState.board[i + 1] = ' ';
//                        localState.board[i + 12] = whoseMove;
//                        localState.board[i + 13] = 'H';
                        if (!BotPlayer.isSquareInCheck(localState.board, (whoseMove == 'W')
                                ? localState.whiteKingPos : localState.blackKingPos, enemyMove)) {
                            splitOrNot(state, returnState, localState, enemyMove);
                        }
                    }

                    localState = GameState.coppyState(state);

                    if (distFromleft >= 2 && distFromTop >= 1 && (state.board.charAt(i - 20) == enemyMove
                            || state.board.charAt(i - 20) == ' ')) {
                        localState.board = localState.board.substring(0, i - 20) + whoseMove + "H"
                                + localState.board.substring(i - 18, i) + "  "
                                + localState.board.substring(i + 2);
//                        localState.board[i] = ' ';
//                        localState.board[i + 1] = ' ';
//                        localState.board[i - 20] = whoseMove;
//                        localState.board[i - 19] = 'H';
                        if (!BotPlayer.isSquareInCheck(localState.board, (whoseMove == 'W')
                                ? localState.whiteKingPos : localState.blackKingPos, enemyMove)) {
                            splitOrNot(state, returnState, localState, enemyMove);
                        }
                    }
                }
            }


            GameState localState = GameState.coppyState(state);

            if (whoseMove == 'W') { //castling bottom left
                if (!state.whiteKingMoved
                        && !state.bottomLeftRookMoved && state.board.charAt(118) == ' '
                        && state.board.charAt(116) == ' ' && state.board.charAt(114) == ' '
                        && !BotPlayer.isSquareInCheck(state.board, 120, 'B')
                        && !BotPlayer.isSquareInCheck(state.board, 118, 'B')
                        && !BotPlayer.isSquareInCheck(state.board, 116, 'B')) {
                    localState.board = localState.board.substring(0, 112) + "    WKWR  "
                            + localState.board.substring(122);

//                        localState.board[112] = ' ';
//                        localState.board[113] = ' ';
//                        localState.board[116] = 'W';
//                        localState.board[117] = 'K';
//                        localState.board[118] = 'W';
//                        localState.board[119] = 'R';
                    localState.whiteKingPos = 116;
                    splitOrNot(state, returnState, localState, enemyMove);

                }
                localState = GameState.coppyState(state);
                if (!state.whiteKingMoved && !state.bottomRightRookMoved && state.board.charAt(122) == ' '
                        && state.board.charAt(124) == ' ' && !BotPlayer.isSquareInCheck(state.board, 120, 'B')
                        && !BotPlayer.isSquareInCheck(state.board, 122, 'B')
                        && !BotPlayer.isSquareInCheck(state.board, 124, 'B')) {
                    localState.board = localState.board.substring(0, 120) + "  WRWK  ";

//                        localState.board[126] = ' ';
//                        localState.board[127] = ' ';
//                        localState.board[124] = 'W';
//                        localState.board[125] = 'K';
//                        localState.board[122] = 'W';
//                        localState.board[123] = 'R';
                    localState.whiteKingPos = 124;
                    splitOrNot(state, returnState, localState, enemyMove);
                }
            } else {
                if (!state.blackKingMoved && !state.topLeftRookMoved && state.board.charAt(2) == ' '
                        && state.board.charAt(4) == ' ' && state.board.charAt(6) == ' '
                        && !BotPlayer.isSquareInCheck(state.board, 4, 'W')
                        && !BotPlayer.isSquareInCheck(state.board, 6, 'W')
                        && !BotPlayer.isSquareInCheck(state.board, 8, 'W')) {
                    localState.board = "    bkbr  " + localState.board.substring(10);

//                        localState.board[0] = ' ';
//                        localState.board[1] = ' ';
//                        localState.board[4] = 'B';
//                        localState.board[5] = 'K';
//                        localState.board[6] = 'B';
//                        localState.board[7] = 'R';
                    localState.blackKingPos = 4;
                    splitOrNot(state, returnState, localState, enemyMove);
                }
                localState = GameState.coppyState(state);
                if (!state.blackKingMoved && !state.topRightRookMoved && state.board.charAt(10) == ' '
                        && state.board.charAt(12) == ' ' && !BotPlayer.isSquareInCheck(state.board, 8, 'W')
                        && !BotPlayer.isSquareInCheck(state.board, 10, 'W')
                        && !BotPlayer.isSquareInCheck(state.board, 12, 'W')) {
                    localState.board = localState.board.substring(0, 8) + "  brbk  "
                            + localState.board.substring(16);

//                        localState.board[14] = ' ';
//                        localState.board[15] = ' ';
//                        localState.board[12] = 'B';
//                        localState.board[13] = 'K';
//                        localState.board[10] = 'B';
//                        localState.board[11] = 'R';
                    localState.blackKingPos = 12;
                    splitOrNot(state, returnState, localState, enemyMove);
                }
            }
            if (returnState.returnValue == 999999) {
                returnState.returnValue = 999999 - state.movesMade;
//                System.out.println("here");
            }
            if (returnState.returnValue == -999999) {
                returnState.returnValue = -999999 + state.movesMade;
//                System.out.println("or here");

            }
            return returnState;
        }
    }


    //MODIFIES: this
    //EFFECTS: sets the output field to the next good move for whoseMove.
    @Override
    public void run() {
        output = whiteMakeMove(toComplete, whoseMove);
    }

    public GameState getOutput() {
        return output;
    }
}

