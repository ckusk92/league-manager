package learn.mlb_gm.domain;

import learn.mlb_gm.data.SeasonRepository;
import org.springframework.stereotype.Service;

@Service
public class SeasonService {

    private final SeasonRepository repository;

    public SeasonService(SeasonRepository repository) {
        this.repository = repository;
    }

    public void startNewLeague(int userId) {
        repository.startNewLeague(userId);
    }

    public void startNewSeason(int userId) {
        repository.startNewSeason(userId);
    }

}
