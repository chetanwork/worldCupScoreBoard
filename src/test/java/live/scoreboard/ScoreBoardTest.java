package live.scoreboard;

import live.scoreboard.football.*;
import live.scoreboard.football.impl.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class ScoreBoardTest {

    public ScoreBoard scoreBoard;

    @Test
    void emptyScoreBoard() {
        scoreBoard = new ScoreBoardImpl();
        assertTrue(scoreBoard.getMatches().isEmpty());
    }

    @Test
    void shouldAddMatch() {
        FootballTeam homeTeam = new FootballTeam("Uruguay");
        FootballTeam awayTeam = new FootballTeam("Italy");
        scoreBoard = new ScoreBoardImpl();
        //add Match with teams to scoreboard
        scoreBoard.shouldAddMatch(homeTeam, awayTeam);
        //check if scoreBoard is contains a match
        assertFalse(scoreBoard.getMatches().isEmpty());
    }

    @Test
    void checkNameOfFootballTeam() {
        FootballTeam homeTeam = new FootballTeam("Mexico");
        FootballTeam awayTeam = new FootballTeam("Canada");
        scoreBoard = new ScoreBoardImpl();
        scoreBoard.shouldAddMatch(homeTeam, awayTeam);
        assertTrue(scoreBoard.getMatches().stream()
                .anyMatch(match -> "Mexico".equals(match.getHomeTeam().getTeamName())) );
        assertTrue(scoreBoard.getMatches().stream()
                .anyMatch(match -> "Canada".equals(match.getAwayTeam().getTeamName())) );
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
