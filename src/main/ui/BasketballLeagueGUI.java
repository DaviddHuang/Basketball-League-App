package ui;

import model.League;
import model.Player;
import model.Team;
import persistence.Reader;
import persistence.Writer;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class BasketballLeagueGUI extends JFrame implements ActionListener {
    private static final String FILE_DIRECTORY = "./data/league.json";
    private League league;
    private Player player;
    private Team team;
    private JButton save;
    private JButton start;
    private JButton load;
    private JButton submitLeague;
    private JPanel startMenu = new JPanel();
    private JPanel startScreen;
    private JPanel buttonsPanel = new JPanel();
    private JPanel playerButtonsPanel = new JPanel();
    private JPanel standingsPanel = new JPanel();
    private JPanel rosterPanel = new JPanel();
    private JPanel teamMenu = new JPanel();
    private JTextField leagueName = new JTextField();
    private JTextField teamName;
    private JTextField playerName;
    private JTextField positionName;
    private JTextField height;
    private JTextField weight;
    private JTextField jerseyNumber;
    private JTextField removeTeamName;
    private JTextField selectTeamName;
    private JTextField removePlayerName;
    private JTextField selectPlayerName;
    private JTextField healthStatus;
    private JTextField points;
    private JTextField rebounds;
    private JTextField assists;
    private JTextField steals;
    private JTextField blocks;
    private JTextField gamesPlayed;
    private JFrame leagueMenu = new JFrame();
    private JFrame success = new JFrame();
    private JFrame addTeam;
    private JFrame removeTeam;
    private JFrame selectTeam;
    private JFrame addPlayer;
    private JFrame removePlayer;
    private JFrame selectPlayer;
    private JTable standings;
    private JTable roster;
    private DefaultTableModel model;
    private DefaultTableModel rosterModel;


    // EFFECTS: creates a frame that holds the menu screen
    public BasketballLeagueGUI() {
        super("Basketball League");
        league = new League("Basketball League");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setSize(new Dimension(400,300));
        menuPanel();

        add(startScreen);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: determines which action performed when a specific button is pressed
    @Override
    public void actionPerformed(ActionEvent e) {
        actionPerformedHelper(e);
        secondActionPerformedHelper(e);
        thirdActionPerformedHelper(e);
    }

    private void actionPerformedHelper(ActionEvent e) {
        if (e.getActionCommand().equals("startButton")) {
            startApp();
        } else if (e.getActionCommand().equals("submitName")) {
            submitNameConfirmation();
        } else if (e.getActionCommand().equals("save")) {
            saveLeague();
        } else if (e.getActionCommand().equals("load")) {
            loadLeague();
        } else if (e.getActionCommand().equals("add")) {
            addTeamMenu();
        } else if (e.getActionCommand().equals("submitTeam")) {
            addTeam();
        } else if (e.getActionCommand().equals("menu")) {
            menuVisibility();
        } else if (e.getActionCommand().equals("view")) {
            displayTeams();
        } else if (e.getActionCommand().equals("remove")) {
            removeTeamMenu();
        } else if (e.getActionCommand().equals("submitRemoveTeam")) {
            removeTeam();
        } else if (e.getActionCommand().equals("select")) {
            selectTeamMenu();
        }
    }

    private void secondActionPerformedHelper(ActionEvent e) {
        if (e.getActionCommand().equals("submitSelectTeam")) {
            selectTeam();
        } else if (e.getActionCommand().equals("back")) {
            playerButtonsPanel.setVisible(false);
            buttonsPanel.setVisible(true);
            rosterPanel.setVisible(false);
            leagueMenu();
        } else if (e.getActionCommand().equals("viewRoster")) {
            displayPlayers();
        } else if (e.getActionCommand().equals("addPlayer")) {
            addPlayerMenu();
        } else if (e.getActionCommand().equals("nameNext")) {
            addPosition();
        } else if (e.getActionCommand().equals("positionNext")) {
            addJerseyNumber();
        } else if (e.getActionCommand().equals("jerseyNext")) {
            addHeight();
        } else if (e.getActionCommand().equals("heightNext")) {
            addWeight();
        } else if (e.getActionCommand().equals("submitPlayer")) {
            addPlayer();
        }
    }

    private void thirdActionPerformedHelper(ActionEvent e) {
        if (e.getActionCommand().equals("removePlayer")) {
            removePlayerMenu();
        } else if (e.getActionCommand().equals("submitRemovePlayer")) {
            removePlayer();
        } else if (e.getActionCommand().equals("selectPlayer")) {
            selectPlayerMenu();
        } else if (e.getActionCommand().equals("nextSelectPlayer")) {
            selectPlayer();
        } else if (e.getActionCommand().equals("healthStatusNext")) {
            editPoints();
        } else if (e.getActionCommand().equals("pointsNext")) {
            editRebounds();
        } else if (e.getActionCommand().equals("reboundsNext")) {
            editAssists();
        } else if (e.getActionCommand().equals("assistsNext")) {
            editSteals();
        } else if (e.getActionCommand().equals("stealsNext")) {
            editBlocks();
        } else if (e.getActionCommand().equals("blocksNext")) {
            editGamesPlayed();
        } else if (e.getActionCommand().equals("submitPlayerStats")) {
            editPlayerStats();
        }
    }

    // MODIFIES: this
    // EFFECTS: creates menu panel with start, save, and load
    private void menuPanel() {
        startScreen = new JPanel();
        startScreen.setLayout(null);
        startScreen.setSize(400,300);
        start = new JButton("Start");
        start.setActionCommand("startButton");
        start.addActionListener(this);
        save = new JButton("Save");
        save.setActionCommand("save");
        save.addActionListener(this);
        load = new JButton("Load");
        load.setActionCommand("load");
        load.addActionListener(this);
        start.setBounds(100, 90, 200, 40);
        save.setBounds(100,130,200,40);
        load.setBounds(100, 170, 200, 40);
        startScreen.add(start);
        startScreen.add(save);
        startScreen.add(load);
        startScreen.setVisible(true);
    }

    // EFFECTS: creates a new panel to submit the name of the league
    private void startApp() {
        startScreen.setVisible(false);
        startMenu.setVisible(true);
        startMenu.setLayout(null);
        startMenu.setSize(400,300);
        submitLeague = new JButton("Submit");
        submitLeague.setActionCommand("submitName");
        submitLeague.addActionListener(this);
        JLabel enterName = new JLabel();
        enterName.setText("Enter the league name!");
        enterName.setBounds(130,50,200,40);
        leagueName.setSize(200,200);
        leagueName.setBounds(100,90,200,40);
        submitLeague.setBounds(137,140,125,40);
        startMenu.add(enterName);
        startMenu.add(leagueName);
        startMenu.add(submitLeague);
        add(startMenu);
    }

    // EFFECTS: creates a league menu
    private void leagueMenu() {
        leagueName.setText("");
        success.setVisible(false);
        setVisible(false);
        leagueMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        leagueMenu.setTitle("League: " + league.getLeagueName());
        leagueMenu.setSize(new Dimension(600,600));
        leagueMenu.setLayout(null);
        buttonsPanel.setBounds(0,15,600,65);
        leagueButtons();
        leagueMenu.add(buttonsPanel);
        startMenu.setVisible(false);
        startScreen.setVisible(false);
        leagueMenu.setLocationRelativeTo(null);
        leagueMenu.setVisible(true);
    }

    // EFFECTS: helper method to initialize all the buttons in leagueMenu
    private void leagueButtons() {
        buttonsPanel.removeAll();
        JButton createTeam = new JButton("Add Team");
        createTeam.setActionCommand("add");
        createTeam.addActionListener(this);
        JButton removeTeam = new JButton("Remove team");
        removeTeam.setActionCommand("remove");
        removeTeam.addActionListener(this);
        JButton selectTeam = new JButton("Select team");
        selectTeam.setActionCommand("select");
        selectTeam.addActionListener(this);
        JButton view = new JButton("View teams");
        view.setActionCommand("view");
        view.addActionListener(this);
        JButton end = new JButton("End season");
        end.setActionCommand("end");
        end.addActionListener(this);
        buttonsPanel.add(createTeam);
        buttonsPanel.add(removeTeam);
        buttonsPanel.add(selectTeam);
        buttonsPanel.add(view);
        buttonsPanel.add(end);
        leagueMenu.setResizable(false);
        menuButton();
    }

    // EFFECTS: helper method to initialize buttons in leagueMenu
    private void menuButton() {
        JButton menu = new JButton("Main menu");
        menu.setActionCommand("menu");
        menu.addActionListener(this);
        buttonsPanel.add(menu);
    }

    // MODIFIES: this
    // EFFECTS: saves league from file
    private void saveLeague() {
        Writer writer = new Writer(FILE_DIRECTORY);
        try {
            writer.open();
            writer.write(league);
            writer.closeWriter();
            JOptionPane.showConfirmDialog(null, "Successfully saved: " + league.getLeagueName()
                    + " to " + FILE_DIRECTORY, "title", JOptionPane.DEFAULT_OPTION);
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "File could not be written: " + FILE_DIRECTORY,
                    "title", JOptionPane.ERROR_MESSAGE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads league from file
    private void loadLeague() {
        Reader reader = new Reader(FILE_DIRECTORY);
        try {
            league = reader.read();
            standingsPanel.setVisible(false);
            JOptionPane.showConfirmDialog(null, "Successfully loaded: " + league.getLeagueName()
                    + " from " + FILE_DIRECTORY, "title", JOptionPane.DEFAULT_OPTION);
            leagueMenu();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "File could not be read: " + FILE_DIRECTORY,
                    "title", JOptionPane.ERROR_MESSAGE);
        }
    }

    // EFFECTS: creates a team menu
    private void addTeamMenu() {
        if (addTeam == null || !addTeam.isVisible()) {
            addTeam = new JFrame();
            addTeam.setLayout(null);
            addTeam.setPreferredSize(new Dimension(400,300));
            addTeam.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            teamName = new JTextField();
            teamName.setBounds(100,90,200,40);
            JButton submitTeamName = new JButton("Submit");
            submitTeamName.setActionCommand("submitTeam");
            submitTeamName.addActionListener(this);
            submitTeamName.setBounds(137,140,125,40);
            JLabel enterTeamName = new JLabel();
            enterTeamName.setText("Enter team name!");
            enterTeamName.setBounds(145,50,200,40);
            addTeam.add(enterTeamName);
            addTeam.add(teamName);
            addTeam.add(submitTeamName);
            addTeam.getRootPane().setDefaultButton(submitTeamName);
            addTeam.pack();
            addTeam.setLocationRelativeTo(null);
            addTeam.setResizable(false);
            addTeam.setVisible(true);
        }
    }

    // MODIFIES: this
    // EFFECTS: creates a JOption pane that adds a team when OK is clicked
    private void addTeam() {
        int answer = JOptionPane.showConfirmDialog(null, "Team added: "
                + teamName.getText()
                + " has been created!","title", JOptionPane.DEFAULT_OPTION);
        if (answer == JOptionPane.OK_OPTION) {
            addTeam.setVisible(false);
            team = new Team(teamName.getText());
            league.addTeam(team);
        }
    }

    // EFFECTS: returns back to the main menu
    private void menuVisibility() {
        leagueMenu.setVisible(false);
        setVisible(true);
        startScreen.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: creates a JTable that stores teams as an 2-D object array if there is no JTable, otherwise clear rows
    //          and add all the teams to each row again
    private void displayTeams() {
        standingsPanel.setLayout(new BorderLayout());
        standingsPanel.setBounds(0,119,600,531);
        String[] categories = {"Team name", "Wins", "Losses", "Pct%"};

        if (standings == null) {
            model = new DefaultTableModel(addTeamsToDisplay().toArray(new Object[addTeamsToDisplay().size()][]),
                    categories);
            standings = new JTable(model);
            standingsPanel.add(new JScrollPane(standings));
            standings.setDefaultEditor(Object.class,null);
        } else {
            model.setRowCount(0);
            for (Object[] team : addTeamsToDisplay()) {
                model.addRow(team);
            }
        }
        standingsPanel.setVisible(true);
        leagueMenu.add(standingsPanel);
        leagueMenu.repaint();
        leagueMenu.revalidate();
    }

    // EFFECTS: adds each team as an object array stored in a list and returns it
    private ArrayList<Object[]> addTeamsToDisplay() {
        ArrayList<Object[]> data = new ArrayList<>();
        for (Team t : league.getTeams()) {
            data.add(new Object[]{t.getTeamName(),t.getWins(),t.getLosses(),t.winPercentage()});
        }
        return data;
    }

    // EFFECTS: creates a JOptionPane that confirms league has been added, changes league title if Ok is pressed
    private void submitNameConfirmation() {
        int answer = JOptionPane.showConfirmDialog(null, "Basketball league: "
                + leagueName.getText()
                + " has been created!","title", JOptionPane.DEFAULT_OPTION);
        if (answer == JOptionPane.OK_OPTION) {
            league = new League(leagueName.getText());
            leagueMenu.setTitle(leagueName.getText());
            leagueMenu();
        }
    }

    // EFFECTS: creates a removeTeamMenu
    private void removeTeamMenu() {
        if (removeTeam == null || !removeTeam.isVisible()) {
            removeTeam = new JFrame();
            removeTeam.setLayout(null);
            removeTeam.setPreferredSize(new Dimension(400,300));
            removeTeam.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            removeTeamName = new JTextField();
            removeTeamName.setBounds(100,90,200,40);
            JButton submitTeamName = new JButton("Submit");
            submitTeamName.setActionCommand("submitRemoveTeam");
            submitTeamName.addActionListener(this);
            submitTeamName.setBounds(137,140,125,40);
            JLabel enterTeamName = new JLabel();
            enterTeamName.setText("Enter the name of a team to remove!");
            enterTeamName.setBounds(85,50,250,40);
            removeTeam.add(enterTeamName);
            removeTeam.add(removeTeamName);
            removeTeam.add(submitTeamName);
            removeTeam.getRootPane().setDefaultButton(submitTeamName);
            removeTeam.pack();
            removeTeam.setLocationRelativeTo(null);
            removeTeam.setResizable(false);
            removeTeam.setVisible(true);
        }
    }

    // MODIFIES: this
    // EFFECTS: creates a JOption pane that removes a team when OK is clicked
    private void removeTeam() {
        for (Team t: league.getTeams()) {
            if (t.getTeamName().equalsIgnoreCase(removeTeamName.getText())) {
                league.removeTeam(removeTeamName.getText());
                int answer = JOptionPane.showConfirmDialog(null, "Team removed: "
                        + removeTeamName.getText()
                        + " has been removed!","title", JOptionPane.DEFAULT_OPTION);
                if (answer == JOptionPane.OK_OPTION) {
                    removeTeam.setVisible(false);
                }
                return;
            }
        }
        JOptionPane.showMessageDialog(null, "Team not found", "title",
                JOptionPane.ERROR_MESSAGE);
    }

    // EFFECTS: creates a selectTeamMenu
    private void selectTeamMenu() {
        if (selectTeam == null || !selectTeam.isVisible()) {
            selectTeam = new JFrame();
            selectTeam.setLayout(null);
            selectTeam.setPreferredSize(new Dimension(400,300));
            selectTeam.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            selectTeamName = new JTextField();
            selectTeamName.setBounds(100,90,200,40);
            JButton submitTeamName = new JButton("Submit");
            submitTeamName.setActionCommand("submitSelectTeam");
            submitTeamName.addActionListener(this);
            submitTeamName.setBounds(137,140,125,40);
            JLabel enterTeamName = new JLabel();
            enterTeamName.setText("Enter the name of a team to select!");
            enterTeamName.setBounds(90,50,250,40);
            selectTeam.add(enterTeamName);
            selectTeam.add(selectTeamName);
            selectTeam.add(submitTeamName);
            selectTeam.getRootPane().setDefaultButton(submitTeamName);
            selectTeam.pack();
            selectTeam.setLocationRelativeTo(null);
            selectTeam.setResizable(false);
            selectTeam.setVisible(true);
        }
    }

    // MODIFIES: this
    // EFFECTS: creates a JOption pane that selects a team when OK is clicked
    private void selectTeam() {
        for (Team t: league.getTeams()) {
            if (t.getTeamName().equalsIgnoreCase(selectTeamName.getText())) {
                team = t;
                int answer = JOptionPane.showConfirmDialog(null, "Team selected: "
                        + selectTeamName.getText()
                        + " has been selected!","title", JOptionPane.DEFAULT_OPTION);
                if (answer == JOptionPane.OK_OPTION) {
                    selectTeam.setVisible(false);
                    playerMenu();
                    standingsPanel.setVisible(false);
                }
                return;
            }
        }
        JOptionPane.showMessageDialog(null, "Team not found", "title",
                JOptionPane.ERROR_MESSAGE);
    }

    // EFFECTS: creates a player menu
    private void playerMenu() {
        buttonsPanel.setVisible(false);
        leagueMenu.setTitle("Team: " + selectTeamName.getText());
        selectTeamName.setText("");
        teamMenu.setSize(new Dimension(600,600));
        teamMenu.setLayout(null);
        playerButtonsPanel.setBounds(0,15,600,65);
        playerButtons();
        leagueMenu.add(playerButtonsPanel);
        playerButtonsPanel.setVisible(true);
        teamMenu.setVisible(true);
        leagueMenu.revalidate();
        leagueMenu.repaint();
    }

    // EFFECTS: helper method to initialize all the buttons in playerMenu
    private void playerButtons() {
        playerButtonsPanel.removeAll();
        JButton addPlayer = new JButton("Add player");
        addPlayer.setActionCommand("addPlayer");
        addPlayer.addActionListener(this);
        JButton selectPlayer = new JButton("Select player");
        selectPlayer.setActionCommand("selectPlayer");
        selectPlayer.addActionListener(this);
        JButton removePlayer = new JButton("Remove player");
        removePlayer.setActionCommand("removePlayer");
        removePlayer.addActionListener(this);
        JButton viewRoster = new JButton("View roster");
        viewRoster.setActionCommand("viewRoster");
        viewRoster.addActionListener(this);
        JButton injuryReserve = new JButton("View injury reserve");
        injuryReserve.setActionCommand("viewInjuryReserve");
        injuryReserve.addActionListener(this);
        playerButtonsPanel.add(addPlayer);
        playerButtonsPanel.add(removePlayer);
        playerButtonsPanel.add(selectPlayer);
        playerButtonsPanel.add(viewRoster);
        playerButtonsPanel.add(injuryReserve);
        playerButtonsHelper();
    }

    private void playerButtonsHelper() {
        JButton edit = new JButton("Edit Team Record");
        edit.setActionCommand("edit");
        edit.addActionListener(this);
        JButton back = new JButton("Back");
        back.setActionCommand("back");
        back.addActionListener(this);
        playerButtonsPanel.add(edit);
        playerButtonsPanel.add(back);
    }

    // MODIFIES: this
    // EFFECTS: creates a JTable that stores players as an 2-D object array if there is no JTable, otherwise clear rows
    //          and add all the players to each row again
    private void displayPlayers() {
        rosterPanel.setLayout(new BorderLayout());
        rosterPanel.setBounds(0,119,600,531);
        String[] categories = {"Name", "Position", "#", "Height (cm)", "Weight (lbs)", "PPG", "RPG", "APG", "SPG",
                "BPG", "GP"};

        if (roster == null) {
            rosterModel = new DefaultTableModel(addPlayersToDisplay().toArray(new Object[addPlayersToDisplay().size()][]
            ),
                    categories);
            roster = new JTable(rosterModel);
            rosterPanel.add(new JScrollPane(roster));
            roster.setDefaultEditor(Object.class,null);
            roster.getColumnModel().getColumn(0).setPreferredWidth(200);
        } else {
            rosterModel.setRowCount(0);
            for (Object[] player : addPlayersToDisplay()) {
                rosterModel.addRow(player);
            }
        }
        rosterPanel.setVisible(true);
        leagueMenu.add(rosterPanel);
        leagueMenu.repaint();
        leagueMenu.revalidate();
    }

    // EFFECTS: adds each player as an object array stored in a list and returns it
    private ArrayList<Object[]> addPlayersToDisplay() {
        ArrayList<Object[]> data = new ArrayList<>();
        for (Player p : team.getRoster()) {
            data.add(new Object[]{p.getName(), p.getPosition(), p.getJerseyNumber(), p.getHeight(), p.getWeight(),
                    p.averagePoints(), p.averageRebounds(), p.averageAssists(), p.averageSteals(), p.averageBlocks(),
                    p.getGamesPlayed()});
        }
        return data;
    }

    // EFFECTS: creates a player menu
    private void addPlayerMenu() {
        if (addPlayer == null || !addPlayer.isVisible()) {
            addPlayer = new JFrame();
            addPlayer.setLayout(null);
            addPlayer.setPreferredSize(new Dimension(400,300));
            addPlayer.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            playerName = new JTextField();
            playerName.setBounds(100,90,200,40);
            JButton submitTeamName = new JButton("Next");
            submitTeamName.setActionCommand("nameNext");
            submitTeamName.addActionListener(this);
            submitTeamName.setBounds(137,140,125,40);
            JLabel enterTeamName = new JLabel();
            enterTeamName.setText("Enter Player name!");
            enterTeamName.setBounds(145,50,200,40);
            addPlayer.add(enterTeamName);
            addPlayer.add(playerName);
            addPlayer.add(submitTeamName);
            addPlayer.getRootPane().setDefaultButton(submitTeamName);
            addPlayer.pack();
            addPlayer.setLocationRelativeTo(null);
            addPlayer.setResizable(false);
            addPlayer.setVisible(true);
        }
    }

    private void addPosition() {
        addPlayer.getContentPane().removeAll();
        positionName =  new JTextField();
        positionName.setBounds(100,90,200,40);
        JButton submitPositionName = new JButton("Next");
        submitPositionName.setActionCommand("positionNext");
        submitPositionName.addActionListener(this);
        submitPositionName.setBounds(137,140,125,40);
        JLabel enterPositionName = new JLabel();
        enterPositionName.setText("Enter Position!");
        enterPositionName.setBounds(145,50,200,40);
        addPlayer.add(positionName);
        addPlayer.add(submitPositionName);
        addPlayer.add(enterPositionName);
        addPlayer.getRootPane().setDefaultButton(submitPositionName);
        addPlayer.revalidate();
        addPlayer.repaint();
    }

    private void addJerseyNumber() {
        addPlayer.getContentPane().removeAll();
        jerseyNumber =  new JTextField();
        jerseyNumber.setBounds(100,90,200,40);
        JButton submitJerseyNumber = new JButton("Next");
        submitJerseyNumber.setActionCommand("jerseyNext");
        submitJerseyNumber.addActionListener(this);
        submitJerseyNumber.setBounds(137,140,125,40);
        JLabel enterJerseyNumber = new JLabel();
        enterJerseyNumber.setText("Enter Jersey Number!");
        enterJerseyNumber.setBounds(145,50,200,40);
        addPlayer.add(jerseyNumber);
        addPlayer.add(submitJerseyNumber);
        addPlayer.add(enterJerseyNumber);
        addPlayer.getRootPane().setDefaultButton(submitJerseyNumber);
        addPlayer.revalidate();
        addPlayer.repaint();
    }

    private void addHeight() {
        addPlayer.getContentPane().removeAll();
        height =  new JTextField();
        height.setBounds(100,90,200,40);
        JButton submitHeight = new JButton("Next");
        submitHeight.setActionCommand("heightNext");
        submitHeight.addActionListener(this);
        submitHeight.setBounds(137,140,125,40);
        JLabel enterHeight = new JLabel();
        enterHeight.setText("Enter Height! (cm)");
        enterHeight.setBounds(145,50,200,40);
        addPlayer.add(height);
        addPlayer.add(submitHeight);
        addPlayer.add(enterHeight);
        addPlayer.getRootPane().setDefaultButton(submitHeight);
        addPlayer.revalidate();
        addPlayer.repaint();
    }

    private void addWeight() {
        addPlayer.getContentPane().removeAll();
        weight =  new JTextField();
        weight.setBounds(100,90,200,40);
        JButton submitWeight = new JButton("Submit");
        submitWeight.setActionCommand("submitPlayer");
        submitWeight.addActionListener(this);
        submitWeight.setBounds(137,140,125,40);
        JLabel enterWeight = new JLabel();
        enterWeight.setText("Enter Weight! (lbs)");
        enterWeight.setBounds(145,50,200,40);
        addPlayer.add(weight);
        addPlayer.add(submitWeight);
        addPlayer.add(enterWeight);
        addPlayer.getRootPane().setDefaultButton(submitWeight);
        addPlayer.revalidate();
        addPlayer.repaint();
    }

    private void addPlayer() {
        player = new Player(playerName.getText(), positionName.getText(),
                Integer.parseInt(jerseyNumber.getText()),
                Integer.parseInt(height.getText()), Double.parseDouble(weight.getText()));
        if (!team.addPlayer(player)) {
            JOptionPane.showMessageDialog(null, "Player cannot be added: jersey number is"
                           + " already taken", "title",
                        JOptionPane.ERROR_MESSAGE);
            addPlayer.dispose();
            addPlayerMenu();
        } else {
            int answer = JOptionPane.showConfirmDialog(null, "Player added: "
                    + playerName.getText(),"title", JOptionPane.DEFAULT_OPTION);
            if (answer == JOptionPane.OK_OPTION) {
                addPlayer.setVisible(false);
            }
        }
    }

    private void removePlayerMenu() {
        if (removePlayer == null || !removePlayer.isVisible()) {
            removePlayer = new JFrame();
            removePlayer.setLayout(null);
            removePlayer.setPreferredSize(new Dimension(400,300));
            removePlayer.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            removePlayerName = new JTextField();
            removePlayerName.setBounds(100,90,200,40);
            JButton submitRemovePlayerName = new JButton("Submit");
            submitRemovePlayerName.setActionCommand("submitRemovePlayer");
            submitRemovePlayerName.addActionListener(this);
            submitRemovePlayerName.setBounds(137,140,125,40);
            JLabel enterRemovePlayerName = new JLabel();
            enterRemovePlayerName.setText("Enter Player name!");
            enterRemovePlayerName.setBounds(145,50,200,40);
            removePlayer.add(enterRemovePlayerName);
            removePlayer.add(removePlayerName);
            removePlayer.add(submitRemovePlayerName);
            selectPlayer.getRootPane().setDefaultButton(submitRemovePlayerName);
            removePlayer.pack();
            removePlayer.setLocationRelativeTo(null);
            removePlayer.setResizable(false);
            removePlayer.setVisible(true);
        }
    }

    private void removePlayer() {
        for (Player p: team.getRoster()) {
            if (p.getName().equalsIgnoreCase(removePlayerName.getText())) {
                team.removePlayer(removePlayerName.getText());
                int answer = JOptionPane.showConfirmDialog(null, "Player removed: "
                        + removePlayerName.getText()
                        + " has been removed!","title", JOptionPane.DEFAULT_OPTION);
                if (answer == JOptionPane.OK_OPTION) {
                    removePlayer.setVisible(false);
                }
                return;
            }
        }
        JOptionPane.showMessageDialog(null, "Player not found", "title",
                JOptionPane.ERROR_MESSAGE);
    }

    private void selectPlayerMenu() {
        if (selectPlayer == null || !selectPlayer.isVisible()) {
            selectPlayer = new JFrame();
            selectPlayer.setLayout(null);
            selectPlayer.setPreferredSize(new Dimension(400,300));
            selectPlayer.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            selectPlayerName = new JTextField();
            selectPlayerName.setBounds(100,90,200,40);
            JButton submitSelectPlayerName = new JButton("Next");
            submitSelectPlayerName.setActionCommand("nextSelectPlayer");
            submitSelectPlayerName.addActionListener(this);
            submitSelectPlayerName.setBounds(137,140,125,40);
            JLabel enterSelectPlayerName = new JLabel();
            enterSelectPlayerName.setText("Enter Player name!");
            enterSelectPlayerName.setBounds(145,50,200,40);
            selectPlayer.add(enterSelectPlayerName);
            selectPlayer.add(selectPlayerName);
            selectPlayer.add(submitSelectPlayerName);
            selectPlayer.getRootPane().setDefaultButton(submitSelectPlayerName);
            selectPlayer.pack();
            selectPlayer.setLocationRelativeTo(null);
            selectPlayer.setResizable(false);
            selectPlayer.setVisible(true);
        }
    }

    private void editHealthStatus() {
        selectPlayer.getContentPane().removeAll();
        healthStatus =  new JTextField();
        healthStatus.setBounds(100,90,200,40);
        JButton submitHealthStatus = new JButton("Next");
        submitHealthStatus.setActionCommand("healthStatusNext");
        submitHealthStatus.addActionListener(this);
        submitHealthStatus.setBounds(137,140,125,40);
        JLabel enterHealthStatus = new JLabel();
        enterHealthStatus.setText("Enter health status (True = health | False = injured)");
        enterHealthStatus.setBounds(40,50,400,40);
        selectPlayer.add(healthStatus);
        selectPlayer.add(submitHealthStatus);
        selectPlayer.add(enterHealthStatus);
        selectPlayer.getRootPane().setDefaultButton(submitHealthStatus);
        selectPlayer.revalidate();
        selectPlayer.repaint();
    }

    private void editPoints() {
        points =  new JTextField();
        JLabel enter = new JLabel();
        enter.setText("Enter latest amount of points scored");
        enter.setBounds(80,50,400,40);
        addStatsHelper(points, "Next", "pointsNext");
        selectPlayer.add(enter);
        points.setBounds(100,90,200,40);
    }

    private void editRebounds() {
        rebounds =  new JTextField();
        JLabel enter = new JLabel();
        enter.setText("Enter latest amount of rebounds collected");
        enter.setBounds(65,50,400,40);
        addStatsHelper(rebounds, "Next", "reboundsNext");
        selectPlayer.add(enter);
        rebounds.setBounds(100,90,200,40);
    }

    private void editAssists() {
        assists =  new JTextField();
        JLabel enter = new JLabel();
        enter.setText("Enter latest amount of assists collected");
        enter.setBounds(70,50,400,40);
        addStatsHelper(assists, "Next", "assistsNext");
        selectPlayer.add(enter);
        assists.setBounds(100,90,200,40);
    }

    private void editSteals() {
        steals =  new JTextField();
        JLabel enter = new JLabel();
        enter.setText("Enter latest amount of steals collected");
        enter.setBounds(70,50,400,40);
        addStatsHelper(steals, "Next", "stealsNext");
        selectPlayer.add(enter);
        steals.setBounds(100,90,200,40);
    }

    private void editBlocks() {
        blocks =  new JTextField();
        JLabel enter = new JLabel();
        enter.setText("Enter latest amount of blocks collected");
        enter.setBounds(70,50,400,40);
        addStatsHelper(blocks, "Next", "blocksNext");
        selectPlayer.add(enter);
        blocks.setBounds(100,90,200,40);
    }

    private void editGamesPlayed() {
        gamesPlayed =  new JTextField();
        JLabel enter = new JLabel();
        enter.setText("Enter latest amount of games played");
        enter.setBounds(80,50,400,40);
        addStatsHelper(gamesPlayed, "Submit", "submitPlayerStats");
        selectPlayer.add(enter);
        gamesPlayed.setBounds(100,90,200,40);
    }

    private void addStatsHelper(JTextField stat, String buttonName, String chooseAction) {
        selectPlayer.getContentPane().removeAll();
        stat.setBounds(100,90,200,40);
        JButton submit = new JButton(buttonName);
        submit.setActionCommand(chooseAction);
        submit.addActionListener(this);
        submit.setBounds(137,140,125,40);
        selectPlayer.add(stat);
        selectPlayer.add(submit);
        selectPlayer.getRootPane().setDefaultButton(submit);
        selectPlayer.revalidate();
        selectPlayer.repaint();
    }

    private void editPlayerStats() {
        int answer = JOptionPane.showConfirmDialog(null, "Stats updated: "
                    + selectPlayerName.getText()
                    + " stats have been updated!","title", JOptionPane.DEFAULT_OPTION);
        if (answer == JOptionPane.OK_OPTION) {
            selectPlayer.setVisible(false);
            player.playGame(Integer.parseInt(points.getText()), Integer.parseInt(rebounds.getText()),
                    Integer.parseInt(assists.getText()), Integer.parseInt(steals.getText()),
                    Integer.parseInt(blocks.getText()), Integer.parseInt(gamesPlayed.getText()));
            player.isPlayerHealthy(Boolean.parseBoolean(healthStatus.getText()));
            team.addPlayerInjuryReserve();
        }
    }

    private void selectPlayer() {
        for (Player p: team.getRoster()) {
            if (p.getName().equalsIgnoreCase(selectPlayerName.getText())) {
                player = p;
                int answer = JOptionPane.showConfirmDialog(null, "Player selected: "
                        + selectPlayerName.getText()
                        + " has been selected!","title", JOptionPane.DEFAULT_OPTION);
                if (answer == JOptionPane.OK_OPTION) {
                    editHealthStatus();
                }
                return;
            }
        }
        JOptionPane.showMessageDialog(null, "Player not found", "title",
                JOptionPane.ERROR_MESSAGE);
    }


    public static void main(String[] args) {
        new BasketballLeagueGUI();
    }
}
