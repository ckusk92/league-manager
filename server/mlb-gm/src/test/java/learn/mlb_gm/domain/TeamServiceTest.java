package learn.mlb_gm.domain;

import learn.mlb_gm.data.TeamRepository;
import learn.mlb_gm.models.Team;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class TeamServiceTest {

    @Autowired
    TeamService service;

    @MockBean
    TeamRepository repository;

    @Test
    void shouldAdd() {
        Team team = new Team();
        team.setName("Team1");
        Team mockOut = new Team(1, "Team1");

        when(repository.add(team)).thenReturn(mockOut);

        Result<Team> actual = service.add(team);
        assertEquals(ResultType.SUCCESS, actual.getType());
        assertEquals(mockOut, actual.getPayload());
    }

    @Test
    void shouldNotAddBlankName() {
        Team team = new Team();
        team.setName("    ");

        Result<Team> actual = service.add(team);
        assertEquals(ResultType.INVALID, actual.getType());
    }

    @Test
    void shouldUpdate() {
        Team team = new Team(2, "Team");

        when(repository.update(team)).thenReturn(true);

        Result<Team> actual = service.update(team);
        assertEquals(ResultType.SUCCESS, actual.getType());
    }

    @Test
    void shouldNotUpdateEmptyName() {
        Team team = new Team(3, "   ");

        Result<Team> actual = service.update(team);
        assertEquals(ResultType.INVALID, actual.getType());
    }

}
