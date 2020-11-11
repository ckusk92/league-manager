package learn.mlb_gm.controllers;

import learn.mlb_gm.domain.Result;
import learn.mlb_gm.domain.TeamService;
import learn.mlb_gm.models.Team;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {"localhost:3000"})
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

}
