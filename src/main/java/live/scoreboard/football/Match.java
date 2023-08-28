package live.scoreboard.football;
import java.time.LocalDateTime;

public class Match {

    private final FootballTeam homeTeam;

    private final FootballTeam awayTeam;

    private boolean isInProgress;

    private LocalDateTime startTimeOfMatch;

    public Match(FootballTeam homeTeam, FootballTeam awayTeam) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.isInProgress = true;
        this.startTimeOfMatch = LocalDateTime.now();
    }

    public FootballTeam getHomeTeam() {
        return homeTeam;
    }

    public FootballTeam getAwayTeam() {
        return awayTeam;
    }

    public boolean isInProgress() {
        return isInProgress;
    }

    public void setInProgress(boolean inProgress) {
        isInProgress = inProgress;
    }

    public LocalDateTime getStartTimeOfMatch() {
        return startTimeOfMatch;
    }

    public int getScore() {
        return homeTeam.getScore() + awayTeam.getScore();
    }
}
