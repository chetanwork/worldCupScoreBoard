package live.scoreboard;

import live.scoreboard.football.FootballTeam;
import live.scoreboard.football.ScoreBoard;
import live.scoreboard.football.impl.ScoreBoardImpl;
import org.junit.jupiter.api.Test;

public class ScoreBoardTest {

    public ScoreBoard scoreBoard;

    @Test
    void emptyScoreBoard() {
        scoreBoard = new ScoreBoardImpl();
    }

    @Test
    void shouldAddMatch() {
        FootballTeam homeTeam = new FootballTeam();
        FootballTeam awayTeam = new FootballTeam();
        scoreBoard = new ScoreBoardImpl();
        scoreBoard.shouldAddMatch(homeTeam, awayTeam);
    }

    @Test
    void shouldRemoveMatch() {
        scoreBoard = new ScoreBoardImpl();
    }

    @Test
    void shouldUpdateMatch() {
        scoreBoard = new ScoreBoardImpl();
    }





}
