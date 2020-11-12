package learn.mlb_gm.domain;

import learn.mlb_gm.data.UserTeamRepository;
import learn.mlb_gm.models.UserTeam;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UserTeamServiceTest {

    @Autowired
    UserTeamService service;

    @MockBean
    UserTeamRepository repository;

    @Test
    void shouldAdd() {
        UserTeam userTeam = new UserTeam(1, 1, true, 90);
        UserTeam mockOut = new UserTeam(1, 1, 1, true, 90);

        when(repository.add(userTeam)).thenReturn(mockOut);

        Result<UserTeam> actual = service.add(userTeam);
        assertEquals(ResultType.SUCCESS, actual.getType());
        assertEquals(mockOut, actual.getPayload());
    }

    @Test
    void shouldNotAddNegativeRating() {
        UserTeam userTeam = new UserTeam(1, 1, true, -10);
        Result<UserTeam> actual = service.add(userTeam);
        assertEquals(ResultType.INVALID, actual.getType());
    }

    @Test
    void shouldUpdate() {
        UserTeam userTeam = new UserTeam(1, 1, 1, true, 95);

        when(repository.update(userTeam)).thenReturn(true);

        Result<UserTeam> actual = service.update(userTeam);
        assertEquals(ResultType.SUCCESS, actual.getType());
    }

    @Test
    void shouldNotUpdateOverHundredRating() {
        UserTeam userTeam = new UserTeam(1, 1, false, 104);
        Result<UserTeam> actual = service.update(userTeam);
        assertEquals(ResultType.INVALID, actual.getType());
    }

}
