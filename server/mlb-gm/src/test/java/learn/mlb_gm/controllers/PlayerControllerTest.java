package learn.mlb_gm.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import learn.mlb_gm.data.PlayerRepository;
import learn.mlb_gm.models.Player;
import learn.mlb_gm.models.Position;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PlayerControllerTest {

    @MockBean
    PlayerRepository repository;

    @Autowired
    MockMvc mvc;

    @Test
    void shouldGetAll() throws Exception {

        List<Player> players = List.of(
                new Player(1, "Charlie", "Kusk", Position.THIRD_BASE, 99),
                new Player(2, "Anthony", "Rizzo", Position.FIRST_BASE, 88),
                new Player(3, "Kyle", "Hendricks", Position.CATCHER, 90)
        );

        ObjectMapper jsonMapper = new ObjectMapper();
        String expectedJson = jsonMapper.writeValueAsString(players);

        when(repository.findAll()).thenReturn(players);

        mvc.perform(get("/player"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expectedJson));
    }

    @Test
    void shouldFindById() throws Exception {
        Player player = new Player(1, "Charlie", "Kusk", Position.THIRD_BASE, 99);

        ObjectMapper jsonMapper = new ObjectMapper();
        String expectedJson = jsonMapper.writeValueAsString(player);

        when(repository.findById(1)).thenReturn(player);

        mvc.perform(get("/player/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expectedJson));

    }

    @Test
    void shouldNotFindMissingById() throws Exception {

        when(repository.findById(1)).thenReturn(null);

        mvc.perform(get("/player/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldAdd() throws Exception {
        Player playerIn = new Player(0, "Charlie", "Kusk", Position.THIRD_BASE, 99);
        Player expected = new Player(1, "Charlie", "Kusk", Position.THIRD_BASE, 99);

        when(repository.add(any())).thenReturn(expected);

        ObjectMapper jsonMapper = new ObjectMapper();
        String jsonIn = jsonMapper.writeValueAsString(playerIn);
        String expectedJson = jsonMapper.writeValueAsString(expected);

        var request = post("/player")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonIn);

        mvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expectedJson));
    }

    @Test
    void shouldNotAddEmptyFirstName() throws Exception {

        Player playerIn = new Player(0, "  ", "Kusk", Position.THIRD_BASE, 99);

        ObjectMapper jsonMapper = new ObjectMapper();
        String jsonIn = jsonMapper.writeValueAsString(playerIn);

        var request = post("/player")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonIn);

        mvc.perform(request)
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldNotAddInvalidRating() throws Exception {

        Player playerIn = new Player(0, "  ", "Kusk", Position.THIRD_BASE, 110);

        ObjectMapper jsonMapper = new ObjectMapper();
        String jsonIn = jsonMapper.writeValueAsString(playerIn);

        var request = post("/player")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonIn);

        mvc.perform(request)
                .andExpect(status().isBadRequest());
    }

    // NOT PASSING, RETURNING 404
    @Test
    void shouldUpdate() throws Exception {
        Player player = new Player(1, "Charlie", "Kusk", Position.THIRD_BASE, 99);

        when(repository.update(player)).thenReturn(true);

        ObjectMapper jsonMapper = new ObjectMapper();
        String jsonIn = jsonMapper.writeValueAsString(player);

        var request = put("/player/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonIn);

        mvc.perform(request)
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldNotUpdateMissingId() throws Exception {
        Player player = new Player(1000, "Charlie", "Kusk", Position.THIRD_BASE, 99);

        when(repository.update(player)).thenReturn(false);

        ObjectMapper jsonMapper = new ObjectMapper();
        String jsonIn = jsonMapper.writeValueAsString(player);

        var request = put("/player/1000")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonIn);

        mvc.perform(request)
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldDelete() throws Exception {
        when(repository.deleteById(1)).thenReturn(true);

        var request = delete("/player/1");

        mvc.perform(request)
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldNotDeleteMissingId() throws Exception {
        when(repository.deleteById(1000)).thenReturn(false);

        var request = delete("/player/1000");

        mvc.perform(request)
                .andExpect(status().isNotFound());
    }
}
