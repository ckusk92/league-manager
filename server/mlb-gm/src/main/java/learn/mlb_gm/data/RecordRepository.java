package learn.mlb_gm.data;

import learn.mlb_gm.models.Record;
import learn.mlb_gm.models.response_objects.RecordWithTeam;

import java.util.List;

public interface RecordRepository {

    public List<Record> findAll();

    public List<RecordWithTeam> getStandings(int userId);

    public Record findForTeam(int userTeamId);

    public Record add(Record record);

    public boolean update(Record record);

    public boolean deleteForTeam(int userTeamId);
}
