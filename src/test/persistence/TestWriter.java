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
            Player p2 = new Player("Lebron James", "SF", 23, 205, 255.0);
            Player p3 = new Player("Kyle Lowry", "PG", 0, 205, 255.0);
            p.playGame(1,1,1,1,1,1);
            t1.addPlayer(p);
            t1.addPlayer(p2);
            t2.addPlayer(p3);
            t1.teamRecord(10,2);
            t2.teamRecord(0,10);
            p2.isPlayerHealthy(false);
            t1.calculateMostValuablePlayer();
            t1.calculateDefensivePlayer();
            t1.addPlayerInjuryReserve();
            t2.calculateMostValuablePlayer();
            t2.calculateDefensivePlayer();
            t2.addPlayerInjuryReserve();
            l.calculateLeagueWinner();
            l.calculateLeagueDefensivePlayer();
            l.calculateLeagueMostValuablePlayer();
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
            assertEquals("Lebron James", l.getTeams().get(0).getInjuryReserve().get(0).getName());
            assertEquals("Stephen Curry", l.getLeagueMostValuablePlayer().get(0).getName());
            assertEquals("Stephen Curry", l.getLeagueDefensivePlayer().get(0).getName());
            assertEquals("Vancouver Grizzlies", l.getLeagueWinner().get(0).getTeamName());
        } catch (IOException e) {
            fail("Should not have thrown IOException");
        }
    }
}
