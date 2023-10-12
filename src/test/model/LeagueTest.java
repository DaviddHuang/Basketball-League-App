package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LeagueTest {
    private League testLeague;
    private Team testTeam1;
    private Team testTeam2;
    private Player testPlayer1;
    private Player testPlayer2;

    @BeforeEach
    void setUp() {
        testLeague = new League("LA Fitness Rec League");
        testTeam1 = new Team("Shanghai Sharks");
        testTeam2 = new Team("Vancouver Grizzlies");
        testPlayer1 = new Player("Stephen Curry", "PG", 30, 190, 190.0);
        testPlayer2 = new Player("Lebron James", "SF", 23, 211, 210.0);
    }

    @Test
    void testConstructor() {
        assertEquals("LA Fitness Rec League", testLeague.getLeagueName());
    }

    @Test
    void testAddTeam() {
        assertTrue(testLeague.addTeam(testTeam1));
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
    void testAddTeamFail() {
        testLeague.addTeam(testTeam1);
        assertFalse(testLeague.addTeam(testTeam1));
        assertEquals(1, testLeague.getAmountOfTeams());
        assertTrue(testLeague.getTeams().contains(testTeam1));
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

    @Test
    void testCalculateLeagueMostValuablePlayer() {
        testLeague.addTeam(testTeam2);
        testLeague.addTeam(testTeam1);
        testTeam1.addPlayer(testPlayer1);
        testTeam2.addPlayer(testPlayer2);
        testPlayer1.playGame(100,100,100,100,100,1);
        testPlayer2.playGame(1,1,1,1,1,1);
        assertTrue(testTeam1.calculateMostValuablePlayer());
        assertTrue(testTeam2.calculateMostValuablePlayer());
        assertTrue(testTeam1.getScoringLeader().contains(testPlayer1));
        assertTrue(testTeam2.getScoringLeader().contains(testPlayer2));
        assertTrue(testLeague.calculateLeagueMostValuablePlayer());
        assertTrue(testLeague.getLeagueMostValuablePlayer().contains(testPlayer1));
    }

    @Test
    void testCalculateLeagueMostValuablePlayerNoLeague() {
        assertFalse(testLeague.calculateLeagueMostValuablePlayer());
    }

    @Test
    void testCalculateLeagueDefensivePlayer() {
        testLeague.addTeam(testTeam2);
        testLeague.addTeam(testTeam1);
        testTeam1.addPlayer(testPlayer1);
        testTeam2.addPlayer(testPlayer2);
        testPlayer1.playGame(100,100,100,100,100,1);
        testPlayer2.playGame(1,1,1,1,1,1);
        assertTrue(testTeam1.calculateDefensivePlayer());
        assertTrue(testTeam2.calculateDefensivePlayer());
        assertTrue(testTeam1.getDefensiveLeader().contains(testPlayer1));
        assertTrue(testTeam2.getDefensiveLeader().contains(testPlayer2));
        assertTrue(testLeague.calculateLeagueDefensivePlayer());
        assertTrue(testLeague.getLeagueDefensivePlayer().contains(testPlayer1));
    }

    @Test
    void testCalculateLeagueDefensivePlayerNoLeague() {
        assertFalse(testLeague.calculateLeagueDefensivePlayer());
    }

    @Test
    void testLeagueStatus() {
        testLeague.leagueStatus(false);
        assertFalse(testLeague.getLeagueStatus());
    }
}
