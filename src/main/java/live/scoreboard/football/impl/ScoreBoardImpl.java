package live.scoreboard.football.impl;

import live.scoreboard.exception.GeneralException;
import live.scoreboard.football.FootballTeam;
import live.scoreboard.football.Match;
import live.scoreboard.football.ScoreBoard;

import java.util.*;

public class ScoreBoardImpl implements ScoreBoard {

    private final List<Match> matches = new ArrayList();

    /**
     * check if the scoreboard is empty or not and return the message
     */
    @Override
    public void checkScoreBoard() {
        if(matches.isEmpty() || !matches.stream().anyMatch(matches -> matches.isInProgress())) {
            throw new GeneralException("No Match is in progress currently, please check later");
        }
    }

    /**
     * This method add the match to scoreboard.
     *
     * @param homeTeam Team one going to play match
     * @param awayTeam Team two going to play match
     */
    @Override
    public void shouldAddMatch(FootballTeam homeTeam, FootballTeam awayTeam) throws GeneralException {
        Match match = new Match(homeTeam, awayTeam);
        if(isExistingMatch(match)) {
            throw new GeneralException("Same Match cannot be added again");
        }
        matches.add(new Match(homeTeam, awayTeam));
    }

    private boolean isExistingMatch(Match newMatch) {
        return matches.stream()
            .filter(Match::isInProgress)
            .anyMatch(existingMatch ->
            existingMatch.getHomeTeam().getTeamName().equals(newMatch.getHomeTeam().getTeamName()) ||
            existingMatch.getAwayTeam().getTeamName().equals(newMatch.getAwayTeam().getTeamName()));
    }

    /**
     * Remove the match from scoreboard while changing the status.
     *
     * @param homeTeam Team one going to play match
     * @param awayTeam Team two going to play match
     */
    @Override
    public void shouldRemoveMatch(FootballTeam homeTeam, FootballTeam awayTeam) {
        Match checkToRemoveMatch = new Match(homeTeam, awayTeam);
        Optional<Match> checkAndRemoveMatch = matches.stream()
            .filter(Match::isInProgress)
            .filter(existingMatch ->
              existingMatch.getHomeTeam().getTeamName().equals(checkToRemoveMatch.getHomeTeam().getTeamName()) &&
              existingMatch.getAwayTeam().getTeamName().equals(checkToRemoveMatch.getAwayTeam().getTeamName()))
              .findFirst();
        if(checkAndRemoveMatch.isPresent()) {
            Match toBeRemoved = checkAndRemoveMatch.get();
            toBeRemoved.setInProgress(false);
        } else {
            throw new GeneralException("Match Not found to be removed from ScoreBoard");
        }
    }

    /**
     * Update the score of the given teams.
     *
     * @param homeTeam Team one going to play match
     * @param awayTeam Team two going to play match
     * @param homeTeamScore hometeam current score needs to be updated
     * @param awayTeamScore awayteam current score needs to be updated
     */
    @Override
    public void shouldUpdateScore(FootballTeam homeTeam, FootballTeam awayTeam,
                                  int homeTeamScore, int awayTeamScore) {
        checkForNegativeScore(homeTeamScore, awayTeamScore);
        Optional<Match> scoreUpdate = matches.stream()
            .filter(Match::isInProgress)
            .filter(existingMatch ->
                existingMatch.getHomeTeam().getTeamName().equals(homeTeam.getTeamName()) &&
                existingMatch.getAwayTeam().getTeamName().equals(awayTeam.getTeamName()))
                .findFirst();
        if(scoreUpdate.isEmpty()) {
            throw new GeneralException("Match Not Found To Update Score");
        }
        Match getMatch = scoreUpdate.get();
        FootballTeam home = getMatch.getHomeTeam();
        FootballTeam away = getMatch.getAwayTeam();
        home.setScore(getMatch.getHomeTeam().getScore() + homeTeamScore);
        away.setScore(getMatch.getAwayTeam().getScore() + awayTeamScore);
    }

    private void checkForNegativeScore(int homeTeamScore, int awayTeamScore) {
        if(homeTeamScore < 0 || awayTeamScore < 0 ) {
            throw new GeneralException("Negative Score cannot be update");
        }
    }

    /**
     * Order the ongoing matches according to score and start time of the match.
     *
     */
    @Override
    public List<Match> shouldOrderedMatches() {
        if(!matches.isEmpty() && matches.stream().anyMatch(matches -> matches.isInProgress())) {
            return matches.stream()
                .filter(Match::isInProgress)
                .sorted(Comparator.comparing(Match::getScore)
                    .thenComparing(Match::getStartTimeOfMatch).reversed()).toList();
        } else {
            throw new GeneralException("No Match is progress right now.");
        }
    }

    /**
     * Return the matches.
     *
     */
    public List<Match> getMatches() {
        return matches;
    }
}
