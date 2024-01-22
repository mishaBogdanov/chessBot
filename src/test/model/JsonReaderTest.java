package model;

import org.junit.jupiter.api.Test;
import persistance.JsonReader;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class JsonReaderTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            ListOfGames games = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyWorkRoom() {
        JsonReader reader = new JsonReader("./data/testingGames.json");
        try {
            ListOfGames wr = reader.read();
            assertEquals("BRBHBBBQBKBBBHBRBPBPBPBP  BPBPBP                        " +
                    "BP              WP                      WPWPWPWP  WPWPWPWRWHWBWQWKWBWHWR",
                    wr.getGames().get(0).getMoves().get(1)); //tests the 2nd move of the game
            assertEquals(2, wr.getGames().get(0).getMoves().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    public void testingThrow() {
        JsonReader reader = new JsonReader("./data/testCrash.json");
        try {
            ListOfGames games = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        } catch (RuntimeException e) {
            // pass
        }
    }

//    @Test
//    void testReaderGeneralWorkRoom() {
//        JsonReader reader = new JsonReader("./data/testReaderGeneralWorkRoom.json");
//        try {
//            WorkRoom wr = reader.read();
//            assertEquals("My work room", wr.getName());
//            List<Thingy> thingies = wr.getThingies();
//            assertEquals(2, thingies.size());
//            checkThingy("needle", Category.STITCHING, thingies.get(0));
//            checkThingy("saw", Category.WOODWORK, thingies.get(1));
//        } catch (IOException e) {
//            fail("Couldn't read from file");
//        }
//    }
}
