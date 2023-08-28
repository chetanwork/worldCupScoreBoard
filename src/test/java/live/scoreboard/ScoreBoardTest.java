package live.scoreboard;

import live.scoreboard.exception.GeneralException;
import live.scoreboard.football.*;
import live.scoreboard.football.impl.*;
import org.junit.jupiter.api.Test;

import java.util.List;

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

    @Test
    void shouldOrderedMatchesTimeAndScoreReturn() {
        scoreBoard = new ScoreBoardImpl();
        FootballTeam home = new FootballTeam("Mexico");
        FootballTeam away = new FootballTeam("Canada");
        scoreBoard.shouldAddMatch(home, away);
        scoreBoard.shouldUpdateScore(home, away, 0, 5);

        FootballTeam home2 = new FootballTeam("Spain");
        FootballTeam away2 = new FootballTeam("Brazil");
        scoreBoard.shouldAddMatch(home2, away2);
        scoreBoard.shouldUpdateScore(home2, away2, 10, 2);

        FootballTeam home3 = new FootballTeam("Germany");
        FootballTeam away3 = new FootballTeam("France");
        scoreBoard.shouldAddMatch(home3, away3);
        scoreBoard.shouldUpdateScore(home3, away3, 2, 2);

        FootballTeam home4 = new FootballTeam("Uruguay");
        FootballTeam away4 = new FootballTeam("Italy");
        scoreBoard.shouldAddMatch(home4, away4);
        scoreBoard.shouldUpdateScore(home4, away4, 6,6);

        FootballTeam home5 = new FootballTeam("Argentina");
        FootballTeam away5 = new FootballTeam("Australia");
        scoreBoard.shouldAddMatch(home5, away5);
        scoreBoard.shouldUpdateScore(home5, away5, 3, 1);

        List<Match> orderedFootballMatches = scoreBoard.shouldOrderedMatches();

        assertFalse(orderedFootballMatches.isEmpty());
    /*for(int i=0; i < orderedFootballMatches.size(); i++) {
        System.out.println(orderedFootballMatches.get(i).getHomeTeam().getTeamName());
    }*/

        //1. Uruguay 6 - Italy 6
        assertEquals("Uruguay", orderedFootballMatches.get(0).getHomeTeam().getTeamName());
        assertEquals("Italy", orderedFootballMatches.get(0).getAwayTeam().getTeamName());
        //2. Spain 10 - Brazil 2
        assertEquals("Spain", orderedFootballMatches.get(1).getHomeTeam().getTeamName());
        assertEquals("Brazil", orderedFootballMatches.get(1).getAwayTeam().getTeamName());
        //3. Mexico 0 - Canada 5
        assertEquals("Mexico", orderedFootballMatches.get(2).getHomeTeam().getTeamName());
        assertEquals("Canada", orderedFootballMatches.get(2).getAwayTeam().getTeamName());
        //4. Argentina 3 - Australia 1
        assertEquals("Argentina", orderedFootballMatches.get(3).getHomeTeam().getTeamName());
        assertEquals("Australia", orderedFootballMatches.get(3).getAwayTeam().getTeamName());
        //5. Germany 2 - France 2
        assertEquals("Germany", orderedFootballMatches.get(4).getHomeTeam().getTeamName());
        assertEquals("France", orderedFootballMatches.get(4).getAwayTeam().getTeamName());
    }

    @Test
    void shouldOrderedMatchesTInProgressOnly() {
        scoreBoard = new ScoreBoardImpl();
        FootballTeam home = new FootballTeam("Mexico");
        FootballTeam away = new FootballTeam("Canada");
        scoreBoard.shouldAddMatch(home, away);
        scoreBoard.shouldUpdateScore(home, away, 2, 3);

        FootballTeam home2 = new FootballTeam("Spain");
        FootballTeam away2 = new FootballTeam("Brazil");
        scoreBoard.shouldAddMatch(home2, away2);
        scoreBoard.shouldUpdateScore(home2, away2, 4, 8);

        FootballTeam home3 = new FootballTeam("Germany");
        FootballTeam away3 = new FootballTeam("France");
        scoreBoard.shouldAddMatch(home3, away3);
        scoreBoard.shouldUpdateScore(home3, away3, 10, 8);
        scoreBoard.shouldRemoveMatch(home3,away3);

        List<Match> orderedFootballMatches = scoreBoard.shouldOrderedMatches();

        assertFalse(orderedFootballMatches.isEmpty());
        assertEquals(2, orderedFootballMatches.size());
        //1. Spain 4 - Brazil 8
        assertEquals("Spain", orderedFootballMatches.get(0).getHomeTeam().getTeamName());
        assertEquals("Brazil", orderedFootballMatches.get(0).getAwayTeam().getTeamName());
        //2. Mexico 2 - Canada 3
        assertEquals("Mexico", orderedFootballMatches.get(1).getHomeTeam().getTeamName());
        assertEquals("Canada", orderedFootballMatches.get(1).getAwayTeam().getTeamName());
    }

    @Test
    void shouldOrderedMatchesInProgressOnly() {
        scoreBoard = new ScoreBoardImpl();
        FootballTeam home = new FootballTeam("Mexico");
        FootballTeam away = new FootballTeam("Canada");
        scoreBoard.shouldAddMatch(home, away);
        scoreBoard.shouldUpdateScore(home, away, 2, 3);

        FootballTeam home2 = new FootballTeam("Spain");
        FootballTeam away2 = new FootballTeam("Brazil");
        scoreBoard.shouldAddMatch(home2, away2);
        scoreBoard.shouldUpdateScore(home2, away2, 4, 8);

        FootballTeam home3 = new FootballTeam("Germany");
        FootballTeam away3 = new FootballTeam("France");
        scoreBoard.shouldAddMatch(home3, away3);
        scoreBoard.shouldUpdateScore(home3, away3, 10, 8);
        scoreBoard.shouldRemoveMatch(home3,away3);

        List<Match> orderedFootballMatches = scoreBoard.shouldOrderedMatches();

        assertFalse(orderedFootballMatches.isEmpty());
        assertEquals(2, orderedFootballMatches.size());
        //1. Spain 4 - Brazil 8
        assertEquals("Spain", orderedFootballMatches.get(0).getHomeTeam().getTeamName());
        assertEquals("Brazil", orderedFootballMatches.get(0).getAwayTeam().getTeamName());
        //2. Mexico 2 - Canada 3
        assertEquals("Mexico", orderedFootballMatches.get(1).getHomeTeam().getTeamName());
        assertEquals("Canada", orderedFootballMatches.get(1).getAwayTeam().getTeamName());
    }

    @Test
    void shouldOrderedMatchesWithSameScore() {
        scoreBoard = new ScoreBoardImpl();
        FootballTeam home = new FootballTeam("Mexico");
        FootballTeam away = new FootballTeam("Canada");
        scoreBoard.shouldAddMatch(home, away);
        scoreBoard.shouldUpdateScore(home, away, 4, 4);

        FootballTeam home2 = new FootballTeam("Spain");
        FootballTeam away2 = new FootballTeam("Brazil");
        scoreBoard.shouldAddMatch(home2, away2);
        scoreBoard.shouldUpdateScore(home2, away2, 4, 4);

        List<Match> orderedFootballMatches = scoreBoard.shouldOrderedMatches();

        assertFalse(orderedFootballMatches.isEmpty());
        //1. Spain 4 - Brazil 4
        assertEquals("Spain", orderedFootballMatches.get(0).getHomeTeam().getTeamName());
        assertEquals("Brazil", orderedFootballMatches.get(0).getAwayTeam().getTeamName());
        //2. Mexico 4 - Canada 4
        assertEquals("Mexico", orderedFootballMatches.get(1).getHomeTeam().getTeamName());
        assertEquals("Canada", orderedFootballMatches.get(1).getAwayTeam().getTeamName());
    }

    @Test
    void shouldThrowErrorWhenNoMatchesToOrder() {
        scoreBoard = new ScoreBoardImpl();
        GeneralException exception = assertThrows(GeneralException.class, () ->
            scoreBoard.shouldOrderedMatches());
        String expectedErrorMessage = "No Match is progress right now.";
        assertEquals(expectedErrorMessage, exception.getMessage());
    }


}
