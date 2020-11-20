package learn.mlb_gm.data;


import learn.mlb_gm.models.Game;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class GameJdbcTemplateRepositoryTest {

    final static int NEXT_GAME_ID = 5;

    @Autowired
    GameJdbcTemplateRepository repository;

    @Autowired
    KnownGoodState knownGoodState;

    @BeforeEach
    void setup() {knownGoodState.set();}

    @Test
    void shouldFindAllFour() {
        List<Game> all = repository.findAll();
        assertEquals(4, all.size());
    }

    @Test
    void shouldFindGameTwo() {
        Game game = repository.findById(2);
        assertEquals(3, game.getHomeTeamId());
        assertEquals(4, game.getAwayTeamId());
        assertEquals(1, game.getGameNumber());
        assertEquals(0, game.getHomeScore());
        assertEquals(0, game.getAwayScore());
        assertFalse(game.isPlayed());
    }

    @Test
    void shouldAdd() {
        Game game = new Game(1, 4, 2, 0, 0, false);
        Game actual = repository.add(game);
        assertNotNull(actual);
        assertEquals(NEXT_GAME_ID, actual.getGameId());
    }

    @Disabled
    @Test
    void shouldUpdate() {
        Game game = new Game(1, 1, 2, 1, 5, 4, true);
        assertTrue(repository.update(game));
        assertTrue(repository.findById(1).isPlayed());
    }

    @Test
    void shouldNotUpdateMissing() {
        Game game = new Game(1000, 1, 2, 1, 5, 4, true);
        assertFalse(repository.update(game));
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
