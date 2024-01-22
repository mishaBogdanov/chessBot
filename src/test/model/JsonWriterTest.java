package model;

import org.junit.jupiter.api.Test;
import persistance.JsonReader;
import persistance.JsonWriter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

class JsonWriterTest {
    //NOTE TO CPSC 210 STUDENTS: the strategy in designing tests for the JsonWriter is to
    //write data to a file and then use the reader to read it back in and check that we
    //read in a copy of what was written out.

    @Test
    void testWriterInvalidFile() {
        try {
            ListOfGames wr = new ListOfGames();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyWorkroom() {
        try {
            ArrayList<String> list = new ArrayList<>();
            list.add("move 1");
            ChessGame game = new ChessGame('B', list, "now");
            ListOfGames wr = new ListOfGames();
            wr.addGame(game);
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyWorkroom.json");
            writer.open();
            writer.write(wr);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyWorkroom.json");
            wr = reader.read();
            assertEquals('B', wr.getGames().get(0).getWhoWon());
            assertEquals(1, wr.getGames().get(0).getMoves().size());
            assertEquals("now", wr.getGames().get(0).getEndTimeInString());
            assertEquals("move 1", wr.getGames().get(0).getMoves().get(0));



            list = new ArrayList<>();
            list.add("move 2");
            list.add("Move 3");
            LocalDateTime time = LocalDateTime.now();
            String timeInString = (DateTimeFormatter.ofPattern("EEEE d").format(time)) + "th"
                    + DateTimeFormatter.ofPattern("H:m").format(time) + ". ";
            game = new ChessGame('W', list, time);
            wr = new ListOfGames();
            wr.addGame(game);
            writer = new JsonWriter("./data/testWriterEmptyWorkroom.json");
            writer.open();
            writer.write(wr);
            writer.close();
            reader = new JsonReader("./data/testWriterEmptyWorkroom.json");
            wr = reader.read();
            assertEquals('W', wr.getGames().get(0).getWhoWon());
            assertEquals(2, wr.getGames().get(0).getMoves().size());
            assertEquals(timeInString, wr.getGames().get(0).getEndTimeInString());
            assertEquals("move 2", wr.getGames().get(0).getMoves().get(0));
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }


    //yes, I know this is weird, but for some reason this isn't getting line coverage for is empty in logTest... ?? :/
    @Test
    public void testIsEmpty() {
        ListOfGames list = new ListOfGames();
        assertTrue(list.isListEmpty());
        ChessGame game = new ChessGame();
        list.addGame(game);
        assertFalse(list.isListEmpty());
    }

//    @Test
//    void testWriterGeneralWorkroom() {
//        try {
//            WorkRoom wr = new WorkRoom("My work room");
//            wr.addThingy(new Thingy("saw", Category.METALWORK));
//            wr.addThingy(new Thingy("needle", Category.STITCHING));
//            JsonWriter writer = new JsonWriter("./data/testWriterGeneralWorkroom.json");
//            writer.open();
//            writer.write(wr);
//            writer.close();
//
//            JsonReader reader = new JsonReader("./data/testWriterGeneralWorkroom.json");
//            wr = reader.read();
//            assertEquals("My work room", wr.getName());
//            List<Thingy> thingies = wr.getThingies();
//            assertEquals(2, thingies.size());
//            checkThingy("saw", Category.METALWORK, thingies.get(0));
//            checkThingy("needle", Category.STITCHING, thingies.get(1));
//
//        } catch (IOException e) {
//            fail("Exception should not have been thrown");
//        }
//    }
}