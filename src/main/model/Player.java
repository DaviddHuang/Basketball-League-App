package model;

public class Player {
    private String name;
    private int number;
    private Double weight;
    private int height;
    private int points;
    private int rebounds;
    private int assists;
    private int gamesPlayed;

    // REQUIRES: 0 <= number <= 99, height >= 0, weight >= 0.00
    // MODIFIES: this
    // EFFECTS: constructs a player with a name and a jersey number, along with their height,
    //          weight, points, rebounds, assists, and games played
    public Player(String name, int number, int height, Double weight) {
        this.name = name;
        this.number = number;
        this.height = height;
        this.weight = weight;
        points = 0;
        rebounds = 0;
        assists = 0;
        gamesPlayed = 0;
    }

    // EFFECTS: averages the points of a player
    public Double averagePoints() {
        if (gamesPlayed == 0) {
            return 0.0;
        }
        double pointsAverage = (double) points / gamesPlayed;
        return Math.round(pointsAverage * 10.0) / 10.0;
    }

    // EFFECTS: averages the rebounds of a player
    public Double averageRebounds() {
        if (gamesPlayed == 0) {
            return 0.0;
        }
        double reboundAverage = (double) rebounds / gamesPlayed;
        return Math.round(reboundAverage * 10.0) / 10.0;
    }

    // EFFECTS: averages the rebounds of a player
    public Double averageAssists() {
        if (gamesPlayed == 0) {
            return 0.0;
        }
        double assistAverage = (double) assists / gamesPlayed;
        return Math.round(assistAverage * 10.0) / 10.0;
    }


    // REQUIRES: points >= 0, rebounds >= 0, assists >= 0
    // MODIFIES: this
    // REQUIRES: adds points, rebounds, and assists to their respective totals,
    //           increase the number of games played
    public void playGame(int points, int rebounds, int assists) {
        this.points += points;
        this.rebounds += rebounds;
        this.assists += assists;
        gamesPlayed++;
    }

    public String getName() {
        return name;
    }

    public int getJerseyNumber() {
        return number;
    }

    public int getPoints() {
        return points;
    }

    public int getRebounds() {
        return rebounds;
    }

    public int getAssists() {
        return assists;
    }

    public int getGamesPlayed() {
        return gamesPlayed;
    }
}
