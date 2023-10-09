package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LeagueTest {
    private League testLeague;
    private Team testTeam1;
    private Team testTeam2;

    @BeforeEach
    void setUp() {
        testLeague = new League("LA Fitness Rec League");
        testTeam1 = new Team("Shanghai Shark");
        testTeam2 = new Team("Vancouver Grizzlies");
    }

    @Test
    void testAddTeam() {
        testLeague.addTeam(testTeam1);
        assertEquals(1, testLeague.getAmountOfTeams());
        assertTrue(testLeague.getTeams().contains(testTeam1));
    }

    @Test
    void testAddTeamMultiple() {
        testLeague.addTeam(testTeam1);
        testLeague.addTeam(testTeam2);
        assertEquals(2, testLeague.getAmountOfTeams());
        assertTrue(testLeague.getTeams().contains(testTeam1));
        assertTrue(testLeague.getTeams().contains(testTeam2));
    }
}
