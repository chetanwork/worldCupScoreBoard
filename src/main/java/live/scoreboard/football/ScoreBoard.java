package live.scoreboard.football;

import java.util.List;

public interface ScoreBoard {

    void emptyScoreBoard();

    void shouldAddMatch(FootballTeam homeTeam, FootballTeam awayTeam);

    void shouldRemoveMatch();

    void shouldUpdateMatch();

    List<Match> getMatches();
}
