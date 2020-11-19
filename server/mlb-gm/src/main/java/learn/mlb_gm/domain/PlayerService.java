package learn.mlb_gm.domain;

import learn.mlb_gm.data.PlayerRepository;
import learn.mlb_gm.data.TeamPlayerRepository;
import learn.mlb_gm.data.UserTeamRepository;
import learn.mlb_gm.models.Player;
import learn.mlb_gm.models.UserTeam;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PlayerService {

    private final PlayerRepository repository;
    private final TeamPlayerRepository teamPlayerRepository;
    private final UserTeamRepository userTeamRepository;

    public PlayerService(PlayerRepository repository, TeamPlayerRepository teamPlayerRepository, UserTeamRepository userTeamRepository) {
        this.repository = repository;
        this.teamPlayerRepository = teamPlayerRepository;
        this.userTeamRepository = userTeamRepository;
    }

    public List<Player> findAll() { return repository.findAll(); }

    public List<Player> findFreeAgents(int userId) {return repository.findFreeAgents(userId);}

    public List<Player> findSelectableFreeAgents(int userId) {
            List<Player> allFreeAgents = repository.findFreeAgents(userId);

            // Need userTEAMID, NOT USER TEAM

            // Finds userTeamId associated with userId
            int userTeamId = 0;
            List<UserTeam> userTeams = userTeamRepository.findAllByUser(userId);
            for(UserTeam userTeam: userTeams) {
                if(userTeam.isUserControlled()) {
                    userTeamId = userTeam.getUserTeamId();
                }
            }

            List<Player> currentTeam = teamPlayerRepository.findAllPlayersForTeam(userTeamId);
            List<Player> selectableFreeAgents = new ArrayList<>();

            for(Player fa : allFreeAgents) {
                boolean available = true;
                for (Player onRoster : currentTeam) {
                    if (onRoster.getPosition() == fa.getPosition()) {
                        available = false;
                    }
                }
                if(available) {
                    selectableFreeAgents.add(fa);
                }
            }

            return selectableFreeAgents;
    }

    public Player findById(int playerId) { return repository.findById(playerId); }

    public Result<Player> add(Player player) {
        Result<Player> result = validate(player);

        if(!result.isSuccess()) {
            return result;
        }

        if(player.getPlayerId() != 0) {
            result.addMessage("playerId cannot be set for `add` operation", ResultType.INVALID);
            return result;
        }

        player = repository.add(player);
        result.setPayload(player);
        return result;
    }

    public Result<Player> update(Player player) {
        Result<Player> result = validate(player);

        if(!result.isSuccess()) {
            return result;
        }

        if(player.getPlayerId() <= 0) {
            result.addMessage("playerId must be set for `update` operation", ResultType.INVALID);
            return result;
        }

        if(!repository.update(player)) {
            String msg = String.format("playerId: %s, not found", player.getPlayerId());
            result.addMessage(msg, ResultType.NOT_FOUND);
        }

        return result;
    }

    public boolean deleteById(int playerId) { return repository.deleteById(playerId); }

    private Result<Player> validate(Player player) {
        Result<Player> result = new Result<>();

        if(player == null) {
            result.addMessage("player cannot be null", ResultType.INVALID);
            return result;
        }

        if(Validations.isNullOrBlank(player.getFirstName())) {
            result.addMessage("first name is required", ResultType.INVALID);
        }

        if(Validations.isNullOrBlank(player.getLastName())) {
            result.addMessage("last name is required", ResultType.INVALID);
        }

        if(player.getPosition() == null) {
            result.addMessage("position is required", ResultType.INVALID);
        }

        if(player.getRating() < 0 || player.getRating() > 99) {
            result.addMessage("rating must be between 0 and 99", ResultType.INVALID);
        }

        return result;
    }



}
