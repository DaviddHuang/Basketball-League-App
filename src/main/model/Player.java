package model;

// Represents a basketball player with a name, jersey number, height(cm), weight(lbs)
public class Player {
    private String name;
    private int number;
    private Double weight;
    private String position;
    private int height;
    private int points;
    private int rebounds;
    private int assists;
    private int gamesPlayed;
    private int steals;
    private int blocks;
    private boolean healthy;

    // REQUIRES: 0 <= number <= 99, height >= 0, weight >= 0.00
    // MODIFIES: this
    // EFFECTS: constructs a player with a name and a jersey number, along with their height,
    //          weight, points, rebounds, assists, steals, blocks, and games played
    public Player(String name, String position, int number, int height, Double weight) {
        this.name = name;
        this.number = number;
        this.height = height;
        this.weight = weight;
        this.position = position;
        points = 0;
        rebounds = 0;
        assists = 0;
        steals = 0;
        blocks = 0;
        gamesPlayed = 0;
        healthy = true;
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

    // EFFECTS: averages the steals of a player
    public Double averageSteals() {
        if (gamesPlayed == 0) {
            return 0.0;
        }
        double stealAverage = (double) steals / gamesPlayed;
        return Math.round(stealAverage * 10.0) / 10.0;
    }

    // EFFECTS: averages the steals of a player
    public Double averageBlocks() {
        if (gamesPlayed == 0) {
            return 0.0;
        }
        double blockAverage = (double) blocks / gamesPlayed;
        return Math.round(blockAverage * 10.0) / 10.0;
    }

    // REQUIRES: points >= 0, rebounds >= 0, assists >= 0, gamesPlayed >= 0
    // MODIFIES: this
    // EFFECTS: adds points, rebounds, assists and gamesPlayed to their respective totals,
    //           increase the number of games played
    public void playGame(int points, int rebounds, int assists, int steals, int blocks, int gamesPlayed) {
        this.points += points;
        this.rebounds += rebounds;
        this.assists += assists;
        this.steals += steals;
        this.blocks += blocks;
        this.gamesPlayed += gamesPlayed;
    }

    // MODIFIES: this
    // EFFECTS: sets players health status to given status
    public void isPlayerHealthy(Boolean status) {
        this.healthy = status;
    }

    // getters

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

    public int getSteals() {
        return steals;
    }

    public int getBlocks() {
        return blocks;
    }

    public int getGamesPlayed() {
        return gamesPlayed;
    }

    public double getWeight() {
        return weight;
    }

    public int getHeight() {
        return height;
    }

    public String getPosition() {
        return position;
    }

    public boolean getHealthStatus() {
        return healthy;
    }
}
