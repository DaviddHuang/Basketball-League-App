package persistence;

import model.Team;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkTeam(String name, Team t) {
        assertEquals(name, t.getTeamName());
    }
}
