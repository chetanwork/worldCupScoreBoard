package live.scoreboard;

import live.scoreboard.football.FootballTeam;
import live.scoreboard.football.ScoreBoard;
import org.junit.jupiter.api.Test;

public class ScoreBoardTest {

    public ScoreBoard scoreBoard;

    @Test
    void emptyScoreBoard() {
        scoreBoard = new ScoreBoard();
    }

    @Test
    void shouldAddMatch() {
        FootballTeam hometeam = new FootballTeam();
        FootballTeam awayTeam = new FootballTeam();
        scoreBoard = new ScoreBoard();
    }

    @Test
    void shouldRemoveMatch() {
        scoreBoard = new ScoreBoard();
    }

    @Test
    void shouldUpdateMatch() {
        scoreBoard = new ScoreBoard();
    }





}
