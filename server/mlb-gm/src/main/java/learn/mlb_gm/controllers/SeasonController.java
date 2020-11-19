package learn.mlb_gm.controllers;

import learn.mlb_gm.domain.GameService;
import learn.mlb_gm.domain.SeasonService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/season")
public class SeasonController {

    private final SeasonService service;
    private final GameService gameService;

    public SeasonController(SeasonService service, GameService gameService) {

        this.service = service;
        this.gameService = gameService;
    }

    @GetMapping("/newleague/{userId}")
    public int startNewLeague(@PathVariable int userId) {
        service.startNewLeague(userId);

        return 1;
    }

    @GetMapping("/newseason/{userId}")
    public int startNewSeason(@PathVariable int userId) {

        service.startNewSeason(userId);


        // Initiate new schedule here
        //gameService.createSchedule();

        return 1;
    }

}
