package learn.mlb_gm.domain;

import learn.mlb_gm.data.GameRepository;
import learn.mlb_gm.data.RecordRepository;
import learn.mlb_gm.data.TeamRepository;
import learn.mlb_gm.models.*;
import learn.mlb_gm.models.Record;
import learn.mlb_gm.models.response_objects.GameWithTeam;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class GameService {

    private final GameRepository repository;
    private final RecordRepository recordRepository;
    private final TeamRepository teamRepository;

    public GameService(GameRepository repository, RecordRepository recordRepository, TeamRepository teamRepository) {

        this.repository = repository;
        this.recordRepository = recordRepository;
        this.teamRepository = teamRepository;
    }

    public List<Game> findAll() {
        return repository.findAll();
    }

    public List<GameWithTeam> getSchedule() {
//        List<Game> all = repository.findAll();
//        List<Game> orderedAll = new ArrayList<>();
//        // Loops through and gets games with game# 1 -> size in order
//        for(int i = 0; i < all.size(); i++) {
//            if(all.get(i).getGameNumber() == i + 1 && !orderedAll.contains(all.get(i))) {
//                orderedAll.add(all.get(i));
//            }
//        }
        // Hardcoded, will change later
        int userId = 1;
        List<Game> orderedAll = repository.findAllForUserInOrderOfGame(userId);
        List<GameWithTeam> schedule = new ArrayList<>();

        for(Game game : orderedAll) {
            GameWithTeam newGame = new GameWithTeam();
            Team homeTeam = teamRepository.findById(game.getHomeTeamId());
            Team awayTeam = teamRepository.findById(game.getAwayTeamId());
            newGame.setHomeTeamName(homeTeam.getName());
            newGame.setAwayTeamName(awayTeam.getName());
            newGame.setHomeScore(game.getHomeScore());
            newGame.setAwayScore(game.getAwayScore());
            newGame.setPlayed(game.isPlayed());
            schedule.add(newGame);
        }

        return schedule;
    }

    public int gamesRemaining() {
        int userId = 1;
        List<Game> schedule = repository.findAllForUserInOrderOfGame(userId);

        int gamesPlayed = 1;
        for(Game game : schedule) {
            if(game.isPlayed()) {
                gamesPlayed++;
            }
        }
        return schedule.size() - gamesPlayed;
    }

    public Game findById(int gameId) {
        return repository.findById(gameId);
    }

    public Result<Game> add(Game game) {
        Result<Game> result = validate(game);

        if(!result.isSuccess()) {
            return result;
        }

        if(game.getGameId() != 0) {
            result.addMessage("gameId cannot be set for `add operation", ResultType.INVALID);
            return result;
        }

        game = repository.add(game);
        result.setPayload(game);
        return result;
    }

    public Result<Game> update(Game game) {
        Result<Game> result = validate(game);

        if(!result.isSuccess()) {
            return result;
        }

        if(game.getGameId() <= 0) {
            result.addMessage("gameId must be set for `update` operation", ResultType.INVALID);
            return result;
        }

        if(!repository.update(game)) {
            String msg = String.format("gameId: %s, nto found", game.getGameId());
            result.addMessage(msg, ResultType.NOT_FOUND);
        }

        return result;
    }

    public boolean deleteById(int gameId) {
        return repository.deleteById(gameId);
    }

    public void createSchedule(List<UserTeam> userTeams, int numGames) {
        Random random = new Random();
        // Loop will repeat once for every game day
        for(int i = 0; i < numGames; i++) {
            List<Integer> teamIds = new ArrayList<>();

            // Full up teamIds array in a new order every time
            while(teamIds.size() < userTeams.size()) {
                Integer index = random.nextInt(userTeams.size());
                if(!teamIds.contains(userTeams.get(index).getUserTeamId())) {
                    teamIds.add(userTeams.get(index).getUserTeamId());
                }
            }

            // Adds games to schedule for number of games selected, random matchings
            for(int j = 0; j < teamIds.size(); j = j + 2) {
                repository.add(new Game(1, teamIds.get(j), teamIds.get(j+1), i+1, 0, 0, false));
            }
        }
    }

    public void simulateGame() {
        Random random = new Random();
        List<Game> allGames = repository.findAll();
        int gameNumber = 0;

        for(Game game : allGames) {
            if(!game.isPlayed()) {
                gameNumber = game.getGameNumber();
                break;
            }
        }

        for(Game game : allGames) {
            if(game.getGameNumber() == gameNumber) {
                while(game.getHomeScore() == game.getAwayScore()) {
                    game.setHomeScore(random.nextInt(11));
                    game.setAwayScore(random.nextInt(11));
                }

                game.setPlayed(true);
                repository.update(game);

                Record homeRecord = recordRepository.findForTeam(game.getHomeTeamId());
                Record awayRecord = recordRepository.findForTeam(game.getAwayTeamId());

                if(game.getHomeScore() > game.getAwayScore()) {
                    homeRecord.setWin(homeRecord.getWin() + 1);
                    awayRecord.setLoss(awayRecord.getLoss() + 1);
                } else {
                    awayRecord.setWin(awayRecord.getWin() + 1);
                    homeRecord.setLoss(homeRecord.getLoss() + 1);
                }

                recordRepository.update(homeRecord);
                recordRepository.update(awayRecord);
            }
        }
    }

    public void simulateSeason() {
        Random random = new Random();
        List<Game> allGames = repository.findAll();

        for(Game game : allGames) {
            if(!game.isPlayed()) {
                while (game.getHomeScore() == game.getAwayScore()) {
                    game.setHomeScore(random.nextInt(11));
                    game.setAwayScore(random.nextInt(11));
                }

                game.setPlayed(true);
                repository.update(game);

                Record homeRecord = recordRepository.findForTeam(game.getHomeTeamId());
                Record awayRecord = recordRepository.findForTeam(game.getAwayTeamId());

                if (game.getHomeScore() > game.getAwayScore()) {
                    homeRecord.setWin(homeRecord.getWin() + 1);
                    awayRecord.setLoss(awayRecord.getLoss() + 1);
                } else {
                    awayRecord.setWin(awayRecord.getWin() + 1);
                    homeRecord.setLoss(homeRecord.getLoss() + 1);
                }

                recordRepository.update(homeRecord);
                recordRepository.update(awayRecord);
            }
        }

    }

    private Result<Game> validate(Game game) {
        Result<Game> result = new Result<>();

        if(game.getGameNumber() < 1) {
            result.addMessage("game number must be positive number", ResultType.INVALID);
        }

        if(game.getHomeScore() < 0) {
            result.addMessage("game score cannot be negative", ResultType.INVALID);
        }

        if(game.getAwayScore() < 0) {
            result.addMessage("game score cannot be negative", ResultType.INVALID);
        }

        return result;
    }
}
