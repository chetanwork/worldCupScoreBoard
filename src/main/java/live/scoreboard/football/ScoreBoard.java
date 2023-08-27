package live.scoreboard.football;

import java.util.List;

public interface ScoreBoard {

    void emptyScoreBoard();

    void shouldAddMatch(FootballTeam homeTeam, FootballTeam awayTeam);

    void shouldRemoveMatch(FootballTeam homeTeam, FootballTeam awayTeam);

    void shouldUpdateMatch();

    List<Match> getMatches();
}
