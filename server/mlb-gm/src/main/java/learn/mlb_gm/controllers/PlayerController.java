package learn.mlb_gm.controllers;

import learn.mlb_gm.domain.PlayerService;
import learn.mlb_gm.domain.Result;
import learn.mlb_gm.models.Player;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {"localhost:3000"})
@RequestMapping("/player")
public class PlayerController {

    private final PlayerService service;

    public PlayerController(PlayerService service) {
        this.service = service;
    }

    @GetMapping
    public List<Player> findAll() {
        return service.findAll();
    }

    @GetMapping("/freeagents")
    public List<Player> findFreeAgents() {return service.findFreeAgents();}

    @GetMapping("/{playerId}")
    public ResponseEntity<Player> findById(@PathVariable int playerId) {
        Player player = service.findById(playerId);
        if(player == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(player);
    }

    @PostMapping
    public ResponseEntity<Object> add(@RequestBody Player player) {
        Result<Player> result = service.add(player);

        if(result.isSuccess()) {
            return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
        }
        return ErrorResponse.build(result);
    }

    @PutMapping("/{playerId}")
    public ResponseEntity<Object> update(@PathVariable int playerId, @RequestBody Player player) {
        if(playerId != player.getPlayerId()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        Result<Player> result = service.update(player);
        if(result.isSuccess()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ErrorResponse.build(result);
    }

    @DeleteMapping("{playerId}")
    public ResponseEntity<Void> deleteById(@PathVariable int playerId) {
        if(service.deleteById(playerId)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
