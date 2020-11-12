package learn.mlb_gm.domain;

import learn.mlb_gm.data.GameRepository;
import learn.mlb_gm.models.Game;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class GameServiceTest {

    @Autowired
    GameService service;

    @MockBean
    GameRepository repository;

    @Test
    void shouldFindAll() {
        List<Game> all = List.of(
            new Game(1, 1, 1, 1, 0, 0, false),
            new Game(2, 3, 4, 1, 0, 0, false),
            new Game(3, 1, 3, 2, 0, 0, false),
            new Game(4, 2, 4, 2, 0, 0, false)
        );

        when(repository.findAll()).thenReturn(all);

        List<Game> allGames = repository.findAll();
        assertEquals(4, allGames.size());
    }

    @Test
    void shouldAdd() {
        Game game = new Game(1, 1, 2, 4, 3, true);
        Game mockOut = new Game(1,1, 1, 2, 4, 3, true);

        when(repository.add(game)).thenReturn(mockOut);

        Result<Game> actual = service.add(game);
        assertEquals(ResultType.SUCCESS, actual.getType());
        assertEquals(mockOut, actual.getPayload());
    }

    @Test
    void shouldNotAddNegativeScore() {
        Game game = new Game(1, 1, 2, -1, 4, true);
        Result<Game> actual = service.add(game);
        assertEquals(ResultType.INVALID, actual.getType());
    }

    @Test
    void shouldUpdate() {
        Game game = new Game(1, 1, 1, 2, 1, 4, true);

        when(repository.update(game)).thenReturn(true);

        Result<Game> actual = service.update(game);
        assertEquals(ResultType.SUCCESS, actual.getType());
    }

    @Test
    void shouldNotUpdateZeroGameNumber() {
        Game game = new Game(1, 1, 1, 0, 1, 4, true);
        Result<Game> actual = service.update(game);
        assertEquals(ResultType.INVALID, actual.getType());
    }

    @Test
    void shouldDelete() {
        when(repository.deleteById(1)).thenReturn(true);
        assertTrue(service.deleteById(1));
    }

    @Test
    void shouldNotDeleteBadId() {
        when(repository.deleteById(1000)).thenReturn(false);
        assertFalse(service.deleteById(1000));
    }
}
