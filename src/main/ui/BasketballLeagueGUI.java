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
    private JTextField leagueName = new JTextField();
    private JTextField teamName;
    private JTextField removeTeamName;
    private JTextField selectTeamName;
    private JFrame leagueMenu = new JFrame();
    private JFrame success = new JFrame();
    private JFrame teamMenu = new JFrame();
    private JFrame addTeam;
    private JFrame removeTeam;
    private JFrame selectTeam;
    private JTable standings;
    private DefaultTableModel model;


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
        if (e.getActionCommand().equals("submitSelectTeam")) {
            selectTeam();
            playerMenu();
        }
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
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    // MODIFIES: this
    // EFFECTS: loads league from file
    private void loadLeague() {
        Reader reader = new Reader(FILE_DIRECTORY);
        try {
            league = reader.read();
            standingsPanel.setVisible(false);
            leagueMenu();
        } catch (IOException e) {
            e.printStackTrace();
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
                }
                return;
            }
        }
        JOptionPane.showMessageDialog(null, "Team not found", "title",
                JOptionPane.ERROR_MESSAGE);
    }

    // EFFECTS: creates a player menu
    private void playerMenu() {
        selectTeamName.setText("");
        leagueMenu.setVisible(false);
        teamMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        teamMenu.setTitle("League: " + league.getLeagueName());
        teamMenu.setSize(new Dimension(600,600));
        teamMenu.setLayout(null);
        playerButtonsPanel.setBounds(0,15,600,65);
        playerButtons();
        teamMenu.add(playerButtonsPanel);
        teamMenu.setLocationRelativeTo(null);
        teamMenu.setVisible(true);
    }

    // EFFECTS: helper method to initialize all the buttons in leagueMenu
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
        JButton back = new JButton("Back");
        back.setActionCommand("back");
        back.addActionListener(this);
        playerButtonsPanel.add(back);
    }


    public static void main(String[] args) {
        new BasketballLeagueGUI();
    }
}
