package learn.mlb_gm.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import learn.mlb_gm.data.TeamRepository;
import learn.mlb_gm.models.Team;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Disabled
@SpringBootTest
@AutoConfigureMockMvc
public class TeamControllerTest {

    @MockBean
    TeamRepository repository;

    @Autowired
    MockMvc mvc;

    @Test
    void shouldGetAll() throws Exception {

        List<Team> teams = List.of(
                new Team(1, "Cubs"),
                new Team(2, "Red Sox"),
                new Team(3, "Yankees")
        );

        ObjectMapper jsonMapper = new ObjectMapper();
        String expectedJson = jsonMapper.writeValueAsString(teams);

        when(repository.findAll()).thenReturn(teams);

        mvc.perform(get("/team"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expectedJson));

    }

    @Test
    void shouldFindById() throws Exception {
        Team team = new Team(1, "Chicago Cubs");

        ObjectMapper jsonMapper = new ObjectMapper();
        String expectedJson = jsonMapper.writeValueAsString(team);

        when(repository.findById(1)).thenReturn(team);

        mvc.perform(get("/team/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expectedJson));
    }

    @Test
    void shouldNotFindMissingById() throws Exception {

        when(repository.findById(1)).thenReturn(null);

        mvc.perform(get("/team/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldAdd() throws Exception {
        Team teamIn = new Team(0, "Cubs");
        Team expected = new Team(1, "Cubs");

        when(repository.add(any())).thenReturn(expected);

        ObjectMapper jsonMapper = new ObjectMapper();
        String jsonIn = jsonMapper.writeValueAsString(teamIn);
        String expectedJson = jsonMapper.writeValueAsString(expected);

        var request = post("/team")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonIn);

        mvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expectedJson));
    }

    @Test
    void shouldNotAddEmptyName() throws Exception {

        Team teamIn = new Team(0, "  ");

        ObjectMapper jsonMapper = new ObjectMapper();
        String jsonIn = jsonMapper.writeValueAsString(teamIn);

        var request = post("/team")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonIn);

        // 3. Send the request and assert.
        mvc.perform(request)
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldUpdate() {

    }

    @Test
    void shouldNotUpdateMissingId() throws Exception {
        Team team = new Team(1000, "Chicago Cubs");

        when(repository.update(team)).thenReturn(false);

        ObjectMapper jsonMapper = new ObjectMapper();
        String jsonIn = jsonMapper.writeValueAsString(team);

        var request = put("/team/1000")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonIn);

        mvc.perform(request)
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldDelete() throws Exception {
        when(repository.deleteById(1)).thenReturn(true);

        var request = delete("/team/1");

        mvc.perform(request)
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldNotDeleteMissingId() throws Exception {
        when(repository.deleteById(1000)).thenReturn(false);

        var request = delete("/team/1000");

        mvc.perform(request)
                .andExpect(status().isNotFound());
    }
}
