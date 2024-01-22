package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ListOfGamesTesting {
    ListOfGames list;
    ChessGame game;
    ChessGame game2;
    GameState chessState;
    @BeforeEach
    public void runBeforeEach() {
        list = new ListOfGames();
        game = new ChessGame();
        chessState = new GameState(3);
        game2=new ChessGame(chessState);

    }

    @Test
    public void testIsEmpty() {
        assertTrue(list.isListEmpty());
        list.addGame(game);
        assertFalse(list.isListEmpty());
    }

    @Test

    public void testAddGame() {
        list.addGame(game);
        assertEquals(1, list.getGames().size());
        list.addGame(game2);
        assertEquals(2, list.getGames().size());
        assertEquals(game2, list.getGames().get(1));
        assertEquals(game, list.getGames().get(0));

    }
}
