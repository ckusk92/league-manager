package learn.mlb_gm.domain;

import learn.mlb_gm.data.GameRepository;
import learn.mlb_gm.models.Game;
import learn.mlb_gm.models.UserTeam;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class GameService {

    private final GameRepository repository;

    public GameService(GameRepository repository) {
        this.repository = repository;
    }

    public List<Game> findAll() {
        return repository.findAll();
    }

    public List<Game> getSchedule() {
//        List<Game> all = repository.findAll();
//        List<Game> orderedAll = new ArrayList<>();
//        // Loops through and gets games with game# 1 -> size in order
//        for(int i = 0; i < all.size(); i++) {
//            if(all.get(i).getGameNumber() == i + 1 && !orderedAll.contains(all.get(i))) {
//                orderedAll.add(all.get(i));
//            }
//        }
        // Hardcoded, will change later
        int userId = 1;
        List<Game> orderedAll = repository.findAllForUserInOrderOfGame(userId);
        return orderedAll;
    }

    public Game findById(int gameId) {
        return repository.findById(gameId);
    }

    public Result<Game> add(Game game) {
        Result<Game> result = validate(game);

        if(!result.isSuccess()) {
            return result;
        }

        if(game.getGameId() != 0) {
            result.addMessage("gameId cannot be set for `add operation", ResultType.INVALID);
            return result;
        }

        game = repository.add(game);
        result.setPayload(game);
        return result;
    }

    public Result<Game> update(Game game) {
        Result<Game> result = validate(game);

        if(!result.isSuccess()) {
            return result;
        }

        if(game.getGameId() <= 0) {
            result.addMessage("gameId must be set for `update` operation", ResultType.INVALID);
            return result;
        }

        if(!repository.update(game)) {
            String msg = String.format("gameId: %s, nto found", game.getGameId());
            result.addMessage(msg, ResultType.NOT_FOUND);
        }

        return result;
    }

    public boolean deleteById(int gameId) {
        return repository.deleteById(gameId);
    }

    public void createSchedule(List<UserTeam> userTeams, int numGames) {
        Random random = new Random();
        // Loop will repeat once for every game day
        for(int i = 0; i < numGames; i++) {
            List<Integer> teamIds = new ArrayList<>();

            // Full up teamIds array in a new order every time
            while(teamIds.size() < userTeams.size()) {
                Integer index = random.nextInt(userTeams.size());
                if(!teamIds.contains(userTeams.get(index).getUserTeamId())) {
                    teamIds.add(userTeams.get(index).getUserTeamId());
                }
            }

            // Adds games to schedule for number of games selected, random matchings
            for(int j = 0; j < teamIds.size(); j = j + 2) {
                repository.add(new Game(1, teamIds.get(j), teamIds.get(j+1), i+1, 0, 0, false));
            }
        }
    }

    private Result<Game> validate(Game game) {
        Result<Game> result = new Result<>();

        if(game.getGameNumber() < 1) {
            result.addMessage("game number must be positive number", ResultType.INVALID);
        }

        if(game.getHomeScore() < 0) {
            result.addMessage("game score cannot be negative", ResultType.INVALID);
        }

        if(game.getAwayScore() < 0) {
            result.addMessage("game score cannot be negative", ResultType.INVALID);
        }

        return result;
    }
}
