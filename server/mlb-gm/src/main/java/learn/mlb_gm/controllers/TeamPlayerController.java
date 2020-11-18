package learn.mlb_gm.controllers;


import learn.mlb_gm.domain.Result;
import learn.mlb_gm.domain.TeamPlayerService;
import learn.mlb_gm.domain.UserTeamService;
import learn.mlb_gm.models.Player;
import learn.mlb_gm.models.TeamPlayer;
import learn.mlb_gm.models.response_objects.TeamPlayerInfo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/teamplayer")
public class TeamPlayerController {

    private final TeamPlayerService service;
    private final UserTeamService userTeamService;

    public TeamPlayerController(TeamPlayerService service, UserTeamService userTeamService) {

        this.service = service;
        this.userTeamService = userTeamService;
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

    @GetMapping("/roster/{userTeamId}")
    public List<Player> getRoster(@PathVariable int userTeamId) {
        List<Player> roster = service.getRoster(userTeamId);
        return roster;
    }

    @GetMapping("/{teamPlayerId}")
    public ResponseEntity<TeamPlayer> findById(@PathVariable int teamPlayerId) {
        TeamPlayer teamPlayer = service.findById(teamPlayerId);
        if(teamPlayer == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(teamPlayer);
    }

    @GetMapping("/")

    @PostMapping
    public ResponseEntity<Object> add(@RequestBody TeamPlayer teamPlayer) {
        Result<TeamPlayer> result = service.add(teamPlayer);

        if(result.isSuccess()) {
            return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
        }
        return ErrorResponse.build(result);
    }

//    @PostMapping("/draft")
//    public ResponseEntity<Object> draft(@RequestBody TeamPlayer teamPlayer) {
//        Result<TeamPlayer> result = service.draft(teamPlayer);
//
//        if(result.isSuccess()) {
//            // Update team rating whenever a player is drafted
//            userTeamService.updateRating();
//            return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
//        }
//        return ErrorResponse.build(result);
//    }

    @PostMapping("/draft")
    public List<TeamPlayerInfo> draft(@RequestBody TeamPlayer teamPlayer) {
        return service.draft(teamPlayer);
    }

    @PostMapping("/sign")
    public void sign(@RequestBody TeamPlayer teamPlayer) {
        service.sign(teamPlayer);
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
