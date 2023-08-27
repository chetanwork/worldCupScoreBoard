package live.scoreboard.football;

public class FootballTeam {

    private final String teamName;

    private int score;

    public FootballTeam(String teamName) {
        this.teamName = teamName;
        this.score = 0;
    }

    public String getTeamName() {
        return teamName;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

}
