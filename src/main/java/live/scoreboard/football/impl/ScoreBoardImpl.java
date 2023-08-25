package live.scoreboard.football.impl;

import live.scoreboard.exception.GeneralException;
import live.scoreboard.football.FootballTeam;
import live.scoreboard.football.Match;
import live.scoreboard.football.ScoreBoard;

import java.util.ArrayList;
import java.util.List;

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
    public void shouldRemoveMatch() {

    }

    @Override
    public void shouldUpdateMatch() {

    }

    public List<Match> getMatches() {
        return matches;
    }
}
