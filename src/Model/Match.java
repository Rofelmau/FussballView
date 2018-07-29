package Model;

public class Match {
    private int matchId;
    private boolean isFinished;
    private Team teamHome;
    private Team teamGuest;
    private int goalTeam1;
    private int goalTeam2;

    public Match(int matchId, boolean isFinished, Team teamHome, Team teamGuest, int goalOne, int goalTwo) {
        this.matchId = matchId;
        this.isFinished = isFinished;
        this.teamHome = teamHome;
        this.teamGuest = teamGuest;
        this.goalTeam1 = goalOne;
        this.goalTeam2 = goalTwo;
    }

    public Team getTeamGuest() {
        return teamGuest;
    }

    public Team getTeamHome() {
        return teamHome;
    }

    public int getGoalTeam1(){
        return this.goalTeam1;
    }
    public int getGoalTeam2(){
        return this.goalTeam2;
    }

    public boolean isFinished() {
        return isFinished;
    }
}
