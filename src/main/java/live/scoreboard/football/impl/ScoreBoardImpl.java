package live.scoreboard.football.impl;

import live.scoreboard.football.Match;
import live.scoreboard.football.ScoreBoard;

import java.util.ArrayList;
import java.util.List;

public class ScoreBoardImpl implements ScoreBoard {

    private final List<Match> matches = new ArrayList<>();

    @Override
    public void emptyScoreBoard() {

    }

    @Override
    public void shouldAddMatch() {
        matches.add(new Match());
    }

    @Override
    public void shouldRemoveMatch() {

    }

    @Override
    public void shouldUpdateMatch() {

    }
}
