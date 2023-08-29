package live.scoreboard.football;

import java.util.List;

public interface ScoreBoard {

    void checkScoreBoard();

    void shouldAddMatch(FootballTeam homeTeam, FootballTeam awayTeam);

    void shouldRemoveMatch(FootballTeam homeTeam, FootballTeam awayTeam);

    void shouldUpdateScore(FootballTeam homeTeam, FootballTeam awayTeam, int homeScore, int awayScore);

    List<Match> getMatches();

    List<Match> shouldOrderedMatches();
}
