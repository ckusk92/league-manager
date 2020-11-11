package learn.mlb_gm.domain;

import learn.mlb_gm.data.PlayerRepository;
import learn.mlb_gm.models.Player;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayerService {

    private final PlayerRepository repository;

    public PlayerService(PlayerRepository repository) {
        this.repository = repository;
    }

    public List<Player> findAll() { return repository.findAll(); }

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
