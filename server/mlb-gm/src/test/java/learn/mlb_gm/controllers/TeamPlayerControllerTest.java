package learn.mlb_gm.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import learn.mlb_gm.data.TeamPlayerRepository;
import learn.mlb_gm.models.TeamPlayer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TeamPlayerControllerTest {

    @MockBean
    TeamPlayerRepository repository;

    @Autowired
    MockMvc mvc;

    @Test
    void shouldFindAll() throws Exception {
        List<TeamPlayer> teamPlayers = List.of(
                new TeamPlayer(1, 1, 1),
                new TeamPlayer (2, 1, 2),
                new TeamPlayer (3, 2, 3),
                new TeamPlayer (4, 2, 4)
        );

        ObjectMapper jsonMapper = new ObjectMapper();
        String expectedJson = jsonMapper.writeValueAsString(teamPlayers);

        when(repository.findAll()).thenReturn(teamPlayers);

        mvc.perform(get("/teamplayer"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expectedJson));
    }

    @Test
    void shouldFindAllForTeamTwo() throws Exception {
        List<TeamPlayer> teamPlayers = List.of(
                new TeamPlayer (3, 2, 3),
                new TeamPlayer (4, 2, 4)
        );

        ObjectMapper jsonMapper = new ObjectMapper();
        String expectedJson = jsonMapper.writeValueAsString(teamPlayers);

        when(repository.findAllForTeam(2)).thenReturn(teamPlayers);

        mvc.perform(get("/teamplayer/team/2"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expectedJson));
    }

    @Test
    void shouldFindById() throws Exception {
        TeamPlayer teamPlayer = new TeamPlayer(5, 4, 10);

        ObjectMapper jsonMapper = new ObjectMapper();
        String expectedJson = jsonMapper.writeValueAsString(teamPlayer);

        when(repository.findById(5)).thenReturn(teamPlayer);

        mvc.perform(get("/teamplayer/5"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expectedJson));

    }

    @Test
    void shouldAdd() throws Exception {
        TeamPlayer teamPlayer = new TeamPlayer(0, 4, 10);
        TeamPlayer expected = new TeamPlayer(1, 4, 10);

        when(repository.add(teamPlayer)).thenReturn(expected);
        ObjectMapper jsonMapper = new ObjectMapper();

        String teamPlayerJson = jsonMapper.writeValueAsString(teamPlayer);
        String expectedJson = jsonMapper.writeValueAsString(expected);

        var request = post("/teamplayer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(teamPlayerJson);

        mvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(content().json(expectedJson));
    }

    @Test
    void addShouldReturn400WhenEmpty() throws Exception {
        var request = post("/teamplayer")
                .contentType(MediaType.APPLICATION_JSON);

        mvc.perform(request)
                .andExpect(status().isBadRequest());
    }

    // NOT WORKING
    @Test
    void shouldUpdate() throws Exception {
        TeamPlayer teamPlayer = new TeamPlayer(1, 4, 10);

        when(repository.update(teamPlayer)).thenReturn(true);

        ObjectMapper jsonMapper = new ObjectMapper();
        String jsonIn = jsonMapper.writeValueAsString(teamPlayer);

        var request = put("/teamplayer/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonIn);

        mvc.perform(request)
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldNotUpdateMissingId() throws Exception {
        TeamPlayer teamPlayer = new TeamPlayer(1000, 4, 10);

        when(repository.update(teamPlayer)).thenReturn(false);

        ObjectMapper jsonMapper = new ObjectMapper();
        String jsonIn = jsonMapper.writeValueAsString(teamPlayer);

        var request = put("/teamplayer/1000")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonIn);

        mvc.perform(request)
                .andExpect(status().isNotFound());

    }

    @Test
    void shouldDelete() throws Exception {
        when(repository.deleteById(1)).thenReturn(true);

        var request = delete("/teamplayer/1");

        mvc.perform(request)
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldNotDeleteMissingId() throws Exception {
        when(repository.deleteById(1000)).thenReturn(false);

        var request = delete("/teamplayer/1000");

        mvc.perform(request)
                .andExpect(status().isNotFound());
    }
}
