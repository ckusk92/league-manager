package learn.mlb_gm.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import learn.mlb_gm.data.UserTeamRepository;
import learn.mlb_gm.models.UserTeam;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserTeamControllerTest {

    @MockBean
    UserTeamRepository repository;

    @Autowired
    MockMvc mvc;

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
}
