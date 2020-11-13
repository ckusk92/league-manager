package learn.mlb_gm.domain;

import learn.mlb_gm.data.TeamPlayerRepository;
import learn.mlb_gm.models.TeamPlayer;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamPlayerService {

    private final TeamPlayerRepository repository;

    public TeamPlayerService(TeamPlayerRepository repository) {
        this.repository = repository;
    }

    public List<TeamPlayer> findAll() {return repository.findAll();}

    public List<TeamPlayer> findAllByTeam(int playerTeamId) {return repository.findAllForTeam(playerTeamId);}

    public TeamPlayer findById(int teamPlayerId) {return repository.findById(teamPlayerId);}

    public Result<TeamPlayer> add(TeamPlayer teamPlayer) {
        Result<TeamPlayer> result = validate(teamPlayer);

        if(!result.isSuccess()) {
            return result;
        }

        if(teamPlayer.getTeamPlayerId() != 0) {
            result.addMessage("teamPlayerId cannot be set for `add` operation", ResultType.INVALID);
            return result;
        }

        teamPlayer = repository.add(teamPlayer);
        result.setPayload(teamPlayer);
        return result;
    }

    public Result<TeamPlayer> update(TeamPlayer teamPlayer) {
        Result<TeamPlayer> result = validate(teamPlayer);

        if(!result.isSuccess()) {
            return result;
        }

        if(teamPlayer.getTeamPlayerId() <= 0) {
            result.addMessage("teamPlayerId must be set for `update` operation", ResultType.INVALID);
            return result;
        }

        if(!repository.update(teamPlayer)) {
            String msg = String.format("teamPlayerId: %s, not found", teamPlayer.getTeamPlayerId());
            result.addMessage(msg, ResultType.NOT_FOUND);
        }

        return result;
    }

    public boolean deleteById(int teamPlayerId) {return repository.deleteById(teamPlayerId);}

    private Result<TeamPlayer> validate(TeamPlayer teamPlayer) {
        Result<TeamPlayer> result = new Result<>();

        // ADD VALIDATION LATER
        // Player cannot be on two teams in same league

        return result;
    }

}
