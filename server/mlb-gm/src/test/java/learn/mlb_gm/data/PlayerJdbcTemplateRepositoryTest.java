package learn.mlb_gm.data;

import learn.mlb_gm.models.Player;
import learn.mlb_gm.models.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class PlayerJdbcTemplateRepositoryTest {

    final static int NEXT_PLAYER_ID = 12;

    @Autowired
    PlayerJdbcTemplateRepository repository;

    @Autowired
    KnownGoodState knownGoodState;

    @BeforeEach
    void setup() {
        knownGoodState.set();
    }

    @Test
    void shouldFindAllEleven() {
        List<Player> all = repository.findAll();
        assertEquals(11, all.size());
    }

    @Test
    void shouldFindFiveActivePlayers() {
        List<Player> active = repository.findAllActive();
        assertEquals(10, active.size());
    }

    @Test
    void shouldFindOneFreeAgent() {
        List<Player> freeAgents = repository.findFreeAgents();
        assertEquals(1, freeAgents.size());
    }

    @Test
    void shouldFindTomMurphy() {
        Player tom = repository.findById(4);
        assertNotNull(tom);
        assertEquals("Tom", tom.getFirstName());
        assertEquals("Murphy", tom.getLastName());
        assertEquals(Position.CATCHER, tom.getPosition());
        assertEquals(82, tom.getRating());
        assertEquals(4, tom.getPlayerId());
    }

    @Test
    void shouldAdd() {
        Player player = new Player("Charlie", "Kusk", Position.THIRD_BASE, 99);
        Player actual = repository.add(player);
        assertNotNull(actual);
        assertEquals(NEXT_PLAYER_ID, actual.getPlayerId());
        assertEquals(player.getFirstName(), actual.getFirstName());
    }

    @Test
    void shouldUpdate() {
        Player player = new Player(1, "Charlie", "Kusk", Position.THIRD_BASE, 99);
        assertTrue(repository.update(player));
        assertEquals("Charlie", repository.findById(1).getFirstName());
    }

    @Test
    void shouldNotUpdateMissing() {
        Player player = new Player(1000, "Charlie", "Kusk", Position.THIRD_BASE, 99);
        assertFalse(repository.update(player));
        assertEquals("Chance", repository.findById(1).getFirstName());
    }

    @Test
    void shouldDelete() {
        int before = repository.findAll().size();
        assertTrue(repository.deleteById(3));
        int after = repository.findAll().size();
        assertEquals(before - 1, after);
    }

    @Test
    void shouldNotDeleteMissing() {
        int before = repository.findAll().size();
        assertFalse(repository.deleteById(3000));
        int after = repository.findAll().size();
        assertEquals(before, after);
    }
}
