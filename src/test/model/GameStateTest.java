package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameStateTest {
    GameState t1;
    GameState t2;
    GameState t3;
    GameState t4;

    @BeforeEach
    public void runBefore() {
        t1 = new GameState();
        t2 = new GameState(4);
        t3 = new GameState();
        t4 = new GameState(5);
    }

    @Test
    public void testConstructor() {
        for (int i = 0; i < 8; i++) {
            assertFalse(t1.blackPawnsMoved[i]);
            assertFalse(t1.whitePawnsMoved[i]);
            assertFalse(t2.whitePawnsMoved[i]);
            assertFalse(t2.blackPawnsMoved[i]);
            assertFalse(t3.whitePawnsMoved[i]);
            assertFalse(t3.blackPawnsMoved[i]);
            assertFalse(t4.whitePawnsMoved[i]);
            assertFalse(t4.blackPawnsMoved[i]);
        }
        assertEquals("BRBHBBBQBKBBBHBRBPBPBPBPBPBPBPBP                          "
                + "                                      WPWPWPWPWPWPWPWPWRWHWBWQWKWBWHWR", t2.getBoard());
        assertEquals("BRBHBBBQBKBBBHBRBPBPBPBPBPBPBPBP                          "
                + "                                      WPWPWPWPWPWPWPWPWRWHWBWQWKWBWHWR", t4.getBoard());
        assertEquals(4, t2.getMovesRemaining());
        assertEquals(5, t4.getMovesRemaining());
    }

    @Test
    public void testSetBoard() {
        t1.setBoard("BRBHBB  BKBBBHBR" +
                "BP    BP  BPBP  " +
                "        BP    BP" +
                "  WHBPWPWP      " +
                "  BQ            " +
                "          WH    " +
                "WPWPWP    WPWPWP" +
                "WR      WK    WR");
        assertEquals("BRBHBB  BKBBBHBR" +
                "BP    BP  BPBP  " +
                "        BP    BP" +
                "  WHBPWPWP      " +
                "  BQ            " +
                "          WH    " +
                "WPWPWP    WPWPWP" +
                "WR      WK    WR", t1.getBoard());
        t2.setBoard("BRBHBB  BKBBBHBR" +
                "BP    BP  BPBP  " +
                "        BP    BP" +
                "  WHBPWPWP      " +
                "  BQ            " +
                "          WH    " +
                "WPWPWP    WPWPWP" +
                "WR      WK    WR");
        assertEquals("BRBHBB  BKBBBHBR" +
                "BP    BP  BPBP  " +
                "        BP    BP" +
                "  WHBPWPWP      " +
                "  BQ            " +
                "          WH    " +
                "WPWPWP    WPWPWP" +
                "WR      WK    WR", t2.getBoard());
    }

    @Test
    public void testMovesMade() {
        GameState t3 = GameState.coppyState(t1);
        assertEquals(t3.board, t1.board);
        assertEquals(t3.movesMade, t1.movesMade);
        assertEquals(0, t1.getMovesMade());
        assertEquals(0, t2.getMovesMade());
        t3.blackPawnsMoved[0]=true;
        GameState.resetPawnMoves(t3.blackPawnsMoved);
        assertFalse(t3.blackPawnsMoved[0]);

        t1.movesMade++;
        assertEquals(1, t1.getMovesMade());
        t1.resetMovesMade();
        assertEquals(0, t1.getMovesMade());
    }

    @Test
    public void testGetters() {
        t1.setBlackKingPos(12);
        t1.setWhiteKingPos(12);
        t1.setBlackKingMoved(true);
        t1.setWhiteKingMoved(true);
        t1.getWhiteKingPos();
        t1.getBlackKingMoved();
        t1.getWhitePawnsMoved();
        t1.getWhiteKingMoved();
        t1.getBottomLeftRookMoved();
        t1.getBottomRightRookMoved();
        t1.getTopRightRookMoved();
        t1.getTopLeftRookMoved();
    }
}