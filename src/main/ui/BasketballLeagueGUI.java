package ui;

import model.EventLog;
import model.League;
import model.Player;
import model.Team;
import model.Event;
import persistence.Reader;
import persistence.Writer;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

// Basketball league graphical user interface
// Code is influenced by:
// https://www.youtube.com/watch?v=Kmgo00avvEw&t=974s&ab_channel=BroCode
// https://www.youtube.com/watch?v=skryksKiIK0&ab_channel=MukulSainiSkills
// https://stackoverflow.com/questions/479523/jframe-maximize-window
// https://stackoverflow.com/questions/1990817/how-to-make-a-jtable-non-editable
// https://stackoverflow.com/questions/13731710/allowing-the-enter-key-to-press-the-submit-button-as-opposed-to-only-
// using-mo
// https://stackoverflow.com/questions/4640138/setting-the-focus-to-a-text-field
// https://stackoverflow.com/questions/9572795/convert-list-to-array-in-java
// https://stackoverflow.com/questions/5585779/how-do-i-convert-a-string-to-an-int-in-java
// https://stackoverflow.com/questions/6578205/swing-jlabel-text-change-on-the-running-application
public class BasketballLeagueGUI extends JFrame implements ActionListener, WindowListener {
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
    private JPanel injuryPanel = new JPanel();
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
    private JTextField wins;
    private JTextField losses;
    private JFrame leagueMenu = new JFrame();
    private JFrame success = new JFrame();
    private JFrame addTeam;
    private JFrame removeTeam;
    private JFrame selectTeam;
    private JFrame addPlayer;
    private JFrame removePlayer;
    private JFrame selectPlayer;
    private JFrame editRecord;
    private JFrame endSeason;
    private JTable standings;
    private JTable injuries;
    private JTable roster;
    private DefaultTableModel model;
    private DefaultTableModel rosterModel;
    private DefaultTableModel injuryModel;


    // EFFECTS: creates a frame that holds the menu screen
    public BasketballLeagueGUI() {
        super("Basketball League");
        league = new League("Basketball League");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setSize(new Dimension(400,300));
        Dimension windowSize = getSize();
        ImageIcon startScreenIcon = new ImageIcon(new ImageIcon("data/basketballCourt.jpeg")
                .getImage().getScaledInstance(windowSize.width, windowSize.height, Image.SCALE_DEFAULT));
        JLabel label = new JLabel();
        label.setIcon(startScreenIcon);
        label.setBounds(0,0, windowSize.width, windowSize.height);
        menuPanel();

        add(startScreen);
        addWindowListener(this);
        startScreen.add(label);
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
        if (e.getActionCommand().equals("viewInjuryReserve")) {
            displayInjuryReserve();
        } else if (e.getActionCommand().equals("edit")) {
            editWins();
        } else if (e.getActionCommand().equals("nextWins")) {
            editLosses();
        } else if (e.getActionCommand().equals("submitRecord")) {
            editTeamRecord();
        } else if (e.getActionCommand().equals("end")) {
            endSeasonMenu();
        }
    }

    // MODIFIES: this
    // EFFECTS: determines which action performed when a specific button is pressed
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

    // MODIFIES: this
    // EFFECTS: determines which action performed when a specific button is pressed
    private void secondActionPerformedHelper(ActionEvent e) {
        if (e.getActionCommand().equals("submitSelectTeam")) {
            selectTeam();
        } else if (e.getActionCommand().equals("back")) {
            playerButtonsPanel.setVisible(false);
            buttonsPanel.setVisible(true);
            rosterPanel.setVisible(false);
            injuryPanel.setVisible(false);
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

    // MODIFIES: this
    // EFFECTS: determines which action performed when a specific button is pressed
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
        startScreen.setFocusable(true);
        startScreen.setVisible(true);
    }

    // MODIFIES: this
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
        getRootPane().setDefaultButton(submitLeague);
        add(startMenu);
    }

    // MODIFIES: this
    // EFFECTS: creates a league menu with buttons for specific actions
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
        leagueMenu.addWindowListener(this);
        startMenu.setVisible(false);
        startScreen.setVisible(false);
        leagueMenu.setLocationRelativeTo(null);
        leagueMenu.setVisible(true);
        leagueMenu.setFocusable(true);
    }

    // MODIFIES: this
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

    // MODIFIES: this
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

    // MODIFIES: this
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
        team = new Team(teamName.getText());
        if (!league.addTeam(team)) {
            JOptionPane.showMessageDialog(null, "Team cannot be added: team name is"
                            + " already taken", "title", JOptionPane.ERROR_MESSAGE);
            addTeam.dispose();
            addTeamMenu();
        } else {
            int answer = JOptionPane.showConfirmDialog(null, "Team added: "
                    + teamName.getText()
                    + " has been created!", "title", JOptionPane.DEFAULT_OPTION);
            if (answer == JOptionPane.OK_OPTION) {
                addTeam.setVisible(false);
            }
        }
    }

    // EFFECTS: returns back to the main menu
    private void menuVisibility() {
        if (removeTeam != null) {
            removeTeam.dispose();
        }
        if (selectTeam != null) {
            selectTeam.dispose();
        }
        if (addTeam != null) {
            addTeam.dispose();
        }
        leagueMenu.setVisible(false);
        setLocationRelativeTo(null);
        setVisible(true);
        startScreen.setVisible(true);
        standingsPanel.setVisible(false);
    }

    // MODIFIES: this
    // EFFECTS: creates a JTable that stores teams as an 2-D object array if there is no JTable, otherwise clear rows
    //          and add all the teams to each row again
    private void displayTeams() {
        standingsPanel.setLayout(new BorderLayout());
        standingsPanel.setBounds(0,119,600,531);
        String[] categories = {"Team name", "Games played", "Wins", "Losses", "Pct%"};

        if (standings == null) {
            model = new DefaultTableModel(addTeamsToDisplay().toArray(new Object[addTeamsToDisplay().size()][]),
                    categories);
            standings = new JTable(model);
            standingsPanel.add(new JScrollPane(standings));
            standings.setDefaultEditor(Object.class,null);
            standings.getColumnModel().getColumn(0).setPreferredWidth(175);
        } else {
            model.setRowCount(0);
            for (Object[] team : addTeamsToDisplay()) {
                model.addRow(team);
            }
        }
        league.displayTeamsLogEvent();
        standingsPanel.setVisible(true);
        leagueMenu.add(standingsPanel);
        leagueMenu.repaint();
        leagueMenu.revalidate();
    }



    // EFFECTS: adds each team as an object array stored in a list and returns it
    private ArrayList<Object[]> addTeamsToDisplay() {
        ArrayList<Object[]> data = new ArrayList<>();
        for (Team t : league.getTeams()) {
            data.add(new Object[]{t.getTeamName(),t.getTeamGamesPlayed(), t.getWins(),t.getLosses(),t.winPercentage()});
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

    // MODIFIES: this
    // EFFECTS: creates a removeTeamMenu to remove specific team
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

    // MODIFIES: this
    // EFFECTS: creates a selectTeamMenu to select a specific player
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

    // MODIFIES: this
    // EFFECTS: creates a player menu
    private void playerMenu() {
        buttonsPanel.setVisible(false);
        leagueMenu.setTitle("Team: " + team.getTeamName());
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

    // MODIFIES: this
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

    // MODIFIES: this
    // EFFECTS: helper method to initialize all buttons in playerMenu
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
        injuryPanel.setVisible(false);
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
            setRosterTableColumns(roster);
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

    // EFFECTS: sets the preferred width of the name, position, #, height, and weight columns
    private void setRosterTableColumns(JTable table) {
        table.getColumnModel().getColumn(0).setPreferredWidth(200);
        table.getColumnModel().getColumn(1).setPreferredWidth(100);
        table.getColumnModel().getColumn(2).setPreferredWidth(50);
        table.getColumnModel().getColumn(3).setPreferredWidth(125);
        table.getColumnModel().getColumn(4).setPreferredWidth(125);
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

    // MODIFIES: this
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

    // MODIFIES: this
    // EFFECTS: clears the addPlayer frame and creates a menu to add a text field to enter the player's position
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
        positionName.requestFocus();
        addPlayer.add(enterPositionName);
        addPlayer.getRootPane().setDefaultButton(submitPositionName);
        addPlayer.revalidate();
        addPlayer.repaint();
    }

    // MODIFIES: this
    // EFFECTS: clears the addPlayer frame and creates a menu to add a text field to enter the player's jersey number
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
        enterJerseyNumber.setBounds(130,50,200,40);
        addPlayer.add(jerseyNumber);
        addPlayer.add(submitJerseyNumber);
        jerseyNumber.requestFocus();
        addPlayer.add(enterJerseyNumber);
        addPlayer.getRootPane().setDefaultButton(submitJerseyNumber);
        addPlayer.revalidate();
        addPlayer.repaint();
    }

    // MODIFIES: this
    // EFFECTS: clears the addPlayer frame and creates a menu to add a text field to enter the player's height
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
        height.requestFocus();
        addPlayer.add(enterHeight);
        addPlayer.getRootPane().setDefaultButton(submitHeight);
        addPlayer.revalidate();
        addPlayer.repaint();
    }

    // MODIFIES: this
    // EFFECTS: clears the addPlayer frame and creates a menu to add a text field to enter the player's weight
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
        weight.requestFocus();
        addPlayer.add(enterWeight);
        addPlayer.getRootPane().setDefaultButton(submitWeight);
        addPlayer.revalidate();
        addPlayer.repaint();
    }

    // EFFECTS: creates a player if the player added has a unique jersey number, and displays a JOption plane and
    //          clears the addPlayer window when OK is clicked
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

    // MODIFIES: this
    // EFFECTS: creates a remove player menu that allows user to remove a specific player
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
            removePlayer.getRootPane().setDefaultButton(submitRemovePlayerName);
            removePlayer.pack();
            removePlayer.setLocationRelativeTo(null);
            removePlayer.setResizable(false);
            removePlayer.setVisible(true);
        }
    }

    // EFFECTS: removes player from roster or injury reserve if the player name matches the user entered
    //          input in removePlayerName text field
    private void removePlayer() {
        for (Player p: team.getRoster()) {
            if (p.getName().equalsIgnoreCase(removePlayerName.getText())) {
                team.removePlayer(removePlayerName.getText());
                team.calculateDefensivePlayer();
                team.calculateMostValuablePlayer();
                int answer = JOptionPane.showConfirmDialog(null, "Player removed: "
                        + removePlayerName.getText()
                        + " has been removed!","title", JOptionPane.DEFAULT_OPTION);
                if (answer == JOptionPane.OK_OPTION) {
                    removePlayer.setVisible(false);
                }
                return;
            }
        }
        if (removePlayerInjuryReserve()) {
            return;
        }
        JOptionPane.showMessageDialog(null, "Player not found", "title",
                JOptionPane.ERROR_MESSAGE);
    }

    // EFFECTS: helper method that removes player from injury reserve if player name matches the user entered input in
    //          removePlayerName text field
    private Boolean removePlayerInjuryReserve() {
        for (Player p: team.getInjuryReserve()) {
            if (p.getName().equalsIgnoreCase(removePlayerName.getText())) {
                team.removePlayer(removePlayerName.getText());
                team.calculateDefensivePlayer();
                team.calculateMostValuablePlayer();
                int answer = JOptionPane.showConfirmDialog(null, "Player removed: "
                        + removePlayerName.getText()
                        + " has been removed!","title", JOptionPane.DEFAULT_OPTION);
                if (answer == JOptionPane.OK_OPTION) {
                    removePlayer.setVisible(false);
                }
                return true;
            }
        }
        return false;
    }

    // MODIFIES: this
    // EFFECTS: creates a select player menu that allows user to select a specific player from a team
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

    // MODIFIES: this
    // EFFECTS: clears the select player frame and creates a new text field to allow for user input of player health
    //          status
    private void editHealthStatus() {
        selectPlayer.getContentPane().removeAll();
        healthStatus =  new JTextField();
        healthStatus.setBounds(100,90,200,40);
        JButton submitHealthStatus = new JButton("Next");
        submitHealthStatus.setActionCommand("healthStatusNext");
        submitHealthStatus.addActionListener(this);
        submitHealthStatus.setBounds(137,140,125,40);
        JLabel enterHealthStatus = new JLabel();
        enterHealthStatus.setText("Enter health status (True = healthy | False = injured)");
        enterHealthStatus.setBounds(40,50,400,40);
        selectPlayer.add(healthStatus);
        selectPlayer.add(submitHealthStatus);
        healthStatus.requestFocus();
        selectPlayer.add(enterHealthStatus);
        selectPlayer.getRootPane().setDefaultButton(submitHealthStatus);
        selectPlayer.revalidate();
        selectPlayer.repaint();
    }

    // MODIFIES: this
    // EFFECTS: clears the select player frame and creates a new text field to allow for user input of player points
    private void editPoints() {
        points =  new JTextField();
        JLabel enter = new JLabel();
        enter.setText("Enter latest amount of points scored");
        enter.setBounds(80,50,400,40);
        addStatsHelper(selectPlayer, points, "Next", "pointsNext");
        selectPlayer.add(enter);
        points.requestFocus();
        points.setBounds(100,90,200,40);
    }

    // MODIFIES: this
    // EFFECTS: clears the select player frame and creates a new text field to allow for user input of player rebounds
    private void editRebounds() {
        rebounds =  new JTextField();
        JLabel enter = new JLabel();
        enter.setText("Enter latest amount of rebounds collected");
        enter.setBounds(65,50,400,40);
        addStatsHelper(selectPlayer, rebounds, "Next", "reboundsNext");
        selectPlayer.add(enter);
        rebounds.requestFocus();
        rebounds.setBounds(100,90,200,40);
    }

    // MODIFIES: this
    // EFFECTS: clears the select player frame and creates a new text field to allow for user input of player assists
    private void editAssists() {
        assists =  new JTextField();
        JLabel enter = new JLabel();
        enter.setText("Enter latest amount of assists collected");
        enter.setBounds(70,50,400,40);
        addStatsHelper(selectPlayer, assists, "Next", "assistsNext");
        selectPlayer.add(enter);
        assists.requestFocus();
        assists.setBounds(100,90,200,40);
    }

    // MODIFIES: this
    // EFFECTS: clears the select player frame and creates a new text field to allow for user input of player steals
    private void editSteals() {
        steals =  new JTextField();
        JLabel enter = new JLabel();
        enter.setText("Enter latest amount of steals collected");
        enter.setBounds(70,50,400,40);
        addStatsHelper(selectPlayer, steals, "Next", "stealsNext");
        selectPlayer.add(enter);
        steals.requestFocus();
        steals.setBounds(100,90,200,40);
    }

    // MODIFIES: this
    // EFFECTS: clears the select player frame and creates a new text field to allow for user input of player blocks
    private void editBlocks() {
        blocks =  new JTextField();
        JLabel enter = new JLabel();
        enter.setText("Enter latest amount of blocks collected");
        enter.setBounds(70,50,400,40);
        addStatsHelper(selectPlayer, blocks, "Next", "blocksNext");
        selectPlayer.add(enter);
        blocks.requestFocus();
        blocks.setBounds(100,90,200,40);
    }

    // MODIFIES: this
    // EFFECTS: clears the select player frame and creates a new text field to allow for user input of player games
    //          played
    private void editGamesPlayed() {
        gamesPlayed =  new JTextField();
        JLabel enter = new JLabel();
        enter.setText("Enter latest amount of games played");
        enter.setBounds(80,50,400,40);
        addStatsHelper(selectPlayer, gamesPlayed, "Submit", "submitPlayerStats");
        selectPlayer.add(enter);
        gamesPlayed.requestFocus();
        gamesPlayed.setBounds(100,90,200,40);
    }

    // MODIFIES: this
    // EFFECTS: helper method that allows that clears given frame, adds text field, changes button name, and gives
    //          button an action command so player stats can be entered
    private void addStatsHelper(JFrame frame, JTextField stat, String buttonName, String chooseAction) {
        frame.getContentPane().removeAll();
        stat.setBounds(100,90,200,40);
        JButton submit = new JButton(buttonName);
        submit.setActionCommand(chooseAction);
        submit.addActionListener(this);
        submit.setBounds(137,140,125,40);
        frame.add(stat);
        frame.add(submit);
        frame.getRootPane().setDefaultButton(submit);
        frame.revalidate();
        frame.repaint();
    }

    // EFFECTS: updates player stats and displays JOption upon successful update, if OK is clicked selectPlayer frame
    //          is hidden and player stats are updated
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
            team.movePlayerOffInjuryReserve();
            team.calculateMostValuablePlayer();
            team.calculateDefensivePlayer();
        }
    }

    // EFFECTS: if player name is equal to user entered input in selectPlayerName text field, player is changed to
    //          selected player in roster or injury reserve and JOption pane appears, if OK is clicked display start of
    //          edit player stats
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
        if (selectInjuredPlayer()) {
            return;
        }
        JOptionPane.showMessageDialog(null, "Player not found", "title",
                JOptionPane.ERROR_MESSAGE);
    }

    // EFFECTS: if player name is equal to user entered input in selectPlayerName text field, player is changed to
    //          selected player in injury reserve and JOption pane appears, if OK is clicked display start of
    //          edit player stats
    private Boolean selectInjuredPlayer() {
        for (Player p: team.getInjuryReserve()) {
            if (p.getName().equalsIgnoreCase(selectPlayerName.getText())) {
                player = p;
                int answer = JOptionPane.showConfirmDialog(null, "Player selected: "
                        + selectPlayerName.getText()
                        + " has been selected!","title", JOptionPane.DEFAULT_OPTION);
                if (answer == JOptionPane.OK_OPTION) {
                    editHealthStatus();
                }
                return true;
            }
        }
        return false;
    }

    // MODIFIES: this
    // EFFECTS: creates a JTable that stores injured players as an 2-D object array if there is no JTable created,
    // otherwise clear rows and add all the injured players to each row again
    private void displayInjuryReserve() {
        rosterPanel.setVisible(false);
        injuryPanel.setLayout(new BorderLayout());
        injuryPanel.setBounds(0,119,600,531);
        String[] categories = {"Name", "Position", "#", "Height (cm)", "Weight (lbs)", "PPG", "RPG", "APG", "SPG",
                "BPG", "GP"};

        if (injuries == null) {
            injuryModel = new DefaultTableModel(addInjuredPlayersToDisplay().toArray(new Object[
                    addInjuredPlayersToDisplay().size()][]),
                    categories);
            injuries = new JTable(injuryModel);
            injuryPanel.add(new JScrollPane(injuries));
            injuries.setDefaultEditor(Object.class,null);
            setRosterTableColumns(injuries);
        } else {
            injuryModel.setRowCount(0);
            for (Object[] player : addInjuredPlayersToDisplay()) {
                injuryModel.addRow(player);
            }
        }
        injuryPanel.setVisible(true);
        leagueMenu.add(injuryPanel);
        leagueMenu.repaint();
        leagueMenu.revalidate();
    }


    // EFFECTS: adds each injured player as an object array stored in a list and returns it
    private ArrayList<Object[]> addInjuredPlayersToDisplay() {
        ArrayList<Object[]> data = new ArrayList<>();
        for (Player p : team.getInjuryReserve()) {
            data.add(new Object[]{p.getName(), p.getPosition(), p.getJerseyNumber(), p.getHeight(), p.getWeight(),
                    p.averagePoints(), p.averageRebounds(), p.averageAssists(), p.averageSteals(), p.averageBlocks(),
                    p.getGamesPlayed()});
        }
        return data;
    }

    // MODIFIES: this
    // EFFECTS: creates a new frame that contains a menu to edit wins through a text field
    private void editWins() {
        if (editRecord == null || !editRecord.isVisible()) {
            editRecord = new JFrame();
            editRecord.setLayout(null);
            editRecord.setPreferredSize(new Dimension(400,300));
            editRecord.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            wins = new JTextField();
            wins.setBounds(100,90,200,40);
            JButton submitWins = new JButton("Next");
            submitWins.setActionCommand("nextWins");
            submitWins.addActionListener(this);
            submitWins.setBounds(137,140,125,40);
            JLabel enterWins = new JLabel();
            enterWins.setText("Enter latest amount of wins!");
            enterWins.setBounds(110,50,200,40);
            editRecord.add(enterWins);
            editRecord.add(wins);
            editRecord.add(submitWins);
            editRecord.getRootPane().setDefaultButton(submitWins);
            editRecord.pack();
            editRecord.setLocationRelativeTo(null);
            editRecord.setResizable(false);
            editRecord.setVisible(true);
        }
    }

    // MODIFIES: this
    // EFFECTS: creates a new frame that contains a menu to edit losses through a text field
    private void editLosses() {
        losses =  new JTextField();
        JLabel enter = new JLabel();
        enter.setText("Enter latest amount of losses!");
        enter.setBounds(105,50,400,40);
        addStatsHelper(editRecord, losses, "Submit", "submitRecord");
        editRecord.add(enter);
        losses.requestFocus();
        losses.setBounds(100,90,200,40);
    }

    // EFFECTS: displays JOptionPane for successful team record update, if OK is clicked team record is updated
    private void editTeamRecord() {
        int answer = JOptionPane.showConfirmDialog(null, "Team record updated: "
                + team.getTeamName()
                + " record has been updated","title", JOptionPane.DEFAULT_OPTION);
        if (answer == JOptionPane.OK_OPTION) {
            editRecord.setVisible(false);
            team.teamRecord(Integer.parseInt(wins.getText()), Integer.parseInt(losses.getText()));
        }
    }

    // EFFECTS: displays JOptionPane for confirmation to end season, if YES is clicked calculate and display end of
    //          season awards
    private void endSeasonMenu() {
        int answer = JOptionPane.showConfirmDialog(null,
                "Are you sure you want to end the season?","title", JOptionPane.YES_NO_OPTION);
        if (answer == JOptionPane.YES_OPTION) {
            league.calculateLeagueWinner();
            league.calculateLeagueMostValuablePlayer();
            league.calculateLeagueDefensivePlayer();
            endSeason();
        }
    }

    // MODIFIES: this
    // EFFECTS: creates a new frame with gif set as the background and displays end of season awards
    private void endSeason() {
        if (endSeason == null || !endSeason.isVisible()) {
            endSeason = new JFrame();
            endSeason.setLayout(null);
            Dimension windowSize = Toolkit.getDefaultToolkit().getScreenSize();
            ImageIcon gif = new ImageIcon(new ImageIcon("data/endSeason.gif")
                    .getImage().getScaledInstance(windowSize.width, windowSize.height, Image.SCALE_DEFAULT));
            JLabel label = new JLabel();
            label.setIcon(gif);
            label.setBounds(0,0, windowSize.width, windowSize.height);
            endSeasonHelper();
            endSeason.add(label);
            endSeason.setExtendedState(JFrame.MAXIMIZED_BOTH);
            endSeason.setUndecorated(false);
            endSeason.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            endSeason.pack();
            endSeason.setLocationRelativeTo(null);
            endSeason.setResizable(false);
            endSeason.setVisible(true);
        }
    }

    // MODIFIES: this
    // EFFECTS: creates JLabels for league winner, league MVP, and league DPOY added to the endSeason frame
    private void endSeasonHelper() {
        Team t = league.getLeagueWinner().get(0);
        Player p = league.getLeagueMostValuablePlayer().get(0);
        Player d = league.getLeagueDefensivePlayer().get(0);
        JLabel congrats = new JLabel();
        congrats.setText("Congratulations on an amazing season!");
        congrats.setFont(new Font("Calibri", Font.BOLD, 50));
        congrats.setBounds(225,240,2000,100);
        JLabel leagueWinner = new JLabel();
        leagueWinner.setText("League Winner: " + t.getTeamName() + " ||" + " Wins: " + t.getWins() + " ||"
                + " Losses: " + t.getLosses() + " ||" + " PCT: " + t.winPercentage());
        leagueWinner.setFont(new Font("Calibri", Font.PLAIN, 25));
        leagueWinner.setBounds(15, 320, 2000, 100);
        JLabel leagueMVP = new JLabel();
        endSeasonStatsHelper(leagueMVP, p, "MVP");
        leagueMVP.setBounds(15, 400, 2000, 100);
        JLabel leagueDefensive = new JLabel();
        endSeasonStatsHelper(leagueDefensive, d, "DPOY");
        leagueDefensive.setBounds(15, 480, 2000, 100);
        endSeason.add(congrats);
        endSeason.add(leagueWinner);
        endSeason.add(leagueMVP);
        endSeason.add(leagueDefensive);
    }

    // EFFECTS: sets the label text to display stats of league MVP and league DPOY
    private void endSeasonStatsHelper(JLabel label, Player p, String award) {
        label.setText("League " + award + ": " + p.getName() + " ||" + " Position: " + p.getPosition()
                + " ||" + " PPG: " + p.averagePoints() + " ||"
                + " RPG: " + p.averageRebounds() + " ||" + " APG: " + p.averageAssists() + " ||" + " SPG: "
                + p.averageSteals() + " ||" + " BPG: " + p.averageBlocks() + " ||" + " GP: " + p.getGamesPlayed());
        label.setFont(new Font("Calibri", Font.PLAIN, 25));
    }

    @Override
    public void windowOpened(WindowEvent e) {
    }

    @Override
    public void windowClosing(WindowEvent e) {
        printLogToConsole(EventLog.getInstance());
    }

    @Override
    public void windowClosed(WindowEvent e) {
    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }

    private void printLogToConsole(EventLog el) {
        System.out.println("Event Log:\n ");
        for (Event next : el) {
            System.out.println(next.toString() + "\n");
        }
    }

    // initializes the basketballLeagueGUI
    public static void main(String[] args) {
        new BasketballLeagueGUI();
    }
}
