package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.*;

// Represents a basketball team with a list of players on the time, amount of wins, losses, win percentage, and
// games played
public class Team implements Writable {
    private List<Player> roster;
    private int wins;
    private int losses;
    private int teamGamesPlayed;
    private String name;
    private List<Player> injuryReserve;
    private List<Player> scoringLeader;
    private List<Player> defensiveLeader;

    // MODIFIES: this
    // EFFECTS: constructs a team with a name, 0 wins, 0 losses, 0 games played, an empty roster, empty injury
    //          reserve, and empty list of statistical team leaders
    public Team(String name) {
        this.name = name;
        wins = 0;
        losses = 0;
        teamGamesPlayed = 0;
        roster = new ArrayList<>();
        injuryReserve = new ArrayList<>();
        scoringLeader = new ArrayList<>();
        defensiveLeader = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: adds a new player to the roster, if the player shares the same number as another player don't add
    //          the player, return true if add is successful and return false otherwise
    // Code referenced from: https://github.students.cs.ubc.ca/CPSC210/ControlAndDataFlowProjects.git Hockey team
    // util package, HockeyTeam class, method: insert()
    public Boolean addPlayer(Player player) {
        for (Player p : roster) {
            if (p.getJerseyNumber() == player.getJerseyNumber()) {
                return false;
            }
        }
        roster.add(player);
        return true;
    }

    // MODIFIES: this
    // EFFECTS: removes player with given name from roster or injury reserve and return true, otherwise return false
    // Code referenced from: https://github.students.cs.ubc.ca/CPSC210/ControlAndDataFlowProjects.git Hockey team
    // util package, HockeyTeam class, method: remove()
    public boolean removePlayer(String name) {
        for (Player p : roster) {
            if (p.getName().equalsIgnoreCase(name)) {
                roster.remove(p);
                return true;
            }
        }
        for (Player p : injuryReserve) {
            if (p.getName().equalsIgnoreCase(name)) {
                injuryReserve.remove(p);
                roster.remove(p);
                return true;
            }
        }
        return false;
    }

    // MODIFIES: this
    // EFFECTS: checks if each player is healthy, if they are not remove them from roster and add to injury reserve
    //          return true if successful, otherwise return false
    public boolean addPlayerInjuryReserve() {
        for (Player p : roster) {
            if (p.getHealthStatus() == false) {
                roster.remove(p);
                injuryReserve.add(p);
                return true;
            }
        }
        return false;
    }

    // MODIFIES: this
    // EFFECTS: removes player off IR if they are healthy and adds them back into roster, return true if successful
    //          otherwise return false
    public boolean movePlayerOffInjuryReserve() {
        for (Player p : injuryReserve) {
            if (p.getHealthStatus() == true) {
                injuryReserve.remove(p);
                roster.add(p);
                return true;
            }
        }
        return false;
    }

    // MODIFIES: this
    // EFFECTS: calculates which player has the highest mvp score, and adds the calculated mvp to the mvp list, return
    //          true if successful, otherwise return false
    public boolean calculateMostValuablePlayer() {
        if (!roster.isEmpty()) {
            Player leader = roster.get(0);

            for (Player p : roster) {
                if (leader.mvpScore() < p.mvpScore()) {
                    leader = p;
                }
            }
            scoringLeader.clear();
            scoringLeader.add(leader);
            return true;
        }
        return false;
    }

    // MODIFIES: this
    // EFFECTS: calculates which player has the highest dpoy score, and adds the calculated dpoy to the dpoy list,
    //          return true if successful, otherwise return false
    public boolean calculateDefensivePlayer() {
        while (!roster.isEmpty()) {
            Player leader = roster.get(0);

            for (Player p : roster) {
                if (leader.dpoyScore() < p.dpoyScore()) {
                    leader = p;
                }
            }
            defensiveLeader.clear();
            defensiveLeader.add(leader);
            return true;
        }
        return false;
    }

    // EFFECTS: calculates the win percentage of a team
    public double winPercentage() {
        if (teamGamesPlayed == 0) {
            return 0.000;
        }
        double teamWinPercentage = (double) wins / teamGamesPlayed;
        return Math.round(teamWinPercentage * 1000.0) / 1000.0;
    }

    // REQUIRES: wins >= 0, losses >= 0, gamesPlayed >= 0
    // MODIFIES: this
    // EFFECTS: updates the teams wins, losses
    public void teamRecord(int wins, int losses) {
        this.wins += wins;
        this.losses += losses;
        this.teamGamesPlayed = this.wins + this.losses;
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

    // EFFECTS: converts Team into JSON object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("Team name", name);
        json.put("Wins", wins);
        json.put("Losses", losses);
        json.put("Games played", teamGamesPlayed);
        json.put("Roster", playersToJson());
        json.put("Injury reserve", injuryReservePlayersToJson());
        json.put("Scoring leader", scoringLeaderToJson());
        json.put("Defensive leader", defensiveLeaderToJson());
        return json;
    }

    // EFFECTS: returns players in the team as a JSON array
    private JSONArray playersToJson() {
        JSONArray array = new JSONArray();

        for (Player p : roster) {
            array.put(p.toJson());
        }
        return array;
    }

    // EFFECTS: returns injured players in the team as a JSON array
    private JSONArray injuryReservePlayersToJson() {
        JSONArray array = new JSONArray();

        for (Player p : injuryReserve) {
            array.put(p.toJson());
        }
        return array;
    }

    // EFFECTS: returns scoring leaders in the team as a JSON array
    private JSONArray scoringLeaderToJson() {
        JSONArray array = new JSONArray();

        for (Player p : scoringLeader) {
            array.put(p.toJson());
        }
        return array;
    }

    // EFFECTS: returns defensive leaders in the team as a JSON array
    private JSONArray defensiveLeaderToJson() {
        JSONArray array = new JSONArray();

        for (Player p : defensiveLeader) {
            array.put(p.toJson());
        }
        return array;
    }

    // getters

    public List<Player> getRoster() {
        return this.roster;
    }

    public int rosterNumber() {
        return this.roster.size();
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getLosses() {
        return losses;
    }

    public void setLosses(int losses) {
        this.losses = losses;
    }

    public int getTeamGamesPlayed() {
        return teamGamesPlayed;
    }

    public void setTeamGamesPlayed(int gamesPlayed) {
        this.teamGamesPlayed = gamesPlayed;
    }

    public String getTeamName() {
        return name;
    }

    public List<Player> getInjuryReserve() {
        return this.injuryReserve;
    }

    public List<Player> getScoringLeader() {
        return this.scoringLeader;
    }

    public List<Player> getDefensiveLeader() {
        return this.defensiveLeader;
    }
}
