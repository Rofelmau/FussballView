package Model;

public class Match {
    private int matchId;
    private boolean isFinished;
    private Team teamHome;
    private Team teamGuest;

    public Match(int matchId, boolean isFinished, Team teamHome, Team teamGuest) {
        this.matchId = matchId;
        this.isFinished = isFinished;
        this.teamHome = teamHome;
        this.teamGuest = teamGuest;
    }

    public Team getTeamGuest() {
        return teamGuest;
    }

    public Team getTeamHome() {
        return teamHome;
    }
}
