package learn.mlb_gm.domain;

import learn.mlb_gm.data.GameRepository;
import learn.mlb_gm.models.Game;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameService {

    private final GameRepository repository;

    public GameService(GameRepository repository) {
        this.repository = repository;
    }

    public List<Game> findAll() {
        return repository.findAll();
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
