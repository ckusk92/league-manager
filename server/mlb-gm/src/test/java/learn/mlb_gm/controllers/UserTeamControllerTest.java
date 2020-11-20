package learn.mlb_gm.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import learn.mlb_gm.data.UserTeamRepository;
import learn.mlb_gm.models.UserTeam;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Disabled
@SpringBootTest
@AutoConfigureMockMvc
public class UserTeamControllerTest {

    @MockBean
    UserTeamRepository repository;

    @Autowired
    MockMvc mvc;

    @Test
    void shouldFindAll() throws Exception {
        List<UserTeam> userTeams = List.of(
                new UserTeam(1, 1, 1, true, 90),
                new UserTeam(2, 1, 20, false, 90),
                new UserTeam(3, 2, 1, true, 78),
                new UserTeam(4, 2, 15, false, 81)
        );

        ObjectMapper jsonMapper = new ObjectMapper();
        String expectedJson = jsonMapper.writeValueAsString(userTeams);

        when(repository.findAll()).thenReturn(userTeams);

        mvc.perform(get("/userteam"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expectedJson));
    }

    @Disabled
    @Test
    void shouldFindAllForUser2() throws Exception {
        List<UserTeam> userTeams = List.of(
                new UserTeam(3, 2, 1, true, 78),
                new UserTeam(4, 2, 15, false, 81)
        );

        ObjectMapper jsonMapper = new ObjectMapper();
        String expectedJson = jsonMapper.writeValueAsString(userTeams);

        when(repository.findAllByUser(2)).thenReturn(userTeams);

        mvc.perform(get("/userteam/user/2"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expectedJson));
    }

    @Test
    void shouldFindById() throws Exception {
        UserTeam userTeam = new UserTeam(1, 1, 1, true, 90);

        ObjectMapper jsonMapper = new ObjectMapper();
        String expectedJson = jsonMapper.writeValueAsString(userTeam);

        when(repository.findById(1)).thenReturn(userTeam);

        mvc.perform(get("/userteam/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expectedJson));
    }

    @Test
    void shouldNotFindMissingById() throws Exception {

        when(repository.findById(1000)).thenReturn(null);

        mvc.perform(get("/userteam/1000"))
                .andExpect(status().isNotFound());
    }

    @Test
    void addShouldReturn400WhenEmpty() throws Exception {
        var request = post("/userteam")
                .contentType(MediaType.APPLICATION_JSON);

        mvc.perform(request)
                .andExpect(status().isBadRequest());
    }

    // NOT PASSING
    @Test
    void addShouldReturn400WhenInvalidField() throws Exception {

        ObjectMapper jsonMapper = new ObjectMapper();

        UserTeam userTeam = new UserTeam(0, 1, 1, false, 105);
        String userTeamJson = jsonMapper.writeValueAsString(userTeam);

        var request = post("/userteam")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userTeamJson);

        mvc.perform(request)
                .andExpect(status().isBadRequest());

    }

    @Test
    void addShouldReturn415WhenMultipart() throws Exception {

        ObjectMapper jsonMapper = new ObjectMapper();

        UserTeam userTeam = new UserTeam(0, 1, 1, true, 90);
        String userTeamJson = jsonMapper.writeValueAsString(userTeam);

        var request = post("/userteam")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .content(userTeamJson);

        mvc.perform(request)
                .andExpect(status().isUnsupportedMediaType());
    }

    @Test
    void addShouldReturn201() throws Exception {

        UserTeam userTeam = new UserTeam(0, 1, 1, true, 90);
        UserTeam expected = new UserTeam(1, 1, 1, true, 90);

        when(repository.add(any())).thenReturn(expected);
        ObjectMapper jsonMapper = new ObjectMapper();

        String agencyJson = jsonMapper.writeValueAsString(userTeam);
        String expectedJson = jsonMapper.writeValueAsString(expected);

        var request = post("/userteam")
                .contentType(MediaType.APPLICATION_JSON)
                .content(agencyJson);

        mvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(content().json(expectedJson));
    }

    @Test
    void shouldUpdate() throws Exception {

    }

    @Test
    void shouldNotUpdateMissingId() throws Exception {
        UserTeam userTeam = new UserTeam(1000, 1, 1, true, 90);

        when(repository.update(userTeam)).thenReturn(false);

        ObjectMapper jsonMapper = new ObjectMapper();
        String jsonIn = jsonMapper.writeValueAsString(userTeam);

        var request = put("/userteam/1000")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonIn);

        mvc.perform(request)
                .andExpect(status().isNotFound());

    }

    @Test
    void shouldDelete() throws Exception {
        when(repository.deleteById(1)).thenReturn(true);

        var request = delete("/userteam/1");

        mvc.perform(request)
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldNotDeleteMissingId() throws Exception {
        when(repository.deleteById(1000)).thenReturn(false);

        var request = delete("/userteam/1000");

        mvc.perform(request)
                .andExpect(status().isNotFound());
    }
}
