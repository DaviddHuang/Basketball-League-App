package persistence;

import model.League;
import model.Player;
import model.Team;
import org.json.JSONWriter;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class TestWriter {

    @Test
    void testWriterCannotWriteFail() {
        try {
            League l = new League("CPSC 210 BASKETBALL LEAGUE");
            Writer writer = new Writer("./data/\0random:failNow.json");
            writer.open();
            fail("Expected IOException");
        } catch (IOException e) {
            // expected
        }
    }

    @Test
    void testWriterEmptyLeague() {
        try {
            League l = new League("CPSC 210 BASKETBALL LEAGUE");
            Writer writer = new Writer("./data/testWriterEmptyLeague.json");
            writer.open();
            writer.write(l);
            writer.closeWriter();

            Reader reader = new Reader("./data/testWriterEmptyLeague.json");
            l = reader.read();
            assertEquals("CPSC 210 BASKETBALL LEAGUE", l.getLeagueName());
            assertEquals(0, l.getAmountOfTeams());
        } catch (IOException e) {
            fail("Should not have thrown IOException");
        }
    }

    @Test
    void testWriterGeneralLeague() {
        try {
            League l = new League("CPSC 210 BASKETBALL LEAGUE");
            Team t1 = new Team("Vancouver Grizzlies");
            Team t2 = new Team("Calgary Flames");
            l.addTeam(t1);
            l.addTeam(t2);
            Player p = new Player("Stephen Curry", "PG", 30, 188, 205.0);
            t1.addPlayer(p);
            t1.calculateMostValuablePlayer();
            t1.calculateDefensivePlayer();
            Writer writer = new Writer("./data/testWriterGeneralLeague.json");
            writer.open();
            writer.write(l);
            writer.closeWriter();

            Reader reader = new Reader("./data/testWriterGeneralLeague.json");
            l = reader.read();
            assertEquals("CPSC 210 BASKETBALL LEAGUE", l.getLeagueName());
            assertEquals(2, l.getAmountOfTeams());
            assertEquals(1, l.getTeams().get(0).getRoster().size());
            assertEquals("Stephen Curry", l.getTeams().get(0).getRoster().get(0).getName());
            assertEquals("Stephen Curry", l.getTeams().get(0).getScoringLeader().get(0).getName());
            assertEquals("Stephen Curry", l.getTeams().get(0).getDefensiveLeader().get(0).getName());
        } catch (IOException e) {
            fail("Should not have thrown IOException");
        }
    }
}
