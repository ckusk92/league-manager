package learn.mlb_gm.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import learn.mlb_gm.data.GameRepository;
import learn.mlb_gm.models.Game;
import learn.mlb_gm.models.UserTeam;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import java.util.List;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Disabled
@SpringBootTest
@AutoConfigureMockMvc
public class GameControllerTest {

    @MockBean
    GameRepository repository;

    @Autowired
    MockMvc mvc;

    @Test
    void shouldFindAll() throws Exception {
        List<Game> all = List.of(
                new Game(1, 1, 1, 1, 0, 0, false),
                new Game(2, 3, 4, 1, 0, 0, false),
                new Game(3, 1, 3, 2, 0, 0, false),
                new Game(4, 2, 4, 2, 0, 0, false)
        );

        ObjectMapper jsonMapper = new ObjectMapper();
        String expectedJson = jsonMapper.writeValueAsString(all);

        when(repository.findAll()).thenReturn(all);

        mvc.perform(get("/game"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expectedJson));
    }

    @Test
    void shouldFindById() throws Exception {
        Game game = new Game(1,1,2, 1, 1, 2, true);

        ObjectMapper jsonMapper = new ObjectMapper();
        String expectedJson = jsonMapper.writeValueAsString(game);

        when(repository.findById(1)).thenReturn(game);

        mvc.perform(get("/game/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expectedJson));
    }

    @Test
    void shouldNotFindMissingById() throws Exception {

        when(repository.findById(1000)).thenReturn(null);

        mvc.perform(get("/game/1000"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldAdd() throws Exception {
        Game game = new Game(0,1,2, 1, 1, 2, true);
        Game expected = new Game(1,1,2, 1, 1, 2, true);

        when(repository.add(any())).thenReturn(expected);
        ObjectMapper jsonMapper = new ObjectMapper();

        String agencyJson = jsonMapper.writeValueAsString(game);
        String expectedJson = jsonMapper.writeValueAsString(expected);

        var request = post("/game")
                .contentType(MediaType.APPLICATION_JSON)
                .content(agencyJson);

        mvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(content().json(expectedJson));
    }

    @Test
    void addShouldReturn400WhenEmpty() throws Exception {
        var request = post("/game")
                .contentType(MediaType.APPLICATION_JSON);

        mvc.perform(request)
                .andExpect(status().isBadRequest());
    }

    @Test
    void addShouldReturn400WhenInvalidField() throws Exception {
        Game game = new Game(1, 1, 2, -10, 5, 4, true);

        ObjectMapper jsonMapper = new ObjectMapper();
        String userTeamJson = jsonMapper.writeValueAsString(game);

        var request = post("/game")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userTeamJson);

        mvc.perform(request)
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldUpdate() throws Exception {

    }

    @Test
    void shouldNotUpdateMissingId() throws Exception {
        Game game = new Game(1000,1,2, 1, 1, 2, true);

        when(repository.update(game)).thenReturn(false);

        ObjectMapper jsonMapper = new ObjectMapper();
        String jsonIn = jsonMapper.writeValueAsString(game);

        var request = put("/game/1000")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonIn);

        mvc.perform(request)
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldDelete() throws Exception {
        when(repository.deleteById(1)).thenReturn(true);

        var request = delete("/game/1");

        mvc.perform(request)
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldNotDeleteMissingId() throws Exception {
        when(repository.deleteById(1000)).thenReturn(false);

        var request = delete("/game/1000");

        mvc.perform(request)
                .andExpect(status().isNotFound());
    }

}
