package ui;

import model.League;
import model.Player;
import model.Team;

import java.util.*;

// Basketball league application
public class BasketballLeagueApp {
    private Scanner input;
    private Team funTeam;
    private League funLeague;
    private Player myPlayer;

    public BasketballLeagueApp() {
        startApp();
    }

    // MODIFIES: this
    // EFFECTS: read and use the user inputs
    // Code referenced from https://github.students.cs.ubc.ca/CPSC210/TellerApp.git from the tellerApp class in the ui
    // package, method: runTeller()
    private void startApp() {
        boolean notOver = true;
        String userInput = null;

        initializeLeague();

        System.out.println("\nWelcome to the Basketball League Tracker!");
        System.out.println("-----------------------------------------");
        startMenu();

        while (notOver) {
            userInput = input.next();
            userInput = userInput.toLowerCase();

            if (userInput.equals("quit")) {
                notOver = false;
                break;
            } else {
                readInputs(userInput);
            }
        }

        System.out.println("It's sad to see you go...");
        System.exit(0);
    }

    // EFFECTS: displays the start menu to user
    // Code referenced from https://github.students.cs.ubc.ca/CPSC210/TellerApp.git from the tellerApp class in the ui
    // package, method: displayMenu()
    private void startMenu() {
        System.out.println("Type 'Start' to create your own league: ");
        System.out.println("Type 'Quit' to exit now: ");
    }

    // MODIFIES: this
    // EFFECTS: reads the input from the user and does the next command
    // Code referenced from https://github.students.cs.ubc.ca/CPSC210/TellerApp.git from the tellerApp class in the ui
    // package, method: processCommand()
    private void readInputs(String userInput) {
        if (userInput.equals("start")) {
            createLeague();
        } else {
            System.out.println("Invalid input, please try again.\n");
            startMenu();
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes a scanner for input
    // Code referenced from https://github.students.cs.ubc.ca/CPSC210/TellerApp.git from the tellerApp class in the ui
    // package, method: init()
    private void initializeLeague() {
        input = new Scanner(System.in);
        input.useDelimiter("\n");
    }


    // MODIFIES: this
    // EFFECTS: takes a name from the user and creates a league with a name then continues with creating a team, if
    //          user input enters a blank space, prompt invalid input and retry
    private void createLeague() {
        System.out.println("Please enter the name of your league: ");
        String name = input.next();

        if (!name.isEmpty()) {
            funLeague = new League(name);
            System.out.println("League added: " + name);
            createTeam();
        } else {
            System.out.println("Invalid Input...");
            createLeague();
        }
    }

    // MODIFIES: this
    // EFFECTS: take a name from the user and creates a team with that name then adds it to the league then displays
    //          team menu, if the user enters a blank space prompt invalid input and retry
    private void createTeam() {
        System.out.println("Please enter the name of a team: ");
        String name = input.next();

        if (!name.isEmpty()) {
            funTeam = new Team(name);
            System.out.println("Team added: " + name);
            funLeague.addTeam(funTeam);
            teamMenu();
        } else {
            System.out.println("Invalid input...");
            createTeam();
        }
    }

    // MODIFIES: this
    // EFFECTS: gives prompts for user inputs for name, jersey number, height, and weight, and creates a player using
    //          the given inputs. Adds player to the current team and displays player menu
    private void createPlayer() {
        System.out.println("Please enter the name of a player you want to add to the roster: ");
        String name = input.next();
        System.out.println("Please enter the position of the player: (PG = Point Guard, SG = Shooting Guard, SF ="
                + " Small Forward, PF = Power Forward, C = Center) ");
        String pos = input.next();
        System.out.println("Please enter the players jersey number: ");
        int jersey = input.nextInt();
        checkSameJersey(jersey);
        System.out.println("Please enter the players height(cm): ");
        int height = input.nextInt();
        System.out.println("Please enter the players weight(lbs): ");
        double weight = input.nextDouble();
        myPlayer = new Player(name, pos, jersey, height, weight);
        funTeam.addPlayer(myPlayer);
        System.out.println("Player added: " + myPlayer.getName());
        playerMenu();
    }

    // MODIFIES: this
    // EFFECTS: asks user for input for player they want to remove, takes input and removes player from the team then
    //          prompts player menu if successful, otherwise keep prompting user to remove player
    private void removePlayer() {
        System.out.println("Please enter the name of a player on your roster you wish to remove: ");
        String name = input.next();
        if (funTeam.removePlayer(name)) {
            System.out.println("Played removed: " + name);
            playerMenu();
        } else {
            System.out.println("That player does not exist...");
            removePlayer();
        }
    }

    // MODIFIES: this
    // EFFECTS: asks user for input for team they want to remove, takes input and removes team from league, prompts
    //          team menu if successful, otherwise keep prompting to remove team
    private void removeTeam() {
        System.out.println("Please enter the name of a team in the league you wish to remove: ");
        String teamName = input.next();
        if (funLeague.removeTeam(teamName)) {
            System.out.println("Team removed: " + teamName);
            teamMenu();
        } else {
            System.out.println("That team does not exist...");
            removeTeam();
        }
    }

    // MODIFIES: this
    // EFFECTS: displays options that a user has when interacting with a player
    // Code referenced from https://github.students.cs.ubc.ca/CPSC210/TellerApp.git from the tellerApp class in the ui
    // package, method: runTeller()
    private void playerMenu() {
        playerMenuPrints();

        String newPlayer = input.next();
        newPlayer = newPlayer.toLowerCase();
        if (newPlayer.equals("new")) {
            createPlayer();
        } else if (newPlayer.equals("back")) {
            teamMenu();
        } else if (newPlayer.equals("view")) {
            displayRosterInfo();
            playerMenu();
        } else if (newPlayer.equals("injuries")) {
            displayInjuryReserve();
            playerMenu();
        } else if (newPlayer.equals("select player")) {
            isRosterEmptySelectPlayer();
        } else if (newPlayer.equals("remove player")) {
            isRosterEmptyRemovePlayer();
        } else if (newPlayer.equals("edit")) {
            changeRecord();
        } else {
            System.out.println("Invalid Selection...");
            playerMenu();
        }
    }

    // MODIFIES: this
    // EFFECTS: displays options that a user has when interacting with a team
    // Code referenced from https://github.students.cs.ubc.ca/CPSC210/TellerApp.git from the tellerApp class in the ui
    // package method: runTeller()
    private void teamMenu() {
        teamMenuPrints();

        String newTeam = input.next();
        newTeam = newTeam.toLowerCase();
        if (newTeam.equals("new")) {
            createTeam();
        } else if (newTeam.equals("menu")) {
            startApp();
        } else if (newTeam.equals("continue")) {
            continueWithNoTeam();
            playerMenu();
        } else if (newTeam.equals("view")) {
            displayTeamInfo();
            teamMenu();
        } else if (newTeam.equals("select team")) {
            selectTeamWithNoTeam();
            selectTeamUseAsInput();
        } else if (newTeam.equals("remove team")) {
            isTeamEmptyRemoveTeam();
        } else if (newTeam.equals("end")) {
            endSeason();
        } else {
            organizeTeamMenu();
        }
    }

    // EFFECTS: display the players on the roster with their name, jersey number, average points, rebounds, and
    //          assists
    private void displayRosterInfo() {
        if (funTeam.getRoster().isEmpty()) {
            System.out.println("Team: " + funTeam.getTeamName() + " ||" + " Wins: " + funTeam.getWins() + " ||"
                    + " Losses: " + funTeam.getLosses() + " ||" + " PCT: " + funTeam.winPercentage());
            System.out.println("The roster is empty...");
            return;
        }
        System.out.println("Team: " + funTeam.getTeamName() + " ||" + " Wins: " + funTeam.getWins() + " ||"
                + " Losses: " + funTeam.getLosses() + " ||" + " PCT: " + funTeam.winPercentage());
        System.out.println("Current Roster: ");
        System.out.println("---------------");
        for (Player p : funTeam.getRoster()) {
            System.out.println("Name: " + p.getName() + " ||" + " Position: " + p.getPosition() + " ||"
                    + " #: " + p.getJerseyNumber() + " ||" + " Height: " + p.getHeight() + "cm"
                    + " ||" + " Weight: " + p.getWeight() + "lbs" + " ||" + " PPG: " + p.averagePoints() + " ||"
                    + " RPG: " + p.averageRebounds() + " ||" + " APG: " + p.averageAssists() + " ||" + " SPG: "
                    + p.averageSteals() + " ||" + " BPG: " + p.averageBlocks() + " ||" + " GP: " + p.getGamesPlayed());
        }
    }

    // EFFECTS: displays the teams name, wins, and losses if there are any
    private void displayTeamInfo() {
        if (funLeague.getTeams().isEmpty()) {
            System.out.println("The league is currently empty...");
            return;
        }
        System.out.println("Current Teams: ");
        for (Team t : funLeague.getTeams()) {
            System.out.println("Team: " + t.getTeamName() + " ||" + " Wins: " + t.getWins() + " ||" + " Losses: "
                     + t.getLosses() + " ||" + " PCT: " + t.winPercentage());
        }
    }

    // EFFECTS: displays the teams injured players if there are any
    private void displayInjuryReserve() {
        if (funTeam.getInjuryReserve().isEmpty()) {
            System.out.println("There are no injured players...");
            return;
        }
        System.out.println("Current Injured Players: ");
        System.out.println("-------------------------");
        for (Player p : funTeam.getInjuryReserve()) {
            System.out.println("Name: " + p.getName() + " ||" + " Position: " + p.getPosition() + " ||"
                    + " #: " + p.getJerseyNumber() + " ||" + " Height: " + p.getHeight() + "cm"
                    + " ||" + " Weight: " + p.getWeight() + "lbs" + " ||" + " PPG: " + p.averagePoints() + " ||"
                    + " RPG: " + p.averageRebounds() + " ||" + " APG: " + p.averageAssists() + " ||" + " SPG: "
                    + p.averageSteals() + " ||" + " BPG: " + p.averageBlocks() + " ||" + " GP: " + p.getGamesPlayed());
        }
    }

    // EFFECTS: displays the league MVP
    private void displayLeagueMostValuablePlayer() {
        for (Player p : funLeague.getLeagueMostValuablePlayer()) {
            System.out.println("League MVP: " + p.getName());
        }
    }

    // EFFECTS: displays the league DPOY
    private void displayLeagueDefensivePlayer() {
        for (Player p : funLeague.getLeagueDefensivePlayer()) {
            System.out.println("League DPOY: " + p.getName());
        }
    }

    // EFFECTS: displays the league winner
    private void displayLeagueWinner() {
        for (Team t : funLeague.getLeagueWinner()) {
            System.out.println("League Winner: " + t.getTeamName() + " ||" + " Wins: " + t.getWins() + " ||"
                    + " Losses: " + t.getLosses() + " ||" + " PCT: " + t.winPercentage());
        }
    }

    // EFFECTS: prints team menu options
    // Code referenced from https://github.students.cs.ubc.ca/CPSC210/TellerApp.git from the tellerApp class in the ui
    // package, method: displayMenu()
    private void teamMenuPrints() {
        System.out.println("\nType 'New' to add a new team: ");
        System.out.println("Type 'Continue' to add players to the current team: ");
        System.out.println("Type 'View' to view a list of teams: ");
        System.out.println("Type 'Select Team' to add players to a different team or edit a teams record: ");
        System.out.println("Type 'Remove Team' to remove a team: ");
        System.out.println("Type 'End' to end the league season: ");
        System.out.println("Type 'Menu' to go back to the main menu: ");
    }

    // MODIFIES: this
    // EFFECTS: changes the current team to the team with the given name, if there are no current teams give prompt
    //          and ask for user input again
    private void selectTeam(String name) {
        for (Team t: funLeague.getTeams()) {
            if (t.getTeamName().equalsIgnoreCase(name)) {
                System.out.println("Current Team Selected: " + t.getTeamName());
                funTeam = t;
                playerMenu();
            }
        }
        System.out.println("Team not found...");
        selectTeamUseAsInput();
    }

    // EFFECTS: processes the user's input when asked to select a team
    private void selectTeamUseAsInput() {
        System.out.println("Type the name of the team you wish to select: ");
        String teamInput = input.next();
        teamInput = teamInput.toLowerCase();
        selectTeam(teamInput);
    }

    // MODIFIES: this
    // EFFECTS: changes the current player to the player with the given name to add stats for them, if there is no
    //          players on a team give user a prompt and ask for user input again
    private void selectPlayer(String name) {
        for (Player p : funTeam.getRoster()) {
            if (p.getName().equalsIgnoreCase(name)) {
                System.out.println("Current Player Selected: " + p.getName());
                myPlayer = p;
                addStatsForPlayer();
            }
        }
        for (Player p : funTeam.getInjuryReserve()) {
            if (p.getName().equalsIgnoreCase(name)) {
                System.out.println("Current Player Selected: " + p.getName());
                myPlayer = p;
                addStatsForPlayer();
            }
        }
        System.out.println("Player not found...");
        selectPlayerInput();
    }

    // EFFECTS: takes user input on selected player
    private void selectPlayerInput() {
        System.out.println("Type the name of the player you wish to select: ");
        String playerInput = input.next();
        playerInput = playerInput.toLowerCase();
        selectPlayer(playerInput);
    }

    // EFFECTS: displays print statements for the player menu
    // Code referenced from https://github.students.cs.ubc.ca/CPSC210/TellerApp.git from the tellerApp class in the ui
    // package, method: displayMenu()
    private void playerMenuPrints() {
        System.out.println("\nType 'New' to add new player: ");
        System.out.println("Type 'View' to view the current roster: ");
        System.out.println("Type 'Injuries' to view currently injured players: ");
        System.out.println("Type 'Edit' to edit the current teams record");
        System.out.println("Type 'Select Player' for the player you wish to add stats for: ");
        System.out.println("Type 'Remove Player' for the player you wish to remove: ");
        System.out.println("Type 'Back' to go back: ");

    }

    // MODIFIES: this
    // EFFECTS: gives prompt for user input for latest amount of points, rebounds, assists, and games played for the
    //          current player, takes user input and updates the current stats of the player
    private void addStatsForPlayer() {
        System.out.println("Please enter the players current healthy status (True = healthy, false = injured): ");
        boolean status = input.nextBoolean();
        myPlayer.isPlayerHealthy(status);
        System.out.println("Please enter the latest amount of points scored for this player: ");
        int points = input.nextInt();
        System.out.println("Please enter the latest amount of rebounds collected for this player: ");
        int rebounds = input.nextInt();
        System.out.println("Please enter the latest amount of assists collected for this player: ");
        int assists = input.nextInt();
        System.out.println("Please enter the latest amount of steals collected for this player: ");
        int steals = input.nextInt();
        System.out.println("Please enter the latest amount of blocks collected for this player: ");
        int blocks = input.nextInt();
        System.out.println("Please enter the latest amount of games played for this player: ");
        int gamesPlayed = input.nextInt();
        myPlayer.playGame(points, rebounds, assists, steals,blocks, gamesPlayed);
        funTeam.calculateMostValuablePlayer();
        funTeam.calculateDefensivePlayer();
        funTeam.addPlayerInjuryReserve();
        funTeam.movePlayerOffInjuryReserve();
        System.out.println("Player stats have been updated for player: " + myPlayer.getName());
        playerMenu();
    }

    // MODIFIES: this
    // EFFECTS: checks if the roster and in injury reserve is currently empty, display the current roster and prompts
    //          user to select a player
    private void isRosterEmptySelectPlayer() {
        if (funTeam.getRoster().isEmpty() && funTeam.getInjuryReserve().isEmpty()) {
            System.out.println("The roster is currently empty...");
            playerMenu();
        }
        displayRosterInfo();
        displayInjuryReserve();
        selectPlayerInput();
    }

    // MODIFIES: this
    // EFFECTS: checks if the roster is currently empty, display the current roster and injury reserve and prompts user
    //          to remove a player
    private void isRosterEmptyRemovePlayer() {
        if (funTeam.getRoster().isEmpty()) {
            System.out.println("The roster is currently empty...");
            playerMenu();
        }
        displayRosterInfo();
        displayInjuryReserve();
        removePlayer();
    }

    // MODIFIES: this
    // EFFECTS: checks if the league is currently empty, displays the current teams and prompts user to remove a team
    private void isTeamEmptyRemoveTeam() {
        if (funLeague.getTeams().isEmpty()) {
            System.out.println("The league is empty...");
            teamMenu();
        }
        displayTeamInfo();
        removeTeam();
    }

    // MODIFIES: this
    // EFFECTS: sets the league status to false and prints the MVP, DPOY, and exits the application
    private void endSeason() {
        if (funLeague.getAmountOfTeams() == 0) {
            System.out.println("There are no teams in the league. Season cannot end!");
            teamMenu();
        }
        funLeague.leagueStatus(false);
        System.out.println("Congratulations! The season has ended!");
        funLeague.calculateLeagueWinner();
        displayLeagueWinner();
        funLeague.calculateLeagueMostValuablePlayer();
        displayLeagueMostValuablePlayer();
        funLeague.calculateLeagueDefensivePlayer();
        displayLeagueDefensivePlayer();

        System.exit(0);
    }

    // REQUIRES: jersey >= 0
    // MODIFIES: this
    // EFFECTS: prompt create player menu if a player's jersey matches given jersey
    private void checkSameJersey(int jersey) {
        for (Player p : funTeam.getRoster()) {
            if (p.getJerseyNumber() == jersey) {
                System.out.println("Player added unsuccessfully: That jersey is already taken. Please try again!");
                createPlayer();
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: gives prompt to user to enter team wins, team losses, and adds those records to the current team, then
    //          displays team menu
    private void changeRecord() {
        System.out.println("Please enter the latest amount of wins this team has: ");
        int wins = input.nextInt();
        System.out.println("Please enter the latest amount of losses this team has: ");
        int losses = input.nextInt();
        funTeam.teamRecord(wins, losses);
        System.out.println("Team record for: " + funTeam.getTeamName() + " has been updated!");
        playerMenu();
    }

    // EFFECTS: prints prompt if user tries to continue with no teams in the league
    private void continueWithNoTeam() {
        if (funLeague.getTeams().isEmpty()) {
            System.out.println("Cannot proceed if there are no teams. Please add a team to continue.");
            teamMenu();
        }
        System.out.println("Proceeding with current team!");
    }

    // EFFECTS: prints prompt if user tries to select team with no teams in the league, display the team information
    //          if the league is not empty
    private void selectTeamWithNoTeam() {
        if (funLeague.getTeams().isEmpty()) {
            System.out.println("Cannot select team if there are no teams. Please add a team to continue.");
            teamMenu();
        }
        displayTeamInfo();
    }

    // EFFECTS: helps organize the team menu code
    private void organizeTeamMenu() {
        System.out.println("Invalid Selection...");
        teamMenu();
    }
}
