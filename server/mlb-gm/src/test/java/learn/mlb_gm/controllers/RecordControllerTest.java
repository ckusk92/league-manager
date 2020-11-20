package learn.mlb_gm.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import learn.mlb_gm.data.RecordRepository;
import learn.mlb_gm.models.Record;
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
public class RecordControllerTest {

    @MockBean
    RecordRepository repository;

    @Autowired
    MockMvc mvc;

    List<Record> all = List.of(
            new Record(1, 5, 4),
            new Record(2, 4,5),
            new Record(3, 1, 8),
            new Record(4, 8, 1)
    );

    @Test
    void shouldFindAll() throws Exception {
        ObjectMapper jsonMapper = new ObjectMapper();
        String expectedJson = jsonMapper.writeValueAsString(all);

        when(repository.findAll()).thenReturn(all);

        mvc.perform(get("/record"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expectedJson));
    }

    @Test
    void shouldFindForTeamTwo() throws Exception {
        ObjectMapper jsonMapper = new ObjectMapper();
        String expectedJson = jsonMapper.writeValueAsString(all.get(2));

        when(repository.findForTeam(2)).thenReturn(all.get(2));

        mvc.perform(get("/record/2"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expectedJson));
    }

    @Test
    void shouldNotFindMissingById() throws Exception {

        when(repository.findForTeam(1000)).thenReturn(null);

        mvc.perform(get("/record/1000"))
                .andExpect(status().isNotFound());
    }

    @Disabled
    @Test
    void shouldAdd() throws Exception {
        Record record = new Record(1,1 ,1);

        when(repository.add(record)).thenReturn(record);
        ObjectMapper jsonMapper = new ObjectMapper();

        String recordJson = jsonMapper.writeValueAsString(record);
        String expectedJson = jsonMapper.writeValueAsString(record);

        var request = post("/record")
                .contentType(MediaType.APPLICATION_JSON)
                .content(recordJson);

        mvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(content().json(expectedJson));
    }

    @Test
    void addShouldReturn400WhenEmpty() throws Exception {
        var request = post("/record")
                .contentType(MediaType.APPLICATION_JSON);

        mvc.perform(request)
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldUpdate() {

    }

    @Test
    void shouldNotUpdateMissing() throws Exception {
        Record record = new Record(1000, 2, 1);

        when(repository.update(record)).thenReturn(false);

        ObjectMapper jsonMapper = new ObjectMapper();
        String jsonIn = jsonMapper.writeValueAsString(record);

        var request = put("/record/1000")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonIn);

        mvc.perform(request)
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldDelete() throws Exception {
        when(repository.deleteForTeam(1)).thenReturn(true);

        var request = delete("/record/1");

        mvc.perform(request)
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldNotDeleteMissingId() throws Exception {
        when(repository.deleteForTeam(1000)).thenReturn(false);

        var request = delete("/record/1000");

        mvc.perform(request)
                .andExpect(status().isNotFound());
    }

}
