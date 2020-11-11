package learn.mlb_gm.data;

import learn.mlb_gm.models.Team;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class TeamJdbcTemplateRepositoryTest {

    final static int NEXT_TEAM_ID = 9;

    @Autowired
    TeamJdbcTemplateRepository repository;

    @Autowired
    KnownGoodState knownGoodState;

    @BeforeEach
    void setup() {
        knownGoodState.set();
    }

    @Test
    void shouldFindAllEight() {
        List<Team> all = repository.findAll();
        assertEquals(8, all.size());
    }

    @Test
    void shouldFindCubs() {
        Team team = repository.findById(6);
        assertNotNull(team);
        assertEquals(6, team.getTeamId());
        assertEquals("Chicago Cubs", team.getName());
    }

    @Test
    void shouldAdd() {
        Team team = new Team();
        team.setName("Kane County Cougars");
        Team actual = repository.add(team);
        assertNotNull(actual);
        assertEquals(NEXT_TEAM_ID, actual.getTeamId());
    }

    @Test
    void shouldUpdate() {
        Team team = new Team();
        team.setName("Montreal Expos");
        team.setTeamId(2);
        assertTrue(repository.update(team));
    }

    @Test
    void shouldNotUpdateMissing() {
        Team team = new Team();
        team.setName("Name");
        team.setTeamId(200);
        assertFalse(repository.update(team));
    }

    @Test
    void shouldDelete() {
        int before = repository.findAll().size();
        assertTrue(repository.deleteById(1));
        int after = repository.findAll().size();
        assertEquals(before - 1, after);
    }

    @Test
    void shouldNotDeleteMissing() {
        int before = repository.findAll().size();
        assertFalse(repository.deleteById(1000));
        int after = repository.findAll().size();
        assertEquals(before, after);
    }
}
