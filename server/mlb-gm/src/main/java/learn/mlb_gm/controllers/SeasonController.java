package learn.mlb_gm.controllers;

import learn.mlb_gm.domain.SeasonService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/season")
public class SeasonController {

    private final SeasonService service;

    public SeasonController(SeasonService service) {
        this.service = service;
    }

    @GetMapping("/newleague/{userId}")
    public void startNewLeague(@PathVariable int userId) {
        service.startNewLeague(userId);
    }

    @GetMapping("/newseason/{userId}")
    public void startNewSeason(@PathVariable int userId) {
        service.startNewSeason(userId);
    }

}
