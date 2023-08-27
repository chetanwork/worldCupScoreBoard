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
    void notAddSameMatchAgainWhenInProgress() {
        FootballTeam homeTeam = new FootballTeam("Mexico");
        FootballTeam awayTeam = new FootballTeam("Canada");
        FootballTeam homeTeam1 = new FootballTeam("Delhi");
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
        assertTrue(scoreBoard.getMatches().stream()
            .filter(Match::isInProgress)
            .anyMatch(match -> "Spain".equals(match.getHomeTeam().getTeamName())));
        assertTrue(scoreBoard.getMatches().stream()
            .filter(Match::isInProgress)
            .anyMatch(match -> "Brazil".equals(match.getAwayTeam().getTeamName())));
        assertTrue(scoreBoard.getMatches().stream().filter(Match::isInProgress).count() == 1);
    }

    @Test
    void shouldThrowErrorToRemoveInActiveMatch() {
        scoreBoard = new ScoreBoardImpl();
        FootballTeam homeTeam = new FootballTeam("Mexico");
        FootballTeam awayTeam = new FootballTeam("Canada");
        FootballTeam homeTeam1 = new FootballTeam("Spain");
        FootballTeam awayTeam1= new FootballTeam("Brazil");
        scoreBoard = new ScoreBoardImpl();
        scoreBoard.shouldAddMatch(homeTeam, awayTeam);
        scoreBoard.shouldAddMatch(homeTeam1, awayTeam1);
        Match setDeActive = scoreBoard.getMatches().get(1);
        setDeActive.setInProgress(false);
        GeneralException exception = assertThrows(GeneralException.class, () ->
            scoreBoard.shouldRemoveMatch(homeTeam1, awayTeam1));
        String expectedErrorMessage = "Match Not found to be removed from ScoreBoard";
        assertEquals(expectedErrorMessage, exception.getMessage());
    }

    @Test
    void checkDefaultScoreAsZero() {
        scoreBoard = new ScoreBoardImpl();
        FootballTeam homeTeam = new FootballTeam("Mexico");
        FootballTeam awayTeam = new FootballTeam("Canada");
        scoreBoard.shouldAddMatch(homeTeam, awayTeam);
        assertEquals(0, homeTeam.getScore());
        assertEquals(0, homeTeam.getScore());
    }

    @Test
    void shouldNotUpdateMatchScoreWithNegative() {
        scoreBoard = new ScoreBoardImpl();
        FootballTeam homeTeam = new FootballTeam("Mexico");
        FootballTeam awayTeam = new FootballTeam("Canada");
        scoreBoard.shouldAddMatch(homeTeam, awayTeam);
        GeneralException exception = assertThrows(GeneralException.class, () ->
            scoreBoard.shouldUpdateScore(homeTeam, awayTeam, -2, 1));
        String expectedErrorMessage = "Negative Score cannot be update";
        assertEquals(expectedErrorMessage, exception.getMessage());
    }

    @Test
    void shouldUpdateCorrectMatchScore() {
        scoreBoard = new ScoreBoardImpl();
        FootballTeam homeTeam = new FootballTeam("Mexico");
        FootballTeam awayTeam = new FootballTeam("Canada");
        scoreBoard.shouldAddMatch(homeTeam, awayTeam);
        scoreBoard.shouldUpdateScore(homeTeam, awayTeam, 3, 4);
        assertEquals(3, homeTeam.getScore());
        assertEquals(4, awayTeam.getScore());
    }

    @Test
    void throwErrorWhenUpdateMatchIsNotFound() {
        scoreBoard = new ScoreBoardImpl();
        FootballTeam homeTeam = new FootballTeam("Mexico");
        FootballTeam awayTeam = new FootballTeam("Canada");
        FootballTeam homeTeam1 = new FootballTeam("Germany");
        FootballTeam awayTeam1 = new FootballTeam("France");
        scoreBoard.shouldAddMatch(homeTeam, awayTeam);
        scoreBoard.shouldUpdateScore(homeTeam, awayTeam, 1,0);
        GeneralException exception = assertThrows(GeneralException.class, () ->
            scoreBoard.shouldUpdateScore(homeTeam1, awayTeam1, 1,2));
        String expectedErrorMessage = "Match Not Found To Update Score";
        assertEquals(expectedErrorMessage, exception.getMessage());
    }

}
