package learn.mlb_gm.domain;

import learn.mlb_gm.data.PlayerRepository;
import learn.mlb_gm.models.Player;
import learn.mlb_gm.models.Position;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class PlayerServiceTest {

    @Autowired
    PlayerService service;

    @MockBean
    PlayerRepository repository;

    @Test
    void shouldAdd() {
        Player player = new Player("Charlie", "Kusk", Position.THIRD_BASE, 99);
        Player mockOut = new Player(1, "Charlie", "Kusk", Position.THIRD_BASE, 99);

        when(repository.add(player)).thenReturn(mockOut);

        Result<Player> actual = service.add(player);
        assertEquals(ResultType.SUCCESS, actual.getType());
        assertEquals(mockOut, actual.getPayload());
    }

    @Test
    void shouldNotAddBlankFirstName() {
        Player player = new Player("  ", "Kusk", Position.THIRD_BASE, 99);

        Result<Player> actual = service.add(player);
        assertEquals(ResultType.INVALID, actual.getType());
    }

    @Test
    void shouldNotNullPosition() {
        Player player = new Player("  ", "Kusk", null, 99);

        Result<Player> actual = service.add(player);
        assertEquals(ResultType.INVALID, actual.getType());
    }

    @Test
    void shouldNotAddNegativeRating() {
        Player player = new Player("  ", "Kusk", Position.THIRD_BASE, -1);

        Result<Player> actual = service.add(player);
        assertEquals(ResultType.INVALID, actual.getType());
    }

    @Test
    void shouldUpdate() {
        Player player = new Player(3, "first", "last", Position.CATCHER, 90);

        when(repository.update(player)).thenReturn(true);

        Result<Player> actual = service.update(player);
        assertEquals(ResultType.SUCCESS, actual.getType());
    }

    @Test
    void shouldNotUpdateBlankLastName() {
        Player player = new Player ("first", "  ", Position.FIRST_BASE, 80);
        Result<Player> actual = service.update(player);
        assertEquals(ResultType.INVALID, actual.getType());
    }

    @Test
    void shouldNotUpdateNullPosition() {
        Player player = new Player ("first", "last", null, 80);
        Result<Player> actual = service.update(player);
        assertEquals(ResultType.INVALID, actual.getType());
    }

    @Test
    void shouldNotUpdateRatingOver99() {
        Player player = new Player ("first", "last", Position.FIRST_BASE, 105);
        Result<Player> actual = service.update(player);
        assertEquals(ResultType.INVALID, actual.getType());
    }
}
