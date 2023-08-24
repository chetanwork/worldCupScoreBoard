package live.scoreboard.football;

public interface ScoreBoard {

    void emptyScoreBoard();

    void shouldAddMatch(FootballTeam homeTeam, FootballTeam awayTeam);

    void shouldRemoveMatch();

    void shouldUpdateMatch();
}
