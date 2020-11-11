package learn.mlb_gm.domain;

import learn.mlb_gm.data.TeamRepository;
import learn.mlb_gm.models.Team;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamService {

    private final TeamRepository repository;

    public TeamService(TeamRepository repository) {
        this.repository = repository;
    }

    public List<Team> findAll() {
        return repository.findAll();
    }

    public Team findById(int teamId) {
        return repository.findById(teamId);
    }

    public Result<Team> add(Team team) {
        Result<Team> result = validate(team);

        if(!result.isSuccess()) {
            return result;
        }

        if(team.getTeamId() != 0) {
            result.addMessage("teamId cannot be set for `add` operation", ResultType.INVALID);
            return result;
        }

        team = repository.add(team);
        result.setPayload(team);
        return result;
    }

    public Result<Team> update(Team team) {
        Result<Team> result = validate(team);

        if(!result.isSuccess()) {
            return result;
        }

        if(team.getTeamId() <= 0) {
            result.addMessage("teamId must be set for `update` operation", ResultType.INVALID);
            return result;
        }

        if(!repository.update(team)) {
            String msg = String.format("teamId: %s, not found", team.getTeamId());
            result.addMessage(msg, ResultType.NOT_FOUND);
        }

        return result;
    }

    public boolean deleteById(int teamId) {
        return repository.deleteById(teamId);
    }

    private Result<Team> validate(Team team) {
        Result<Team> result = new Result<>();

        if (team == null) {
            result.addMessage("team cannot be null", ResultType.INVALID);
            return result;
        }

        if (Validations.isNullOrBlank(team.getName())) {
            result.addMessage("name is required", ResultType.INVALID);
        }

        return result;
    }

}
