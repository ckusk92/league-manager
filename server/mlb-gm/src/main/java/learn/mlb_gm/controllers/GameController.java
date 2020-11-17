package learn.mlb_gm.controllers;

import learn.mlb_gm.domain.GameService;
import learn.mlb_gm.domain.Result;
import learn.mlb_gm.models.Game;
import learn.mlb_gm.models.GameWithTeam;
import learn.mlb_gm.models.InitInfo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/game")
public class GameController {

    private final GameService service;

    public GameController(GameService service) {
        this.service = service;
    }

    @GetMapping
    public List<Game> findAll() {
        return service.findAll();
    }

//    @GetMapping("/schedule/{userId}")
//    public ResponseEntity<List<Game>> getSchedule(@PathVariable int userId) {
//        List<Game> schedule = service.getSchedule();
//        if(schedule.size() == 0) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//        return ResponseEntity.ok(schedule);
//    }

    @GetMapping("/schedule/{userId}")
    public ResponseEntity<List<GameWithTeam>> getSchedule(@PathVariable int userId) {
        List<GameWithTeam> schedule = service.getSchedule();
        if(schedule.size() == 0) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(schedule);
    }

    @GetMapping("/schedule/{userId}/gamesremaining")
    public int gamesRemaining(@PathVariable int userId) {
        return service.gamesRemaining();
    }

    @GetMapping("/{gameId}")
    public ResponseEntity<Game> findById(@PathVariable int gameId) {
        Game game = service.findById(gameId);
        if(game == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(game);
    }

    @GetMapping("/simgame")
    public void simulateGame() {
        service.simulateGame();
    }

    @GetMapping("/simseason")
    public void simulateSeason() {
        service.simulateSeason();
    }

    @PostMapping
    public ResponseEntity<Object> add(@RequestBody Game game) {
        Result<Game> result = service.add(game);

        if(result.isSuccess()) {
            return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
        }
        return ErrorResponse.build(result);
    }

    @PutMapping("/{gameId}")
    public ResponseEntity<Object> update(@PathVariable int gameId, @RequestBody Game game) {
        if(gameId != game.getGameId()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        Result<Game> result = service.update(game);
        if(result.isSuccess()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ErrorResponse.build(result);
    }

    @DeleteMapping("/{gameId}")
    public ResponseEntity<Void> deleteById(@PathVariable int gameId) {
        if(service.deleteById(gameId)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
