package live.scoreboard.football.impl;

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
    public void shouldAddMatch(FootballTeam homeTeam, FootballTeam awayTeam) {
        matches.add(new Match(homeTeam, awayTeam));
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
