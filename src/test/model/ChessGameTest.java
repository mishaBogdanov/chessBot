package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ChessGameTest {
    ChessGame c1;
    GameState g1;
    ChessGame c2;
    ChessGame c3;
    GameState g2;
    @BeforeEach
    public void runBeforeEach() {
        g1 = new GameState(2);
        c1 = new ChessGame();
        c2 = new ChessGame(g1);
    }

    @Test
    public void testConstructor() {
        assertEquals(g1, c2.getGameState());
        assertEquals("BRBHBBBQBKBBBHBRBPBPBPBPBPBPBPBP                             "
                + "                                   WPWPWPWPWPWPWPWPWRWHWBWQWKWBWHWR",
                c1.getGameState().board);
    }

    @Test
    public void testParseNextMove() {
        assertTrue(c1.parseNextMove("G2G4"));
        assertTrue(c1.parseNextMove("A1A2"));
        assertTrue(c1.parseNextMove("H2H8"));
        assertFalse(c1.parseNextMove("I2L4"));
        assertFalse(c1.parseNextMove("A10A"));
        assertFalse(c1.parseNextMove("L1A2"));
        c1.parseNextMove("L1A2");
        assertFalse(c1.parseNextMove("A9A2"));
        assertFalse(c1.parseNextMove("A1L2"));
        assertFalse(c1.parseNextMove("A1A9"));
        assertFalse(c1.parseNextMove("A1A2P"));
        assertTrue(c1.parseNextMove("A1A2"));
        assertFalse(c1.parseNextMove("@1A2"));
        assertFalse(c1.parseNextMove("A1@2"));
        assertFalse(c1.parseNextMove("A1L2"));
        assertFalse(c1.parseNextMove("A/A2"));
        assertFalse(c1.parseNextMove("A1A/"));
        assertFalse(c1.parseNextMove("A1/2"));
        assertFalse(c1.parseNextMove("A1{1"));
        assertFalse(c1.parseNextMove("}1A1"));


        g2 = new GameState();
        g2.board =
                "        BK      " +
                "                " +
                "                " +
                "                " +
                "                " +
                "                " +
                "                " +
                "        WK      ";
        c3 = new ChessGame(g2);
        assertTrue(c3.parseNextMove("E1E2"));
        assertTrue(c3.parseNextMove("E8E7"));
        assertEquals(104, c3.getGameState().whiteKingPos);
        assertEquals(24, c3.getGameState().blackKingPos);


    }

    @Test
    public void testWhoWon() {
        c1.setWhoWon('W');
        assertEquals('W', c1.getWhoWon());
        c1.getDate();
    }

    @Test
    public void testGetMoves() {
        c2.parseNextMove("E2E4");
        c2.parseNextMove("E7E5");
        assertEquals("BRBHBBBQBKBBBH" +
                "BRBPBPBPBP  BPBPBP" +
                "                  " +
                "      BP          " +
                "    WP            " +
                "          WPWPWPWP" +
                "  WPWPWPWRWHWBWQWK" +
                "WBWHWR", c2.getMoves().get(1));
        c1.makeBotMove(c2.getGameState());
        assertEquals("BRBHBBBQBKBBBH" +
                "BRBPBPBPBP  BPBPBP" +
                "                  " +
                "      BP          " +
                "    WP            " +
                "          WPWPWPWP" +
                "  WPWPWPWRWHWBWQWK" +
                "WBWHWR", c2.getGameState().getBoard());

    }

    @Test
    public void testMakeNumericMoves() {
        c1.makeNumericMove(12, 13);
        assertEquals("BRBHBBBQBKBB  HRBPBPBPBPBPBPBPBP                                                " +
                "                WPWPWPWPWPWPWPWPWRWHWBWQWKWBWHWR", c1.getGameState().getBoard());
    }

}
