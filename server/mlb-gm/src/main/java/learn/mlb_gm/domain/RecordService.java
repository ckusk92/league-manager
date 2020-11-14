package learn.mlb_gm.domain;

import learn.mlb_gm.data.RecordRepository;
import learn.mlb_gm.models.Record;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecordService {

    public final RecordRepository repository;

    public RecordService(RecordRepository repository) {
        this.repository = repository;
    }

    public List<Record> findAll() {
        return repository.findAll();
    }

    public Record findForTeam(int userTeamId) {
        return repository.findForTeam(userTeamId);
    }

    public Result<Record> add(Record record) {
        Result<Record> result = validate(record);

        if(!result.isSuccess()) {
            return result;
        }

        record = repository.add(record);
        result.setPayload(record);
        return result;
    }

    public Result<Record> update(Record record) {
        Result<Record> result = validate(record);

        if(!result.isSuccess()) {
            return result;
        }

        if(!repository.update(record)) {
            String msg = String.format("userTeaMid: %s, not found", record.getUserTeamId());
            result.addMessage(msg, ResultType.NOT_FOUND);
        }

        return result;
    }

    public boolean deleteForTeam(int userTeamId) {
        return repository.deleteForTeam(userTeamId);
    }



    private Result<Record> validate(Record record) {
        Result<Record> result = new Result<>();

        if(record == null) {
            result.addMessage("record cannot be null", ResultType.INVALID);
        }

        if(record.getWin() < 0) {
            result.addMessage("wins cannot be less than zero", ResultType.INVALID);
        }

        if(record.getLoss() < 0) {
            result.addMessage("losses cannot be less than zero", ResultType.INVALID);
        }

        return result;
    }
}
