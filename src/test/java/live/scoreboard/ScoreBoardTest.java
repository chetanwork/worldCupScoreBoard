package live.scoreboard;

import live.scoreboard.exception.GeneralException;
import live.scoreboard.football.*;
import live.scoreboard.football.impl.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class ScoreBoardTest {

    public ScoreBoard scoreBoard;

    @BeforeEach
    void setUp() {
        scoreBoard = new ScoreBoardImpl();
    }

    @Test
    @DisplayName("Test when scoreboard is empty or having no in progress match")
    void checkScoreBoard() {
        //then
        assertTrue(scoreBoard.getMatches().isEmpty());
        //given
        FootballTeam homeTeam = new FootballTeam("Uruguay");
        FootballTeam awayTeam = new FootballTeam("Italy");
        //when
        scoreBoard.shouldAddMatch(homeTeam, awayTeam);
        scoreBoard.shouldRemoveMatch(homeTeam, awayTeam);
        //then
        GeneralException exception = assertThrows(GeneralException.class, () -> scoreBoard.checkScoreBoard());
        String expectedErrorMessage = "No Match is in progress currently, please check later";
        assertEquals(expectedErrorMessage, exception.getMessage());
    }

    @Test
    @DisplayName("New Match should be added to the scoreboard")
    void shouldAddMatch() {
        //given
        FootballTeam homeTeam = new FootballTeam("Uruguay");
        FootballTeam awayTeam = new FootballTeam("Italy");
        //when
        scoreBoard.shouldAddMatch(homeTeam, awayTeam);
        //then
        assertFalse(scoreBoard.getMatches().isEmpty());
    }

    @Test
    @DisplayName("Check if the correct team is added to match and displayed on scoreboard")
    void checkNameOfFootballTeam() {
        //given
        FootballTeam homeTeam = new FootballTeam("Mexico");
        FootballTeam awayTeam = new FootballTeam("Canada");
        //when
        scoreBoard.shouldAddMatch(homeTeam, awayTeam);
        //then
        assertTrue(scoreBoard.getMatches().stream()
                .anyMatch(match -> "Mexico".equals(match.getHomeTeam().getTeamName())) );
        assertTrue(scoreBoard.getMatches().stream()
                .anyMatch(match -> "Canada".equals(match.getAwayTeam().getTeamName())) );
    }

    @Test
    @DisplayName("Match will not be added if any of the team is already playing any match")
    void notAddSameMatchAgainWhenInProgress() {
        //given
        FootballTeam homeTeam = new FootballTeam("Mexico");
        FootballTeam awayTeam = new FootballTeam("Canada");
        FootballTeam homeTeam1 = new FootballTeam("Delhi");
        FootballTeam awayTeam1 = new FootballTeam("Canada");
        //when
        scoreBoard.shouldAddMatch(homeTeam, awayTeam);
        //then
        GeneralException exception = assertThrows(GeneralException.class, () ->
                scoreBoard.shouldAddMatch(homeTeam1, awayTeam1));
        String expectedErrorMessage = "Same Match cannot be added again";
        assertEquals(expectedErrorMessage, exception.getMessage());
    }

    @Test
    @DisplayName("Remove the match from scoreboard which not in progress")
    void shouldRemoveMatch() {
        //given
        FootballTeam homeTeam = new FootballTeam("Mexico");
        FootballTeam awayTeam = new FootballTeam("Canada");
        FootballTeam homeTeam1 = new FootballTeam("Spain");
        FootballTeam awayTeam1= new FootballTeam("Brazil");
        //when
        scoreBoard.shouldAddMatch(homeTeam, awayTeam);
        scoreBoard.shouldAddMatch(homeTeam1, awayTeam1);
        scoreBoard.shouldRemoveMatch(homeTeam, awayTeam);
        //then
        assertTrue(scoreBoard.getMatches().stream()
            .filter(Match::isInProgress)
            .anyMatch(match -> "Spain".equals(match.getHomeTeam().getTeamName())));

        assertTrue(scoreBoard.getMatches().stream()
            .filter(Match::isInProgress)
            .anyMatch(match -> "Brazil".equals(match.getAwayTeam().getTeamName())));

        assertTrue(scoreBoard.getMatches().stream().filter(Match::isInProgress).count() == 1);
    }

    @Test
    @DisplayName("Only in-progress matches can be removed from the scoreboard")
    void shouldThrowErrorToRemoveInActiveMatch() {
        //given
        FootballTeam homeTeam = new FootballTeam("Mexico");
        FootballTeam awayTeam = new FootballTeam("Canada");
        FootballTeam homeTeam1 = new FootballTeam("Spain");
        FootballTeam awayTeam1= new FootballTeam("Brazil");
        //when
        scoreBoard.shouldAddMatch(homeTeam, awayTeam);
        scoreBoard.shouldAddMatch(homeTeam1, awayTeam1);
        Match setDeActive = scoreBoard.getMatches().get(1);
        setDeActive.setInProgress(false);
        //then
        GeneralException exception = assertThrows(GeneralException.class, () ->
            scoreBoard.shouldRemoveMatch(homeTeam1, awayTeam1));
        String expectedErrorMessage = "Match Not found to be removed from ScoreBoard";
        assertEquals(expectedErrorMessage, exception.getMessage());
    }

    @Test
    @DisplayName("check default score is set to zero when match is added to scoreboard")
    void checkDefaultScoreAsZero() {
        //given
        FootballTeam homeTeam = new FootballTeam("Mexico");
        FootballTeam awayTeam = new FootballTeam("Canada");
        //when
        scoreBoard.shouldAddMatch(homeTeam, awayTeam);
        //then
        assertEquals(0, homeTeam.getScore());
        assertEquals(0, homeTeam.getScore());
    }

    @Test
    @DisplayName("Match score should not be updated with negative score")
    void shouldNotUpdateMatchScoreWithNegative() {
        //given
        FootballTeam homeTeam = new FootballTeam("Mexico");
        FootballTeam awayTeam = new FootballTeam("Canada");
        //when
        scoreBoard.shouldAddMatch(homeTeam, awayTeam);
        //then
        GeneralException exception = assertThrows(GeneralException.class, () ->
            scoreBoard.shouldUpdateScore(homeTeam, awayTeam, -2, 1));
        String expectedErrorMessage = "Negative Score cannot be update";
        assertEquals(expectedErrorMessage, exception.getMessage());
    }

    @Test
    @DisplayName("check if correct score has been updated on scoreboard")
    void shouldUpdateCorrectMatchScore() {
        //given
        FootballTeam homeTeam = new FootballTeam("Mexico");
        FootballTeam awayTeam = new FootballTeam("Canada");
        //when
        scoreBoard.shouldAddMatch(homeTeam, awayTeam);
        scoreBoard.shouldUpdateScore(homeTeam, awayTeam, 3, 4);
        //then
        assertEquals(3, homeTeam.getScore());
        assertEquals(4, awayTeam.getScore());
    }

    @Test
    @DisplayName("Match score should be updated only for in-progress matches")
    void throwErrorWhenUpdateMatchIsNotFound() {
        //given
        FootballTeam homeTeam = new FootballTeam("Mexico");
        FootballTeam awayTeam = new FootballTeam("Canada");
        FootballTeam homeTeam1 = new FootballTeam("Germany");
        FootballTeam awayTeam1 = new FootballTeam("France");
        //when
        scoreBoard.shouldAddMatch(homeTeam, awayTeam);
        scoreBoard.shouldUpdateScore(homeTeam, awayTeam, 1,0);
        //then
        GeneralException exception = assertThrows(GeneralException.class, () ->
            scoreBoard.shouldUpdateScore(homeTeam1, awayTeam1, 1,2));
        String expectedErrorMessage = "Match Not Found To Update Score";
        assertEquals(expectedErrorMessage, exception.getMessage());
    }

    @Test
    @DisplayName("Match score should be displayed according to high score and start time of in progress matches")
    void shouldOrderedMatchesTimeAndScoreReturn() {
        //given
        List<Match> orderedFootballMatches = addOngoingMatchesToScoreBoard();
        //then
        assertFalse(orderedFootballMatches.isEmpty());
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
    @DisplayName("Only in-progress match score should be displayed according to high score and start time")
    void shouldOrderedMatchesTInProgressOnly() {
        //given
        List<Match> orderedFootballMatches = orderOngoingFootballMatches();
        //then
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
    @DisplayName("display same match Score of different teams order by start time")
    void shouldOrderedMatchesWithSameScore() {
        //given
        List<Match> orderedFootballMatches = orderMatchWithSameScore();
        //then
        assertFalse(orderedFootballMatches.isEmpty());
        //1. Spain 4 - Brazil 4
        assertEquals("Spain", orderedFootballMatches.get(0).getHomeTeam().getTeamName());
        assertEquals("Brazil", orderedFootballMatches.get(0).getAwayTeam().getTeamName());
        //2. Mexico 4 - Canada 4
        assertEquals("Mexico", orderedFootballMatches.get(1).getHomeTeam().getTeamName());
        assertEquals("Canada", orderedFootballMatches.get(1).getAwayTeam().getTeamName());
    }

    @Test
    @DisplayName("display message when no match is in progress and asked for order score for matches")
    void shouldThrowErrorWhenNoMatchesToOrder() {
        GeneralException exception = assertThrows(GeneralException.class, () ->
            scoreBoard.shouldOrderedMatches());
        String expectedErrorMessage = "No Match is progress right now.";
        assertEquals(expectedErrorMessage, exception.getMessage());
    }

    private List<Match> addOngoingMatchesToScoreBoard() {
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

        //when
        return scoreBoard.shouldOrderedMatches();
    }

    private List<Match> orderOngoingFootballMatches() {
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
        //when
        return scoreBoard.shouldOrderedMatches();
    }

    private List<Match> orderMatchWithSameScore() {
        FootballTeam home = new FootballTeam("Mexico");
        FootballTeam away = new FootballTeam("Canada");
        scoreBoard.shouldAddMatch(home, away);
        scoreBoard.shouldUpdateScore(home, away, 4, 4);

        FootballTeam home2 = new FootballTeam("Spain");
        FootballTeam away2 = new FootballTeam("Brazil");
        scoreBoard.shouldAddMatch(home2, away2);
        scoreBoard.shouldUpdateScore(home2, away2, 4, 4);
        //when
        return scoreBoard.shouldOrderedMatches();
    }

}