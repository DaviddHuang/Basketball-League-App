package persistence;

import model.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// Represents a reader responsible for extracting league information from JSON data stored in a file
// Code is influenced by: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
public class Reader {
    private String sourceFile;

    public Reader(String sourceFile) {
        this.sourceFile = sourceFile;
    }

    // EFFECTS: reads league from file and returns it
    // throws an IOException if an error occurs reading data from file
    public League read() throws IOException {
        String data = readFile(sourceFile);
        JSONObject jsonObject = new JSONObject(data);
        return parseLeague(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses league from JSON object and returns it
    private League parseLeague(JSONObject jsonObject) {
        String name = jsonObject.getString("League name");
        League l = new League(name);
        EventLog.getInstance().logEvent(new Event("Loading: " + l.getLeagueName() + " league"));
        addTeam(l, jsonObject);
        l.calculateLeagueMostValuablePlayer();
        l.calculateLeagueDefensivePlayer();
        l.calculateLeagueWinner();
        return l;
    }

    // MODIFIES: l
    // EFFECTS: parses teams from JSON object and adds it to the league
    private void addTeam(League l, JSONObject jsonObject) {
        JSONArray array = jsonObject.getJSONArray("Teams");
        for (Object json : array) {
            JSONObject nextTeam = (JSONObject) json;
            Team team = addOneTeam(l, nextTeam);
            addRoster(team, nextTeam);
            addInjuryReserve(team, nextTeam);
            addScoringLeader(team, nextTeam);
            addDefensiveLeader(team, nextTeam);
        }
    }

    // MODIFIES: l
    // EFFECTS: Parses a team from JSON object and returns it
    private Team addOneTeam(League l, JSONObject jsonObject) {
        String name = jsonObject.getString("Team name");
        int wins = jsonObject.getInt("Wins");
        int losses = jsonObject.getInt("Losses");
        int gamesPlayed = jsonObject.getInt("Games played");
        Team team = new Team(name);
        team.setWins(wins);
        team.setLosses(losses);
        team.setTeamGamesPlayed(gamesPlayed);
        l.addTeamNoLog(team);
        return team;
    }

    // MODIFIES: t
    // EFFECTS: parses roster from JSON object and adds it to team
    private void addRoster(Team t, JSONObject jsonObject) {
        JSONArray array = jsonObject.getJSONArray("Roster");
        for (Object json : array) {
            JSONObject nextRoster = (JSONObject) json;
            addOnePlayer(t, nextRoster);
        }
    }

    // MODIFIES: t
    // EFFECTS: parses injury reserve from JSON object and adds it to team
    private void addInjuryReserve(Team t, JSONObject jsonObject) {
        JSONArray array = jsonObject.getJSONArray("Injury reserve");
        for (Object json : array) {
            JSONObject nextInjuryReserve = (JSONObject) json;
            addOnePlayer(t, nextInjuryReserve);
        }
    }

    // MODIFIES: t
    // EFFECTS: parses scoring leaders from JSON object and adds it to scoring leader
    private void addScoringLeader(Team t, JSONObject jsonObject) {
        JSONArray array = jsonObject.getJSONArray("Scoring leader");
        for (Object json : array) {
            JSONObject nextScoringLeader = (JSONObject) json;
            addOnePlayer(t, nextScoringLeader);
            t.calculateMostValuablePlayer();
        }
    }

    // MODIFIES: t
    // EFFECTS: parses defensive leaders from JSON object and adds it to defensive leaders
    private void addDefensiveLeader(Team t, JSONObject jsonObject) {
        JSONArray array = jsonObject.getJSONArray("Defensive leader");
        for (Object json : array) {
            JSONObject nextDefensiveLeader = (JSONObject) json;
            addOnePlayer(t, nextDefensiveLeader);
            t.calculateDefensivePlayer();
        }
    }

    // MODIFIES: t
    // EFFECTS: parses player from JSON object and adds them to roster or injury reserve
    private void addOnePlayer(Team t, JSONObject jsonObject) {
        String name = jsonObject.getString("Name");
        String position = jsonObject.getString("Position");
        int number = jsonObject.getInt("Number");
        int height = jsonObject.getInt("Height");
        double weight = jsonObject.getDouble("Weight");
        int points = jsonObject.getInt("Points");
        int rebounds = jsonObject.getInt("Rebounds");
        int assists = jsonObject.getInt("Assists");
        int steal = jsonObject.getInt("Steals");
        int blocks = jsonObject.getInt("Blocks");
        int gamesPlayed = jsonObject.getInt("Games played");
        boolean healthStatus = jsonObject.getBoolean("Health status");
        Player player = new Player(name, position, number, height, weight);
        player.playGame(points, rebounds, assists, steal, blocks, gamesPlayed);
        player.isPlayerHealthy(healthStatus);
        t.addPlayer(player);
        t.addPlayerInjuryReserve();
    }
}
