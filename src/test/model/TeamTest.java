package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class TeamTest {
    private Team testTeam;
    private Player testPlayer1;
    private Player testPlayer2;

    @BeforeEach
    void setup(){
        testTeam = new Team("Shanghai Sharks");
        testPlayer1 = new Player("Lebron James", 23, 195, 230.3);
        testPlayer2 = new Player("Kevin Durant", 35, 202, 201.7);
    }

    @Test
    void testAddPlayer() {
        testTeam.addPlayer(testPlayer1);
        assertEquals(1, testTeam.getRoster().size());
    }

    @Test
    void testAddPlayerSamePlayer() {
        testTeam.addPlayer(testPlayer1);
        testTeam.addPlayer(testPlayer1);
        assertEquals(1, testTeam.getRoster().size());
        assertEquals("Lebron James", testTeam.getRoster().get(0).getName());
    }

    @Test
    void testRemovePlayer() {
        testTeam.addPlayer(testPlayer1);
        assertEquals(1, testTeam.getRoster().size());
        assertEquals("Lebron James", testTeam.getRoster().get(0).getName());
        testTeam.removePlayer("Lebron James");
        assertFalse(testTeam.getRoster().contains(testPlayer1));
        assertEquals(0, testTeam.getRoster().size());
    }

    @Test
    void testRemovePlayerFail() {
        testTeam.addPlayer(testPlayer1);
        assertEquals(1, testTeam.rosterNumber());
        assertTrue(testTeam.getRoster().contains(testPlayer1));
        assertFalse(testTeam.removePlayer("Klay Thompson"));
    }
}
