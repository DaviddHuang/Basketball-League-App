package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {
    private Player testPlayer;

    @BeforeEach
    void setUp() {
        testPlayer = new Player("Steph Curry", "PG", 30, 187, 180.0);
    }

    @Test
    void testConstructor() {
        assertEquals("Steph Curry", testPlayer.getName());
        assertEquals(30, testPlayer.getJerseyNumber());
        assertEquals(187, testPlayer.getHeight());
        assertEquals(180.0, testPlayer.getWeight());
        assertEquals("PG", testPlayer.getPosition());
        assertTrue(testPlayer.getHealthStatus());
    }

    @Test
    void testPlayGame() {
        testPlayer.playGame(25, 10, 10,1,1,1);
        assertEquals(25, testPlayer.getPoints());
        assertEquals(10, testPlayer.getRebounds());
        assertEquals(10, testPlayer.getAssists());
        assertEquals(1, testPlayer.getBlocks());
        assertEquals(1, testPlayer.getSteals());
        assertEquals(1, testPlayer.getGamesPlayed());
    }

    @Test
    void testPlayGameMultiple() {
        testPlayer.playGame(30, 20, 10, 1,1,1);
        testPlayer.playGame(1, 2, 3, 1,1,1);
        assertEquals(31, testPlayer.getPoints());
        assertEquals(22, testPlayer.getRebounds());
        assertEquals(13, testPlayer.getAssists());
        assertEquals(2, testPlayer.getSteals());
        assertEquals(2, testPlayer.getBlocks());
        assertEquals(2, testPlayer.getGamesPlayed());
    }

    @Test
    void testAveragePoints() {
        testPlayer.playGame(30, 20, 10,1,1,1);
        testPlayer.playGame(1, 2, 3,1,1,1);
        testPlayer.playGame(1,2,3,1,1,1);
        assertEquals(10.7, testPlayer.averagePoints());
    }

    @Test
    void testAveragePointsZero() {
        assertEquals(0, testPlayer.averagePoints());
    }

    @Test
    void testAverageRebounds() {
        testPlayer.playGame(30, 20, 10,1,1,1);
        testPlayer.playGame(1, 2, 3,1,1,1);
        assertEquals(11.0, testPlayer.averageRebounds());
    }

    @Test
    void testAverageReboundsZero() {
        assertEquals(0, testPlayer.averageRebounds());
    }

    @Test
    void testAverageAssists() {
        testPlayer.playGame(30, 20, 10,1,1,1);
        testPlayer.playGame(1, 2, 3,1,1,1);
        testPlayer.playGame(21, 5, 7,1,1,1);
        assertEquals(6.7, testPlayer.averageAssists());
    }

    @Test
    void testAverageAssistsZero() {
        assertEquals(0, testPlayer.averageAssists());
    }

    @Test
    void testAverageBlocks() {
        testPlayer.playGame(30, 20, 10,1,1,1);
        testPlayer.playGame(1, 2, 3,1,1,1);
        testPlayer.playGame(21, 5, 7,1,1,1);
        assertEquals(1.0, testPlayer.averageBlocks());
    }

    @Test
    void testAverageBlocksZero() {
        assertEquals(0, testPlayer.averageBlocks());
    }

    @Test
    void testAverageSteals() {
        testPlayer.playGame(30, 20, 10,1,1,1);
        testPlayer.playGame(1, 2, 3,1,1,1);
        testPlayer.playGame(21, 5, 7,1,1,1);
        assertEquals(1.0, testPlayer.averageSteals());
    }

    @Test
    void testAverageStealsZero() {
        assertEquals(0, testPlayer.averageSteals());
    }

    @Test
    void testIsPlayerHealthy() {
        assertTrue(testPlayer.getHealthStatus());
        testPlayer.isPlayerHealthy(false);
        assertFalse(testPlayer.getHealthStatus());
    }
}