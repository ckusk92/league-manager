package learn.mlb_gm.controllers;

import learn.mlb_gm.domain.PlayerService;
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

    @GetMapping("/{playerId}")
    public ResponseEntity<Player> findById(@PathVariable int playerId) {
        Player player = service.findById(playerId);
        if(player == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(player);
    }

}
