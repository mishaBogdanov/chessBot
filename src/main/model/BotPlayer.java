package model;

import java.util.ArrayList;
import java.util.Scanner;

//this is the class that contains the main single threaded function and the function
//that initiates the whole process.
public class BotPlayer {

    //REQUIRES: whoseMove, enemyMove 'W' or 'B'
    //MODIFIES: returnState
    //EFFECTS: makes the future moves, and checks if we should change returnState according to minmax.
    private static void betterMoveOrNot(GameState localState, GameState returnState, char whoseMove, char enemyMove) {
        GameState result = whiteMakeMove(localState, enemyMove);
        if ((whoseMove == 'W' && result.returnValue >= returnState.returnValue)
                || (whoseMove == 'B' && result.returnValue < returnState.returnValue)) {
            returnState.returnValue = result.returnValue;
            returnState.board = localState.board;
        }
    }

    //REQUIRES: 0<=i<=127, badSide == 'W' || badSide =='B'
    //EFFECTS: returns true if the given square on a board is in check from the badSide
    @SuppressWarnings("methodlength")
    public static Boolean isSquareInCheck(String givenBoard, int i, char badSide) {
        int distFromleft = i % 16 / 2;
        int distFromTop = i / 16;

        int movesLeftUp = Math.min(distFromleft, distFromTop);
        int movesRightUp = Math.min((8 - distFromleft - 1), distFromTop);
        int movesLeftDown = Math.min(distFromleft, (8 - distFromTop - 1));
        int movesRightDown = (8 - distFromleft) > (8 - distFromTop) ? (8 - distFromTop - 1) : (8 - distFromleft - 1);

        int movesUp = distFromTop;
        int movesDown = 8 - distFromTop - 1;
        int movesRight = 8 - distFromleft - 1;
        int movesLeft = distFromleft;


        if (badSide == 'B') { //pawn
            if (distFromTop > 0) {
                if (distFromleft > 0) {
                    if (givenBoard.charAt(i - 18) == 'B' && givenBoard.charAt(i - 17) == 'P') {
                        return true;
                    }
                }
                if (8 - distFromleft > 1) {
                    if (givenBoard.charAt(i - 14) == 'B' && givenBoard.charAt(i - 13) == 'P') {
                        return true;
                    }
                }
            }
        } else {
            if (7 > distFromTop) {

                if (distFromleft > 0) {
                    if (givenBoard.charAt(i + 14) == 'W' && givenBoard.charAt(i + 15) == 'P') {
                        return true;
                    }
                }
                if (7 > distFromleft) {
                    if (givenBoard.charAt(i + 18) == 'W' && givenBoard.charAt(i + 19) == 'P') {
                        return true;
                    }
                }
            }
        } //this is garbage but there's no other way


        //horses
        if (distFromTop > 0 && distFromleft > 1 && givenBoard.charAt(i - 20) == badSide && givenBoard.charAt(i - 19)
                == 'H') {
            return true;

        } else if (distFromTop > 1 && distFromleft > 0 && givenBoard.charAt(i - 34) == badSide
                && givenBoard.charAt(i - 33) == 'H') {
            return true;
        } else if (distFromTop > 1 && distFromleft <= 6 && givenBoard.charAt(i - 30) == badSide
                && givenBoard.charAt(i - 29) == 'H') {
            return true;
        } else if (distFromTop > 0 && distFromleft <= 5 && givenBoard.charAt(i - 12) == badSide
                && givenBoard.charAt(i - 11) == 'H') {
            return true;
        } else if (distFromTop <= 6 && distFromleft <= 5 && givenBoard.charAt(i + 20) == badSide
                && givenBoard.charAt(i + 21) == 'H') {
            return true;
        } else if (distFromTop <= 5 && distFromleft <= 6 && givenBoard.charAt(i + 34) == badSide
                && givenBoard.charAt(i + 35) == 'H') {
            return true;
        } else if (distFromTop <= 5 && distFromleft > 0 && givenBoard.charAt(i + 30) == badSide
                && givenBoard.charAt(i + 31) == 'H') {
            return true;
        } else if (distFromTop <= 6 && distFromleft > 1 && givenBoard.charAt(i + 12) == badSide
                && givenBoard.charAt(i + 13) == 'H') {
            return true;
        }


        int curPos = i - 18;
        while (movesLeftUp > 0) { // up left
            if (givenBoard.charAt(curPos) != ' ') {
                if (givenBoard.charAt(curPos) == badSide && (givenBoard.charAt(curPos + 1) == 'B'
                        || givenBoard.charAt(curPos + 1) == 'Q')) {
                    return true;

                } else {
                    break;
                }
            }
            movesLeftUp -= 1;
            curPos -= 18;
        }

        curPos = i - 14;

        while (movesRightUp > 0) { // up right
            if (givenBoard.charAt(curPos) != ' ') {
                if (givenBoard.charAt(curPos) == badSide && (givenBoard.charAt(curPos + 1) == 'B'
                        || givenBoard.charAt(curPos + 1) == 'Q')) {
                    return true;
                } else {
                    break;
                }
            }
            movesRightUp -= 1;
            curPos -= 14;
        }

        curPos = i + 14;

        while (movesLeftDown > 0) { // down left
            if (givenBoard.charAt(curPos) != ' ') {
                if (givenBoard.charAt(curPos) == badSide && (givenBoard.charAt(curPos + 1) == 'B'
                        || givenBoard.charAt(curPos + 1) == 'Q')) {
                    return true;
                } else {
                    break;
                }
            }
            movesLeftDown -= 1;
            curPos += 14;
        }

        curPos = i + 18;

        while (movesRightDown > 0) { // down right
            if (givenBoard.charAt(curPos) != ' ') {
                if (givenBoard.charAt(curPos) == badSide
                        && (givenBoard.charAt(curPos + 1) == 'B'
                        || givenBoard.charAt(curPos + 1) == 'Q')) {
                    return true;
                } else {
                    break;
                }
            }
            movesRightDown -= 1;
            curPos += 18;
        }

        curPos = i + 2;

        while (movesRight > 0) { // right
            if (givenBoard.charAt(curPos) != ' ') {
                if (givenBoard.charAt(curPos) == badSide && (givenBoard.charAt(curPos + 1) == 'R'
                        || givenBoard.charAt(curPos + 1) == 'Q')) {
                    return true;
                } else {
                    break;
                }
            }
            movesRight -= 1;
            curPos += 2;
        }

        curPos = i - 2;

        while (movesLeft > 0) { // left
            if (givenBoard.charAt(curPos) != ' ') {
                if (givenBoard.charAt(curPos) == badSide && (givenBoard.charAt(curPos + 1) == 'R'
                        || givenBoard.charAt(curPos + 1) == 'Q')) {
                    return true;
                } else {
                    break;
                }
            }
            movesLeft -= 1;
            curPos -= 2;
        }

        curPos = i - 16;


        while (movesUp > 0) { // up
            if (givenBoard.charAt(curPos) != ' ') {


                if (givenBoard.charAt(curPos) == badSide && (givenBoard.charAt(curPos + 1) == 'R'
                        || givenBoard.charAt(curPos + 1) == 'Q')) {
                    return true;
                } else {
                    break;
                }
            }
            movesUp -= 1;
            curPos -= 16;
        }

        curPos = i + 16;

        while (movesDown > 0) { // down
            if (givenBoard.charAt(curPos) != ' ') {
                if (givenBoard.charAt(curPos) == badSide && (givenBoard.charAt(curPos) == 'R'
                        || givenBoard.charAt(curPos + 1) == 'Q')) {
                    return true;
                } else {
                    break;
                }
            }
            movesDown -= 1;
            curPos += 16;
        }
        if ((distFromleft <= 6 && givenBoard.charAt(i + 2) == badSide
                && givenBoard.charAt(i + 3) == 'K')
                ||  //enemy king
                (distFromleft > 0 && givenBoard.charAt(i - 2) == badSide && givenBoard.charAt(i - 1) == 'K')
                || (distFromTop <= 6 && givenBoard.charAt(i + 16) == badSide && givenBoard.charAt(i + 17) == 'K')
                || (distFromTop > 0 && givenBoard.charAt(i - 16) == badSide && givenBoard.charAt(i - 15) == 'K')
                || (distFromTop > 0 && distFromleft > 0 && givenBoard.charAt(i - 18) == badSide
                && givenBoard.charAt(i - 17) == 'K')
                || (distFromTop > 0 && distFromleft <= 6 && givenBoard.charAt(i - 12) == badSide
                && givenBoard.charAt(i - 11) == 'K')
                || (distFromTop <= 6 && distFromleft > 0 && givenBoard.charAt(i + 14) == badSide
                && givenBoard.charAt(i + 15) == 'K')
                || (distFromTop <= 0 && distFromleft <= 6 && givenBoard.charAt(i + 18) == badSide
                && givenBoard.charAt(i + 19) == 'K')) {
            return true;
        }
        return false;

    }

    //REQUIRES: whoseMove == 'W' || badSide =='B'
    //EFFECTS: returns a gamestate with the next logical move for the whoseMove side
    @SuppressWarnings("methodlength")
    static GameState whiteMakeMove(GameState state, char whoseMove) { //might make return int to make things faster


        if (state.movesRemaining == 0) {
            GameState returningValue = GameState.coppyState(state);
            returningValue.returnValue = getCurrentScore(returningValue);
            return returningValue;


        } else {                                //multithread this later to speed things up


            GameState returnState = GameState.coppyState(state);
            returnState.movesMade++;
            returnState.returnValue = (whoseMove == 'W') ? -999999 : 999999;
            if (whoseMove == 'W') {
                GameState.resetPawnMoves(state.whitePawnsMoved);
            } else {
                GameState.resetPawnMoves(state.blackPawnsMoved);
            }
            char enemyMove = (whoseMove == 'W') ? 'B' : 'W';
            state.movesRemaining -= 1;
            state.movesMade++;
            for (int i = 0; i < 127; i += 2) {

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
                                if (!isSquareInCheck(localState.board, localState.whiteKingPos, 'B')) {
                                    betterMoveOrNot(localState, returnState, whoseMove, enemyMove);
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
                                if (!isSquareInCheck(localState.board, localState.whiteKingPos, 'B')) {
                                    betterMoveOrNot(localState, returnState, whoseMove, enemyMove);
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
                            if (!isSquareInCheck(localState.board, localState.whiteKingPos, 'B')) {
                                betterMoveOrNot(localState, returnState, whoseMove, enemyMove);
                            }
                        }
                        if (distFromleft < 7 && state.board.charAt(i - 14) == 'B') { //capturing right

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
                            if (!isSquareInCheck(localState.board, localState.whiteKingPos, 'B')) {
                                betterMoveOrNot(localState, returnState, whoseMove, enemyMove);
                            }
                        }
                        if (distFromTop == 1 && state.board.charAt(i - 16) == ' ') { //queening
                            GameState localState = GameState.coppyState(state);
                            localState.board = localState.board.substring(0, i - 16) + "WQ"
                                    + localState.board.substring(i - 14, i) + "  "
                                    + localState.board.substring(i + 2);
//                            localState.board[i - 16] = 'W';
//                            localState.board[i - 15] = 'Q';
//                            localState.board[i] = ' ';
//                            localState.board[i + 1] = ' ';
                            if (!isSquareInCheck(localState.board, localState.whiteKingPos, 'B')) {
                                betterMoveOrNot(localState, returnState, whoseMove, enemyMove);
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
                            if (!isSquareInCheck(localState.board, localState.whiteKingPos, 'B')) {
                                betterMoveOrNot(localState, returnState, whoseMove, enemyMove);
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
                                if (!isSquareInCheck(localState.board, localState.whiteKingPos, 'B')) {
                                    betterMoveOrNot(localState, returnState, whoseMove, enemyMove);
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
                                if (!isSquareInCheck(localState.board, localState.blackKingPos, 'W')) {
                                    betterMoveOrNot(localState, returnState, whoseMove, enemyMove);
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
                                if (!isSquareInCheck(localState.board, localState.blackKingPos, 'W')) {
                                    betterMoveOrNot(localState, returnState, whoseMove, enemyMove);
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
                            if (!isSquareInCheck(localState.board, localState.blackKingPos, 'W')) {
                                GameState result = whiteMakeMove(localState, 'W');
                                if (result.returnValue <= returnState.returnValue) {
                                    returnState.returnValue = result.returnValue;
                                    returnState.board = localState.board;
                                }
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
                            if (!isSquareInCheck(localState.board, localState.blackKingPos, 'W')) {
                                GameState result = whiteMakeMove(localState, 'W');
                                if (result.returnValue <= returnState.returnValue) {
                                    returnState.returnValue = result.returnValue;
                                    returnState.board = localState.board;
                                }
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
                            if (!isSquareInCheck(localState.board, localState.blackKingPos, 'W')) {
                                betterMoveOrNot(localState, returnState, whoseMove, enemyMove);
                            }
                        }
                        if (state.board.charAt(i + 16) == ' ' && distFromTop != 6) { //moving forward
                            GameState localState = GameState.coppyState(state);
                            localState.board = localState.board.substring(0, i) + "  "
                                    + localState.board.substring(i + 2, i + 16) + "BP"
                                    + localState.board.substring(i + 18);
//                            localState.board[i + 16] = 'B';
//                            localState.board[i + 17] = 'P';
//                            localState.board[i] = ' ';
//                            localState.board[i + 1] = ' ';
                            if (!isSquareInCheck(localState.board, localState.blackKingPos, 'W')) {
                                betterMoveOrNot(localState, returnState, whoseMove, enemyMove);
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
                                if (!isSquareInCheck(localState.board, localState.blackKingPos, 'W')) {
                                    betterMoveOrNot(localState, returnState, whoseMove, enemyMove);
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
                        } else if (i == 14) {
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
                        if (!isSquareInCheck(localState.board, (whoseMove == 'W')
                                ? localState.whiteKingPos : localState.blackKingPos, enemyMove)) {
                            betterMoveOrNot(localState, returnState, whoseMove, enemyMove);
                        }

                    }
                    if (distFromleft >= 0 && state.board.charAt(localPos) == enemyMove) {
                        localState = GameState.coppyState(state);
                        if (i == 126) {
                            localState.bottomRightRookMoved = true;
                        } else if (i == 14) {
                            localState.topRightRookMoved = true;
                        }
                        localState.board = localState.board.substring(0, localPos) + whoseMove + "R"
                                + localState.board.substring(localPos + 2, i) + "  "
                                + localState.board.substring(i + 2);
//                        localState.board[i] = ' ';
//                        localState.board[i + 1] = ' ';
//                        localState.board[localPos] = whoseMove;
//                        localState.board[localPos + 1] = 'R';
                        if (!isSquareInCheck(localState.board, (whoseMove == 'W')
                                ? localState.whiteKingPos : localState.blackKingPos, enemyMove)) {
                            betterMoveOrNot(localState, returnState, whoseMove, enemyMove);
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
                        if (!isSquareInCheck(localState.board, (whoseMove == 'W')
                                ? localState.whiteKingPos : localState.blackKingPos, enemyMove)) {
                            betterMoveOrNot(localState, returnState, whoseMove, enemyMove);
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
                        if (!isSquareInCheck(localState.board, (whoseMove == 'W')
                                ? localState.whiteKingPos : localState.blackKingPos, enemyMove)) {
                            betterMoveOrNot(localState, returnState, whoseMove, enemyMove);
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
                        if (!isSquareInCheck(localState.board, (whoseMove == 'W')
                                ? localState.whiteKingPos : localState.blackKingPos, enemyMove)) {

                            betterMoveOrNot(localState, returnState, whoseMove, enemyMove);
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

                        if (!isSquareInCheck(localState.board, (whoseMove == 'W')
                                ? localState.whiteKingPos : localState.blackKingPos, enemyMove)) {
                            betterMoveOrNot(localState, returnState, whoseMove, enemyMove);
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
                        if (!isSquareInCheck(localState.board, (whoseMove == 'W')
                                ? localState.whiteKingPos : localState.blackKingPos, enemyMove)) {

                            betterMoveOrNot(localState, returnState, whoseMove, enemyMove);
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
                        if (!isSquareInCheck(localState.board, (whoseMove == 'W')
                                ? localState.whiteKingPos : localState.blackKingPos, enemyMove)) {
                            GameState result = whiteMakeMove(localState, enemyMove);
                            if ((whoseMove == 'W' && result.returnValue >= returnState.returnValue)
                                    || (whoseMove == 'B' && result.returnValue < returnState.returnValue)) {
                                returnState.returnValue = result.returnValue;
                                returnState.board = localState.board;
                            }
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
                        if (!isSquareInCheck(localState.board, (whoseMove == 'W')
                                ? localState.whiteKingPos : localState.blackKingPos, enemyMove)) {
                            betterMoveOrNot(localState, returnState, whoseMove, enemyMove);
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
                        if (!isSquareInCheck(localState.board,
                                (whoseMove == 'W') ? localState.whiteKingPos : localState.blackKingPos, enemyMove)) {
                            betterMoveOrNot(localState, returnState, whoseMove, enemyMove);
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
                        if (!isSquareInCheck(localState.board, (whoseMove == 'W') ? localState.whiteKingPos
                                : localState.blackKingPos, enemyMove)) {
                            betterMoveOrNot(localState, returnState, whoseMove, enemyMove);
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
                        if (!isSquareInCheck(localState.board,
                                (whoseMove == 'W') ? localState.whiteKingPos : localState.blackKingPos, enemyMove)) {
                            betterMoveOrNot(localState, returnState, whoseMove, enemyMove);
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
                        if (!isSquareInCheck(localState.board, (whoseMove == 'W') ? localState.whiteKingPos
                                : localState.blackKingPos, enemyMove)) {
                            betterMoveOrNot(localState, returnState, whoseMove, enemyMove);
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
                        if (!isSquareInCheck(localState.board, (whoseMove == 'W')
                                ? localState.whiteKingPos : localState.blackKingPos, enemyMove)) {
                            betterMoveOrNot(localState, returnState, whoseMove, enemyMove);
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
                                + localState.board.substring(i + 2, localPos) + whoseMove + "B"
                                + localState.board.substring(localPos + 2);
//                        localState.board[i] = ' ';
//                        localState.board[i + 1] = ' ';
//                        localState.board[localPos] = whoseMove;
//                        localState.board[localPos + 1] = 'B';
                        localPos += 18;
                        distFromTopCoppy++;
                        distFromLeftCoppy++;
                        if (!isSquareInCheck(localState.board, (whoseMove == 'W') ? localState.whiteKingPos
                                : localState.blackKingPos, enemyMove)) {
                            betterMoveOrNot(localState, returnState, whoseMove, enemyMove);
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
                        if (!isSquareInCheck(localState.board, (whoseMove == 'W')
                                ? localState.whiteKingPos : localState.blackKingPos, enemyMove)) {
                            betterMoveOrNot(localState, returnState, whoseMove, enemyMove);
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
                        if (!isSquareInCheck(localState.board, (whoseMove == 'W')
                                ? localState.whiteKingPos : localState.blackKingPos, enemyMove)) {
                            betterMoveOrNot(localState, returnState, whoseMove, enemyMove);
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
                        if (!isSquareInCheck(localState.board, (whoseMove == 'W')
                                ? localState.whiteKingPos : localState.blackKingPos, enemyMove)) {
                            betterMoveOrNot(localState, returnState, whoseMove, enemyMove);
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
                        if (!isSquareInCheck(localState.board, (whoseMove == 'W')
                                ? localState.whiteKingPos : localState.blackKingPos, enemyMove)) {
                            betterMoveOrNot(localState, returnState, whoseMove, enemyMove);
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
                        if (!isSquareInCheck(localState.board, (whoseMove == 'W')
                                ? localState.whiteKingPos : localState.blackKingPos, enemyMove)) {
                            betterMoveOrNot(localState, returnState, whoseMove, enemyMove);
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
                        if (!isSquareInCheck(localState.board, (whoseMove == 'W')
                                ? localState.whiteKingPos : localState.blackKingPos, enemyMove)) {
                            betterMoveOrNot(localState, returnState, whoseMove, enemyMove);
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

                        if (!isSquareInCheck(localState.board, (whoseMove == 'W')
                                ? localState.whiteKingPos : localState.blackKingPos, enemyMove)) {
                            betterMoveOrNot(localState, returnState, whoseMove, enemyMove);
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
                        if (!isSquareInCheck(localState.board, (whoseMove == 'W')
                                ? localState.whiteKingPos : localState.blackKingPos, enemyMove)) {
                            betterMoveOrNot(localState, returnState, whoseMove, enemyMove);
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
                        if (!isSquareInCheck(localState.board, (whoseMove == 'W')
                                ? localState.whiteKingPos : localState.blackKingPos, enemyMove)) {
                            betterMoveOrNot(localState, returnState, whoseMove, enemyMove);
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
                        if (!isSquareInCheck(localState.board, (whoseMove == 'W')
                                ? localState.whiteKingPos : localState.blackKingPos, enemyMove)) {
                            betterMoveOrNot(localState, returnState, whoseMove, enemyMove);
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
                        if (!isSquareInCheck(localState.board, (whoseMove == 'W')
                                ? localState.whiteKingPos : localState.blackKingPos, enemyMove)) {
                            betterMoveOrNot(localState, returnState, whoseMove, enemyMove);
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
                        if (!isSquareInCheck(localState.board, (whoseMove == 'W')
                                ? localState.whiteKingPos : localState.blackKingPos, enemyMove)) {
                            betterMoveOrNot(localState, returnState, whoseMove, enemyMove);
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
                        if (!isSquareInCheck(localState.board, (whoseMove == 'W')
                                ? localState.whiteKingPos : localState.blackKingPos, enemyMove)) {
                            betterMoveOrNot(localState, returnState, whoseMove, enemyMove);
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
                        if (!isSquareInCheck(localState.board, (whoseMove == 'W')
                                ? localState.whiteKingPos : localState.blackKingPos, enemyMove)) {
                            betterMoveOrNot(localState, returnState, whoseMove, enemyMove);
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
                        if (!isSquareInCheck(localState.board, (whoseMove == 'W')
                                ? localState.whiteKingPos : localState.blackKingPos, enemyMove)) {
                            betterMoveOrNot(localState, returnState, whoseMove, enemyMove);
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
                        if (!isSquareInCheck(localState.board, (whoseMove == 'W')
                                ? localState.whiteKingPos : localState.blackKingPos, enemyMove)) {
                            betterMoveOrNot(localState, returnState, whoseMove, enemyMove);
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
                        if (!isSquareInCheck(localState.board, (whoseMove == 'W') ? localState.whiteKingPos
                                : localState.blackKingPos, enemyMove)) {
                            betterMoveOrNot(localState, returnState, whoseMove, enemyMove);
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
                            && !isSquareInCheck(localState.board, i + 2, enemyMove)) {
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
                        betterMoveOrNot(localState, returnState, whoseMove, enemyMove);
                    }
                    localState = GameState.coppyState(state);

//                    localState.board[i] = ' ';
//                    localState.board[i + 1] = ' ';

                    if (distFromleft > 0 && (localState.board.charAt(i - 2) == ' '
                            || localState.board.charAt(i - 2) == enemyMove)
                            && !isSquareInCheck(localState.board, i - 2, enemyMove)) {
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
                        betterMoveOrNot(localState, returnState, whoseMove, enemyMove);
                    }
                    localState = GameState.coppyState(state);

//                    localState.board[i] = ' ';
//                    localState.board[i + 1] = ' ';
                    if (distFromTop < 7 && (localState.board.charAt(i + 16) == ' ' || localState.board.charAt(i + 16)
                            == enemyMove) && !isSquareInCheck(localState.board, i + 16, enemyMove)) {
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
                        betterMoveOrNot(localState, returnState, whoseMove, enemyMove);
                    }
                    localState = GameState.coppyState(state);

//                    localState.board[i] = ' ';
//                    localState.board[i + 1] = ' ';
                    if (distFromTop > 0 && (localState.board.charAt(i - 16) == ' ' || localState.board.charAt(i - 16)
                            == enemyMove) && !isSquareInCheck(localState.board, i - 16, enemyMove)) {
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
                        betterMoveOrNot(localState, returnState, whoseMove, enemyMove);
                    }
                    localState = GameState.coppyState(state);

//                    localState.board[i] = ' ';
//                    localState.board[i + 1] = ' ';
                    if (distFromTop > 0 && distFromleft > 0 && (localState.board.charAt(i - 18) == ' '
                            || localState.board.charAt(i - 18) == enemyMove)
                            && !isSquareInCheck(localState.board, i - 18, enemyMove)) {
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
                        betterMoveOrNot(localState, returnState, whoseMove, enemyMove);
                    }
                    localState = GameState.coppyState(state);

//                    localState.board[i] = ' ';
//                    localState.board[i + 1] = ' ';

                    if (distFromTop > 0 && distFromleft < 7 && (localState.board.charAt(i - 14) == ' '
                            || localState.board.charAt(i - 14) == enemyMove)
                            && !isSquareInCheck(localState.board, i - 14, enemyMove)) {
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
                        betterMoveOrNot(localState, returnState, whoseMove, enemyMove);
                    }
                    localState = GameState.coppyState(state);

//                    localState.board[i] = ' ';
//                    localState.board[i + 1] = ' ';
                    if (distFromTop < 7 && distFromleft > 0 && (localState.board.charAt(i + 14) == ' '
                            || localState.board.charAt(i + 14) == enemyMove)
                            && !isSquareInCheck(localState.board, i + 14, enemyMove)) {
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
                        betterMoveOrNot(localState, returnState, whoseMove, enemyMove);
                    }
                    localState = GameState.coppyState(state);

//                    localState.board[i] = ' ';
//                    localState.board[i + 1] = ' ';
                    if (distFromleft < 7 && distFromTop < 7 && (localState.board.charAt(i + 18) == ' '
                            || localState.board.charAt(i + 18) == enemyMove)
                            && !isSquareInCheck(localState.board, i + 18, enemyMove)) {
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
                        betterMoveOrNot(localState, returnState, whoseMove, enemyMove);
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
                        if (!isSquareInCheck(localState.board, (whoseMove == 'W')
                                ? localState.whiteKingPos : localState.blackKingPos, enemyMove)) {
                            betterMoveOrNot(localState, returnState, whoseMove, enemyMove);
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
                        if (!isSquareInCheck(localState.board, (whoseMove == 'W')
                                ? localState.whiteKingPos : localState.blackKingPos, enemyMove)) {
                            betterMoveOrNot(localState, returnState, whoseMove, enemyMove);
                        }
                    }

                    localState = GameState.coppyState(state);
                    if (distFromleft <= 5 && distFromTop >= 1 && (state.board.charAt(i - 12)
                            == enemyMove || state.board.charAt(i - 12) == ' ')) {
                        localState.board = localState.board.substring(0, i - 12) + whoseMove + "H"
                                + localState.board.substring(i - 10, i) + "  "
                                + localState.board.substring(i + 2);
//                        localState.board[i] = ' ';
//                        localState.board[i + 1] = ' ';
//                        localState.board[i - 12] = whoseMove;
//                        localState.board[i - 11] = 'H';
                        if (!isSquareInCheck(localState.board, (whoseMove == 'W')
                                ? localState.whiteKingPos : localState.blackKingPos, enemyMove)) {
                            betterMoveOrNot(localState, returnState, whoseMove, enemyMove);
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
                        if (!isSquareInCheck(localState.board, (whoseMove == 'W')
                                ? localState.whiteKingPos : localState.blackKingPos, enemyMove)) {
                            betterMoveOrNot(localState, returnState, whoseMove, enemyMove);
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
                        if (!isSquareInCheck(localState.board, (whoseMove == 'W')
                                ? localState.whiteKingPos : localState.blackKingPos, enemyMove)) {
                            betterMoveOrNot(localState, returnState, whoseMove, enemyMove);
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
                        if (!isSquareInCheck(localState.board, (whoseMove == 'W')
                                ? localState.whiteKingPos : localState.blackKingPos, enemyMove)) {
                            betterMoveOrNot(localState, returnState, whoseMove, enemyMove);
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
                        if (!isSquareInCheck(localState.board, (whoseMove == 'W')
                                ? localState.whiteKingPos : localState.blackKingPos, enemyMove)) {
                            betterMoveOrNot(localState, returnState, whoseMove, enemyMove);
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
                        if (!isSquareInCheck(localState.board, (whoseMove == 'W')
                                ? localState.whiteKingPos : localState.blackKingPos, enemyMove)) {
                            betterMoveOrNot(localState, returnState, whoseMove, enemyMove);
                        }
                    }
                }
            }
            checkCastle(state, returnState, whoseMove, enemyMove);
            if (returnState.returnValue == 999999) {
                returnState.returnValue = 999999 - state.movesMade;
//                System.out.println("or or here");
            }
            if (returnState.returnValue == -999999) {
                returnState.returnValue = -999999 + state.movesMade;
//                System.out.println("or or or here");
            }
            return returnState;
        }
    }


    @SuppressWarnings("methodlength")
    private static void checkCastle(GameState state, GameState returnState, char whoseMove, char enemyMove) {
        GameState localState = GameState.coppyState(state);

        if (whoseMove == 'W') { //castling bottom left
            if (!state.whiteKingMoved
                    && !state.bottomLeftRookMoved && state.board.charAt(118) == ' '
                    && state.board.charAt(116) == ' ' && state.board.charAt(114) == ' '
                    && !isSquareInCheck(state.board, 120, 'B')
                    && !isSquareInCheck(state.board, 118, 'B')
                    && !isSquareInCheck(state.board, 116, 'B')) {
                localState.board = localState.board.substring(0, 112) + "    WKWR  "
                        + localState.board.substring(122);

//                        localState.board[112] = ' ';
//                        localState.board[113] = ' ';
//                        localState.board[116] = 'W';
//                        localState.board[117] = 'K';
//                        localState.board[118] = 'W';
//                        localState.board[119] = 'R';
                localState.whiteKingPos = 116;
                if (!isSquareInCheck(localState.board, (whoseMove == 'W')
                        ? localState.whiteKingPos : localState.blackKingPos, enemyMove)) {
                    betterMoveOrNot(localState, returnState, whoseMove, enemyMove);
                }
            }
            localState = GameState.coppyState(state);
            if (!state.whiteKingMoved && !state.bottomRightRookMoved && state.board.charAt(122) == ' '
                    && state.board.charAt(124) == ' ' && !isSquareInCheck(state.board, 120, 'B')
                    && !isSquareInCheck(state.board, 122, 'B')
                    && !isSquareInCheck(state.board, 124, 'B')) {
                localState.board = localState.board.substring(0, 120) + "  WRWK  ";

//                        localState.board[126] = ' ';
//                        localState.board[127] = ' ';
//                        localState.board[124] = 'W';
//                        localState.board[125] = 'K';
//                        localState.board[122] = 'W';
//                        localState.board[123] = 'R';
                localState.whiteKingPos = 124;
                if (!isSquareInCheck(localState.board, (whoseMove == 'W')
                        ? localState.whiteKingPos : localState.blackKingPos, enemyMove)) {
                    betterMoveOrNot(localState, returnState, whoseMove, enemyMove);
                }
            }
        } else {
            if (!state.blackKingMoved && !state.topLeftRookMoved && state.board.charAt(2) == ' '
                    && state.board.charAt(4) == ' ' && state.board.charAt(6) == ' '
                    && !isSquareInCheck(state.board, 4, 'W')
                    && !isSquareInCheck(state.board, 6, 'W')
                    && !isSquareInCheck(state.board, 8, 'W')) {
                localState.board = "    bkbr  " + localState.board.substring(10);

//                        localState.board[0] = ' ';
//                        localState.board[1] = ' ';
//                        localState.board[4] = 'B';
//                        localState.board[5] = 'K';
//                        localState.board[6] = 'B';
//                        localState.board[7] = 'R';
                localState.blackKingPos = 4;
                if (!isSquareInCheck(localState.board,
                        (whoseMove == 'W') ? localState.whiteKingPos : localState.blackKingPos, enemyMove)) {
                    betterMoveOrNot(localState, returnState, whoseMove, enemyMove);
                }
            }
            localState = GameState.coppyState(state);
            if (!state.blackKingMoved && !state.topRightRookMoved && state.board.charAt(10) == ' '
                    && state.board.charAt(12) == ' ' && !isSquareInCheck(state.board, 8, 'W')
                    && !isSquareInCheck(state.board, 10, 'W')
                    && !isSquareInCheck(state.board, 12, 'W')) {
                localState.board = localState.board.substring(0, 8) + "  brbk  "
                        + localState.board.substring(16);

//                        localState.board[14] = ' ';
//                        localState.board[15] = ' ';
//                        localState.board[12] = 'B';
//                        localState.board[13] = 'K';
//                        localState.board[10] = 'B';
//                        localState.board[11] = 'R';
                localState.blackKingPos = 12;
                if (!isSquareInCheck(localState.board,
                        (whoseMove == 'W') ? localState.whiteKingPos : localState.blackKingPos, enemyMove)) {
                    betterMoveOrNot(localState, returnState, whoseMove, enemyMove);
                }
            }
        }
    }

    //REQUIRES: given board must be of style. must be 128 char long.
    //EFFECTS: returns the current score based on the current board.
    @SuppressWarnings("methodlength")
    static double getCurrentScore(GameState givenBoard) {
        double currentScore = 0.0;
        for (int i = 0; i < 127; i += 2) {
            if (givenBoard.board.charAt(i + 1) == 'R') { // rooks
                int curPos = i - 18;

                int distFromleft = i % 16 / 2;
                int distFromTop = i / 16;

                int movesAvailable = 0;

                int movesUp = distFromTop;
                int movesDown = 8 - distFromTop - 1;
                int movesRight = 8 - distFromleft - 1;
                int movesLeft = distFromleft;

                curPos = i + 2;

                while (movesRight > 0 && givenBoard.board.charAt(curPos) == ' ') { // right
                    movesAvailable += 1;
                    movesRight -= 1;
                    curPos += 2;
                }
                curPos = i - 2;

                while (movesLeft > 0 && givenBoard.board.charAt(curPos) == ' ') { // left
                    movesAvailable += 1;
                    movesLeft -= 1;
                    curPos += 2;
                }

                curPos = i - 16;

                while (movesUp > 0 && givenBoard.board.charAt(curPos) == ' ') { // up
                    movesAvailable += 1;
                    movesUp -= 1;
                    curPos -= 16;
                }

                curPos = i + 16;

                while (movesDown > 0 && givenBoard.board.charAt(curPos) == ' ') { // down
                    movesAvailable += 1;
                    movesDown -= 1;
                    curPos += 16;
                }

                currentScore -= (4.3 + movesAvailable * 0.1) * (givenBoard.board.charAt(i) == 'B' ? 1 : -1);
            } else if (givenBoard.board.charAt(i + 1) == 'H') { // horse
                int distFromleft = i % 16 / 2;
                int distFromTop = i / 16;
                int kingDistFromLeft = (givenBoard.board.charAt(i) == 'B' ? givenBoard.whiteKingPos
                        : givenBoard.blackKingPos) % 16 / 2;
                int kingDistFromTop = (givenBoard.board.charAt(i) == 'B' ? givenBoard.whiteKingPos
                        : givenBoard.blackKingPos) / 16;
                currentScore -= (3.0 + (8 - Math.abs(distFromleft - kingDistFromLeft)
                        - Math.abs(distFromTop - kingDistFromTop)) * 0.15)
                        * (givenBoard.board.charAt(i) == 'B' ? 1 : -1);
            } else if (givenBoard.board.charAt(i + 1) == 'B') { // bishop
                int curPos = i - 18;

                int distFromleft = i % 16 / 2;
                int distFromTop = i / 16;

                int movesAvailable = 0;

                int movesLeftUp = distFromleft > distFromTop ? distFromTop : distFromleft;
                int movesRightUp = (8 - distFromleft - 1) > distFromTop ? distFromTop : (8 - distFromleft - 1);
                int movesLeftDown = distFromleft > (8 - distFromTop - 1) ? (8 - distFromTop - 1) : distFromleft;
                int movesRightDown = (8 - distFromleft) > (8 - distFromTop) ? (8 - distFromTop - 1)
                        : (8 - distFromleft - 1);

                while (movesLeftUp > 0 && givenBoard.board.charAt(curPos) == ' ') { // up left
                    movesAvailable += 1;
                    movesLeftUp -= 1;
                    curPos -= 18;
                }

                curPos = i - 14;

                while (movesRightUp > 0 && givenBoard.board.charAt(curPos) == ' ') { // up right
                    movesAvailable += 1;
                    movesRightUp -= 1;
                    curPos -= 14;
                }

                curPos = i + 14;

                while (movesLeftDown > 0 && givenBoard.board.charAt(curPos) == ' ') { // down left
                    movesAvailable += 1;
                    movesLeftDown -= 1;
                    curPos += 14;
                }

                curPos = i + 16;

                while (movesRightDown > 0 && givenBoard.board.charAt(curPos) == ' ') { // down left
                    movesAvailable += 1;
                    movesRightDown -= 1;
                    curPos += 14;
                }
                currentScore -= (3.5 + movesAvailable * 0.2) * (givenBoard.board.charAt(i) == 'B' ? 1 : -1);
            } else if (givenBoard.board.charAt(i + 1) == 'Q') { // queen
                int curPos = i - 18;

                int distFromleft = i % 16 / 2;
                int distFromTop = i / 16;

                int movesAvailable = 0;

                int movesLeftUp = distFromleft > distFromTop ? distFromTop : distFromleft;
                int movesRightUp = (8 - distFromleft - 1) > distFromTop ? distFromTop : (8 - distFromleft - 1);
                int movesLeftDown = distFromleft > (8 - distFromTop - 1) ? (8 - distFromTop - 1) : distFromleft;
                int movesRightDown = (8 - distFromleft) > (8 - distFromTop) ? (8 - distFromTop - 1)
                        : (8 - distFromleft - 1);

                int movesUp = distFromTop;
                int movesDown = 8 - distFromTop - 1;
                int movesRight = 8 - distFromleft - 1;
                int movesLeft = distFromleft;

                while (movesLeftUp > 0 && givenBoard.board.charAt(curPos) == ' ') { // up left
                    movesAvailable += 1;
                    movesLeftUp -= 1;
                    curPos -= 18;
                }

                curPos = i - 14;

                while (movesRightUp > 0 && givenBoard.board.charAt(curPos) == ' ') { // up right
                    movesAvailable += 1;
                    movesRightUp -= 1;
                    curPos -= 14;
                }

                curPos = i + 14;

                while (movesLeftDown > 0 && givenBoard.board.charAt(curPos) == ' ') { // down left
                    movesAvailable += 1;
                    movesLeftDown -= 1;
                    curPos += 14;
                }

                curPos = i + 16;

                while (movesRightDown > 0 && givenBoard.board.charAt(curPos) == ' ') { // down left
                    movesAvailable += 1;
                    movesRightDown -= 1;
                    curPos += 14;
                }

                curPos = i + 2;

                while (movesRight > 0 && givenBoard.board.charAt(curPos) == ' ') { // right
                    movesAvailable += 1;
                    movesRight -= 1;
                    curPos += 2;
                }

                curPos = i - 2;

                while (movesLeft > 0 && givenBoard.board.charAt(curPos) == ' ') { // left
                    movesAvailable += 1;
                    movesLeft -= 1;
                    curPos += 2;
                }

                curPos = i - 16;

                while (movesUp > 0 && givenBoard.board.charAt(curPos) == ' ') { // up
                    movesAvailable += 1;
                    movesUp -= 1;
                    curPos -= 16;
                }

                curPos = i + 16;

                while (movesDown > 0 && givenBoard.board.charAt(curPos) == ' ') { // down
                    movesAvailable += 1;
                    movesDown -= 1;
                    curPos += 16;
                }
                currentScore -= (8 + movesAvailable * 0.1) * (givenBoard.board.charAt(i) == 'B' ? 1 : -1);
            } else if (givenBoard.board.charAt(i + 1) == 'P') {
                int distFromLeft = i % 16 / 2;
                int distFromTop = i / 16;


                currentScore -= 0.8 * (1 + ((givenBoard.board.charAt(i) == 'B') ? distFromTop / 8.0
                        * (3.5 - Math.abs(3.5 - distFromLeft)) : (8 - distFromTop - 1) / 8.0
                        * (3.5 - Math.abs(3.5 - distFromLeft)))) * (givenBoard.board.charAt(i) == 'B' ? 1 : -1);
            }
        }
        return (int) (currentScore * 100) / 100.0;
    }

    //REQUIRES: whoseMove == 'W' || badSide =='B'
    //EFFECTS: similar to whiteMakeMove, but this one initiates the multithreading function. Returns the
    //         gameState with the next logical move for whoseMove side.
    public GameState makeMove(GameState givenState, char whoseMove) {

        long start = System.currentTimeMillis();

        givenState = MultithreadingStarter.split(givenState, whoseMove);

        long finish = System.currentTimeMillis();
        System.out.println("bot thought for " + (finish - start) / 1000 + " seconds");
        givenState.resetMovesMade();

        return givenState;
    }
}
