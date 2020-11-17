package learn.mlb_gm.domain;

import learn.mlb_gm.data.PlayerRepository;
import learn.mlb_gm.data.TeamPlayerRepository;
import learn.mlb_gm.data.UserTeamRepository;
import learn.mlb_gm.models.Player;
import learn.mlb_gm.models.TeamPlayer;
import learn.mlb_gm.models.UserTeam;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@Service
public class TeamPlayerService {

    private final TeamPlayerRepository repository;
    private final PlayerRepository playerRepository;
    private final UserTeamRepository userTeamRepository;

    public TeamPlayerService(TeamPlayerRepository repository, PlayerRepository playerRepository, UserTeamRepository userTeamRepository) {
        this.repository = repository;
        this.playerRepository = playerRepository;
        this.userTeamRepository = userTeamRepository;
    }

    public List<TeamPlayer> findAll() {return repository.findAll();}

    public List<TeamPlayer> findAllByTeam(int userTeamId) {return repository.findAllForTeam(userTeamId);}

    public List<Player> getRoster(int userTeamId) {
        List<TeamPlayer> teamPlayers = repository.findAllForTeam(userTeamId);
        List<Player> players = new ArrayList<>();
        for(TeamPlayer teamPlayer : teamPlayers) {
            Player player = playerRepository.findById(teamPlayer.getPlayerId());
            player.setRating(teamPlayer.getRating());
            players.add(player);
        }
        return players;
    }

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

    public Result<TeamPlayer> draft(TeamPlayer teamPlayer) {
        int userId = 1;
        Random random = new Random();

        Result<TeamPlayer> result = validate(teamPlayer);

        if(!result.isSuccess()) {
            return result;
        }

        teamPlayer = repository.add(teamPlayer);
        result.setPayload(teamPlayer);

        //CPU Draft players
        List<UserTeam> teams = userTeamRepository.findAllByUser(userId);
        List<Player> freeAgents = playerRepository.findFreeAgents();
        boolean playerAvailable = false;

        for(UserTeam team : teams) {
            if(!team.isUserControlled()) {

                // Refresh list every time
                freeAgents = playerRepository.findFreeAgents();

                Result<TeamPlayer> draftResult = new Result<>();
                draftResult.setType(ResultType.INVALID);
                playerAvailable = false;
                while(!draftResult.isSuccess() && !playerAvailable) {
                    int i = random.nextInt(playerRepository.findAll().size());
                    for(Player fa : freeAgents) {
                        // If randomly selected player is in free agent pool
                        if(fa.getPlayerId() == playerRepository.findById(i).getPlayerId()) { //THROWING NULL POINTER EXCEPTION OCCASIONALLY
                            draftResult = add(new TeamPlayer(team.getUserTeamId(), playerRepository.findById(i).getPlayerId()));
                            // Only switch boolean if result is successful
                            if(draftResult.isSuccess()) {
                                playerAvailable = true;
                            }
                        }
                    }
                }
            }
        }
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

        List<TeamPlayer> playersOnTeam = findAllByTeam(teamPlayer.getUserTeamId());

        for(TeamPlayer player : playersOnTeam) {
            if(playerRepository.findById(player.getPlayerId()).getPosition() == playerRepository.findById(teamPlayer.getPlayerId()).getPosition()) {
                result.addMessage("can only have one player at each position", ResultType.INVALID);
            }
        }

        return result;
    }

}
