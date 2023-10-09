package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LeagueTest {
    private League testLeague;
    private Team testTeam1;
    private Team testTeam2;

    @BeforeEach
    void setUp() {
        testLeague = new League("LA Fitness Rec League");
        testTeam1 = new Team("Shanghai Sharks");
        testTeam2 = new Team("Vancouver Grizzlies");
    }

    @Test
    void testConstructor() {
        assertEquals("LA Fitness Rec League", testLeague.getLeagueName());
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

    @Test
    void testRemoveTeam() {
        testLeague.addTeam(testTeam1);
        testLeague.removeTeam("Shanghai Sharks");
        assertEquals(0, testLeague.getAmountOfTeams());
        assertFalse(testLeague.getTeams().contains(testTeam1));
    }

    @Test
    void testRemoveTeamMultiple() {
        testLeague.addTeam(testTeam1);
        testLeague.addTeam(testTeam2);
        testLeague.removeTeam("Shanghai Sharks");
        testLeague.removeTeam("Vancouver Grizzlies");
        assertEquals(0, testLeague.getAmountOfTeams());
        assertFalse(testLeague.getTeams().contains(testTeam1));
        assertFalse(testLeague.getTeams().contains(testTeam2));
    }

    @Test
    void testRemoveTeamFail() {
        testLeague.addTeam(testTeam1);
        testLeague.removeTeam("Vancouver Canucks");
        assertEquals(1, testLeague.getAmountOfTeams());
        assertTrue(testLeague.getTeams().contains(testTeam1));
    }
}
