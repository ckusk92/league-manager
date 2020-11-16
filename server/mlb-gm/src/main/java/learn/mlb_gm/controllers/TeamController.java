package learn.mlb_gm.controllers;

import learn.mlb_gm.domain.Result;
import learn.mlb_gm.domain.TeamService;
import learn.mlb_gm.models.Team;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/team")
public class TeamController {

    private final TeamService service;

    public TeamController(TeamService service) {
        this.service = service;
    }

    @GetMapping
    public List<Team> findAll() {
        return service.findAll();
    }

    @GetMapping("/{teamId}")
    public ResponseEntity<Team> findById(@PathVariable int teamId) {
        Team team = service.findById(teamId);
        if(team == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(team);
    }

    @PostMapping
    public ResponseEntity<Object> add(@RequestBody Team team) {
        Result<Team> result = service.add(team);

        if(result.isSuccess()) {
            return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
        }
        return ErrorResponse.build(result);
    }

    @PutMapping("/{teamId}")
    public ResponseEntity<Object> update(@PathVariable int teamId, @RequestBody Team team) {
        if(teamId != team.getTeamId()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        Result<Team> result = service.update(team);
        if(result.isSuccess()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ErrorResponse.build(result);
    }

    @DeleteMapping("{teamId}")
    public ResponseEntity<Void> deleteById(@PathVariable int teamId) {
        if(service.deleteById(teamId)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
