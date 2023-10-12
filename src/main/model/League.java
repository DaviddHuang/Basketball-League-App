package model;

import java.util.*;

// represents a basketball league with a name and an empty list of teams
public class League {
    private List<Team> league;
    private String name;
    private boolean seasonNotOver;
    private List<Player> leagueMostValuablePlayer;
    private List<Player> leagueDefensivePlayer;

    // EFFECTS: constructs a league with a name, empty list of teams, and season status set to true
    public League(String name) {
        this.name = name;
        this.league = new ArrayList<>();
        this.seasonNotOver = true;
        this.leagueMostValuablePlayer = new ArrayList<>();
        this.leagueDefensivePlayer = new ArrayList<>();

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

    // MODIFIES: this
    // EFFECTS: takes the scoring leader from each team and compares their MVP score, the player with the highest MVP
    //          score becomes MVP and is added to the mvp list. Return true if successful, otherwise return false
    public boolean calculateLeagueMostValuablePlayer() {
        while (!league.isEmpty()) {
            Player mvp = league.get(0).getScoringLeader().get(0);

            for (Team t : league) {
                if (mvp.mvpScore() < t.getScoringLeader().get(0).mvpScore()) {
                    mvp = t.getScoringLeader().get(0);

                }
            }
            leagueMostValuablePlayer.add(mvp);
            return true;
        }
        return false;
    }

    public boolean calculateLeagueDefensivePlayer() {
        while (!league.isEmpty()) {
            Player dpoy = league.get(0).getDefensiveLeader().get(0);

            for (Team t : league) {
                if (dpoy.dpoyScore() < t.getDefensiveLeader().get(0).dpoyScore()) {
                    dpoy = t.getDefensiveLeader().get(0);

                }
            }
            leagueDefensivePlayer.add(dpoy);
            return true;
        }
        return false;
    }

    // EFFECTS: changes the status of the season to the given status
    public void leagueStatus(boolean status) {
        this.seasonNotOver = status;
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
}
