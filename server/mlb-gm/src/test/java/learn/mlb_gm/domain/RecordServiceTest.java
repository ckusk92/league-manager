package learn.mlb_gm.domain;

import learn.mlb_gm.data.RecordRepository;
import learn.mlb_gm.models.Record;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class RecordServiceTest {

    @Autowired
    RecordService service;

    @MockBean
    RecordRepository repository;

    List<Record> all = List.of(
            new Record(1, 5, 4),
            new Record(2, 4,5),
            new Record(3, 1, 8),
            new Record(4, 8, 1)
    );


    @Test
    void shouldFindAllFour() {
        when(repository.findAll()).thenReturn(all);

        List<Record> allRecords = repository.findAll();
        assertEquals(4, allRecords.size());
    }

    @Test
    void shouldFindRecordForTeamTwo() {
        when(repository.findForTeam(2)).thenReturn(all.get(1));
        Record two = repository.findForTeam(2);
        assertEquals(4, two.getWin());
    }

    @Test
    void shouldAdd() {
        Record record = new Record(1, 1, 1);

        when(repository.add(record)).thenReturn(record);

        Result<Record> actual = service.add(record);
        assertEquals(ResultType.SUCCESS, actual.getType());
    }

    @Test
    void shouldNotAddNegativeWins() {
        Record record = new Record(1, -1, 1);
        Result<Record> actual = service.add(record);
        assertEquals(ResultType.INVALID, actual.getType());
    }

    @Test
    void shouldUpdate() {
        Record record = new Record(1, 2, 1);

        when(repository.update(record)).thenReturn(true);

        Result<Record> actual = service.update(record);
        assertEquals(ResultType.SUCCESS, actual.getType());
    }

    @Test
    void shouldNotUpdateNegativeLosses() {
        Record record = new Record(1, 1, -5);
        Result<Record> actual = service.update(record);
        assertEquals(ResultType.INVALID, actual.getType());
    }

    @Test
    void shouldDelete() {
        when(repository.deleteForTeam(1)).thenReturn(true);
        assertTrue(service.deleteForTeam(1));
    }

    @Test
    void shouldNotDeleteMissing() {
        when(repository.deleteForTeam(1000)).thenReturn(false);
        assertFalse(repository.deleteForTeam(1000));
    }
}
