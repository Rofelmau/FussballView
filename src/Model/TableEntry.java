package Model;

import com.google.gson.annotations.SerializedName;

public class TableEntry {

    @SerializedName("Draw")
    private int draw;
    @SerializedName("Goals")
    private int goals;
    @SerializedName("Lost")
    private int lost;
    @SerializedName("Matches")
    private int matches;
    @SerializedName("OpponentGoals")
    private int opponentGoals;
    @SerializedName("Points")
    private int points;
    @SerializedName("Won")
    private int won;
    private Team team;
    private int position;

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }


    public int getPoints() {
        return points;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getDraw() {
        return draw;
    }

    public void setDraw(int draw) {
        this.draw = draw;
    }

    public int getGoals() {
        return goals;
    }

    public void setGoals(int goals) {
        this.goals = goals;
    }

    public int getLost() {
        return lost;
    }

    public void setLost(int lost) {
        this.lost = lost;
    }

    public int getMatches() {
        return matches;
    }

    public void setMatches(int matches) {
        this.matches = matches;
    }

    public int getOpponentGoals() {
        return opponentGoals;
    }

    public void setOpponentGoals(int opponentGoals) {
        this.opponentGoals = opponentGoals;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getWon() {
        return won;
    }

    public void setWon(int won) {
        this.won = won;
    }
}
