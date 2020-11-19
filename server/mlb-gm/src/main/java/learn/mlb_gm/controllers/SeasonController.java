package learn.mlb_gm.controllers;

import learn.mlb_gm.domain.GameService;
import learn.mlb_gm.domain.SeasonService;
import learn.mlb_gm.domain.UserTeamService;
import learn.mlb_gm.models.UserTeam;
import learn.mlb_gm.models.response_objects.GameWithTeam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/season")
public class SeasonController {

    private final SeasonService service;
    private final GameService gameService;
    private final UserTeamService userTeamService;

    public SeasonController(SeasonService service, GameService gameService, UserTeamService userTeamService) {

        this.service = service;
        this.gameService = gameService;
        this.userTeamService = userTeamService;
    }

    @GetMapping("/newleague/{userId}")
    public int startNewLeague(@PathVariable int userId) {
        service.startNewLeague(userId);

        return 1;
    }

    @GetMapping("/newseason/{userId}")
    public int startNewSeason(@PathVariable int userId) {

        int numGames = gameService.getSchedule(userId).size();
        List<UserTeam> allTeams = userTeamService.findAllForUser(userId);

        service.startNewSeason(userId);
        gameService.createSchedule(allTeams, numGames);

        return 1;
    }

}
