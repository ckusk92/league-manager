package learn.mlb_gm.controllers;


import learn.mlb_gm.domain.Result;
import learn.mlb_gm.domain.TeamPlayerService;
import learn.mlb_gm.models.TeamPlayer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {"localhost:3000"})
@RequestMapping("/teamplayer")
public class TeamPlayerController {

    private final TeamPlayerService service;

    public TeamPlayerController(TeamPlayerService service) {
        this.service = service;
    }

    @GetMapping
    public List<TeamPlayer> findAll() {
        return service.findAll();
    }

    @GetMapping("/team/{userTeamId}")
    public List<TeamPlayer> findAllForTeam(@PathVariable int userTeamId) {
        List<TeamPlayer> allForTeam = service.findAllByTeam(userTeamId);
        return allForTeam;
    }

    @GetMapping("/{teamPlayerId}")
    public ResponseEntity<TeamPlayer> findById(@PathVariable int teamPlayerId) {
        TeamPlayer teamPlayer = service.findById(teamPlayerId);
        if(teamPlayer == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(teamPlayer);
    }

    @PostMapping
    public ResponseEntity<Object> add(@RequestBody TeamPlayer teamPlayer) {
        Result<TeamPlayer> result = service.add(teamPlayer);

        if(result.isSuccess()) {
            return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
        }
        return ErrorResponse.build(result);
    }

    @PostMapping("/draft")
    public ResponseEntity<Object> draft(@RequestBody TeamPlayer teamPlayer) {
        Result<TeamPlayer> result = service.draft(teamPlayer);

        if(result.isSuccess()) {
            return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
        }
        return ErrorResponse.build(result);
    }

    @PutMapping("/{teamPlayerId}")
    public ResponseEntity<Object> update(@PathVariable int teamPlayerId, @RequestBody TeamPlayer teamPlayer) {
        if(teamPlayerId != teamPlayer.getTeamPlayerId()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        Result<TeamPlayer> result = service.update(teamPlayer);
        if(result.isSuccess()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ErrorResponse.build(result);
    }

    @DeleteMapping("/{teamPlayerId}")
    public ResponseEntity<Void> deleteById(@PathVariable int teamPlayerId) {
        if(service.deleteById(teamPlayerId)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
