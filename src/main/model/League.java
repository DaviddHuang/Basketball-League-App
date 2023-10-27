package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.*;

// represents a basketball league with a name and an empty list of teams
public class League implements Writable {
    private List<Team> league;
    private String name;
    private boolean seasonNotOver;
    private List<Player> leagueMostValuablePlayer;
    private List<Player> leagueDefensivePlayer;
    private List<Team> leagueWinner;

    // EFFECTS: constructs a league with a name, empty list of teams, empty list of mvp's, empty list of defensive
    //          players, empty list of league winners, and season is still ongoing
    public League(String name) {
        this.name = name;
        this.league = new ArrayList<>();
        this.seasonNotOver = true;
        this.leagueMostValuablePlayer = new ArrayList<>();
        this.leagueDefensivePlayer = new ArrayList<>();
        this.leagueWinner = new ArrayList<>();

    }

    // MODIFIES: this
    // EFFECTS: adds team with given name to the league if team is not already in the league, return true if the add
    //          was successful, otherwise return false
    // Code referenced from: https://github.students.cs.ubc.ca/CPSC210/ControlAndDataFlowProjects.git Hockey team
    // util package, HockeyTeam class, method: insert()
    public boolean addTeam(Team team) {
        for (Team t : league) {
            if (t.getTeamName().equals(team.getTeamName())) {
                return false;
            }
        }
        league.add(team);
        return true;
    }

    // MODIFIES: this
    // EFFECTS: removes a team with the given name, return true if removal was successful, otherwise return false
    // Code referenced from: https://github.students.cs.ubc.ca/CPSC210/ControlAndDataFlowProjects.git Hockey team
    // util package, HockeyTeam class, method: remove()
    public boolean removeTeam(String name) {
        for (Team t : league) {
            if (t.getTeamName().equalsIgnoreCase(name)) {
                league.remove(t);
                return true;
            }
        }
        return false;
    }

    // REQUIRES: all teams have at least one player
    // MODIFIES: this
    // EFFECTS: calculates the scoring leading from every team and adds them to mvp list, return true if successful else
    //          return false
    public boolean calculateLeagueMostValuablePlayer() {
        if (!league.isEmpty() && !league.get(0).getScoringLeader().isEmpty()) {
            Player mvp = league.get(0).getScoringLeader().get(0);

            for (Team t : league) {
                if (mvp.mvpScore() < t.getScoringLeader().get(0).mvpScore()) {
                    mvp = t.getScoringLeader().get(0);

                }
            }
            leagueMostValuablePlayer.clear();
            leagueMostValuablePlayer.add(mvp);
            return true;
        }
        return false;
    }

    // MODIFIES: this
    // EFFECTS: calculates the defensive leader from every team and adds them to the defensive player list, return true
    //          if successful else return false
    public boolean calculateLeagueDefensivePlayer() {
        if (!league.isEmpty() && !league.get(0).getDefensiveLeader().isEmpty()) {
            Player dpoy = league.get(0).getDefensiveLeader().get(0);

            for (Team t : league) {
                if (dpoy.dpoyScore() < t.getDefensiveLeader().get(0).dpoyScore()) {
                    dpoy = t.getDefensiveLeader().get(0);

                }
            }
            leagueDefensivePlayer.clear();
            leagueDefensivePlayer.add(dpoy);
            return true;
        }
        return false;
    }

    // MODIFIES: this
    // EFFECTS: calculates the league winner from all team and adds the winner to winner list, return true if successful
    //          otherwise return false
    public boolean calculateLeagueWinner() {
        if (!league.isEmpty()) {
            Team winner = league.get(0);

            for (Team t : league) {
                if (winner.getWins() < t.getWins()) {
                    winner = t;
                }
            }
            leagueWinner.clear();
            leagueWinner.add(winner);
            return true;
        }
        return false;
    }

    // EFFECTS: changes the status of the season to the given status
    public void leagueStatus(boolean status) {
        this.seasonNotOver = status;
    }

    // EFFECTS: converts League into JSON object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("League name", name);
        json.put("Teams", teamsToJson());
        json.put("League MVP", leagueMostValuablePlayerToJson());
        json.put("League DPOY", leagueDefensivePlayerToJson());
        json.put("League winner", leagueWinnerTOJson());
        return json;
    }

    // EFFECTS: returns teams in this league as a JSON array
    private JSONArray teamsToJson() {
        JSONArray array = new JSONArray();

        for (Team t : league) {
            array.put(t.toJson());
        }
        return array;
    }

    // EFFECTS: returns the league MVP in this league as a JSON array
    private JSONArray leagueMostValuablePlayerToJson() {
        JSONArray array = new JSONArray();

        for (Player p : leagueMostValuablePlayer) {
            array.put(p.toJson());
        }
        return array;
    }

    // EFFECTS: returns the league DPOY in this league as a JSON array
    private JSONArray leagueDefensivePlayerToJson() {
        JSONArray array = new JSONArray();

        for (Player p : leagueDefensivePlayer) {
            array.put(p.toJson());
        }
        return array;
    }

    // EFFECTS: returns the league winner in this league as a JSON array
    private JSONArray leagueWinnerTOJson() {
        JSONArray array = new JSONArray();

        for (Team t : leagueWinner) {
            array.put(t.toJson());
        }
        return array;
    }

    // getters

    public String getLeagueName() {
        return name;
    }

    public int getAmountOfTeams() {
        return league.size();
    }

    public List<Team> getTeams() {
        return league;
    }

    public List<Player> getLeagueMostValuablePlayer() {
        return leagueMostValuablePlayer;
    }

    public List<Player> getLeagueDefensivePlayer() {
        return leagueDefensivePlayer;
    }

    public List<Team> getLeagueWinner() {
        return leagueWinner;
    }

    public boolean getLeagueStatus() {
        return seasonNotOver;
    }
}
