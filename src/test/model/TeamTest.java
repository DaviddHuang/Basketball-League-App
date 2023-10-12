package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TeamTest {
    private Team testTeam;
    private Player testPlayer1;
    private Player testPlayer2;
    private Player testPlayer3;
    private Player testPlayer4;

    @BeforeEach
    void setup(){
        testTeam = new Team("Shanghai Sharks");
        testPlayer1 = new Player("Lebron James","SF" ,23, 195, 230.3);
        testPlayer2 = new Player("Kevin Durant", "SF",35, 202, 201.7);
        testPlayer3 = new Player("Ducky", "C",23, 201, 201.9);
        testPlayer4 = new Player("Stephen Curry", "PG", 30, 190, 185.0);
    }

    @Test
    void testConstructor() {
        assertEquals("Shanghai Sharks", testTeam.getTeamName());
        assertEquals(0, testTeam.getWins());
        assertEquals(0, testTeam.getLosses());
        assertEquals(0, testTeam.getTeamGamesPlayed());
    }

    @Test
    void testAddPlayer() {
        assertTrue(testTeam.addPlayer(testPlayer1));
        assertEquals(1, testTeam.getRoster().size());
        assertTrue(testTeam.getRoster().contains(testPlayer1));
    }

    @Test
    void testAddPlayerSameJersey() {
        assertTrue(testTeam.addPlayer(testPlayer1));
        assertFalse(testTeam.addPlayer(testPlayer3));
        assertEquals(1, testTeam.getRoster().size());
        assertEquals("Lebron James", testTeam.getRoster().get(0).getName());
    }

    @Test
    void testAddPlayerMultiple() {
        assertTrue(testTeam.addPlayer(testPlayer1));
        assertTrue(testTeam.addPlayer(testPlayer2));
        assertEquals(2, testTeam.getRoster().size());
        assertTrue(testTeam.getRoster().contains(testPlayer1));
        assertTrue(testTeam.getRoster().contains(testPlayer2));
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
        assertFalse(testTeam.removePlayer("Tristan Thompson"));
    }

    @Test
    void testRemovePlayerInjured() {
        testTeam.addPlayer(testPlayer1);
        testPlayer1.isPlayerHealthy(false);
        testTeam.addPlayerInjuryReserve();
        assertEquals(1, testTeam.getInjuryReserve().size());
        testTeam.removePlayer("Lebron James");
        assertEquals(0, testTeam.getInjuryReserve().size());
        assertEquals(0, testTeam.getRoster().size());
    }

    @Test
    void testRemovePlayerInjuredMultiple() {
        testTeam.addPlayer(testPlayer1);
        testTeam.addPlayer(testPlayer2);
        testPlayer1.isPlayerHealthy(false);
        testPlayer2.isPlayerHealthy(false);
        testTeam.addPlayerInjuryReserve();
        testTeam.addPlayerInjuryReserve();
        assertEquals(2, testTeam.getInjuryReserve().size());
        testTeam.removePlayer("Lebron James");
        testTeam.removePlayer("Kevin Durant");
        assertEquals(0, testTeam.getInjuryReserve().size());
        assertEquals(0, testTeam.getRoster().size());
    }

    @Test
    void testRemovePlayerInjuredFail() {
        testTeam.addPlayer(testPlayer1);
        testPlayer1.isPlayerHealthy(false);
        testTeam.addPlayerInjuryReserve();
        assertEquals(1, testTeam.getInjuryReserve().size());
        assertFalse(testTeam.removePlayer("Steph Curry"));
        assertEquals(1, testTeam.getInjuryReserve().size());
        assertEquals(0, testTeam.getRoster().size());
    }

    @Test
    void testWinPercentage() {
        testTeam.matchWin();
        testTeam.matchLose();
        testTeam.matchWin();
        testTeam.matchWin();
        assertEquals(0.750, testTeam.winPercentage());
    }

    @Test
    void testWinPercentageZeroGamesPlayed() {
        assertEquals(0, testTeam.winPercentage());
    }

    @Test
    void testTeamRecord() {
        testTeam.teamRecord(50,2);
        assertEquals(50, testTeam.getWins());
        assertEquals(2, testTeam.getLosses());
        assertEquals(52, testTeam.getTeamGamesPlayed());
        assertEquals(0.962, testTeam.winPercentage());
    }

    @Test
    void testTeamRecordMultiple() {
        testTeam.teamRecord(50,2);
        assertEquals(50, testTeam.getWins());
        assertEquals(2, testTeam.getLosses());
        assertEquals(52, testTeam.getTeamGamesPlayed());
        assertEquals(0.962, testTeam.winPercentage());
        testTeam.teamRecord(4, 0);
        assertEquals(54, testTeam.getWins());
        assertEquals(2, testTeam.getLosses());
        assertEquals(0.964, testTeam.winPercentage());
    }

    @Test
    void testAddInjuryReserve() {
        testTeam.addPlayer(testPlayer1);
        testTeam.addPlayer(testPlayer2);
        testPlayer1.isPlayerHealthy(false);
        testTeam.addPlayerInjuryReserve();
        assertEquals(1, testTeam.getRoster().size());
        assertEquals(1, testTeam.getInjuryReserve().size());
        assertTrue(testTeam.getRoster().contains(testPlayer2));
        assertTrue(testTeam.getInjuryReserve().contains(testPlayer1));
    }

    @Test
    void testAddInjuryReserveMultiple() {
        testTeam.addPlayer(testPlayer1);
        testTeam.addPlayer(testPlayer2);
        testPlayer1.isPlayerHealthy(false);
        testPlayer2.isPlayerHealthy(false);
        testTeam.addPlayerInjuryReserve();
        testTeam.addPlayerInjuryReserve();
        assertEquals(2, testTeam.getInjuryReserve().size());
        assertTrue(testTeam.getInjuryReserve().contains(testPlayer1));
        assertTrue(testTeam.getInjuryReserve().contains(testPlayer2));
    }

    @Test
    void testAddInjuryReserveFail() {
        testTeam.addPlayer(testPlayer1);
        testPlayer1.isPlayerHealthy(true);
        assertFalse(testTeam.addPlayerInjuryReserve());
    }

    @Test
    void testMovePlayerOffInjuryReserve() {
        testTeam.addPlayer(testPlayer1);
        testPlayer1.isPlayerHealthy(false);
        testTeam.addPlayerInjuryReserve();
        assertTrue(testTeam.getInjuryReserve().contains(testPlayer1));
        assertEquals(1, testTeam.getInjuryReserve().size());
        testPlayer1.isPlayerHealthy(true);
        assertTrue(testTeam.movePlayerOffInjuryReserve());
        assertEquals(1, testTeam.getRoster().size());
        assertTrue(testTeam.getRoster().contains(testPlayer1));
    }

    @Test
    void testMovePlayerOffInjuryReserveFail() {
        testTeam.addPlayer(testPlayer1);
        testPlayer1.isPlayerHealthy(false);
        testTeam.addPlayerInjuryReserve();
        assertFalse(testTeam.movePlayerOffInjuryReserve());
    }

    @Test
    void testMovePlayerOffInjuryReserveNoPlayers() {
        assertFalse(testTeam.movePlayerOffInjuryReserve());
    }

    @Test
    void testCalculateMostValuablePlayer() {
        testPlayer1.playGame(1,1,1,1,1,1);
        testPlayer2.playGame(2,2,2,2,2,2);
        testTeam.addPlayer(testPlayer1);
        testTeam.addPlayer(testPlayer2);
        assertEquals(2, testTeam.getRoster().size());
        testTeam.calculateMostValuablePlayer();
        assertEquals(1, testTeam.getRoster().size());
        assertEquals(1, testTeam.getScoringLeader().size());
        assertTrue(testTeam.getScoringLeader().contains(testPlayer2));
    }

    @Test
    void testCalculateMostValuablePlayerMultiple() {
        testPlayer1.playGame(1,1,1,1,1,1);
        testPlayer2.playGame(50,100,99,10,10,1);
        testPlayer4.playGame(50,99,99,10,10,1);
        testTeam.addPlayer(testPlayer1);
        testTeam.addPlayer(testPlayer2);
        testTeam.addPlayer(testPlayer4);
        assertEquals(3, testTeam.getRoster().size());
        assertTrue(testTeam.calculateMostValuablePlayer());
        assertEquals(2, testTeam.getRoster().size());
        assertEquals(1, testTeam.getScoringLeader().size());
        assertTrue(testTeam.getScoringLeader().contains(testPlayer2));
    }

    @Test
    void testCalculateMostValuablePlayerFail() {
        assertFalse(testTeam.calculateMostValuablePlayer());
    }

    @Test
    void testCalculateDefensivePlayer() {
        testPlayer1.playGame(1,1,1,1,1,1);
        testTeam.addPlayer(testPlayer1);
        assertEquals(1, testTeam.getRoster().size());
        assertTrue(testTeam.calculateDefensivePlayer());
        assertEquals(0, testTeam.getRoster().size());
        assertEquals(1, testTeam.getDefensiveLeader().size());
        assertTrue(testTeam.getDefensiveLeader().contains(testPlayer1));
    }

    @Test
    void testCalculateDefensivePlayerMultiple() {
        testPlayer1.playGame(1,1,1,2,1,1);
        testPlayer2.playGame(1,1,1,1,1,1);
        testTeam.addPlayer(testPlayer1);
        testTeam.addPlayer(testPlayer2);
        assertEquals(2, testTeam.getRoster().size());
        assertTrue(testTeam.calculateDefensivePlayer());
        assertEquals(1, testTeam.getRoster().size());
        assertEquals(1, testTeam.getDefensiveLeader().size());
        assertTrue(testTeam.getDefensiveLeader().contains(testPlayer1));
    }

    @Test
    void testCalculateDefensivePlayerFail() {
        assertFalse(testTeam.calculateDefensivePlayer());
    }
}
