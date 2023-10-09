package model;

import java.util.*;

public class Team {
    private List<Player> roster;
    private int wins;
    private int losses;
    private int teamGamesPlayed;
    private String name;

    // EFFECTS: constructs a team with a name, 0 wins, and an empty roster list
    public Team(String name) {
        this.name = name;
        wins = 0;
        losses = 0;
        teamGamesPlayed = 0;
        roster = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: adds a new player to the roster if player is not already in the roster
    public void addPlayer(Player player) {
        if (!roster.contains(player)) {
            roster.add(player);
        }
    }

    // MODIFIES: this
    // EFFECTS: removes player with given name from roster and return true, otherwise return false
    public boolean removePlayer(String name) {
        for (Player p : roster) {
            if (p.getName().equals(name)) {
                roster.remove(p);
                return true;
            }
        }
        return false;
    }

    public double winPercentage() {
        if (teamGamesPlayed == 0) {
            return 0.000;
        }
        double teamWinPercentage = (double) wins / teamGamesPlayed;
        return Math.round(teamWinPercentage * 1000.0) / 1000.0;
    }

    // MODIFIES: this
    // EFFECTS: increases the number of wins by a team by 1
    public void matchWin() {
        wins++;
        teamGamesPlayed++;
    }

    // MODIFIES: this
    // EFFECTS: increases the number of losses by a team by 1
    public void matchLose() {
        losses++;
        teamGamesPlayed++;
    }

    public List<Player> getRoster() {
        return this.roster;
    }

    public int rosterNumber() {
        return this.roster.size();
    }

    public int getWins() {
        return wins;
    }

    public int getLosses() {
        return losses;
    }

    public int getTeamGamesPlayed() {
        return teamGamesPlayed;
    }

    public String getTeamName() {
        return name;
    }
}
