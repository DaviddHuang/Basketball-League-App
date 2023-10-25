package persistence;

import model.Team;

import static org.junit.jupiter.api.Assertions.assertEquals;

// Code is influenced by: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
public class JsonTest {
    protected void checkTeam(String name, Team t) {
        assertEquals(name, t.getTeamName());
    }
}
