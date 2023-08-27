package live.scoreboard;

import live.scoreboard.exception.GeneralException;
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
    void notAddSameMatchAgain() {
        FootballTeam homeTeam = new FootballTeam("Mexico");
        FootballTeam awayTeam = new FootballTeam("Canada");
        FootballTeam homeTeam1 = new FootballTeam("Mexico");
        FootballTeam awayTeam1 = new FootballTeam("Canada");
        scoreBoard = new ScoreBoardImpl();
        scoreBoard.shouldAddMatch(homeTeam, awayTeam);
        GeneralException exception = assertThrows(GeneralException.class, () ->
                scoreBoard.shouldAddMatch(homeTeam1, awayTeam1), "Same Match cannot be added again");
        String expectedErrorMessage = "Same Match cannot be added again";
        assertEquals(expectedErrorMessage, exception.getMessage());
    }

    @Test
    void shouldRemoveMatch() {
        scoreBoard = new ScoreBoardImpl();
        FootballTeam homeTeam = new FootballTeam("Mexico");
        FootballTeam awayTeam = new FootballTeam("Canada");
        FootballTeam homeTeam1 = new FootballTeam("Spain");
        FootballTeam awayTeam1= new FootballTeam("Brazil");
        scoreBoard = new ScoreBoardImpl();
        scoreBoard.shouldAddMatch(homeTeam, awayTeam);
        scoreBoard.shouldAddMatch(homeTeam1, awayTeam1);
        scoreBoard.shouldRemoveMatch(homeTeam, awayTeam);
        assertFalse(scoreBoard.getMatches().isEmpty());
        assertTrue(scoreBoard.getMatches().stream()
            .anyMatch(match -> "Spain".equals(match.getHomeTeam().getTeamName())));
        assertTrue(scoreBoard.getMatches().stream()
            .anyMatch(match -> "Brazil".equals(match.getAwayTeam().getTeamName())));
        assertTrue(scoreBoard.getMatches().stream().count() == 1);
    }

    @Test
    void shouldUpdateMatch() {
        scoreBoard = new ScoreBoardImpl();
    }





}
