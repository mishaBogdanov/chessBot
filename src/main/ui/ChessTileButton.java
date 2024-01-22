package ui;

import model.GameState;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.lang.reflect.Array;
import java.util.ArrayList;

// a tile on a chess board
public class ChessTileButton extends JButton implements MouseListener {
    private boolean isWhite;
    private String chessPiece;
    private boolean isInFocus;
    int thisNum;

    char whoseMove;

    ArrayList<ChessTileButton> listButton;

    ChessPvPMenu menu;

    public ChessTileButton(boolean isWhite, String chessPiece, boolean isInFocus,
                           ArrayList<ChessTileButton> list, int thisNum, GameState currentState, char whoseMove,
                           ChessPvPMenu givenMenu) {
        this.menu = givenMenu;
        this.whoseMove = whoseMove;
        this.thisNum = thisNum;
        this.listButton = list;
        this.isWhite = isWhite;
        this.chessPiece = chessPiece;
        this.setBounds(0, 0, 25, 25);
        this.setFocusable(false);
        this.setFocusPainted(false);
        this.setIcon(getChessPieceIcon(chessPiece));
        this.setBackground(
                isInFocus ? new Color(239, 239, 239) :
                        this.isWhite ? new Color(161, 161, 161) :
                                new Color(80, 80, 80));
        addMouseListener(this);
        this.setBorder(null);
    }

    //EFFECTS: returns the icon for a piece
    @SuppressWarnings("methodlength")
    private ImageIcon getChessPieceIcon(String given) {
        if (given.equals("WP")) {
            return new ImageIcon(new ImageIcon("./src/main/ui/things/chessPieces/img.png")
                    .getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));
        } else if (given.equals("WB")) {
            return new ImageIcon(new ImageIcon("./src/main/ui/things/chessPieces/img_1.png")
                    .getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));
        } else if (given.equals("WH")) {
            return new ImageIcon(new ImageIcon("./src/main/ui/things/chessPieces/img_2.png")
                    .getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));
        } else if (given.equals("WR")) {
            return new ImageIcon(new ImageIcon("./src/main/ui/things/chessPieces/img_3.png")
                    .getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));
        } else if (given.equals("WQ")) {
            return new ImageIcon(new ImageIcon("./src/main/ui/things/chessPieces/img_4.png")
                    .getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));
        } else if (given.equals("WK")) {
            return new ImageIcon(new ImageIcon("./src/main/ui/things/chessPieces/img_5.png")
                    .getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));
        } else if (given.equals("BP")) {
            return new ImageIcon(new ImageIcon("./src/main/ui/things/chessPieces/img_6.png")
                    .getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));
        } else if (given.equals("BB")) {
            return new ImageIcon(new ImageIcon("./src/main/ui/things/chessPieces/img_7.png")
                    .getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));
        } else if (given.equals("BH")) {
            return new ImageIcon(new ImageIcon("./src/main/ui/things/chessPieces/img_8.png")
                    .getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));
        } else if (given.equals("BR")) {
            return new ImageIcon(new ImageIcon("./src/main/ui/things/chessPieces/img_9.png")
                    .getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));
        } else if (given.equals("BQ")) {
            return new ImageIcon(new ImageIcon("./src/main/ui/things/chessPieces/img_10.png")
                    .getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));
        } else if (given.equals("BK")) {
            return new ImageIcon(new ImageIcon("./src/main/ui/things/chessPieces/img_11.png")
                    .getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));
        }
        return null;
    }

    //MODIFIES: this
    //EFFECTS: sets this square as an available move
    public void setInFocus(boolean hard) {
        this.isInFocus = hard;
        this.setBackground(new Color(239, 239, 239));
    }

    //MODIFIES: this
    //EFFECTS: sets this square as unavailable
    public void setOutOfFocus() {
        this.isInFocus = false;
        this.setBackground(this.isWhite ? new Color(161, 161, 161) :
                new Color(80, 80, 80));
    }

    public String getPiece() {
        return chessPiece;
    }

    //EFFECTS: sets the required tiles to be set as available moves
    @SuppressWarnings("methodlength")
    private void onHover(boolean hard) {
        char enemyMove = whoseMove == 'W' ? 'B' : 'W';
        int distFromTop = thisNum / 8;
        int distFromLeft = thisNum % 8;
        if (chessPiece.equals("WP") && whoseMove == 'W') {
            if (distFromTop > 0 && listButton.get(thisNum - 8).getPiece().equals("  ")) {
                if (tryToFrom(thisNum, thisNum - 8, whoseMove, enemyMove)) {
                    listButton.get(thisNum - 8).setInFocus(hard);
                }
                if (distFromTop == 6 && listButton.get(thisNum - 16).getPiece().equals("  ")) {
                    if (tryToFrom(thisNum, thisNum - 16, whoseMove, enemyMove)) {
                        listButton.get(thisNum - 16).setInFocus(hard);
                    }
                }

            }
            if (distFromLeft > 0 && listButton.get(thisNum - 9).getPiece().charAt(0) == 'B') {
                if (tryToFrom(thisNum, thisNum - 9, whoseMove, enemyMove)) {
                    listButton.get(thisNum - 9).setInFocus(hard);
                }
            }
            if (distFromLeft < 7 && listButton.get(thisNum - 7).getPiece().charAt(0) == 'B') {
                if (tryToFrom(thisNum, thisNum - 7, whoseMove, enemyMove)) {
                    listButton.get(thisNum - 7).setInFocus(hard);
                }
            }

        } else if (chessPiece.equals("BP") && whoseMove == 'B') {
            if (distFromTop < 7 && listButton.get(thisNum + 8).getPiece().equals("  ")) {
                if (tryToFrom(thisNum, thisNum + 8, whoseMove, enemyMove)) {
                    listButton.get(thisNum + 8).setInFocus(hard);
                }
                if (distFromTop == 1 && listButton.get(thisNum + 16).getPiece().equals("  ")) {
                    if (tryToFrom(thisNum, thisNum + 16, whoseMove, enemyMove)) {
                        listButton.get(thisNum + 16).setInFocus(hard);
                    }
                }

            }
            if (distFromLeft > 0 && listButton.get(thisNum + 7).getPiece().charAt(0) == 'W') {
                if (tryToFrom(thisNum, thisNum + 7, whoseMove, enemyMove)) {
                    listButton.get(thisNum + 7).setInFocus(hard);
                }
            }
            if (distFromLeft < 7 && listButton.get(thisNum + 9).getPiece().charAt(0) == 'W') {
                if (tryToFrom(thisNum, thisNum + 9, whoseMove, enemyMove)) {
                    listButton.get(thisNum + 9).setInFocus(hard);
                }
            }
        } else if (chessPiece.charAt(1) == 'B' && chessPiece.charAt(0) == whoseMove) {
            int distFromTopLeft = Math.min(distFromLeft, distFromTop);
            int distFromTopRight = Math.min(7 - distFromLeft, distFromTop);
            int distFromBottomLeft = Math.min(distFromLeft, 7 - distFromTop);
            int distFromBottomRight = Math.min(7 - distFromLeft, 7 - distFromTop);
            int curPos = thisNum - 9;
            while (distFromTopLeft > 0) {
                distFromTopLeft--;
                if (listButton.get(curPos).getPiece().charAt(0) == enemyMove) {
                    if (tryToFrom(thisNum, curPos, whoseMove, enemyMove)) {
                        listButton.get(curPos).setInFocus(hard);
                    }
                    break;
                } else if (listButton.get(curPos).getPiece().charAt(0) == whoseMove) {
                    break;
                } else {
                    if (tryToFrom(thisNum, curPos, whoseMove, enemyMove)) {
                        listButton.get(curPos).setInFocus(hard);
                    }
                }
                curPos -= 9;
            }
            curPos = thisNum - 7;
            while (distFromTopRight > 0) {
                distFromTopRight--;
                if (listButton.get(curPos).getPiece().charAt(0) == enemyMove) {
                    if (tryToFrom(thisNum, curPos, whoseMove, enemyMove)) {
                        listButton.get(curPos).setInFocus(hard);
                    }
                    break;
                } else if (listButton.get(curPos).getPiece().charAt(0) == whoseMove) {
                    break;
                } else {
                    if (tryToFrom(thisNum, curPos, whoseMove, enemyMove)) {
                        listButton.get(curPos).setInFocus(hard);
                    }
                }
                curPos -= 7;
            }
            curPos = thisNum + 7;
            while (distFromBottomLeft > 0) {
                distFromBottomLeft--;
                if (listButton.get(curPos).getPiece().charAt(0) == enemyMove) {
                    if (tryToFrom(thisNum, curPos, whoseMove, enemyMove)) {
                        listButton.get(curPos).setInFocus(hard);
                    }
                    break;
                } else if (listButton.get(curPos).getPiece().charAt(0) == whoseMove) {
                    break;
                } else {
                    if (tryToFrom(thisNum, curPos, whoseMove, enemyMove)) {
                        listButton.get(curPos).setInFocus(hard);
                    }
                }
                curPos += 7;
            }
            curPos = thisNum + 9;
            while (distFromBottomRight > 0) {
                distFromBottomRight--;
                if (listButton.get(curPos).getPiece().charAt(0) == enemyMove) {
                    if (tryToFrom(thisNum, curPos, whoseMove, enemyMove)) {
                        listButton.get(curPos).setInFocus(hard);
                    }
                    break;
                } else if (listButton.get(curPos).getPiece().charAt(0) == whoseMove) {
                    break;
                } else {
                    if (tryToFrom(thisNum, curPos, whoseMove, enemyMove)) {
                        listButton.get(curPos).setInFocus(hard);
                    }
                }
                curPos += 9;
            }

        } else if (chessPiece.charAt(1) == 'R' && chessPiece.charAt(0) == whoseMove) {
            int distFromTopCopy = distFromTop;
            int distFromBottom = 7 - distFromTop;
            int distFromLeftCopy = distFromLeft;
            int distFromRight = 7 - distFromLeft;
            int curPos = thisNum - 1;
            while (distFromLeftCopy > 0) {
                distFromLeftCopy--;
                if (listButton.get(curPos).getPiece().charAt(0) == enemyMove) {
                    if (tryToFrom(thisNum, curPos, whoseMove, enemyMove)) {
                        listButton.get(curPos).setInFocus(hard);
                    }
                    break;
                } else if (listButton.get(curPos).getPiece().charAt(0) == whoseMove) {
                    break;
                } else {
                    if (tryToFrom(thisNum, curPos, whoseMove, enemyMove)) {
                        listButton.get(curPos).setInFocus(hard);
                    }
                }
                curPos -= 1;
            }
            curPos = thisNum - 8;
            while (distFromTopCopy > 0) {
                distFromTopCopy--;
                if (listButton.get(curPos).getPiece().charAt(0) == enemyMove) {
                    if (tryToFrom(thisNum, curPos, whoseMove, enemyMove)) {
                        listButton.get(curPos).setInFocus(hard);
                    }
                    break;
                } else if (listButton.get(curPos).getPiece().charAt(0) == whoseMove) {
                    break;
                } else {
                    if (tryToFrom(thisNum, curPos, whoseMove, enemyMove)) {
                        listButton.get(curPos).setInFocus(hard);
                    }
                }
                curPos -= 8;
            }
            curPos = thisNum + 1;
            while (distFromRight > 0) {
                distFromRight--;
                if (listButton.get(curPos).getPiece().charAt(0) == enemyMove) {
                    if (tryToFrom(thisNum, curPos, whoseMove, enemyMove)) {
                        listButton.get(curPos).setInFocus(hard);
                    }
                    break;
                } else if (listButton.get(curPos).getPiece().charAt(0) == whoseMove) {
                    break;
                } else {
                    if (tryToFrom(thisNum, curPos, whoseMove, enemyMove)) {
                        listButton.get(curPos).setInFocus(hard);
                    }
                }
                curPos += 1;
            }
            curPos = thisNum + 8;
            while (distFromBottom > 0) {
                distFromBottom--;
                if (listButton.get(curPos).getPiece().charAt(0) == enemyMove) {
                    if (tryToFrom(thisNum, curPos, whoseMove, enemyMove)) {
                        listButton.get(curPos).setInFocus(hard);
                    }
                    break;
                } else if (listButton.get(curPos).getPiece().charAt(0) == whoseMove) {
                    break;
                } else {
                    if (tryToFrom(thisNum, curPos, whoseMove, enemyMove)) {
                        listButton.get(curPos).setInFocus(hard);
                    }
                }
                curPos += 8;
            }
        } else if (chessPiece.charAt(1) == 'Q' && chessPiece.charAt(0) == whoseMove) {
            int distFromTopCopy = distFromTop;
            int distFromBottom = 7 - distFromTop;
            int distFromLeftCopy = distFromLeft;
            int distFromRight = 7 - distFromLeft;
            int curPos = thisNum - 1;
            while (distFromLeftCopy > 0) {
                distFromLeftCopy--;
                if (listButton.get(curPos).getPiece().charAt(0) == enemyMove) {
                    if (tryToFrom(thisNum, curPos, whoseMove, enemyMove)) {
                        listButton.get(curPos).setInFocus(hard);
                    }
                    break;
                } else if (listButton.get(curPos).getPiece().charAt(0) == whoseMove) {
                    break;
                } else {
                    if (tryToFrom(thisNum, curPos, whoseMove, enemyMove)) {
                        listButton.get(curPos).setInFocus(hard);
                    }
                }
                curPos -= 1;
            }
            curPos = thisNum - 8;
            while (distFromTopCopy > 0) {
                distFromTopCopy--;
                if (listButton.get(curPos).getPiece().charAt(0) == enemyMove) {
                    if (tryToFrom(thisNum, curPos, whoseMove, enemyMove)) {
                        listButton.get(curPos).setInFocus(hard);
                    }
                    break;
                } else if (listButton.get(curPos).getPiece().charAt(0) == whoseMove) {
                    break;
                } else {
                    if (tryToFrom(thisNum, curPos, whoseMove, enemyMove)) {
                        listButton.get(curPos).setInFocus(hard);
                    }
                }
                curPos -= 8;
            }
            curPos = thisNum + 1;
            while (distFromRight > 0) {
                distFromRight--;
                if (listButton.get(curPos).getPiece().charAt(0) == enemyMove) {
                    if (tryToFrom(thisNum, curPos, whoseMove, enemyMove)) {
                        listButton.get(curPos).setInFocus(hard);
                    }
                    break;
                } else if (listButton.get(curPos).getPiece().charAt(0) == whoseMove) {
                    break;
                } else {
                    if (tryToFrom(thisNum, curPos, whoseMove, enemyMove)) {
                        listButton.get(curPos).setInFocus(hard);
                    }
                }
                curPos += 1;
            }
            curPos = thisNum + 8;
            while (distFromBottom > 0) {
                distFromBottom--;
                if (listButton.get(curPos).getPiece().charAt(0) == enemyMove) {
                    if (tryToFrom(thisNum, curPos, whoseMove, enemyMove)) {
                        listButton.get(curPos).setInFocus(hard);
                    }
                    break;
                } else if (listButton.get(curPos).getPiece().charAt(0) == whoseMove) {
                    break;
                } else {
                    if (tryToFrom(thisNum, curPos, whoseMove, enemyMove)) {
                        listButton.get(curPos).setInFocus(hard);
                    }
                }
                curPos += 8;
            }
            int distFromTopLeft = Math.min(distFromLeft, distFromTop);
            int distFromTopRight = Math.min(7 - distFromLeft, distFromTop);
            int distFromBottomLeft = Math.min(distFromLeft, 7 - distFromTop);
            int distFromBottomRight = Math.min(7 - distFromLeft, 7 - distFromTop);
            curPos = thisNum - 9;
            while (distFromTopLeft > 0) {
                distFromTopLeft--;
                if (listButton.get(curPos).getPiece().charAt(0) == enemyMove) {
                    if (tryToFrom(thisNum, curPos, whoseMove, enemyMove)) {
                        listButton.get(curPos).setInFocus(hard);
                    }
                    break;
                } else if (listButton.get(curPos).getPiece().charAt(0) == whoseMove) {
                    break;
                } else {
                    if (tryToFrom(thisNum, curPos, whoseMove, enemyMove)) {
                        listButton.get(curPos).setInFocus(hard);
                    }
                }
                curPos -= 9;
            }
            curPos = thisNum - 7;
            while (distFromTopRight > 0) {
                distFromTopRight--;
                if (listButton.get(curPos).getPiece().charAt(0) == enemyMove) {
                    if (tryToFrom(thisNum, curPos, whoseMove, enemyMove)) {
                        listButton.get(curPos).setInFocus(hard);
                    }
                    break;
                } else if (listButton.get(curPos).getPiece().charAt(0) == whoseMove) {
                    break;
                } else {
                    if (tryToFrom(thisNum, curPos, whoseMove, enemyMove)) {
                        listButton.get(curPos).setInFocus(hard);
                    }
                }
                curPos -= 7;
            }
            curPos = thisNum + 7;
            while (distFromBottomLeft > 0) {
                distFromBottomLeft--;
                if (listButton.get(curPos).getPiece().charAt(0) == enemyMove) {
                    if (tryToFrom(thisNum, curPos, whoseMove, enemyMove)) {
                        listButton.get(curPos).setInFocus(hard);
                    }
                    break;
                } else if (listButton.get(curPos).getPiece().charAt(0) == whoseMove) {
                    break;
                } else {
                    if (tryToFrom(thisNum, curPos, whoseMove, enemyMove)) {
                        listButton.get(curPos).setInFocus(hard);
                    }
                }
                curPos += 7;
            }
            curPos = thisNum + 9;
            while (distFromBottomRight > 0) {
                distFromBottomRight--;
                if (listButton.get(curPos).getPiece().charAt(0) == enemyMove) {
                    if (tryToFrom(thisNum, curPos, whoseMove, enemyMove)) {
                        listButton.get(curPos).setInFocus(hard);
                    }
                    break;
                } else if (listButton.get(curPos).getPiece().charAt(0) == whoseMove) {
                    break;
                } else {
                    if (tryToFrom(thisNum, curPos, whoseMove, enemyMove)) {
                        listButton.get(curPos).setInFocus(hard);
                    }
                }
                curPos += 9;
            }
        } else if (chessPiece.charAt(1) == 'K' && chessPiece.charAt(0) == whoseMove) {
            if (whoseMove == 'W' && !menu.thisGame.getGameState().getWhiteKingMoved()
                    && !menu.thisGame.getGameState().getBottomRightRookMoved()
                    && menu.thisGame.getGameState().getBoard().startsWith("    ", 122)
                    && !isSquareInCheck(menu.thisGame.getGameState().getBoard(), 120, 'B')
                    && !isSquareInCheck(menu.thisGame.getGameState().getBoard(), 122, 'B')
                    && !isSquareInCheck(menu.thisGame.getGameState().getBoard(), 124, 'B')) {
                listButton.get(62).setInFocus(hard);
            }
            if (whoseMove == 'W' && !menu.thisGame.getGameState().getWhiteKingMoved()
                    && !menu.thisGame.getGameState().getBottomLeftRookMoved()
                    && menu.thisGame.getGameState().getBoard().startsWith("      ", 114)
                    && !isSquareInCheck(menu.thisGame.getGameState().getBoard(), 114, 'B')
                    && !isSquareInCheck(menu.thisGame.getGameState().getBoard(), 116, 'B')
                    && !isSquareInCheck(menu.thisGame.getGameState().getBoard(), 118, 'B')
                    && !isSquareInCheck(menu.thisGame.getGameState().getBoard(), 120, 'B')) {
                listButton.get(58).setInFocus(hard);
            }
            if (whoseMove == 'B' && !menu.thisGame.getGameState().getBlackKingMoved()
                    && !menu.thisGame.getGameState().getTopLeftRookMoved()
                    && menu.thisGame.getGameState().getBoard().startsWith("      ", 2)
                    && !isSquareInCheck(menu.thisGame.getGameState().getBoard(), 2, 'W')
                    && !isSquareInCheck(menu.thisGame.getGameState().getBoard(), 4, 'W')
                    && !isSquareInCheck(menu.thisGame.getGameState().getBoard(), 6, 'W')
                    && !isSquareInCheck(menu.thisGame.getGameState().getBoard(), 8, 'W')) {
                listButton.get(2).setInFocus(hard);
            }
            if (whoseMove == 'B' && !menu.thisGame.getGameState().getBlackKingMoved()
                    && !menu.thisGame.getGameState().getTopRightRookMoved()
                    && menu.thisGame.getGameState().getBoard().startsWith("    ", 10)
                    && !isSquareInCheck(menu.thisGame.getGameState().getBoard(), 8, 'W')
                    && !isSquareInCheck(menu.thisGame.getGameState().getBoard(), 10, 'W')
                    && !isSquareInCheck(menu.thisGame.getGameState().getBoard(), 12, 'W')
            ) {
                listButton.get(6).setInFocus(hard);
            }
            if (distFromLeft < 7 && (listButton.get(thisNum + 1).getPiece().charAt(0) == enemyMove
                    || listButton.get(thisNum + 1).getPiece().charAt(0) == ' ')
            ) {
                if (tryToFrom(thisNum, thisNum + 1, whoseMove, enemyMove)) {
                    listButton.get(thisNum + 1).setInFocus(hard);
                }

            }
            if (distFromLeft > 0 && distFromTop < 7 && (listButton.get(thisNum + 7).getPiece().charAt(0) == enemyMove
                    || listButton.get(thisNum + 7).getPiece().charAt(0) == ' ')
            ) {
                if (tryToFrom(thisNum, thisNum + 7, whoseMove, enemyMove)) {
                    listButton.get(thisNum + 7).setInFocus(hard);
                }
            }
            if (distFromTop < 7 && (listButton.get(thisNum + 8).getPiece().charAt(0) == enemyMove
                    || listButton.get(thisNum + 8).getPiece().charAt(0) == ' ')
            ) {
                if (tryToFrom(thisNum, thisNum + 8, whoseMove, enemyMove)) {
                    listButton.get(thisNum + 8).setInFocus(hard);
                }
            }
            if (distFromTop < 7 && distFromLeft < 7 && (listButton.get(thisNum + 9).getPiece().charAt(0) == enemyMove
                    || listButton.get(thisNum + 9).getPiece().charAt(0) == ' ')
            ) {
                if (tryToFrom(thisNum, thisNum + 9, whoseMove, enemyMove)) {
                    listButton.get(thisNum + 9).setInFocus(hard);
                }
            }
            if (distFromLeft > 0 && (listButton.get(thisNum - 1).getPiece().charAt(0) == enemyMove
                    || listButton.get(thisNum - 1).getPiece().charAt(0) == ' ')
            ) {
                if (tryToFrom(thisNum, thisNum - 1, whoseMove, enemyMove)) {
                    listButton.get(thisNum - 1).setInFocus(hard);
                }
            }
            if (distFromLeft < 7 && distFromTop > 0 && (listButton.get(thisNum - 7).getPiece().charAt(0) == enemyMove
                    || listButton.get(thisNum - 7).getPiece().charAt(0) == ' ')
            ) {
                if (tryToFrom(thisNum, thisNum - 7, whoseMove, enemyMove)) {
                    listButton.get(thisNum - 7).setInFocus(hard);
                }
            }
            if (distFromTop > 0 && (listButton.get(thisNum - 8).getPiece().charAt(0) == enemyMove
                    || listButton.get(thisNum - 8).getPiece().charAt(0) == ' ')
            ) {
                if (tryToFrom(thisNum, thisNum - 8, whoseMove, enemyMove)) {
                    listButton.get(thisNum - 8).setInFocus(hard);
                }
            }
            if (distFromTop > 0 && distFromLeft > 0 && (listButton.get(thisNum - 9).getPiece().charAt(0) == enemyMove
                    || listButton.get(thisNum - 9).getPiece().charAt(0) == ' ')
            ) {
                if (tryToFrom(thisNum, thisNum - 9, whoseMove, enemyMove)) {
                    listButton.get(thisNum - 9).setInFocus(hard);
                }
            }
        } else if (chessPiece.charAt(1) == 'H' && chessPiece.charAt(0) == whoseMove) {
            if (distFromTop > 1 && distFromLeft > 0 && (listButton.get(thisNum - 17).getPiece().charAt(0) == enemyMove
                    || listButton.get(thisNum - 17).getPiece().charAt(0) == ' ')) {
                if (tryToFrom(thisNum, thisNum - 17, whoseMove, enemyMove)) {
                    listButton.get(thisNum - 17).setInFocus(hard);
                }
            }
            if (distFromTop > 1 && distFromLeft < 7 && (listButton.get(thisNum - 15).getPiece().charAt(0) == enemyMove
                    || listButton.get(thisNum - 15).getPiece().charAt(0) == ' ')) {
                if (tryToFrom(thisNum, thisNum - 15, whoseMove, enemyMove)) {
                    listButton.get(thisNum - 15).setInFocus(hard);
                }
            }
            if (distFromTop > 0 && distFromLeft < 6 && (listButton.get(thisNum - 6).getPiece().charAt(0) == enemyMove
                    || listButton.get(thisNum - 6).getPiece().charAt(0) == ' ')) {
                if (tryToFrom(thisNum, thisNum - 6, whoseMove, enemyMove)) {
                    listButton.get(thisNum - 6).setInFocus(hard);
                }
            }
            if (distFromTop > 0 && distFromLeft > 1 && (listButton.get(thisNum - 10).getPiece().charAt(0) == enemyMove
                    || listButton.get(thisNum - 10).getPiece().charAt(0) == ' ')) {
                if (tryToFrom(thisNum, thisNum - 10, whoseMove, enemyMove)) {
                    listButton.get(thisNum - 10).setInFocus(hard);
                }
            }
            if (distFromTop < 7 && distFromLeft < 6 && (listButton.get(thisNum + 10).getPiece().charAt(0) == enemyMove
                    || listButton.get(thisNum + 10).getPiece().charAt(0) == ' ')) {
                if (tryToFrom(thisNum, thisNum + 10, whoseMove, enemyMove)) {
                    listButton.get(thisNum + 10).setInFocus(hard);
                }
            }
            if (distFromTop < 6 && distFromLeft < 7 && (listButton.get(thisNum + 17).getPiece().charAt(0) == enemyMove
                    || listButton.get(thisNum + 17).getPiece().charAt(0) == ' ')) {
                if (tryToFrom(thisNum, thisNum + 17, whoseMove, enemyMove)) {
                    listButton.get(thisNum + 17).setInFocus(hard);
                }

            }
            if (distFromTop < 6 && distFromLeft > 0 && (listButton.get(thisNum + 15).getPiece().charAt(0) == enemyMove
                    || listButton.get(thisNum + 15).getPiece().charAt(0) == ' ')) {
                if (tryToFrom(thisNum, thisNum + 15, whoseMove, enemyMove)) {
                    listButton.get(thisNum + 15).setInFocus(hard);
                }
            }
            if (distFromTop < 7 && distFromLeft > 1 && (listButton.get(thisNum + 6).getPiece().charAt(0) == enemyMove
                    || listButton.get(thisNum + 6).getPiece().charAt(0) == ' ')) {
                if (tryToFrom(thisNum, thisNum + 6, whoseMove, enemyMove)) {
                    listButton.get(thisNum + 6).setInFocus(hard);
                }
            }
        }

    }

    public boolean getIsInFocus() {
        return isInFocus;
    }

    private void onExit() {
        for (ChessTileButton b : listButton) {
            if (!b.getIsInFocus()) {
                b.setOutOfFocus();
            }
        }
    }

    private void hardClear() {
        for (ChessTileButton b : listButton) {
            b.setOutOfFocus();
        }
    }

    private boolean anyInFocus() {
        for (ChessTileButton b : listButton) {
            if (b.getIsInFocus()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    public void mousePressed(MouseEvent e) {
        if (this.isInFocus && menu != null) {
            menu.makeMove(thisNum);
        } else {
            menu.setCurrentSelected(thisNum);
            hardClear();
            onHover(true);
        }
        onHover(true);
    }

    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if (!anyInFocus()) {
            onHover(false);
        }


    }

    @Override
    public void mouseExited(MouseEvent e) {
        onExit();
    }

    private boolean tryToFrom(int fromIndex, int toIndex, char goodSide, char badSide) {
//        System.out.println(fromIndex);
//        System.out.println(toIndex);
        GameState gameState = GameState.coppyState(menu.thisGame.getGameState());
        if (gameState.getBoard().charAt(fromIndex * 2) == 'W'
                && gameState.getBoard().charAt(fromIndex * 2 + 1) == 'K') {
            gameState.setWhiteKingMoved(true);
            gameState.setWhiteKingPos(toIndex * 2);
        } else if (gameState.getBoard().charAt(fromIndex * 2) == 'B'
                && gameState.getBoard().charAt(fromIndex * 2 + 1) == 'K') {
            gameState.setBlackKingMoved(true);
            gameState.setBlackKingPos(toIndex * 2);
        }
        gameState.setBoard(gameState.getBoard().substring(0, toIndex * 2) + gameState.getBoard()
                .substring(fromIndex * 2, fromIndex * 2 + 2)
                + gameState.getBoard().substring(toIndex * 2 + 2));
        gameState.setBoard(gameState.getBoard().substring(0, fromIndex * 2) + "  "
                + gameState.getBoard().substring(fromIndex * 2 + 2));
        return !isSquareInCheck(gameState.getBoard(), goodSide == 'W' ? gameState.getWhiteKingPos()
                : gameState.getBlackKingPos(), badSide);
    }

    @SuppressWarnings("methodlength")
    private boolean isSquareInCheck(String givenBoard, int i, char badSide) {
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

}
