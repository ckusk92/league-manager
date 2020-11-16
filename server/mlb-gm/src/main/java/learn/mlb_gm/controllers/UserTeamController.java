package learn.mlb_gm.controllers;

import learn.mlb_gm.domain.GameService;
import learn.mlb_gm.domain.Result;
import learn.mlb_gm.domain.UserTeamService;
import learn.mlb_gm.models.InitInfo;
import learn.mlb_gm.models.UserTeam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/userteam")
public class UserTeamController {

    private final UserTeamService service;
    private final GameService gameService;

    public UserTeamController(UserTeamService service, GameService gameService) {

        this.service = service;
        this.gameService = gameService;
    }

    @GetMapping
    public List<UserTeam> findAll() {
        return service.findAll();
    }

    @GetMapping("/{userTeamId}")
    public ResponseEntity<UserTeam> findById(@PathVariable int userTeamId) {
        UserTeam userTeam = service.findById(userTeamId);
        if(userTeam == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(userTeam);
    }

    @GetMapping("/user/{userId}")
    public List<UserTeam> findAllForUser(@PathVariable int userId) {
        List<UserTeam> allForUser = service.findAllForUser(userId);
        return allForUser;
    }

    @PostMapping
    public ResponseEntity<Object> add(@RequestBody UserTeam userTeam) {
        Result<UserTeam> result = service.add(userTeam);

        if(result.isSuccess()) {
            return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
        }
        return ErrorResponse.build(result);
    }

    @PostMapping("/create")
    public void initiateUserTeams(@RequestBody InitInfo initInfo) {
        service.initiateUserTeams(initInfo);
        // 1 will be swapped with userId passed in initInfo later
        gameService.createSchedule(service.findAllForUser(1), initInfo.getNumberOfGames());
    }


    @PutMapping("/{userTeamId}")
    public ResponseEntity<Object> update(@PathVariable int userTeamId, @RequestBody UserTeam userTeam) {
        if(userTeamId != userTeam.getUserTeamId()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        Result<UserTeam> result = service.update(userTeam);
        if(result.isSuccess()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ErrorResponse.build(result);
    }

    @DeleteMapping("/{userTeamId}")
    public ResponseEntity<Void> deleteById(@PathVariable int userTeamId) {
        if(service.deleteById(userTeamId)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
