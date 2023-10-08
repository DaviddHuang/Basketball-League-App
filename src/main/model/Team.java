package model;

import java.util.*;

public class Team {
    private List<Player> roster;
    private int wins;
    private String name;

    // EFFECTS: constructs a team with a name, 0 wins, and an empty roster list
    public Team(String name) {
        this.name = name;
        wins = 0;
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

    public List<Player> getRoster() {
        return this.roster;
    }

    public int rosterNumber() {
        return this.roster.size();
    }
}
