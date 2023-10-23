package persistence;

import model.League;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class TestReader extends JsonTest {

    @Test
    void testReaderFileNotThere() {
        Reader reader = new Reader("./data/bogusFile.json");
        try {
            League l = reader.read();
            fail("expected IOException");
        } catch (IOException e) {
            // expected
        }
    }

    @Test
    void testReaderEmptyLeague() {
        Reader reader = new Reader("./data/testReaderEmptyLeague.json");
        try {
            League l = reader.read();
            assertEquals("My League", l.getLeagueName());
            assertEquals(0, l.getTeams().size());
        } catch (IOException e) {
            fail("File could not be read");
        }
    }

    @Test
    void testReaderGeneralLeague() {
        Reader reader = new Reader("./data/testReaderGeneralLeague.json");
        try {
            League l = reader.read();
            assertEquals("NBA", l.getLeagueName());
            assertEquals(2, l.getTeams().size());
            assertEquals("Vancouver Grizzlies", l.getTeams().get(0).getTeamName());
            assertEquals("Toronto Raptors", l.getTeams().get(1).getTeamName());
            assertEquals(40, l.getTeams().get(1).getWins());
            assertEquals(42, l.getTeams().get(1).getLosses());
            assertEquals(74, l.getTeams().get(0).getWins());
            assertEquals(8, l.getTeams().get(0).getLosses());
            assertEquals("Stephen Curry", l.getTeams().get(0).getRoster().get(0).getName());
            assertEquals("Lebron James", l.getTeams().get(0).getRoster().get(1).getName());
            assertEquals("Stephen Curry", l.getTeams().get(0).getScoringLeader().get(0).getName());
            assertEquals("Stephen Curry", l.getTeams().get(0).getDefensiveLeader().get(0).getName());
            assertEquals("Kyle Lowry", l.getTeams().get(1).getRoster().get(0).getName());
        } catch (IOException e) {
            fail("File could not be read");
        }
    }
}
