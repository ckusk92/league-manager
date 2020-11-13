package learn.mlb_gm.data;

import learn.mlb_gm.models.Record;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class RecordJdbcTemplateRepositoryTest {

    @Autowired
    RecordJdbcTemplateRepository repository;

    @Autowired
    KnownGoodState knownGoodState;

    @BeforeEach
    void setup() {knownGoodState.set();}

    @Test
    void shouldFindAllFour() {
        List<Record> all = repository.findAll();
        assertEquals(4, all.size());
    }

    @Test
    void shouldFindRecordForTeamThree() {
        Record three = repository.findForTeam(3);
        assertEquals(3, three.getUserTeamId());
        assertEquals(9, three.getWin());
        assertEquals(0, three.getLoss());
    }

    @Test
    void shouldAdd() {
        Record record = new Record(1, 3, 6);
        Record actual = repository.add(record);
        assertNotNull(actual);
        assertEquals(3, actual.getWin());
    }

    @Test
    void shouldUpdate() {
        Record record = new Record(1, 6, 4);
        assertTrue(repository.update(record));
        assertEquals(6, repository.findForTeam(1).getWin());
    }

    @Test
    void shouldNotUpdateMissingTeam() {
        Record record = new Record(1000, 5, 4);
        assertFalse(repository.update(record));
    }

    @Test
    void shouldDelete() {
        int before = repository.findAll().size();
        assertTrue(repository.deleteForTeam(1));
        int after = repository.findAll().size();
        assertEquals(before - 1, after);
    }

    @Test
    void shouldNotDeleteMissing() {
        int before = repository.findAll().size();
        assertFalse(repository.deleteForTeam(1000));
        int after = repository.findAll().size();
        assertEquals(before, after);
    }
}
