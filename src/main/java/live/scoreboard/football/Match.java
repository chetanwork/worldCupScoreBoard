package live.scoreboard.football;

public class Match {

    private final FootballTeam homeTeam;

    private final FootballTeam awayTeam;

    public Match(FootballTeam homeTeam, FootballTeam awayTeam) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
    }

    public FootballTeam getHomeTeam() {
        return homeTeam;
    }

    public FootballTeam getAwayTeam() {
        return awayTeam;
    }



}
