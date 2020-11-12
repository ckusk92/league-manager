package learn.mlb_gm.domain;

import learn.mlb_gm.data.UserTeamRepository;
import learn.mlb_gm.models.UserTeam;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserTeamService {

    private final UserTeamRepository repository;

    public UserTeamService(UserTeamRepository repository) {
        this.repository = repository;
    }

    public List<UserTeam> findAll() {
        return repository.findAll();
    }

    public UserTeam findById(int userTeamId) {
        return repository.findById(userTeamId);
    }

    public List<UserTeam> findAllForUser(int userTeamId) {
        return repository.findAllByUser(userTeamId);
    }

    public Result<UserTeam> add(UserTeam userTeam) {
        Result<UserTeam> result = validate(userTeam);

        if(!result.isSuccess()) {
            return result;
        }

        if(userTeam.getUserTeamId() != 0) {
            result.addMessage("userTeamId cannot be set for `add` operation", ResultType.INVALID);
            return result;
        }

        userTeam = repository.add(userTeam);
        result.setPayload(userTeam);
        return result;
    }

    public Result<UserTeam> update(UserTeam userTeam) {
        Result<UserTeam> result = validate(userTeam);

        if(!result.isSuccess()) {
            return result;
        }

        if(userTeam.getUserTeamId() <= 0) {
            result.addMessage("userTeamId must be set for `update` operation", ResultType.INVALID);
            return result;
        }

        if(!repository.update(userTeam)) {
            String msg = String.format("userTeamId: %s, not found", userTeam.getUserTeamId());
            result.addMessage(msg, ResultType.NOT_FOUND);
        }

        return result;
    }

    public boolean deleteById(int userTeamId) { return repository.deleteById(userTeamId); }

    private Result<UserTeam> validate(UserTeam userTeam) {
        Result<UserTeam> result = new Result<>();

        if(userTeam.getRating() < 0 || userTeam.getRating() > 99) {
            result.addMessage("team rating must be between 0 and 99", ResultType.INVALID);
        }

        return result;
    }

}
