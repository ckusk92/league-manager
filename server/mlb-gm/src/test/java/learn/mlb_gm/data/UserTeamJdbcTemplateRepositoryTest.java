package learn.mlb_gm.data;

import learn.mlb_gm.models.UserTeam;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class UserTeamJdbcTemplateRepositoryTest {

    final static int NEXT_USER_TEAM_ID = 9;

    @Autowired
    UserTeamJdbcTemplateRepository repository;

    @Autowired
    KnownGoodState knownGoodState;

    @BeforeEach
    void setup() {knownGoodState.set();}

    @Disabled
    @Test
    void shouldFindAllEight() {
        List<UserTeam> all = repository.findAll();
        assertEquals(8, all.size());
    }

    @Test
    void shouldFindTeamThree() {
        UserTeam team = repository.findById(3);
        assertEquals(1, team.getUserId());
        assertEquals(3, team.getTeamId());
        assertFalse(team.isUserControlled());
        assertEquals(82, team.getRating());
    }

    @Disabled
    @Test
    void shouldAllFourForUserTwo() {
        List<UserTeam> allForUserTwo = repository.findAllByUser(2);
        assertEquals(4, allForUserTwo.size());
    }

    @Test
    void shouldAdd() {
        UserTeam userTeam = new UserTeam(1, 5, false, 99);
        UserTeam actual = repository.add(userTeam);
        assertNotNull(actual);
        assertEquals(NEXT_USER_TEAM_ID, actual.getUserTeamId());
        assertEquals(5, actual.getTeamId());
    }

    @Test
    void shouldUpdate() {
        UserTeam userTeam = new UserTeam(1, 2, 5, true, 90);
        assertTrue(repository.update(userTeam));
        assertEquals(5, repository.findById(1).getTeamId());
    }

    @Test
    void shouldNotUpdateMissing() {
        UserTeam userTeam = new UserTeam(1000, 2, 5, false, 90);
        assertFalse(repository.update(userTeam));
    }

    @Disabled
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
