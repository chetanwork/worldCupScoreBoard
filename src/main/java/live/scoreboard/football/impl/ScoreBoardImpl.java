package live.scoreboard.football.impl;

import live.scoreboard.exception.GeneralException;
import live.scoreboard.football.FootballTeam;
import live.scoreboard.football.Match;
import live.scoreboard.football.ScoreBoard;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ScoreBoardImpl implements ScoreBoard {

    private final List<Match> matches = new ArrayList();

    @Override
    public void emptyScoreBoard() {

    }

    @Override
    public void shouldAddMatch(FootballTeam homeTeam, FootballTeam awayTeam) throws GeneralException {
        Match match = new Match(homeTeam, awayTeam);
        System.out.println(!matches.isEmpty());
        if(isExistingMatch(match)) {
            throw new GeneralException("Same Match cannot be added again");
        }
        matches.add(new Match(homeTeam, awayTeam));
    }

    private boolean isExistingMatch(Match newMatch) {
        return matches.stream()
            .anyMatch(existingMatch ->
            existingMatch.getHomeTeam().getTeamName().equals(newMatch.getHomeTeam().getTeamName()) &&
            existingMatch.getAwayTeam().getTeamName().equals(newMatch.getAwayTeam().getTeamName()));
    }

    @Override
    public void shouldRemoveMatch(FootballTeam homeTeam, FootballTeam awayTeam) {
            matches.removeIf(existingMatch ->
                existingMatch.getHomeTeam().getTeamName().equals(homeTeam.getTeamName()) &&
                    existingMatch.getAwayTeam().getTeamName().equals(awayTeam.getTeamName()));
    }

    @Override
    public void shouldUpdateScore(FootballTeam homeTeam, FootballTeam awayTeam,
                                  int homeTeamScore, int awayTeamScore) {
        checkForNegativeScore(homeTeamScore, awayTeamScore);
        Optional<Match> scoreUpdate = matches.stream().filter(existingMatch ->
                existingMatch.getHomeTeam().getTeamName().equals(homeTeam.getTeamName()) &&
                existingMatch.getAwayTeam().getTeamName().equals(awayTeam.getTeamName()))
                .findFirst();
        System.out.println(matches.isEmpty());
        if(scoreUpdate.isEmpty()) {
            throw new GeneralException("Match Not Found To Update Score");
        }
        System.out.println("outside " + matches.get(0).getAwayTeam());
        Match getMatch = scoreUpdate.get();
        FootballTeam home = getMatch.getHomeTeam();
        FootballTeam away = getMatch.getAwayTeam();
        home.setScore(getMatch.getHomeTeam().getScore() + homeTeamScore);
        away.setScore(getMatch.getAwayTeam().getScore() + awayTeamScore);
    }

    private void checkForNegativeScore(int homeTeamScore, int awayTeamScore) {
        if(homeTeamScore < 0 || awayTeamScore < 0 ) {
            throw new GeneralException(
                "Negative Score cannot be update");
        }
    }

    public List<Match> getMatches() {
        return matches;
    }
}
