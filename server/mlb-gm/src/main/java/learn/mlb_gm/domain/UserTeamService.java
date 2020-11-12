package learn.mlb_gm.domain;

import learn.mlb_gm.data.UserTeamRepository;
import learn.mlb_gm.models.InitInfo;
import learn.mlb_gm.models.UserTeam;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

    public void initiateUserTeams(InitInfo initInfo) {
        Random random = new Random();

        //TODO Use userId in repo.add statements

        List<Integer> teamIds = new ArrayList<>();
        teamIds.add(initInfo.getUserTeamChoiceId());

        // Decide team ids of randoms
        while(teamIds.size() < initInfo.getNumberOfTeams()) {
            // Picks random int between 1-30
            Integer id = random.nextInt(29) + 1;
            if(!teamIds.contains(id)) {
                teamIds.add(id);
            }
        }

        repository.add(new UserTeam(1, teamIds.get(0), true, 50));

        for(int i = 1; i < initInfo.getNumberOfTeams(); i++) {
            repository.add(new UserTeam(1, teamIds.get(i), false, 50));
        }
    }

    private Result<UserTeam> validate(UserTeam userTeam) {
        Result<UserTeam> result = new Result<>();

        if(userTeam.getRating() < 0 || userTeam.getRating() > 99) {
            result.addMessage("team rating must be between 0 and 99", ResultType.INVALID);
        }

        return result;
    }

}
