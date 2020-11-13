package learn.mlb_gm.domain;

import learn.mlb_gm.data.TeamPlayerRepository;
import learn.mlb_gm.models.TeamPlayer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class TeamPlayerServiceTest {

    @Autowired
    TeamPlayerService service;

    @MockBean
    TeamPlayerRepository repository;

    @Test
    void shouldAdd() {
        TeamPlayer teamPlayer = new TeamPlayer(1, 11);
        TeamPlayer mockOut = new TeamPlayer(1, 1, 11);

        when(repository.add(teamPlayer)).thenReturn(mockOut);

        Result<TeamPlayer> actual = service.add(teamPlayer);
        assertEquals(ResultType.SUCCESS, actual.getType());
        assertEquals(mockOut, actual.getPayload());
    }

    @Test
    void shouldUpdate() {
        TeamPlayer teamPlayer = new TeamPlayer(1, 1, 11);

        when(repository.update(teamPlayer)).thenReturn(true);

        Result<TeamPlayer> actual = service.update(teamPlayer);
        assertEquals(ResultType.SUCCESS, actual.getType());
    }

    @Test
    void shouldNotUpdateMissing() {
        TeamPlayer teamPlayer = new TeamPlayer(1000, 1, 11);

        when(repository.update(teamPlayer)).thenReturn(false);

        Result<TeamPlayer> actual = service.update(teamPlayer);
        assertEquals(ResultType.NOT_FOUND, actual.getType());
    }

}
